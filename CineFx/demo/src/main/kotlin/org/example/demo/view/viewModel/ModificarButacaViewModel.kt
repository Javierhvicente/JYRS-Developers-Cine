package org.example.demo.usuarios.viewModel

import javafx.beans.property.SimpleObjectProperty
import org.example.demo.locale.toDefaultMoneyString
import org.example.demo.productos.butaca.mappers.toButacaDto
import org.example.demo.productos.butaca.services.ButacaService
import org.example.demo.productos.models.*
import org.example.demo.usuarios.viewModel.GestionButacaViewModel.GestButacasState
import java.time.ZoneId

class ModificarButacaViewModel(
    val service:ButacaService
) {
    val state: SimpleObjectProperty<ButacaModificadaState> = SimpleObjectProperty(ButacaModificadaState())

    fun initState(butaca: Butaca){
        state.value = state.value.copy(
            butacaModificar = butaca,
            estados = listOf("ACTIVA","MANTENIMIENTO","OCUPADA"),
            tipos = listOf("NORMAL","VIP"),
            ocupaciones = listOf("LIBRE","OCUPADA","INACTIVA"),
            id = butaca.id,
            precio = butaca.precio.toString()
        )
    }

    fun actualizarButaca(
        estado:String,
        tipo:String,
        ocupacion:String,
        precio: String
    ){
        val butaca=Butaca(
            id = state.value.butacaModificar!!.id,
            estado = when(estado){
                "ACTIVA"-> Estado.ACTIVA
                "MANTENIMIENTO"->Estado.MANTENIMIENTO
                "OCUPADA"->Estado.OCUPADA
                else-> Estado.OCUPADA
            },
            tipo = when(tipo){
                "NORMAL"->Tipo.NORMAL
                "VIP"->Tipo.VIP
                else->Tipo.VIP
            },
            create = state.value.butacaModificar!!.create,
            precio = precio.toDouble(),
            ocupacion =  when(ocupacion){
                "LIBRE"-> Ocupacion.LIBRE
                "INACTIVA"->Ocupacion.INACTIVA
                "RESERVA"->Ocupacion.SELECCIONADA
                "OCUPADA" ->Ocupacion.OCUPADA
                else -> Ocupacion.OCUPADA
            }
        )
        service.update(
            state.value.butacaModificar!!.id,
            butaca = butaca
        )
    }

    fun allButacas():List<Butaca>{
        return service.getAll().value
    }

    data class ButacaModificadaState (
        val butacaModificar:Butaca?=null,
        val estados: List<String> = mutableListOf(),
        val tipos: List<String> = mutableListOf(),
        val ocupaciones: List<String> = mutableListOf(),
        val id: String="",
        val precio:String=""
    )
}