package org.example.demo.productos.complementos.storage.images

import com.github.michaelbull.result.Result
import org.example.demo.productos.complementos.errors.ComplementoError
import java.io.File


interface StorageImages {
    fun saveImage(fileName: File): Result<File, ComplementoError>
    fun loadImage(fileName: String): Result<File, ComplementoError>
    fun deleteImage(fileName: File): Result<Unit, ComplementoError>
    fun deleteAllImages(): Result<Long, ComplementoError>
    fun updateImage(imageName: String, newFileImage: File): Result<File, ComplementoError>
}