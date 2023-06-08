package com.example.demo.service

import com.example.demo.model.Sensor
import com.example.demo.repository.SensorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class SensorService @Autowired constructor(private val sensorRepository: SensorRepository) {

    fun configureSensor(id: Long): Boolean {
        val optionalSensor: Optional<Sensor> = sensorRepository.findById(id)
        return if (optionalSensor.isPresent) {
            optionalSensor.get().irrigationEnabled
        } else {
            throw IllegalArgumentException("Sensor not found with ID: $id")
        }
        // Simulate the configuration of the sensor device
    }

    fun addNewSensor(sensor: Sensor): Sensor {
        return sensorRepository.save(sensor)
    }
}