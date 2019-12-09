package com.element.hikers.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "hiker_booking")
public class HikerBooking implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hiker_id", nullable = false)
    private Hiker hiker;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;


}
