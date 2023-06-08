package com.example.demo.controller

import com.example.demo.model.Plot
import com.example.demo.model.Sensor
import com.example.demo.model.TimeSlot
import com.example.demo.service.PlotService
import com.example.demo.service.SensorService
import com.example.demo.service.TimeSlotService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class PlotController(private val plotService: PlotService, private val timeSlotService: TimeSlotService, private val sensorService: SensorService) {

    @GetMapping("/plots")
    fun getAllPlots(): List<Plot> =
        plotService.getAllPlots()

    @PostMapping("/plots")
    fun createNewPlot(@Valid @RequestBody plot: Plot): Plot =
        plotService.createNewPlot(plot)

    @GetMapping("/plots/{id}")
    fun getPlotById(@PathVariable(value = "id") plotId: Long): ResponseEntity<Plot> {
        val plot = plotService.getPlotById(plotId)
        return if (plot.isPresent) {
            ResponseEntity.ok(plot.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/plots/{id}")
    fun updatePlotById(@PathVariable(value = "id") plotId: Long,
                       @Valid @RequestBody newPlot: Plot): ResponseEntity<Plot> {

        val updatedPlot = plotService.updatePlotById(plotId, newPlot)
        return if (updatedPlot.isPresent) {
            ResponseEntity.ok(updatedPlot.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/configure/{plotId}")
    fun configurePlot(
        @PathVariable plotId: Long,
        @RequestParam cropType: String,
        @RequestParam irrigationThreshold: Double,
        @RequestParam timeSlotId: Long,
        @RequestParam sensorId: Long
    ): ResponseEntity<Plot> {
        // Retrieve the plot from the plot service
        val configuredPlot: Plot = plotService.configurePlot(plotId, cropType, irrigationThreshold)

        // Simulate the integration with the sensor by configuring it
        val sensor = Sensor()

        // Retry the sensor configuration for a certain number of times if it's not available
        var retryCount = 0
        val maxRetries = 3
        while (retryCount < maxRetries) {
            try {
                if (sensorService.configureSensor(sensorId)) {
                    // Update the status of the time slot to "active" once the configuration is successfully sent to the sensor
                    val timeSlot: TimeSlot = timeSlotService.configureTimeSlot(timeSlotId, "active")
                    break
                } else {
                    retryCount++
                    println("Sensor configuration failed. Retrying... Attempt: $retryCount")
                }
            } catch (e: Exception) {
                // Handle the exception here if needed
            }
        }

        if (retryCount == maxRetries) {
            // Handle the case when the sensor is not available after maxRetries
            // Implementing the alerting system to notify about the sensor unavailability
            println("Sensor configuration failed after maximum retries. Alerting system triggered.")
        }

        return ResponseEntity.ok(configuredPlot)
    }

    @DeleteMapping("/plots/{id}")
    fun deletePlotById(@PathVariable(value = "id") plotId: Long): ResponseEntity<Void> {
        plotService.deletePlotById(plotId)
        return ResponseEntity.ok().build()
    }
}