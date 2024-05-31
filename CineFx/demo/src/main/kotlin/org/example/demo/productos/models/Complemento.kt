package org.example.demo.productos.models

import kotlinx.serialization.Serializable

/**
 * Representa un complemento.
 * @property id
 * @author Raúl Fernández, Javier Hernández, Samuel Cortés, Yahya El Hadri.
 * @since 1.0
 */

abstract class Complemento(
    val id:String,
    val tipo: String,
    val precio:Double,
    val imagen: String
):Producto()