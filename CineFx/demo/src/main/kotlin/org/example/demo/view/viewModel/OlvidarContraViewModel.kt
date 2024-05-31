package org.example.demo.usuarios.viewModel

import com.github.michaelbull.result.onSuccess
import org.example.demo.usuarios.services.UserService

class OlvidarContraViewModel(
    private val service: UserService
) {
    fun cambiarContraseña(email:String,contraseña:String):Boolean{
        service.cambioContraseña(email,contraseña).onSuccess {
            return true
        }
        return false
    }
}