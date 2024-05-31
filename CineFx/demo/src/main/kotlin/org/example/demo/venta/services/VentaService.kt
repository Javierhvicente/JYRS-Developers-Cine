package org.example.demo.venta.services

import com.github.michaelbull.result.Result
import org.example.demo.usuarios.models.Cliente
import org.example.demo.venta.errors.VentaError
import org.example.demo.venta.models.LineaVenta
import org.example.demo.venta.models.Venta
import java.io.File
import java.util.*

interface VentasService {
    fun getById(id: UUID): Result<Venta, VentaError>
    fun create(venta: Venta): Result<Venta, VentaError>
    fun delete(id: UUID): Result<Venta, VentaError>
    fun getAll(): Result<List<Venta>, VentaError>
    fun exportToHtml(venta: Venta, htmlFile: File, pelicula: String): Result<Unit, VentaError>
}