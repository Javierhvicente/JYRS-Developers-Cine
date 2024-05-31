package org.example.demo.usuarios.viewModel

import com.github.michaelbull.result.mapBoth
import javafx.beans.property.SimpleObjectProperty
import org.example.demo.productos.models.Bebida
import org.example.demo.productos.models.Butaca
import org.example.demo.productos.models.Comida
import org.example.demo.productos.models.Complemento
import org.example.demo.usuarios.models.Cliente
import org.example.demo.usuarios.models7.Usuario
import org.example.demo.venta.models.LineaVenta
import org.example.demo.venta.models.Venta
import org.example.demo.venta.services.VentasService
import org.example.demo.usuarios.viewModel.LoginViewModel.LoginState

class PagoViewModel (
    val service:VentasService
){
    val state: SimpleObjectProperty<PagoState> = SimpleObjectProperty(PagoState())

    fun validarNumerTarjeta(tarjeta:String):Boolean{
        val regex ="^(\\d{4}-){3}\\d{4}\$".toRegex()
        return regex.matches(tarjeta)
    }

    fun validarFecha(fecha:String):Boolean{
        val regex = "^(0[1-9]|1[0-2])\\/(202[5-9]|20[3-9]\\d|2[1-9]\\d{2}|[3-9]\\d{3})\$".toRegex()
        return regex.matches(fecha)
    }

    fun validarCvc(cvc:String):Boolean{
        val regex = "^\\d{3,4}\$".toRegex()
        return regex.matches(cvc)
    }
    fun validarTarjeta(tarjeta: String,fecha: String,cvc: String):Boolean{
        return validarNumerTarjeta(tarjeta)&&validarFecha(fecha)&&validarCvc(cvc)
    }

    fun crearVenta(cliente:Usuario,listaComplementos:List<Complemento>,listaButaca: List<Butaca>):Boolean{
        val lines:MutableList<LineaVenta> = mutableListOf()
        listaComplementos.forEach {
            lines.add(
                LineaVenta(producto = it, cantidad = 1, precio = when(it){
                     is Bebida-> it.precio
                     is Comida->it.precio
                     else->0.0
                    }
                )
            )
        }
        listaButaca.forEach {
            lines.add(
                LineaVenta(producto = it, cantidad = 1, precio = it.precio)
            )
        }
        val venta= Venta(
            cliente = cliente,
            lineas = lines
        )
        service.create(venta).mapBoth(
            success = {
                state.value = state.value.copy(venta = venta)
                return true
            },
            failure = {
                return false
            }
        )
    }




    data class PagoState(
        var pelicula:String="",
        val venta :Venta = Venta(cliente = Cliente(id = 1, nombre = "", apellidos = "", email = "", contraseña = ""), lineas = listOf())
    )
}