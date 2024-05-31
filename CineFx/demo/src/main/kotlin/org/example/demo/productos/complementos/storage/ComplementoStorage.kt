package org.example.demo.productos.complementos.storage

import com.github.michaelbull.result.Result
import org.example.demo.productos.butaca.errors.ButacaError
import org.example.demo.productos.complementos.errors.ComplementoError
import org.example.demo.productos.models.Butaca
import org.example.demo.productos.models.Complemento
import java.io.File
import java.io.InputStream

interface ComplementoStorage {
    fun loadCsv(file: File): Result<List<Complemento>, ComplementoError>
    fun saveJson(file: File, list: List<Complemento>): Result<Long, ComplementoError>
    fun loadJson(file: File): Result<List<Complemento>, ComplementoError>
    fun saveCsv(file: File, list: List<Complemento>): Result<Long, ComplementoError>
}