package org.example.demo.view.controllers

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import org.example.demo.routes.RoutesManager
import org.example.demo.usuarios.viewModel.GestionButacaViewModel
import org.example.demo.usuarios.viewModel.ModificarButacaViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger= logging()
class ModificarButacaViewController:KoinComponent {
    val view: ModificarButacaViewModel by inject()
    val viewGest: GestionButacaViewModel by inject()
    @FXML
    lateinit var textId:TextField
    @FXML
    lateinit var textPrecio:TextField
    @FXML
    lateinit var comboTipo:ComboBox<String>
    @FXML
    lateinit var comboEstado:ComboBox<String>
    @FXML
    lateinit var comboOcupacion:ComboBox<String>
    @FXML
    lateinit var botonGuardar:Button
    @FXML
    lateinit var botonLimpiar:Button
    @FXML
    lateinit var botonCancelar:Button

    @FXML
    fun initialize(){
        logger.debug { "inicializando controller Modificar Butaca" }
        initDefaultEvents()
        initDefaultValues()
        initReactiveProperties()
    }

    private fun initReactiveProperties() {
        view.state.addListener { _,_,newValue->
            textId.text=newValue.id
            textPrecio.text = newValue.precio
        }
    }

    private fun initDefaultValues() {
        val butaca=viewGest.state.value.butacaSeleccionada!!
        view.initState(butaca)

        comboTipo.items = FXCollections.observableList(view.state.value.tipos)
        comboEstado.items = FXCollections.observableList(view.state.value.estados)
        comboOcupacion.items = FXCollections.observableList(view.state.value.ocupaciones)

        comboOcupacion.value = butaca.ocupacion.toString()
        comboEstado.value = butaca.estado.toString()
        comboTipo.value = butaca.tipo.toString()

        textId.text=butaca.id
        textPrecio.text = butaca.precio.toString()

    }

    private fun initDefaultEvents() {
        botonGuardar.setOnAction { guaradarOnAction() }
        botonLimpiar.setOnAction { limpiarOnAction() }
    }

    private fun limpiarOnAction() {
        textPrecio.text=""
    }

    private fun guaradarOnAction() {
        if (textPrecio.text.toDoubleOrNull() == null)RoutesManager.alerta("Precio","Recuerda que el precio debe ser un Double: 0.0")
        else{
            view.actualizarButaca(
                estado = comboEstado.value,
                tipo = comboTipo.value,
                ocupacion = comboOcupacion.value,
                precio = textPrecio.text
            )
            viewGest.initState(view.allButacas())
        }
    }
}