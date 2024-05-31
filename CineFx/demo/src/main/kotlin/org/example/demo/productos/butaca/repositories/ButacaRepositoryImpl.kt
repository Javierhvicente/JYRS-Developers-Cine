package org.example.demo.productos.butaca.repositories

import org.example.demo.database.SqlDelightManager
import org.example.demo.locale.toShortSpanishFormat
import org.example.demo.productos.butaca.mappers.toButaca
import org.example.demo.productos.models.Butaca
import org.example.demo.productos.models.Ocupacion
import org.lighthousegames.logging.logging

private val logger=logging()

/**
 * Repositorio que se comunica con la base de datos de butaca.db
 * @property SqlDelightManager Manager de la base de datos butaca.db
 * @since 1.0
 * @author Javier Hernández, Yahya El Hadri, Samuel Cortés, Raúl Fernández
 */

class ButacaRepositoryImpl(
    private val dbManager: SqlDelightManager
):ButacaRepository{
    private val db  = dbManager.databaseQueries

    /**
     * Obtenemos todas las butacas almacenadas en la base de datos.
     * @return Devuelve una lista de objetos, que representan todas las butacas almacenadas en la base de datos.
     * @since 1.0
     * @author Javier Hernández, Yahya El Hadri, Samuel Cortés, Raúl Fernández
     */

    override fun findAll(): List<Butaca> {
        logger.debug { "Obteniendo todas las butacas" }
        return db.getAllButacaEntity().executeAsList().map { it.toButaca() }
    }

    /**
     * Guardar un objeto (butaca) en la base de datos.
     * @param producto
     * @return producto
     * @since 1.0
     * @author Javier Hernández, Yahya El Hadri, Samuel Cortés, Raúl Fernández
     */

    override fun save(producto: Butaca): Butaca {
        logger.debug { "Guardando butaca: $producto" }

        db.transaction {
            db.insertarbutaca(
                id = producto.id,
                estado = producto.estado.toString(),
                precio = producto.precio,
                tipo = producto.tipo.toString(),
                ocupacion = producto.ocupacion.toString(),
                createAt = producto.create.toShortSpanishFormat()
            )
        }
        return producto
    }

    /**
     * Encontramos una butaca por su id especifíco en la base de datos.
     * @param id
     * @return Butaca o si no encuentra nada salta un error.
     * @since 1.0
     * @author Javier Hernández, Yahya El Hadri, Samuel Cortés, Raúl Fernández
     */

    override fun findById(id: String): Butaca? {
        logger.debug { "Obteniendo butaca por id: $id" }
        return db.getByIdButacaEntity(id)
            .executeAsOneOrNull()
            ?.toButaca()
    }

    /**
     * Encontramos una butaca por su tipo especifíco en la base de datos.
     * @param tipo
     * @return Lista de butacas.
     * @since 1.0
     * @author Javier Hernández, Yahya El Hadri, Samuel Cortés, Raúl Fernández
     */

    override fun findByTipo(tipo: String): List<Butaca> {
        logger.debug { "Obteniendo butacas por tipo: $tipo" }
        return db.getbutacaByTipo(tipo)
            .executeAsList()
            .map { it.toButaca() }
    }

    /**
     * Encontramos una butaca por su id especifíco en la base de datos.
     * @param id
     * @return Devuelve la butaca original.
     * @since 1.0
     * @author Javier Hernández, Yahya El Hadri, Samuel Cortés, Raúl Fernández
     */

    override fun update(id: String, butaca: Butaca): Butaca? {
        logger.debug { "Actualizando butaca con id: $id" }
        db.updateButacaEntity(
            id = butaca.id,
            estado = butaca.estado.toString(),
            tipo = butaca.tipo.toString(),
            ocupacion = butaca.ocupacion.toString(),
            precio = butaca.precio,
        )

        logger.debug { "Actualizada butaca con id: $id" }
        println("ActualizaDA butaca con id: $id")
        val result = this.findById(id)
        return result
    }

    /**
     * Encontramos una butaca por su estado en especifíco en la base de datos.
     * @param estado
     * @return Devuelve una lista de butaca.
     * @since 1.0
     * @author Javier Hernández, Yahya El Hadri, Samuel Cortés, Raúl Fernández
     */

    override fun findByEstado(estado: String): List<Butaca> {
        logger.debug { "Obteniendo butacas por estado: $estado" }
        return db.getButacaByEstado(estado)
            .executeAsList()
            .map { it.toButaca() }
    }

    /**
     * Encontramos una butaca por su ocupación en especifíco en la base de datos.
     * @param ocupacion
     * @return Devuelve una lista de butaca.
     * @since 1.0
     * @author Javier Hernández, Yahya El Hadri, Samuel Cortés, Raúl Fernández
     */

    override fun findByOcupacion(ocupacion: String): List<Butaca> {
        logger.debug { "Obteniendo butacas por estado: $ocupacion" }
        return db.getButacaByOcupacion(ocupacion)
            .executeAsList()
            .map { it.toButaca() }
    }

    /**
     * Borra todos los datos de la tabla butacaEntity.
     * @since 1.0
     * @author Javier Hernández, Yahya El Hadri, Samuel Cortés, Raúl Fernández
     */
     
    override fun deleteAll() {
        logger.debug { "Borrando todas las butacas" }
        db.deleteAllButacaEntity()
    }
}