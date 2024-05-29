package org.example.demo.view.viewModel

import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapBoth
import org.example.demo.productos.butaca.services.ButacaService
import org.example.demo.productos.complementos.services.ComplementoService
import org.example.demo.productos.complementos.storage.ComplementoStorage
import org.example.demo.productos.models.Butaca
import org.example.demo.productos.models.Complemento
import org.example.demo.storage.CineStorage
import org.example.demo.usuarios.models.Cliente
import org.example.demo.usuarios.models7.Usuario
import org.example.demo.usuarios.services.UserService
import org.example.demo.venta.models.Venta
import org.example.demo.venta.services.VentasService
import java.io.File

class ZipViewModel(
    private val storage:CineStorage,
    private val serviceButacas: ButacaService,
    private val serviceComplementos:ComplementoService,
    private val serviceVenta:VentasService,
    private val serviceUsuario:UserService,
) {
    fun crearZip(file: File):Boolean{
        storage.exportToZip(
            file = file,
            butacas = serviceButacas.getAll().value,
            complementos = serviceComplementos.getAll().value,
            ventas = serviceVenta.getAll().value,
            usuarios = serviceUsuario.findAll().value
        ).mapBoth(
            success = {
                return true
            },
            failure = {
                return false
            }
        )
    }
    fun unzip(file:File):Boolean{
        storage.loadFromZip(file).mapBoth(
            success = {
                it.forEach {
                    when(it){
                        is Venta->serviceVenta.create(it)
                        is Cliente->serviceUsuario.save(it)
                        is Complemento->serviceComplementos.create(it)
                        is Butaca->serviceButacas.create(it)
                    }
                }
                return true
            },
            failure = {
                return false
            }
        )
    }

}