package com.element.hikers.backend.controller;

import com.element.hikers.backend.dto.TrailDto;
import com.element.hikers.backend.entity.Trail;
import com.element.hikers.backend.repository.TrailRepository;
import com.element.hikers.backend.service.TrailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(properties = {"element.admin.username=admin", "element.admin.password=admin"})
public class TrailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrailService trailService;

    @MockBean
    private TrailRepository trailRepository;

    @DisplayName("Tests find all trails.")
    @Test
    void findAll() throws Exception {
        TrailDto trail1 = new TrailDto(1L, "Trail1", "09:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");
        TrailDto trail2 = new TrailDto(2L, "Trail2", "13:00", "15:00", 10, 50, BigDecimal.valueOf(15.00), "EUR");

        given(trailService.getAllTrails()).willReturn(Arrays.asList(trail1, trail2));

        String expectedJsonResponse = "[" +
                "{\"trailId\":1,\"name\":\"Trail1\",\"startTime\":\"09:00\",\"endTime\":\"11:00\",\"minAge\":10,\"maxAge\":50,\"ticketPrice\":10.0,\"currency\":\"EUR\"}," +
                "{\"trailId\":2,\"name\":\"Trail2\",\"startTime\":\"13:00\",\"endTime\":\"15:00\",\"minAge\":10,\"maxAge\":50,\"ticketPrice\":15.0,\"currency\":\"EUR\"}" +
                "]";

        this.mockMvc.perform(get("/trails"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonResponse));
    }

    @DisplayName("Tests save new trail.")
    @Test
    void testSaveTrail() throws Exception {
        TrailDto trail = new TrailDto(null, "Trail1", "09:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");

        TrailDto savedTrail = new TrailDto(1L, "Trail1", "09:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");

        given(trailService.save(any(TrailDto.class))).willReturn(savedTrail);

        String expectedJsonResponse = "{\"message\":\"Trail has been created.\",\"body\":{\"trailId\":1,\"name\":\"Trail1\",\"startTime\":\"09:00\",\"endTime\":\"11:00\",\"minAge\":10,\"maxAge\":50,\"ticketPrice\":10.0,\"currency\":\"EUR\"}}";

        this.mockMvc.perform(post("/auth/trails")
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(trail)))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJsonResponse));
    }

    @DisplayName("Tests save new for invalid start time pattern.")
    @Test
    void testSaveTrailInvalidStartTime() throws Exception {
        TrailDto trail = new TrailDto(null, "Trail1", "10000", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");

        String expectedJsonResponse = "{\"message\":\"startTime - Start time must be in HH:MM format\"}";

        this.mockMvc.perform(post("/auth/trails")
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(trail)))
                .andExpect(status().is(400))
                .andExpect(content().json(expectedJsonResponse));
    }

    @DisplayName("Tests update trail.")
    @Test
    void testUpdateTrail() throws Exception {
        TrailDto trail = new TrailDto(1L, "Trail1", "09:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");

        TrailDto savedTrail = new TrailDto(1L, "Trail1", "10:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");

        given(trailRepository.findById(1L)).willReturn(Optional.of(new Trail()));
        given(trailService.update(anyLong(), any(TrailDto.class))).willReturn(savedTrail);

        String expectedJsonResponse = "{\"message\":\"Trail has been updated.\",\"body\":{\"trailId\":1,\"name\":\"Trail1\",\"startTime\":\"10:00\",\"endTime\":\"11:00\",\"minAge\":10,\"maxAge\":50,\"ticketPrice\":10.0,\"currency\":\"EUR\"}}";

        this.mockMvc.perform(put("/auth/trails/1")
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(trail)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(expectedJsonResponse));
    }

    @DisplayName("Tests update trail invalid trail id.")
    @Test
    void testUpdateTrailInvalidId() throws Exception {
        TrailDto trail = new TrailDto(1L, "Trail1", "09:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");

        given(trailRepository.findById(1L)).willReturn(Optional.empty()); //return empty here

        String expectedJsonResponse = "{\"message\":\"Invalid trail id provided\"}";

        this.mockMvc.perform(put("/auth/trails/1")
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(trail)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedJsonResponse));
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
