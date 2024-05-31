package org.example.demo.productos.butaca.dto

import kotlinx.serialization.Serializable
import org.example.demo.productos.dto.ProductoDto

/**
 * Clase Dto de butaca.
 * @property id
 * @property estado
 * @property precio
 * @property tipo
 * @property ocupacion
 * @property createAt
 * @author Raúl Fernández, Yahya El Hadri, Samuel Cortés, Javier Hernández
 * @since 1.0
 */

@Serializable
class ButacaDto(
    val id:String,
    val estado: String,
    val tipo: String,
    val precio:String,
    var ocupacion: String,
    var createAt: String,
):ProductoDto()