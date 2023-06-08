package com.example.demo.service

import com.example.demo.model.TimeSlot
import com.example.demo.repository.TimeSlotRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException


@Service
class TimeSlotService (private val timeSlotRepository: TimeSlotRepository){


    fun getAllTimeSlots(): List<TimeSlot> =
        timeSlotRepository.findAll()


    fun getById(id: Long): TimeSlot = timeSlotRepository.findByIdOrNull(id) ?:
    throw ResponseStatusException(HttpStatus.NOT_FOUND)

    fun createNewTimeSlot(timeSlot: TimeSlot): TimeSlot =
        timeSlotRepository.save(timeSlot)

    fun configureTimeSlot(timeSlotId: Long, status: String): TimeSlot {
        val timeSlot: TimeSlot = timeSlotRepository.findById(timeSlotId)
            .orElseThrow { RuntimeException("Time slot not found with ID: $timeSlotId") }

        timeSlot.status = status
        return timeSlotRepository.save(timeSlot)
    }

    fun updateTimeSlot(id: Long, timeSlot: TimeSlot): TimeSlot {
        return if (timeSlotRepository.existsById(id)) {
            timeSlot.id= id
            timeSlotRepository.save(timeSlot)
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    fun deleteTimeSlotById(id: Long) {
        if (timeSlotRepository.existsById(id)) timeSlotRepository.deleteById(id)
        else throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }
}