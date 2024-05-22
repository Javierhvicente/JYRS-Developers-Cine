package org.example.demo.usuarios.repositories

import org.example.demo.database.SqlDelightManager
import org.example.demo.locale.encodeToBase64
import org.example.demo.usuarios.mappers.toUsuario
import org.example.demo.usuarios.models7.Usuario
import org.lighthousegames.logging.logging

private val logger = logging()

    /**
     * Se encarga de guardar todos los datos que guardemos del usuario en la base de datos.
     * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
     * @since 1.0
     */

class UserRepositoryImpl: UserRepository {
    private val db = SqlDelightManager.databaseQueries
    override fun save(user: Usuario): Usuario {
        logger.debug { "save: $user" }
        db.transaction {
            db.insertUser(
                id = user.id,
                email = user.email,
                nombre = user.nombre,
                apellidos = user.apellidos,
                tipo = "cliente",
                contrasena = user.contraseña.encodeToBase64(),
            )
        }
        return user
    }
    
    /**
     * El cambio de contraseña del usuario.
     * @param email
     * @param contraseña
     * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
     * @since 1.0
     */
     
    override fun cambioContraseña(email: String, contraseña: String): Usuario? {
        logger.debug { "cambiando contraseña en email: $email" }
        findByEmail(email)?.let {
            it.contraseña = contraseña
        }
        return findByEmail(email)
    }

    /**
     * Buscamos un usuario en la base de datos por su email y devuelve el usuario encontrado.
     * @return Devuelve un usuario una vez encontrado el usuario en la base de datos.
     * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
     * @since 1.0
     */

    override fun findByEmail(email: String): Usuario? {
        logger.debug { "Buscando usuario por email $email" }
        return db.selectByEmail(email).executeAsOneOrNull()?.toUsuario()
    }

    /**
     * Busca un usuario en la base de datos utilizando su ID unico y devuelve el usuario encontrado.
     * @return Devuelve el usuario encontrado.
     * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
     * @since 1.0
     */

    override fun findById(id: Long): Usuario? {
        logger.debug { "Buscando usuario por id: $id" }
        return db.selectById(id).executeAsOneOrNull()?.toUsuario()
    }
}