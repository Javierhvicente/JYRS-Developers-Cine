package org.example.demo.usuarios.services

import com.github.michaelbull.result.*
import org.example.demo.usuarios.cache.CacheUsuario
import org.example.demo.usuarios.errors.UserError
import org.example.demo.usuarios.models.Cliente
import org.example.demo.usuarios.models7.Usuario
import org.example.demo.usuarios.repositories.UserRepository
import org.example.demo.usuarios.repositories.UserRepositoryImpl
import org.example.demo.usuarios.validator.validateUser
import org.lighthousegames.logging.logging



private val logger = logging()

/**
 * Servicio que maneja el repositorio de usuario y la cache.
 * @param UserRepository, CacheUsuario el repositorio y la cache de usuario.
 * @return Se devuelve el objeto usuario estando ya guardado.
 * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
 * @since 1.0
 */

class UserServiceImpl(
    private val repository: UserRepository,
    private val cacheUsuario: CacheUsuario
): UserService {

    /**
     * Nos encargamos de guardar un usuario en el sistema.
     * @return Devuelve un resultado que contiene el usuario guardado si la operación se realiza correctamente, si no salta un error.
     * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
     * @since 1.0
     */

    override fun save(user: Usuario): Result<Usuario, UserError> {
        logger.debug { "Guardando Usuario: $user" }
       return if (repository.save(user) != null) {
           cacheUsuario.put(user.id, user)
           Ok(user)
       }else{
           Err(UserError.ValidateProblem("No se ha podido guardar debido a un error de validación"))
       }
    }

    /**
     * Servicio donde nos encargamos de cambiar la contraseña de un usuario en el sistema.
     * @param email
     * @param contraseña
     * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
     * @since 1.0
     */

    override fun saveFromJson(user: Usuario): Result<Usuario, UserError> {
        logger.debug { "Guardando Usuario desde JSON: $user" }
        return if (repository.saveFromJson(user) != null) {
            cacheUsuario.put(user.id, user)
            Ok(user)
        }else{
            Err(UserError.ValidateProblem("No se ha podido guardar debido a un error de valdiación"))
        }
    }


    /**
     * Servicio donde nos encargamos de cambiar la contraseña de un usuario en el sistema.
     * @param email
     * @param contraseña
     * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
     * @since 1.0
     */

    override fun cambioContraseña(email: String, contraseña: String): Result<Usuario, UserError> {
        logger.debug { "Cambiando contraseña en email: ${email}" }
        return repository.cambioContraseña(email = email, contraseña = contraseña)?.let {
            Ok(it)
        } ?: Err(UserError.UserNotFound("No se ha encontrado usuario con email: ${email}"))
    }

    /**
     * En este servicio buscamos un usuario en el repositorio por su dirección de correo electrónico.
     * @param email
     * @return Devuelve el usuario encontrado con su dirección de correo electrónico.
     * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
     * @since 1.0
     */

    override fun findByEmail(email: String): Result<Usuario, UserError> {
        logger.debug { "Buscando Usuario por email: $email" }
        return repository.findByEmail(email)?.let {
            println("Encontrado email $email")
            Ok(it)
        } ?: Err(UserError.UserNotFound("No se ha encontrado usuario con email $email"))
    }

    /**
     * En este servicio buscamos un usuario en la cache por su ID.
     * @param id
     * @return Devuelve el usuario con su ID.
     * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
     * @since 1.0
     */

    override fun findById(id: Long): Result<Usuario, UserError> {
        logger.debug { "Buscando Usuario por id: $id" }
        return cacheUsuario.get(id).mapBoth(
            success = {
                logger.debug { "Usuario encotnrado en la cache" }
                Ok(it)
            },
            failure = {
                logger.debug { "Butaca no encontrada en la cache" }
                repository.findById(id)?.also {
                    cacheUsuario.put(id, it)
                }?.let {
                    Ok(it)
                }?: Err(UserError.UserNotFound("Usuario no encontrado con  id: $id"))
            }
        )
    }

    /**
     * Nos encargamos de obtener todos los usuarios registrados en el sistema.
     * @return Devuelve un resultado que contiene una lista de objetos si la operación salío exitosa y en caso de que no se devuelve un error.
     * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
     * @since 1.0
     */

    override fun findAll(): Result<List<Usuario>, UserError> {
        logger.debug { "Obteniendo todos los clientes registrados" }
        val clientes = mutableListOf<Usuario>()
        repository.getAllClientes().forEach { clientes.add(it) }
        return Ok(clientes)
    }

}