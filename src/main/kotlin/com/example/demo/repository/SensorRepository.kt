package com.example.demo.repository

import com.example.demo.model.Sensor
import org.springframework.data.jpa.repository.JpaRepository

interface SensorRepository: JpaRepository<Sensor,Long>