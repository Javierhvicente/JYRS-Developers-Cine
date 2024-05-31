package org.example.demo.usuarios.services

import com.github.michaelbull.result.Result
import org.example.demo.usuarios.errors.UserError
import org.example.demo.usuarios.models7.Usuario

interface UserService {
    fun save(user: Usuario): Result<Usuario, UserError>
    fun saveFromJson(user: Usuario):Result<Usuario, UserError>
    fun cambioContraseña(email: String, contraseña: String): Result<Usuario, UserError>
    fun findByEmail(email: String): Result<Usuario, UserError>
    fun findById(id: Long): Result<Usuario, UserError>
    fun findAll(): Result<List<Usuario>, UserError>
}