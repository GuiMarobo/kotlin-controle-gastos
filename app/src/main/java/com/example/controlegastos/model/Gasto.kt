package com.example.controlegastos.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gastos")
data class Gasto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val categoria: String,
    val valor: Double,
    val data: Long
)
