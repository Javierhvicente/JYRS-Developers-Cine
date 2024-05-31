package org.example.demo.cache

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.demo.cache.errors.CacheError
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Cache de almacenamiento genérico
 * @property size
 * @since 1.0
 * @author Yahya El Hadri, Raúl Fernández, Samuel Cortés, Javier Hernández
 */

open class CacheImpl<K, T>(
    private val size: Int
) : Cache<K, T> {
    private val cache = mutableMapOf<K, T>()

    /**
     * Obtener un valor en especifíco en la cache asociado a una clave en especifíco.
     * @param key
     * @return Devuelve el resultado encapsulado en un objeto (result), indicando si se encontro el valor o no.
     * @since 1.0
     * @author Yahya El Hadri, Raúl Fernández, Samuel Cortés, Javier Hernández
     */

    override fun get(key: K): Result<T, CacheError> {
        logger.debug { "Obteniendo valor de la cache" }
        return if (cache.containsKey(key)) {
            Ok(cache.getValue(key))
        } else {
            Err(CacheError("No existe el valor en la cache"))
        }
    }

    /**
     * Guardar un valor en la cache asociado a una clave en especifíco.
     * @param key
     * @param value
     * @return Devuelve el valor que ha almacenado en un objeto.
     * @since 1.0
     * @author Yahya El Hadri, Raúl Fernández, Samuel Cortés, Javier Hernández
     */

    override fun put(key: K, value: T): Result<T, Nothing> {
        logger.debug { "Guardando valor en la cache" }
        if (cache.size >= size && !cache.containsKey(key)) {
            logger.debug { "Eliminando valor de la cache" }
            cache.remove(cache.keys.first())
        }
        cache[key] = value
        return Ok(value)
    }

    /**
     * Elimina un valor en la cache asociado a una clave en especifíco.
     * @param key
     * @return Devuelve el resultado encapsulado en un result, indicando si se elimino el valor correctamente o no.
     * @since 1.0
     * @author Yahya El Hadri, Raúl Fernández, Samuel Cortés, Javier Hernández
     */

    override fun remove(key: K): Result<T, CacheError> {
        logger.debug { "Eliminando valor de la cache" }
        return if (cache.containsKey(key)) {
            Ok(cache.remove(key)!!)
        } else {
            Err(CacheError("No existe el valor en la cache"))
        }
    }

    /**
     * Nos encargamos de limpiar todos los archivos de la cache, dejándola vacía.
     * @return Devuelve el resultado encapsulado en un result, indicando si se elimino el valor correctamente o no.
     * @since 1.0
     * @author Yahya El Hadri, Raúl Fernández, Samuel Cortés, Javier Hernández
     */

    override fun clear(): Result<Unit, Nothing> {
        logger.debug { "Limpiando cache" }
        cache.clear()
        return Ok(Unit)
    }
}