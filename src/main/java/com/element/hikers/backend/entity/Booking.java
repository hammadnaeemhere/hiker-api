package com.element.hikers.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date bookingDate;

    @Column(unique = true, nullable = false)
    private String bookingKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trail_id", nullable = false)
    private Trail trail;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "hiker_booking",
            joinColumns = @JoinColumn(name = "booking_id", referencedColumnName = "bookingId"),
            inverseJoinColumns = @JoinColumn(name = "hiker_id", referencedColumnName = "hikerId"))
    List<Hiker> hikers;

    private Boolean isCancelled = false;
}
