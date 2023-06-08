package com.example.demo.service

import com.example.demo.model.Plot
import com.example.demo.repository.PlotRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class PlotService(private val plotRepository: PlotRepository) {

    fun getAllPlots(): List<Plot> =
        plotRepository.findAll()

    fun createNewPlot(plot: Plot): Plot =
        plotRepository.save(plot)

    fun getPlotById(plotId: Long): Optional<Plot> =
        plotRepository.findById(plotId)



    fun updatePlotById(plotId: Long, newPlot: Plot): Optional<Plot> =
        plotRepository.findById(plotId).map { existingPlot ->
            val updatedPlot = existingPlot.copy(
                area = newPlot.area,
                soilType = newPlot.soilType,
                cropType = newPlot.cropType,
                irrigationThreshold = newPlot.irrigationThreshold
            )
            plotRepository.save(updatedPlot)
        }
    fun configurePlot(plotId: Long, cropType: String, irrigationThreshold: Double): Plot {
        val optionalPlot: Optional<Plot> = plotRepository.findById(plotId)
        return if (optionalPlot.isPresent) {
            val plot: Plot = optionalPlot.get()
            plot.cropType = cropType
            plot.irrigationThreshold = irrigationThreshold
            plotRepository.save(plot)
        } else {
            throw IllegalArgumentException("Plot not found with ID: $plotId")
        }
    }
    fun deletePlotById(plotId: Long) {
        plotRepository.findById(plotId).ifPresent { plot ->
            plotRepository.delete(plot)
        }
    }
}