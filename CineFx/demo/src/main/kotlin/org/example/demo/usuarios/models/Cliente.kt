package org.example.demo.usuarios.models

import kotlinx.serialization.Serializable
import org.example.demo.usuarios.models7.Usuario

/**
 * Representa a un cliente.
 * @property id
 * @property nombre
 * @property apellidos
 * @property contraseña
 * @property email
 * @author Javier Hernández, Yahya El Hadri, Samuel Cortés, Raúl Fernández
 * @since 1.0
 */

class Cliente(
    id: Long,
    nombre: String,
    apellidos: String,
    contraseña: String,
    email: String
) : Usuario(id, nombre, apellidos, email, contraseña)
