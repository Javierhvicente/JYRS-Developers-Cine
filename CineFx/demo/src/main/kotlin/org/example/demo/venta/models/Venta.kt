package org.example.demo.venta.models

import org.example.demo.usuarios.models7.Usuario
import java.time.LocalDateTime
import java.util.*

/**
 * Clase de venta.
 * @property id
 * @property cliente
 * @property lineas
 * @property createdAt
 * @property updatedAt
 * @since 1.0
 * @author Raúl Fernández, Yahya El Hadri, Samuel Cortés, Javier Hernández.
 */


data class Venta(
    val id: UUID = UUID.randomUUID(),
    val cliente: Usuario,
    val lineas: List<LineaVenta>,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    val total: Double
        get() = lineas.sumOf { it.precio  }

    override fun toString(): String {
        return "Venta(id=$id, cliente=$cliente, lineas=$lineas, createdAt=$createdAt, updated=$updatedAt, total=$total)"
    }
}