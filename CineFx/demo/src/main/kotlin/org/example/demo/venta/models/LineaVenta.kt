package org.example.demo.venta.models


import org.example.demo.productos.models.Producto
import java.time.LocalDateTime
import java.util.*

/**
 * Clase de linea de venta.
 * @property id
 * @property producto
 * @property cantidad
 * @property precio
 * @property createdAt
 * @property updatedAt
 * @since 1.0
 * @author Raúl Fernández, Yahya El Hadri, Samuel Cortés, Javier Hernández.
 */

data class LineaVenta(
    val id: UUID = UUID.randomUUID(),
    val producto: Producto,
    val cantidad: Int,
    val precio: Double,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)