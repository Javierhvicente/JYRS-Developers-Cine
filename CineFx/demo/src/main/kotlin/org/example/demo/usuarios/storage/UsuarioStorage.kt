package org.example.demo.usuarios.storage

import org.example.demo.usuarios.models7.Usuario
import com.github.michaelbull.result.Result
import org.example.demo.usuarios.errors.UserError
import java.io.File

interface UsuarioStorage {
    fun storeJson(file: File, lista: List<Usuario>): Result<Long, UserError>
    fun loadJson(file: File): Result<List<Usuario>, UserError>
}