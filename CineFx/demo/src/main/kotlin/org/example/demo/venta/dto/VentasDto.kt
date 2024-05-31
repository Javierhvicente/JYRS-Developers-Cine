package org.example.demo.venta.dto

import kotlinx.serialization.Serializable
import org.example.demo.productos.dto.ProductoDto
import org.example.demo.usuarios.dto.UsuarioDto

/**
 * Clase Dto de venta
 * @property id
 * @property cliente
 * @property lineas
 * @property total
 * @property createdAt
 * @property updatedAt
 * @author Raúl Fernández, Yahya El Hadri, Samuel Cortés, Javier Hernández
 * @since 1.0
 */

@Serializable
data class VentaDto(
    val id: String,
    val cliente: UsuarioDto,
    val lineas: List<LineaVentaDto>,
    val total:String,
    val createdAt: String,
    val updatedAt: String
)

/**
 * Clase Dto de LineaVenta.
 * @property id
 * @property producto
 * @property cantidad
 * @property precio
 * @property createdAt
 * @property updatedAt
 * @author Raúl Fernández, Yahya El Hadri, Samuel Cortés, Javier Hernández
 * @since 1.0
 */

@Serializable
data class LineaVentaDto(
    val id: String,
    val producto: ProductoDto,
    val cantidad: String,
    val precio: String,
    val createdAt: String,
    val updatedAt: String
)