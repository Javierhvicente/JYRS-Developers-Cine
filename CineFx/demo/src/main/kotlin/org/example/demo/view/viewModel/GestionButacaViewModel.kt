package org.example.demo.usuarios.viewModel

import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import org.example.demo.locale.toDefaultMoneyString
import org.example.demo.productos.butaca.services.ButacaService
import org.example.demo.productos.butaca.storage.ButacaStorage
import org.example.demo.productos.models.Butaca
import java.io.File


class GestionButacaViewModel(
    private val service: ButacaService,
    private val storage:ButacaStorage
) {
    val state: SimpleObjectProperty<GestButacasState> = SimpleObjectProperty(GestButacasState())
    init {
        if (service.getAll().value.isEmpty()){
            service.importCsv(File("data","butacas.csv")).onSuccess {
                initState(it)
            }
        }else{
            initState(service.getAll().value)
        }
    }

    fun importarButacas(file:File):Boolean{
        var lista= listOf<Butaca>()
        if (file.toPath().toString().contains(".json")){
            storage.loadJson(file).mapBoth(
                success = {
                    lista=it
                },
                failure = {
                    return false
                }
            )
        }else if (file.toPath().toString().contains(".csv")){
            storage.loadCsv(file).mapBoth(
                success = {
                    lista=it
                },
                failure = {
                    return false
                }
            )
        }
        lista.forEach {
            service.update(it.id,it)
        }
        initState(lista)
        return true
    }

    fun exportar(file: File):Boolean{
        if (file.toPath().toString().contains(".json")){
            storage.saveJson(file,state.value.butacas).mapBoth(
                success = {
                    return true
                },
                failure = {
                    return false
                }
            )
        }else if (file.toPath().toString().contains(".csv")){
            storage.saveCsv(file,state.value.butacas).mapBoth(
                success = {
                    return true
                },
                failure = {
                    return false
                }
            )
        }
        return true
    }



    fun filtrarButacas(id: String,estado: String,tipo: String,ocupacion: String):List<Butaca>{
        return state.value.butacas
            .filter { it.id.contains(id,true) }
            .filter { if (estado == "Todos") true else it.estado.toString().contains(estado) }
            .filter { if(tipo == "Todos") true else it.tipo.toString().contains(tipo) }
            .filter { if (ocupacion == "Todas") true else it.ocupacion.toString().contains(ocupacion) }
    }




    fun initState(lista:List<Butaca>){
        state.value = state.value.copy(
            butacas = lista,
            estados = listOf("Todos") + lista.map { it.estado.toString() }.distinct().sorted(),
            tipos = listOf("Todos") + lista.map { it.tipo.toString() }.distinct().sorted(),
            ocupaciones = listOf("Todas") + lista.map { it.ocupacion.toString() }.distinct().sorted()
        )
    }
    fun updateState(butaca:Butaca){
        state.value = state.value.copy(
            butacaSeleccionada = butaca,
            id = butaca.id,
            estado = butaca.estado.toString(),
            tipo = butaca.tipo.toString(),
            precio = butaca.precio.toDefaultMoneyString(),
            ocupacion = butaca.ocupacion.toString()
            )
    }
    data class GestButacasState(
        val butacaSeleccionada:Butaca?=null,
        val butacaModificada:Butaca?=null,
        val butacas:List<Butaca> = listOf(),
        val estados: List<String> = mutableListOf(),
        val tipos: List<String> = mutableListOf(),
        val ocupaciones: List<String> = mutableListOf(),
        val id: String="",
        val estado:String="",
        val tipo:String="",
        val precio:String="",
        val ocupacion:String=""
    )

}