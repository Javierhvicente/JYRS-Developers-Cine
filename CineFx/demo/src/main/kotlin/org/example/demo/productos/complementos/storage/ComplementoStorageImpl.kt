package org.example.demo.productos.complementos.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.demo.productos.butaca.dto.ButacaDto
import org.example.demo.productos.butaca.mappers.toButaca
import org.example.demo.productos.butaca.mappers.toButacaDto
import org.example.demo.productos.complementos.dto.ComplementoDto
import org.example.demo.productos.complementos.errors.ComplementoError
import org.example.demo.productos.complementos.mappers.toComplemento
import org.example.demo.productos.complementos.mappers.toComplementoDto
import org.example.demo.productos.models.Complemento
import org.lighthousegames.logging.logging
import java.io.File


private val logger=logging()

/**
 * Manejamos adecuadamente la carga de datos del archivo csv.
 * @param file
 * @return Devuelve un objeto de tipo complemento si la operación esta bien, si no devuelve un error producido por un fallo al procesar el csv.
 * @author Raúl Fernández, Javier Hernández, Yahya El Hadri, Samuel Cortés
 * @since 1.0
 */

class ComplementoStorageImpl:ComplementoStorage {
    override fun loadCsv(file: File): Result<List<Complemento>, ComplementoError> {
        logger.debug { "Carganado complementos desde fichero Csv" }
        return try {
            Ok(file.reader().readLines().drop(1)
                .map {
                    val data = it.split(",")
                    ComplementoDto(
                        tipoComplemento = data[0],
                        nombre = data[1],
                        precio = data[2],
                        imagen = data[3]
                    ).toComplemento()
                }
            )
        }catch (e: Exception){
            logger.error { "Error al cargar el fichero csv " }
            Err((ComplementoError.FicheroNoValido("Error al leer el fichero csv")))
        }
    }

    /**
     * En esta función nos encargamos de guardar una lista de objetos 'complemento' en un archivo JSON.
     * @param file
     * @param list
     * @return Devuelve un result, donde si el método es exitoso devuelve un ok, en caso de que no sea exitoso devuelve un error.
     * @author Raúl Fernández, Javier Hernández, Yahya El Hadri, Samuel Cortés
     * @since 1.0
     */

    override fun saveJson(file: File, list: List<Complemento>): Result<Long, ComplementoError> {
        logger.debug { "Guardando complementos en fichero json" }
        return try {
            val json = Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
            val jsonString = json.encodeToString<List<ComplementoDto>>(list.map { it.toComplementoDto() })
            file.writeText(jsonString)
            Ok(list.size.toLong())
        }catch (e: Exception){
            logger.error { "Error al guardar el fichero json de complementos" }
            Err(ComplementoError.FicheroNoValido("Error al leer el JSON: ${e.message}"))
        }
    }

    /**
     * Cargamos datos de complementos desde un archivo JSON.
     * @param file
     * @return Devuelve un result, donde si el método es exitoso devuelve un ok, en caso de que no sea exitoso devuelve un error.
     * @author Raúl Fernández, Javier Hernández, Yahya El Hadri, Samuel Cortés
     * @since 1.0
     */

    override fun loadJson(file: File): Result<List<Complemento>, ComplementoError> {
        logger.debug { "Leyendo Complementos desde fichero json $file" }
        val json = Json{
            prettyPrint = true
            ignoreUnknownKeys = true
        }
        return try {
            val jsonString = file.readText()
            val data = json.decodeFromString<List<ComplementoDto>>(jsonString)
            Ok(data.map { it.toComplemento() })
        }catch (e: Exception) {
            Err(ComplementoError.FicheroNoValido("Error al leer el JSON: ${e.message}"))
        }
    }

    /**
     * Guardamos el csv con todos los datos de complemento dentro.
     * @param file
     * @param list
     * @return Devuelve un result, donde si el método es exitoso devuelve un ok, en caso de que no sea exitoso devuelve un error.
     * @author Raúl Fernández, Javier Hernández, Yahya El Hadri, Samuel Cortés
     * @since 1.0
     */
    override fun saveCsv(file: File, list: List<Complemento>): Result<Long, ComplementoError> {
        return try {
                file.writeText("tipo_complemento,nombre,precio,imagen\n")
                list.map { it.toComplementoDto() }
                    .forEach { complemento ->
                    file.appendText("${complemento.tipoComplemento},${complemento.nombre},${complemento.precio},${complemento.imagen}\n")
                }
            Ok(list.size.toLong())
        } catch (e: Exception) {
            Err(ComplementoError.FicheroNoValido("Error al escribir el fichero csv"))
        }
    }

}