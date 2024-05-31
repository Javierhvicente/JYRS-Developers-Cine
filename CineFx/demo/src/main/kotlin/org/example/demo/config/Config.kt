package org.example.demo.config

import org.lighthousegames.logging.logging
import java.io.File
import java.io.InputStream
import java.util.Properties
import kotlin.io.path.Path

private val logger = logging()
private const val CONFIG_FILE_NAME = "config.properties"
/**
 * Clase config
 * @author Yahya El Hadri, Raúl Fernández, Javier Hernández, Samuel Cortés
 * @since 1.0
 */

object Config {

    private val actualDirectory = System.getProperty("user.dir")

    val databaseUrl: String by lazy {
        readProperty("database.url") ?: "jdbc:sqlite:cine.db"
    }

    val databaseInit: Boolean by lazy {
        readProperty("database.init")?.toBoolean() ?: true
    }

    val databaseRemoveData: Boolean by lazy {
        readProperty("database.removedata")?.toBoolean() ?: true
    }
    val databaseInMemory: Boolean by lazy {
        readProperty("database.inmemory")?.toBoolean() ?: false
    }
    val storageData: String by lazy {
        readProperty("storage.data") ?: "data"
    }
    val cacheSize: Int by lazy {
        readProperty("cache.size")?.toInt() ?: 5
    }
    val imagenesDirectory :String by lazy {
        val path = readProperty("config.images") ?: "imagenes"
        "$actualDirectory${File.separator}$path"
    }
    init {
        logger.debug { "Cargando configuración del cine" }
    }
    private fun readProperty(propiedad: String): String? {
        return try {
            logger.debug { "Leyendo propiedad: $propiedad" }
            val properties = Properties()
            val inputStream: InputStream = ClassLoader.getSystemResourceAsStream(CONFIG_FILE_NAME)
                ?: throw Exception("No se puede leer el fichero de configuración $CONFIG_FILE_NAME")
            properties.load(inputStream)
            properties.getProperty(propiedad)
        }catch (e: Exception){
            logger.error { "Error al leer la propiedad $propiedad: ${e.message}" }
            null
        }
    }
}