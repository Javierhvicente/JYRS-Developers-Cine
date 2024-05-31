package org.example.demo.venta.storage

import com.github.michaelbull.result.Result
import org.example.demo.productos.complementos.errors.ComplementoError
import org.example.demo.productos.models.Complemento
import org.example.demo.venta.errors.VentaError
import org.example.demo.venta.models.Venta
import java.io.File

interface VentasStorage {
    fun export(venta: Venta, file: File, pelicula: String): Result<Unit, VentaError>
    fun save(file: File, list: List<Venta>): Result<Unit, VentaError>
    fun load(file: File): Result<List<Venta>, VentaError>
}