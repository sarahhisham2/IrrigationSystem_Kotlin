package com.example.demo.model

import javax.persistence.*

@Entity
class Sensor (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.EAGER) // Fetch association eagerly
    @JoinColumn(name = "plot_id", nullable = false)
    var plot: Plot? = null,

    var irrigationEnabled: Boolean = false,

)