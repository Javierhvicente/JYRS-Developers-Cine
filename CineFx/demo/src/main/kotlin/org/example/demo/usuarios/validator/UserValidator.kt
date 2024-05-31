package org.example.demo.usuarios.validator

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.demo.usuarios.errors.UserError
import org.example.demo.usuarios.models7.Usuario
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/**
 * Realizamos validaciones sobre las propiedades de usuario.
 * @return Devuelve si todas las validaciones son correctas devuelve un objeto ok.
 * @author Raúl Fernández, Yahya El Hadri, Samuel Cortés, Javier Hernández
 * @since 1.0
 */

fun validateUser(user: Usuario): Result<Usuario, UserError>{
    if(!validateNombre(user.nombre)){
        return Err(UserError.ValidateProblem("El nombre no puede estar vacío"))
    }
    if (!validateApellidos(user.apellidos)){
        return Err(UserError.ValidateProblem("Los apellidos no pueden estar vacios"))
    }
    if (!validateEmail(user.email)){
        return Err(UserError.ValidateProblem("El correo electrónico no es correcto"))
    }
    if(!validateContraseña(user.contraseña)){
        println(user.contraseña)
        return Err(UserError.ValidateProblem("La contraseña no es válida, debe tener 6 carácteres, contener al menos una mayúscula y un número"))
    }
    return Ok(user)
}

/**
 * Válida la si una cadena de texto representa una fecha válida en el formato que hemos puesto.
 * @return Si la conversión es exitosa devuelve un true, si no devuelve un error.
 * @author Raúl Fernández, Yahya El Hadri, Samuel Cortés, Javier Hernández
 * @since 1.0
 */

fun isValidLocalDate(dateString: String): Boolean {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        LocalDate.parse(dateString, formatter)
        true
    } catch (e: DateTimeParseException) {
        false
    }
}

/**
 * Valida los nombres de un usuario.
 * @return Devuelve un error si el nombre esta vacio y el apellido validado si es válido.
 * @author Raúl Fernández, Yahya El Hadri, Samuel Cortés, Javier Hernández
 * @since 1.0
 */

fun validateNombre(nombre: String): Boolean{
    if(nombre.isEmpty() || nombre.isBlank()){
        return false
    }
    return true
}

/**
 * Valida los apellidos de un usuario.
 * @return Devuelve un error si el apellido esta vacio y el apellido validado si es válido.
 * @author Raúl Fernández, Yahya El Hadri, Samuel Cortés, Javier Hernández
 * @since 1.0
 */

fun validateApellidos(apellidos: String): Boolean{
    if (apellidos.isEmpty() || apellidos.isBlank()){
        return false
    }
    return true
}


/**
 * Valida la dirección de correo electrónico de un usuario.
 * @return Devuelve un error si no sigue el formato esperado y la dirección de correo electrónico validada si es válida.
 * @author Raúl Fernández, Yahya El Hadri, Samuel Cortés, Javier Hernández
 * @since 1.0
 */

fun validateEmail(email: String): Boolean{
    val regex = Regex("^[^\\s@]+@[^\\s@]+\\.[a-zA-Z]{2,3}\$")
    if(!email.matches(regex)){
        return false
    }
    return true
}


/**
 * Valida la contraseña de un usuario utilizando una expresión regular.
 * @return Devuelve un error si no sigue el formato esperado, y la contraseña validada si es válida.
 * @author Raúl Fernández, Yahya El Hadri, Samuel Cortés, Javier Hernández
 * @since 1.0
 */

fun validateContraseña(contraseña: String): Boolean{
    val regex = Regex("^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{6}\$")
    if(!contraseña.matches(regex)){
        println("No se valida la contraseña")
        return false
    }else{
        return true
    }
}

