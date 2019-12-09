package com.element.hikers.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hiker")
public class Hiker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hikerId;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false)
    private String mobileNo;
    @Column(nullable = false)
    private String email;

}
