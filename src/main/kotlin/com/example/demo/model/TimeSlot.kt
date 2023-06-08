package com.example.demo.model

import java.time.LocalTime
import javax.persistence.*

@Entity
public class TimeSlot (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.EAGER) // Fetch association eagerly
    @JoinColumn(name = "plot_id", nullable = false)
    var plot: Plot? = null,
    @Column(name = "start_time", nullable = false)
    var startTime: LocalTime? = null,
    @Column(name = "end_time", nullable = false)
    var endTime: LocalTime? = null,
    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'idle'")
    var status: String? = "idle"
)
