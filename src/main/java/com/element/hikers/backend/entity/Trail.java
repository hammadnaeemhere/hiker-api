package com.element.hikers.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "trail")
public class Trail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trailId;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String startTime;
    @Column(nullable = false)
    private String endTime;
    @Column(nullable = false)
    private Integer minAge;
    @Column(nullable = false)
    private Integer maxAge;
    @Column(nullable = false)
    private BigDecimal ticketPrice;
    @Column(nullable = false)
    private String currency;

}
