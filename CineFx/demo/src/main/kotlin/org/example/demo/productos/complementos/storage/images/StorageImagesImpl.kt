package org.example.demo.productos.complementos.storage.images
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import org.example.demo.productos.complementos.errors.ComplementoError
import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.time.Instant
import com.github.michaelbull.result.Result
import org.example.demo.config.Config
import java.nio.file.StandardCopyOption
import kotlin.io.path.Path

private val logger = logging()

class StorageImageImpl(
    private val config: Config
): StorageImages {
    init {
        try {
            val imagesPath = Paths.get(config.imagenesDirectory)
            if (Files.notExists(imagesPath)) {
                Files.createDirectories(imagesPath)
                logger.debug { "Carpeta de imágenes creada en: ${config.imagenesDirectory}" }
            }
        } catch (e: Exception) {
            logger.error { "Error al crear la carpeta de imágenes: ${e.message}" }
        }
    }

    /**
     * Nos encargamos de generar un nombre único para una imagen basada en el nombre del archivo original.
     * @param newFileImage
     * @return Devolvemos una cadena que representa el nuevo nombre generado.
     * @author Raúl Fernández, Javier Hernández, Yahya El Hadri, Samuel Cortés
     * @since 1.0
     */

    private fun getImageName(newFileImage: File): String {
        val name = newFileImage.name
        val extension = name.substring(name.lastIndexOf(".") + 1)
        return "${Instant.now().toEpochMilli()}.$extension"
    }

    /**
     * Nos encargamos de guardar todas las imágenes generadas en un directorio específico.
     * @param fileName
     * @return Devuelve un result, en el que si se guarda bien la imagen devolverá un ok, si no se guarda bien devolverá un error.
     * @author Raúl Fernández, Javier Hernández, Yahya El Hadri, Samuel Cortés
     * @since 1.0
     */

    override fun saveImage(fileName: File): Result<File, ComplementoError> {
        logger.debug { "Guardando imagen $fileName" }
        return try {
            Files.createDirectories(Path( config.imagenesDirectory))
            val newFileImage = Paths.get(config.imagenesDirectory, fileName.name).toFile()
            Files.copy(fileName.toPath(), newFileImage.toPath(), StandardCopyOption.REPLACE_EXISTING)
            Ok(newFileImage)
        }catch (e : Exception){
            Err(ComplementoError.ComplememntoImageError("Error al guardar la imagen: ${e.message}"))
        }
    }

    /**
     * Nos encargamos de cargar una imagen sacada de la base de datos.
     * @param fileName
     * @param dir
     * @return Devuelve un resultado exitoso si el archivo se encuentra en el directorio de imágenes y si el archivo no existe devuelve un error.
     * @author Raúl Fernández, Javier Hernández, Yahya El Hadri, Samuel Cortés
     * @since 1.0
     */

    fun saveImageTemp(dir:String,fileName: File): Result<File, ComplementoError> {
        logger.debug { "Guardando imagen $fileName" }
        return try {
            val newFileImage = Paths.get(dir, fileName.name).toFile()
            Files.copy(fileName.toPath(), newFileImage.toPath(), StandardCopyOption.REPLACE_EXISTING)
            Ok(newFileImage)
        }catch (e : Exception){
            Err(ComplementoError.ComplememntoImageError("Error al guardar la imagen: ${e.message}"))
        }
    }


    /**
     * Cargamos todas las imágenes.
     * @return Si la actualización no tiene éxito le salta un error y si tiene éxito no salta ningún error.
     * @author Raúl Fernández, Javier Hernández, Yahya El Hadri, Samuel Cortés
     * @since 1.0
     */

    override fun loadImage(fileName: String): Result<File, ComplementoError> {
        logger.debug { "Cargando imagen $fileName" }
        val file = Paths.get(config.imagenesDirectory, fileName).toFile()
        return if(file.exists()){
            Ok(file)
        }else{
            Err(ComplementoError.ComplememntoImageError("La imagen no existe: ${file.name}"))
        }
    }

    /**
     * Nos encargamos de eliminar una imagen del sistema de archivos.
     * @param fileName
     * @return Si la eliminación no tiene éxito le salta un error y si tiene éxito no salta ningún error.
     * @author Raúl Fernández, Javier Hernández, Yahya El Hadri, Samuel Cortés
     * @since 1.0
     */

    override fun deleteImage(fileName: File): Result<Unit, ComplementoError> {
        return try {
            Files.deleteIfExists(fileName.toPath())
            Ok(Unit)
        }catch (e : Exception){
            Err(ComplementoError.ComplememntoImageError("Error al eliminar la imagen: ${e.message}"))
        }
    }

    /**
     * Eliminamos todas las imágenes.
     * @return Si la actualización no tiene éxito le salta un error y si tiene éxito no salta ningún error.
     * @author Raúl Fernández, Javier Hernández, Yahya El Hadri, Samuel Cortés
     * @since 1.0
     */

    override fun deleteAllImages(): Result<Long, ComplementoError> {
        logger.debug { "Borrando todas las imagenes" }
        return try{
            Ok(Files.walk(Paths.get(config.imagenesDirectory))
                .filter{Files.isRegularFile(it)}
                .map { Files.deleteIfExists(it) }
                .count())
        }catch(e: Exception){
            Err(ComplementoError.ComplememntoImageError("Error al borrar todas las imagenes: ${e.message}"))
        }
    }

    /**
     * Nos encargamos de actualizar imágenes cogidas del directorio donde se encuentran todas.
     * @param imageName
     * @param newFileImage
     * @return Si la actualización no tiene éxito le salta un error y si tiene éxito no salta ningún error.
     * @author Raúl Fernández, Javier Hernández, Yahya El Hadri, Samuel Cortés
     * @since 1.0
     */

    override fun updateImage(imageName: String, newFileImage: File): Result<File, ComplementoError> {
        logger.debug { "Actualizando imagen $imageName" }
        return try {
            val newUpdateImage = Paths.get(config.imagenesDirectory, imageName).toFile()
            Files.copy(newFileImage.toPath(), newUpdateImage.toPath(), StandardCopyOption.REPLACE_EXISTING)
            Ok(newUpdateImage)
        }catch (e : Exception){
            Err(ComplementoError.ComplememntoImageError("Error al guardar la imagen: ${e.message}"))
        }
    }

}