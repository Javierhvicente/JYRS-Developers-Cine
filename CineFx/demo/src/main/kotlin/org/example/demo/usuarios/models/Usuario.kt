package org.example.demo.usuarios.models7

import kotlinx.serialization.Serializable

/**
 * Representa a un admin
 * @property id
 * @property nombre
 * @property apellidos
 * @property contraseña
 * @property email
 * @author Javier Hernández, Yahya El Hadri, Samuel Cortés, Raúl Fernández
 * @since 1.0
 */


abstract class Usuario(
    val id: Long = -1,
    val nombre: String,
    val apellidos: String,
    val email: String,
    var contraseña: String
)