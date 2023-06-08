package com.example.demo.controller

import com.example.demo.model.TimeSlot
import com.example.demo.service.TimeSlotService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/api")
class TimeSlotController(private val timeSlotService: TimeSlotService) {


    @GetMapping("/time-slots")
    fun getAllTimeSlots() = timeSlotService.getAllTimeSlots()

    @GetMapping("time-slots/{id}")
    fun getTimeSlot(@PathVariable id: Long) = timeSlotService.getById(id)

    @PostMapping("/time-slots")
    @ResponseStatus(HttpStatus.CREATED)
    fun createNewTimeSlot(@Valid @RequestBody timeSlot: TimeSlot): TimeSlot = timeSlotService.createNewTimeSlot(timeSlot)

    @PostMapping("/configure/{timeSlotId}")
    fun configureTimeSlot(@PathVariable timeSlotId: Long, @RequestParam status: String): ResponseEntity<TimeSlot> {
        val configuredTimeSlot: TimeSlot = timeSlotService.configureTimeSlot(timeSlotId, status)
        return ResponseEntity.ok(configuredTimeSlot)
    }

    @PutMapping("/{id}")
    fun updatePlayer(
        @PathVariable id: Long, @RequestBody timeSlot: TimeSlot
    ) = timeSlotService.updateTimeSlot(id, timeSlot)


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTimeSlot(@PathVariable id: Long) = timeSlotService.deleteTimeSlotById(id)

}