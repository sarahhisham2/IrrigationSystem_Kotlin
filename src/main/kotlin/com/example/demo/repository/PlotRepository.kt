package com.example.demo.repository

import com.example.demo.model.Plot
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlotRepository : JpaRepository<Plot, Long>