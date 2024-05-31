package org.example.demo.usuarios.models

import org.example.demo.usuarios.models7.Usuario

/**
 * Representa a un admin.
 * @property id
 * @property nombre
 * @property apellidos
 * @property contraseña
 * @property email
 * @author Raúl Fernández, Javier Hernández, Samuel Cortés, Yahya El Hadri.
 * @since 1.0
 */

class Admin(
    id: Long,
    nombre: String,
    apellidos: String,
    contraseña: String,
    email: String
) : Usuario(id, nombre, apellidos, email, contraseña)