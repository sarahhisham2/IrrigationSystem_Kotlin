package com.example.demo.model

import javax.persistence.*

@Entity
data class Plot(

    // This property maps to the primary key in the database.
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    var area: Double = 0.0,
    var soilType: String = "",
    var cropType: String = "",
    var irrigationThreshold: Double = 0.0,


    )