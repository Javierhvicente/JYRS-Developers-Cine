package org.example.demo.usuarios.viewModel

import org.example.demo.venta.models.Venta
import org.example.demo.venta.services.VentasService
import java.io.File
import java.nio.file.Files
import java.time.LocalDate
import kotlin.io.path.Path


class DetallesCompraViewModel(
    val service:VentasService
) {

    fun guardarPdf(venta: Venta, pelicula:String):String{
        Files.createDirectories(Path("data"))
        val file= Path("data","Ticket${LocalDate.now()}-${venta.cliente.nombre}.html").toFile()
        service.exportToHtml(venta = venta, htmlFile = file, pelicula = pelicula)
        return file.toPath().toString()
    }
}