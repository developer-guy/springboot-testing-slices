package com.springboot.testing.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.testing.domain.VehicleDetail;
import com.springboot.testing.service.VehicleDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;

@RunWith(SpringRunner.class)
@WebMvcTest
public class VehicleDetailControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private VehicleDetailsService vehicleDetailsService;

    @Test
    public void is_vehicledetails_saved_successfully() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String vehicleDetailsJson = objectMapper.writeValueAsString(new VehicleDetail(null, "Ford", "Focus"));
        //given
        given(this.vehicleDetailsService.save(any())).willReturn(new VehicleDetail(1l, "Ford", "Focus"));
        //when-then
        this.mvc.perform(MockMvcRequestBuilders.post("/vehicles").contentType(MediaType.APPLICATION_JSON).content(vehicleDetailsJson)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.jsonPath("$.make", is("Ford")));
    }

    @Test
    public void is_allvehicledetails_found_succesfully() throws Exception {
        //given
        List<VehicleDetail> vehicleDetails = Arrays.asList(new VehicleDetail(1l, "Ford", "Focus"),
                new VehicleDetail(2l, "Ford", "Transit"));
        given(this.vehicleDetailsService.findAll()).willReturn(vehicleDetails);
        //when-then
        this.mvc.perform(MockMvcRequestBuilders.get("/vehicles")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2))).
                andExpect(MockMvcResultMatchers.jsonPath("$[0].make", is("Ford")));
    }

    @Test
    public void is_vehicledetails_found_sucessfully_by_given_id() throws Exception {
        //given
        VehicleDetail vehicleDetail = new VehicleDetail(1l, "Ford", "Focus");
        given(this.vehicleDetailsService.findById(anyLong())).willReturn(vehicleDetail);
        //when-then
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get("/vehicles/{id}", 1l)).
                andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();
        String location = mvcResult.getResponse().getHeader(HttpHeaders.LOCATION);
        assertThat(location).contains("/vehicles/1/1");
    }

    @Test
    public void when_vehicledetails_not_found_by_given_id() throws Exception {
        //given
        given(this.vehicleDetailsService.findById(anyLong())).willReturn(null);
        //when-then
        this.mvc.perform(MockMvcRequestBuilders.get("/vehicles/{id}", 1l)).
                andExpect(MockMvcResultMatchers.status().is(204));
    }
}
