package org.example.demo.usuarios.viewModel

import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import org.example.demo.locale.toDefaultMoneyString
import org.example.demo.productos.butaca.services.ButacaService
import org.example.demo.productos.models.Butaca
import org.example.demo.productos.models.Estado
import org.example.demo.productos.models.Ocupacion
import org.example.demo.productos.models.Tipo
import java.io.File

class EstadoCineViewModel(
    private val service:ButacaService
) {
    val state: SimpleObjectProperty<EstadoButacasState> = SimpleObjectProperty(EstadoButacasState())
    init {
        if (service.getAll().value.isEmpty()){
            println("sacando butacas de csv")
            service.importCsv(File("data","butacas.csv")).onSuccess {
                initState(it)
            }
        }else{
            println("sacando butacas de bdd")
            initState(service.getAll().value)
        }
    }

    private fun initState(it: List<Butaca>) {
        state.value = state.value.copy(
            butacas = it.toMutableList()
        )
    }

    fun buscarButacaId(id:String):Butaca?{
        service.getById(id).onSuccess { return it }
        return null
    }
    fun recaudacionVip():String{
        var recaudacion=0.0
        state.value.butacas.forEach {
            if (it.tipo == Tipo.VIP && (it.estado == Estado.OCUPADA || it.ocupacion == Ocupacion.OCUPADA)){
                recaudacion+=it.precio
            }
        }
        return recaudacion.toDefaultMoneyString()
    }
    fun recaudacionNormal():String{
        var recaudacion=0.0
        state.value.butacas.forEach {
            if (it.tipo == Tipo.NORMAL && (it.estado == Estado.OCUPADA || it.ocupacion == Ocupacion.OCUPADA)){
                recaudacion+=it.precio
            }
        }
        return recaudacion.toDefaultMoneyString()
    }
    fun recaudacionTotal():String{
        var recaudacion=0.0
        state.value.butacas.forEach {
            if (it.estado == Estado.OCUPADA || it.ocupacion == Ocupacion.OCUPADA){
                recaudacion+=it.precio
            }
        }
        return recaudacion.toDefaultMoneyString()
    }

    data class EstadoButacasState(
        var butacas:MutableList<Butaca> = mutableListOf(),
    )
}