package org.example.demo.storage

import org.example.demo.errors.ErrorStorage
import org.example.demo.productos.models.Butaca
import org.example.demo.productos.models.Complemento
import org.example.demo.usuarios.models7.Usuario
import org.example.demo.venta.models.Venta
import java.io.File
import com.github.michaelbull.result.Result
import org.example.demo.config.Config

interface CineStorage {
    fun exportToZip(config: Config, file: File, butacas: List<Butaca>, complementos:List<Complemento>, usuarios: List<Usuario>, ventas: List<Venta>): Result<File, ErrorStorage>
    fun loadFromZip(file: File,config: Config): Result<List<Any>, ErrorStorage>
}