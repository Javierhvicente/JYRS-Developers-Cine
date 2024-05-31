package org.example.demo.usuarios.repositories

import org.example.demo.database.SqlDelightManager
import org.example.demo.locale.encodeToBase64
import org.example.demo.usuarios.mappers.toUsuario
import org.example.demo.usuarios.models.Cliente
import org.example.demo.usuarios.models7.Usuario
import org.lighthousegames.logging.logging

private val logger = logging()

    /**
     * Se encarga de guardar todos los datos que guardemos del usuario en la base de datos.
     * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
     * @since 1.0
     */

class UserRepositoryImpl(
        private val dbManager: SqlDelightManager
): UserRepository {
    private val db = dbManager.databaseQueries

        /**
         * Se encarga de guardar un usuario en la base de datos.
         * @param user
         * @return Devuelve un usuario.
         * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
         * @since 1.0
         */

    override fun save(user: Usuario): Usuario? {
        logger.debug { "Guardando Usuario: $user" }
        logger.debug { "save: $user" }
        db.transaction {
            db.insertUser(
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
         * Se encarga de guardar un usuario en la base de datos a partir de un objeto 'usuario'.
         * @param user
         * @return Devuelve el mismo objeto 'usuario'.
         * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
         * @since 1.0
         */

        override fun saveFromJson(user: Usuario): Usuario? {
            logger.debug { "Guardando Usuario desde JSON: $user" }
            logger.debug { "save: $user" }
            db.transaction {
                db.insertUserFromJson(
                    id = user.id,
                    email = user.email,
                    nombre = user.nombre,
                    apellidos = user.apellidos,
                    tipo = "cliente",
                    contrasena = user.contraseña,
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
        logger.debug { "cambiando contraseña en email: ${email}" }
        val user = findByEmail(email)?: return null
         user.let {
            db.transaction {
                db.updateContrasena(
                    email = email,
                    contrasena = contraseña.encodeToBase64()
                )
            }
        }
        return findByEmail(user.email)
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

        /**
         * Nos encargamos de obtener todos los clientes en la base de datos.
         * @return Devuelve los clientes encontrados en la base de datos.
         * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
         * @since 1.0
         */

    override fun getAllClientes(): List<Usuario>{
        logger.debug { "Buscando todos los clientes" }
        return db.selectAllClientes().executeAsList().map { it.toUsuario() }
    }
}