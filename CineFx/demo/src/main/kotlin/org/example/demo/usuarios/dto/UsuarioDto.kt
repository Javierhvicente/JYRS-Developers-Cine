package org.example.demo.usuarios.dto

import kotlinx.serialization.Serializable

/**
 * Clase Dto de usuario.
 * @property id
 * @property tipo
 * @property nombre
 * @property apellidos
 * @property email
 * @property contraseña
 * @author Raúl Fernández, Yahya El Hadri, Samuel Cortés, Javier Hernández
 * @since 1.0
 */

@Serializable
data class UsuarioDto(
    val id: String,
    val tipo:String,
    val nombre: String,
    val apellidos: String,
    val email: String,
    var contraseña: String
)