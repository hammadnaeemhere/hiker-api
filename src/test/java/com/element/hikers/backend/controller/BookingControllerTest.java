package com.element.hikers.backend.controller;

import com.element.hikers.backend.dto.BookingDto;
import com.element.hikers.backend.dto.HikerDto;
import com.element.hikers.backend.dto.TrailDto;
import com.element.hikers.backend.entity.Booking;
import com.element.hikers.backend.entity.Trail;
import com.element.hikers.backend.repository.BookingRepository;
import com.element.hikers.backend.repository.TrailRepository;
import com.element.hikers.backend.service.BookingService;
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
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(properties = {"element.admin.username=admin", "element.admin.password=admin"})
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private TrailRepository trailRepository;

    @MockBean
    private BookingRepository bookingRepository;


    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);


    @Test
    @DisplayName("Test create booking success scenario")
    void testCreateBooking() throws Exception {
        Trail trail = new Trail(1L, "Trail1", "09:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");
        HikerDto hiker = new HikerDto(1L, "Hiker", "One", 30, "03468328336", "hiker1@gmail.com");
        BookingDto booking = new BookingDto();
        booking.setBookingId(1L);
        booking.setBookingKey("123-234");
        booking.setTrailId(1L);
        booking.setBookingDate(sdf.parse("2022-01-01"));
        booking.setHikers(Collections.singletonList(hiker));

        given(bookingService.save(any(BookingDto.class))).willReturn(booking);
        given(trailRepository.findById(1L)).willReturn(Optional.of(trail));

        String expectedJsonResponse = "{\"message\":\"Your booking has been successful. Please remember booking id and booking key in order to cancel the booking.\",\"body\":{\"bookingId\":1,\"bookingKey\":\"123-234\",\"trailId\":1,\"hikers\":[{\"hikerId\":1,\"firstName\":\"Hiker\",\"lastName\":\"One\",\"age\":30,\"mobileNo\":\"03468328336\",\"email\":\"hiker1@gmail.com\"}]}}";

        this.mockMvc.perform(post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(booking)))
                .andExpect(status().is(201))
                .andExpect(content().json(expectedJsonResponse));
    }

    @Test
    @DisplayName("Test create booking invalid trail id scenario")
    void testCreateBookingInvalidTrailId() throws Exception {
        HikerDto hiker = new HikerDto(1L, "Hiker", "One", 30, "03468328336", "hiker1@gmail.com");

        BookingDto booking = new BookingDto();
        booking.setBookingId(1L);
        booking.setBookingKey("123-234");
        booking.setTrailId(1L);
        booking.setBookingDate(sdf.parse("2022-01-01"));
        booking.setHikers(Collections.singletonList(hiker));

        given(bookingService.save(any(BookingDto.class))).willReturn(booking);
        given(trailRepository.findById(1L)).willReturn(Optional.empty()); //return empty trail here

        String expectedJsonResponse = "{\"message\":\"trailId - Invalid trail id provided\"}";

        this.mockMvc.perform(post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(booking)))
                .andExpect(status().is(400))
                .andExpect(content().json(expectedJsonResponse));
    }

    @Test
    @DisplayName("Test create booking missing hikers")
    void testCreateBookingMissingHikers() throws Exception {
        Trail trail = new Trail(1L, "Trail1", "09:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");
        BookingDto booking = new BookingDto();
        booking.setBookingId(1L);
        booking.setBookingKey("123-234");
        booking.setTrailId(1L);
        booking.setBookingDate(sdf.parse("2022-01-01"));
        booking.setHikers(Collections.emptyList()); //Set empty list here

        given(bookingService.save(any(BookingDto.class))).willReturn(booking);
        given(trailRepository.findById(1L)).willReturn(Optional.of(trail));

        String expectedJsonResponse = "{\"message\":\"hikers - Please provide hiker details\"}";

        this.mockMvc.perform(post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(booking)))
                .andExpect(status().is(400))
                .andExpect(content().json(expectedJsonResponse));
    }

    @Test
    @DisplayName("Test cancel booking success hikers")
    void testCancelBookingSuccess() throws Exception {
        TrailDto trail = new TrailDto(1L, "Trail1", "09:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");
        HikerDto hiker = new HikerDto(1L, "Hiker", "One", 30, "03468328336", "hiker1@gmail.com");

        BookingDto booking = new BookingDto();
        booking.setBookingId(1L);
        booking.setBookingKey("123-234");
        booking.setTrailId(1L);
        booking.setTrail(trail);
        booking.setBookingDate(sdf.parse("2022-01-01"));
        booking.setHikers(Collections.singletonList(hiker));

        given(bookingService.cancel(1L, "123-234")).willReturn(booking);
        given(bookingRepository.findById(1L)).willReturn(Optional.of(new Booking()));

        String expectedJsonResponse = "{\"message\":\"Your booking has been cancelled.\",\"body\":{\"bookingId\":1,\"bookingKey\":\"123-234\",\"trailId\":1,\"trail\":{\"trailId\":1,\"name\":\"Trail1\",\"startTime\":\"09:00\",\"endTime\":\"11:00\",\"minAge\":10,\"maxAge\":50,\"ticketPrice\":10.0,\"currency\":\"EUR\"},\"hikers\":[{\"hikerId\":1,\"firstName\":\"Hiker\",\"lastName\":\"One\",\"age\":30,\"mobileNo\":\"03468328336\",\"email\":\"hiker1@gmail.com\"}]}}";

        this.mockMvc.perform(put("/bookings/1/cancel")
                .param("bookingKey", "123-234")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonResponse));
    }

    @Test
    @DisplayName("Test cancel booking for invalid booking id")
    void testCancelBookingInvalidBookingId() throws Exception {
        TrailDto trail = new TrailDto(1L, "Trail1", "09:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");
        HikerDto hiker = new HikerDto(1L, "Hiker", "One", 30, "03468328336", "hiker1@gmail.com");

        BookingDto booking = new BookingDto();
        booking.setBookingId(1L);
        booking.setBookingKey("123-234");
        booking.setTrailId(1L);
        booking.setTrail(trail);
        booking.setBookingDate(sdf.parse("2022-01-01"));
        booking.setHikers(Collections.singletonList(hiker));

        given(bookingRepository.findById(1L)).willReturn(Optional.empty()); //return empty booking here

        String expectedJsonResponse = "{\"message\":\"No booking exists with provided booking id\"}";

        this.mockMvc.perform(put("/bookings/1/cancel")
                .param("bookingKey", "123-234")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(content().json(expectedJsonResponse));
    }

    @Test
    @DisplayName("Test cancel booking if booking key is missing")
    void testCancelBookingMissingBookingKey() throws Exception {
        TrailDto trail = new TrailDto(1L, "Trail1", "09:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");
        HikerDto hiker = new HikerDto(1L, "Hiker", "One", 30, "03468328336", "hiker1@gmail.com");

        BookingDto booking = new BookingDto();
        booking.setBookingId(1L);
        booking.setBookingKey("123-234");
        booking.setTrailId(1L);
        booking.setTrail(trail);
        booking.setBookingDate(sdf.parse("2022-01-01"));
        booking.setHikers(Collections.singletonList(hiker));

        given(bookingRepository.findById(1L)).willReturn(Optional.of(new Booking())); //return empty booking here

        String expectedJsonResponse = "{\"message\":\"bookingKey query parameter is missing.\"}";

        this.mockMvc.perform(put("/bookings/1/cancel")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(content().json(expectedJsonResponse));
    }


    @Test
    @DisplayName("Test get all booking without authorization header")
    void testGetAllBookingNoAuthentication() throws Exception {
        TrailDto trail = new TrailDto(1L, "Trail1", "09:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");
        HikerDto hiker = new HikerDto(1L, "Hiker", "One", 30, "03468328336", "hiker1@gmail.com");

        BookingDto booking = new BookingDto();
        booking.setBookingId(1L);
        booking.setBookingKey("123-234");
        booking.setTrailId(1L);
        booking.setTrail(trail);
        booking.setBookingDate(sdf.parse("2022-01-01"));
        booking.setHikers(Collections.singletonList(hiker));

        given(bookingService.getAll(null, null, null, null, null)).willReturn(Collections.singletonList(booking));

        this.mockMvc.perform(get("/auth/bookings")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

    @Test
    @DisplayName("Test get all booking with authorization header")
    void testGetAllBookingWithAuthentication() throws Exception {
        TrailDto trail = new TrailDto(1L, "Trail1", "09:00", "11:00", 10, 50, BigDecimal.valueOf(10.00), "EUR");
        HikerDto hiker = new HikerDto(1L, "Hiker", "One", 30, "03468328336", "hiker1@gmail.com");

        BookingDto booking = new BookingDto();
        booking.setBookingId(1L);
        booking.setBookingKey("123-234");
        booking.setTrailId(1L);
        booking.setTrail(trail);
        booking.setBookingDate(sdf.parse("2022-01-01"));
        booking.setHikers(Collections.singletonList(hiker));

        given(bookingService.getAll(null, null, null, null, null)).willReturn(Collections.singletonList(booking));

        String expectedJsonResponse = "{\"message\":\"Success!\",\"body\":[{\"bookingId\":1,\"bookingKey\":\"123-234\",\"trailId\":1,\"trail\":{\"trailId\":1,\"name\":\"Trail1\",\"startTime\":\"09:00\",\"endTime\":\"11:00\",\"minAge\":10,\"maxAge\":50,\"ticketPrice\":10.0,\"currency\":\"EUR\"},\"hikers\":[{\"hikerId\":1,\"firstName\":\"Hiker\",\"lastName\":\"One\",\"age\":30,\"mobileNo\":\"03468328336\",\"email\":\"hiker1@gmail.com\"}]}]}";
        this.mockMvc.perform(get("/auth/bookings")
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
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
