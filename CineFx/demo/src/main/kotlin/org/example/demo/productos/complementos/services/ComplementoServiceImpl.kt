package org.example.demo.productos.complementos.services

import com.github.michaelbull.result.*
import org.example.demo.productos.complementos.cache.ComplementoCacheImpl
import org.example.demo.productos.complementos.errors.ComplementoError
import org.example.demo.productos.complementos.repositories.ComplementoRepository
import org.example.demo.productos.complementos.storage.ComplementoStorage
import org.example.demo.productos.models.Complemento
import org.lighthousegames.logging.logging
import java.io.File

private val logger= logging()

/**
 * Servicio que maneja el repositorio de usuario y la cache.
 * @param repository
 * @param cache
 * @param storage
 * @return Devuelve un resultado con la lista de complementos obtenidos.
 * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
 * @since 1.0
 */

class ComplementoServiceImpl(
    private val repository: ComplementoRepository,
    private val cache: ComplementoCacheImpl,
    private val storage: ComplementoStorage
): ComplementoService {
    override fun getAll(): Result<List<Complemento>, ComplementoError> {
        logger.debug { "Obteniendo todos los complementos" }
        return Ok(repository.findAll())
    }

    /**
     * Obtenemos complementos filtrados por tipo.
     * @param tipo
     * @return Devuelve un resultado con la lista de complementos obtenidos.
     * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
     * @since 1.0
     */

    override fun getByTipo(tipo: String): Result<List<Complemento>, ComplementoError> {
        logger.debug { "Obteniendo complementos por tipo: $tipo" }
        return Ok(repository.findByTipo(tipo))
    }

    /**
     * Obtenemos un complemento por su ID desde la cache, si no se encuentra en la cache busca en el repositorio.
     * @param id
     * @return Si se encuentra en el repositorio se devuelve, si no se encuentra se devuelve un error.
     * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
     * @since 1.0
     */

    override fun getById(id: String): Result<Complemento, ComplementoError> {
        return cache.get(id).mapBoth(
            success = {
                logger.debug { "Complemento encontrado en cache" }
                Ok(it)
            },
            failure = {
                logger.debug { "Complemento no encontrado en cache" }
                repository.findById(id)
                    ?.let { Ok(it) }
                    ?: Err(ComplementoError.ComplementoNoEncontrado("Complemento no encontrado con id: $id"))
            }
        )
    }

    /**
     * Guarda un nuevo complemento utilizando el repositorio y luego lo guarda en la cache.
     * @param complemento
     * @return Devuelve el complemento creado.
     * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
     * @since 1.0
     */

    override fun create(complemento: Complemento): Result<Complemento, ComplementoError> {
        logger.debug { "Guardando complemento $complemento" }
        return Ok(repository.save(complemento)).also {
            cache.put(complemento.id,complemento)
        }
    }

    /**
     * Actualizamos un complemento en el repositorio y luego actualiza la cache con el complemento.
     * @param id
     * @return Se devuelve el complemento actualizado y si no se devuelve un error en caso de que falle.
     * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
     * @since 1.0
     */

    override fun update(id: String, complemento: Complemento,imagen:String): Result<Complemento, ComplementoError> {
        logger.debug { "Actualizando complemento con id: $id" }
        return  repository.update(complemento.id, complemento, imagen)
            .also { cache.put(id,complemento) }
            ?.let { Ok(it) }
            ?: Err(ComplementoError.ComplementoNoActualizado("No se ha podido actualizar el complemento: $id"))

    }

    /**
     * Actualizamos un complemento en el repositorio y luego actualiza la cache con el complemento.
     * @param id
     * @return Se devuelve el complemento actualizado y si no se devuelve un error en caso de que falle.
     * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
     * @since 1.0
     */

    override fun delete(id: String): Result<Complemento, ComplementoError> {
        logger.debug { "Borrando complemento con id $id" }
        return repository.delete(id)
            ?.let {
                cache.remove(id)
                Ok(it)
            }
            ?: Err(ComplementoError.ComplementoNoEncontrado("El complemento no a sido eliminada $id"))
    }
    /**
     * Cargamos complementos desde un csv utilizando el storage guardando cada complemento en el repositorio.
     * @return Devuelve un resultado que contiene la lista de complementos y si falla devuelve un error correspondiente.
     * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
     * @since 1.0
     */
    override fun import(csvFile: File): Result<List<Complemento>, ComplementoError> {


        logger.debug { "Cargando complemento desde CSV" }
        return storage.loadCsv(csvFile).andThen { personajes->
            personajes.forEach{ p->
                repository.save(p)
                println("guardado $p")
            }
            Ok(personajes)
        }
    }
}