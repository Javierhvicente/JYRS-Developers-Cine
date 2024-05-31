package org.example.demo.usuarios.mappers

import database.UsuariosEntity
import org.example.demo.usuarios.dto.UsuarioDto
import org.example.demo.usuarios.models.Admin
import org.example.demo.usuarios.models.Cliente
import org.example.demo.usuarios.models7.Usuario
import org.lighthousegames.logging.logging


/**
 * Mapea un UsuarioEntity en un estudiante.
 * @return admin, cliente
 * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
 * @since 1.0
 */

val logger= logging()
fun UsuariosEntity.toUsuario(): Usuario {
    logger.debug { "Mapeando UsuarioEntity ${this.nombre} a Usuario" }
    return when(this.tipo){
        "admin" -> Admin(id = id.toLong(), nombre = nombre, apellidos = apellidos, email = email, contraseña = contrasena)
        "cliente" -> Cliente(id = id.toLong(), nombre = nombre, apellidos = apellidos, email = email, contraseña = contrasena)
        else -> throw IllegalArgumentException("Tipo de usuario no soportado")
    }
}

/**
 * Mapeamos un objeto UsuarioDto.
 * @return UsuarioDto
 * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
 * @since 1.0
 */

fun Usuario.toUsuarioDto():UsuarioDto{
    logger.debug { "Mapeando Usuario ${this.nombre} a UsuarioDto" }
    return UsuarioDto(
        id=this.id.toString(),
        nombre = this.nombre,
        apellidos = this.apellidos,
        email = this.email,
        contraseña= this.contraseña,
        tipo = when(this){
            is Admin->"admin"
            is Cliente->"cliente"
            else->""
        }
    )
}


/**
 * Mapeamos un objeto UsuarioDto a un usuario.
 * @return Si el tipo de usuario no es reconocido se lanza una excepción, si el usuario es reconocido la función ha sido exitosa.
 * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
 * @since 1.0
 */

fun UsuarioDto.toUsuario():Usuario{
    logger.debug { "Mapeando UsuarioDto ${this.nombre} a Usuario" }
    return when(this.tipo){
        "admin" -> Admin(id = id.toLong(), nombre = this.nombre, apellidos = this.apellidos, email = email, contraseña = this.contraseña)
        "cliente" -> Cliente(id = id.toLong(), nombre = this.nombre, apellidos = this.apellidos, email = email, contraseña = this.contraseña)
        else -> throw IllegalArgumentException("Tipo de usuario no soportado")
    }
}