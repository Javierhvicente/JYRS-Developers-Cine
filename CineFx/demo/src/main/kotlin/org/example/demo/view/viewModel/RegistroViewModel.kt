package org.example.demo.usuarios.viewModel

import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.onSuccess
import org.example.demo.usuarios.models.Cliente
import org.example.demo.usuarios.models7.Usuario
import org.example.demo.usuarios.services.UserService
import org.example.demo.usuarios.validator.*

class RegistroViewModel(
    private val service: UserService
) {
    fun darAltaCliente(nombre:String,apellidos:String, email:String, contraseña:String){
        val id= (0..100000000000).random()
        service.save(Cliente(id=id,nombre=nombre, apellidos = apellidos, email = email, contraseña = contraseña))
    }
    fun validarUsuario(nombre:String,apellidos:String, email:String, contraseña:String):Boolean{
        validateUser(Cliente(id=-1,nombre=nombre, apellidos = apellidos, email = email, contraseña = contraseña))
        .mapBoth(
            success = {
                return true
            },
            failure = {
                println(it.message)
                return false
            }
        )
    }
    fun comprobarUsuario(email: String):Boolean{
        service.findByEmail(email).mapBoth(
            success = {
                return false
            },
            failure = {
                return true
            }
        )

    }
    fun comprobarEmail(email: String):Boolean{
        return validateEmail(email)
    }
    fun comprobarContraseña(contraseña: String):Boolean{
        return validateContraseña(contraseña)
    }
    fun comprobarApellido(apellidos: String):Boolean{
        return validateApellidos(apellidos)
    }
    fun comprobarNombre(nombre: String):Boolean{
        return validateNombre(nombre)
    }
}