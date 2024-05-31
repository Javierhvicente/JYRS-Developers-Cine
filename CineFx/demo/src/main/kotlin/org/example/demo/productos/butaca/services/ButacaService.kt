package org.example.demo.productos.butaca.services

import com.github.michaelbull.result.Result
import org.example.demo.productos.butaca.errors.ButacaError
import org.example.demo.productos.models.Butaca
import org.example.demo.productos.models.Ocupacion
import java.io.File
import java.io.InputStream

interface ButacaService {
    fun getAll(): Result<List<Butaca>, ButacaError>
    fun getByTipo(tipo: String): Result<List<Butaca>, ButacaError>
    fun getById(id: String): Result<Butaca, ButacaError>
    fun getByEstado(estado: String): Result<List<Butaca>, ButacaError>
    fun getByOcupacion(ocupacion: String): Result<List<Butaca>, ButacaError>
    fun create(butaca: Butaca): Result<Butaca, ButacaError>
    fun update(id: String, butaca: Butaca): Result<Butaca, ButacaError>
    fun deleteAll()
    fun importCsv(csvFile: File): Result<List<Butaca>, ButacaError>
    fun exportCsv(file:File, list: List<Butaca>): Result<Long, ButacaError>
    fun exportJson(file:File, list: List<Butaca>): Result<Long, ButacaError>
    fun importJson(jsonFile: File): Result<List<Butaca>, ButacaError>

}