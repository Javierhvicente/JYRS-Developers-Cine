package org.example.demo.productos.butaca.storage


import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.demo.productos.butaca.dto.ButacaDto
import org.example.demo.productos.butaca.errors.ButacaError
import org.example.demo.productos.butaca.mappers.toButaca
import org.example.demo.productos.butaca.mappers.toButacaDto
import org.example.demo.productos.models.Butaca
import org.lighthousegames.logging.logging
import java.io.File

private val logger=logging()


class ButacaStorageImpl:ButacaStorage {
    /**
     * Guarda una lista de butacas de un archivo JSON para una fecha especifíca.
     * @param fecha
     * @param list
     * @return Devuelve la lista de butacas si ha sido exitosa la operación, en cambio si ha surgido algun contratiempo devuelve un error.
     * @author Raúl Fernández, Javier Hernández, Yahya El Hadri, Samuel Cortés
     * @since 1.0
     */
    override fun saveJson(file: File, list: List<Butaca>): Result<Long, ButacaError> {
        logger.debug { "Guardando datos en fichero $file" }
        return try {
            val json = Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
            val jsonString = json.encodeToString<List<ButacaDto>>(list.map { it.toButacaDto() })
            file.writeText(jsonString)
            Ok(list.size.toLong())
        } catch (e: Exception) {
            Err(ButacaError.FicheroNoValido("Error al guardar el fichero json"))
        }
    }


    /**
     * Cargamos datos de un archivo json para generar una lista de objetos 'butaca'.
     * @return Devuelve un resultado encapsulado en un objeto, diciendo si la operación fue exitosa o no.
     * @author Raúl Fernández, Javier Hernández, Yahya El Hadri, Samuel Cortés
     * @since 1.0
     */

    override fun loadJson(file: File): Result<List<Butaca> , ButacaError> {
        logger.debug { "Leyendo Butacas desde fichero json $file" }
        logger.debug { "Cargando datos en fichero $file" }
        val json = Json{
            prettyPrint = true
            ignoreUnknownKeys = true
        }
        return try {
            val jsonString = file.readText()
            val data = json.decodeFromString<List<ButacaDto>>(jsonString)
            Ok(data.map { it.toButaca() })
        }catch (e: Exception) {
            Err(ButacaError.StorageError("Error al leer el JSON: ${e.message}"))
        }
    }


    /**
     * Cargamos datos de un archivo csv para generar una lista de objetos 'butaca'.
     * @return Devuelve un resultado encapsulado en un objeto, diciendo si la operación fue exitosa o no.
     * @author Raúl Fernández, Javier Hernández, Yahya El Hadri, Samuel Cortés
     * @since 1.0
     */

    override fun loadCsv(file: File): Result<List<Butaca>, ButacaError> {
        logger.debug { "Carganado butacas desde fichero Csv" }

        return try {
            Ok(file.reader().readLines().drop(1)
                .map {
                    val data = it.split(",")
                    ButacaDto(
                        id = data[0],
                        estado = data[1],
                        tipo = data[2],
                        precio = data[3],
                        ocupacion = data[4],
                        createAt = data[5]
                    ).toButaca()

                }
            )
        }catch (e: Exception){
            logger.error { "Error al cargar el fichero csv " }
            Err((ButacaError.FicheroNoValido("Error al leer el fichero csv")))
        }
    }

    /**
     * Guarda una lista de butacas de un archivo CSV para una fecha especifíca.
     * @param fecha
     * @param list
     * @return Devuelve la lista de butacas si ha sido exitosa la operación, en cambio si ha surgido algun contratiempo devuelve un error.
     * @author Raúl Fernández, Javier Hernández, Yahya El Hadri, Samuel Cortés
     * @since 1.0
     */
    override fun saveCsv(file: File, list: List<Butaca>): Result<Long, ButacaError> {
        logger.debug { "Guardando butacas en fichero csv" }
        return try {
            file.writeText("ID,Estado,Tipo,Precio,Ocupacion,CreatedAt\n")
                list.map { it. toButacaDto() }
                    .forEach {
                        file.appendText("${it.id},${it.estado},${it.tipo},${it.precio},${it.ocupacion},${it.createAt}\n")
                    }
            Ok(list.size.toLong())

        } catch (e: Exception) {
            logger.error { "Error al guardar el fichero csv de butacas: ${e.message}" }
            Err(ButacaError.FicheroNoValido("Error al guardar el fichero csv de butacas: ${e.message}"))
        }
    }

}