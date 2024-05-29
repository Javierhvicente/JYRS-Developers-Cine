package org.example.demo.view.controllers

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Spinner
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.ImageView
import org.example.demo.productos.models.*
import org.example.demo.routes.RoutesManager
import org.example.demo.view.viewModel.SeleccionarAsientoViewModel
import org.example.demo.view.viewModel.SeleccionarComplViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging


private val logger = logging()
class SeleccionarComplViewController :KoinComponent{

    val viewCompl:SeleccionarComplViewModel by inject()


    @FXML
    lateinit var tipoTextfield: TextField
    @FXML
    lateinit var nombreTextfield: TextField
    @FXML
    lateinit var precioTextfield: TextField
    @FXML
    lateinit var cantidadSpinner: Spinner<Int>
    @FXML
    lateinit var añadirComplementoButton:Button
    @FXML
    lateinit var imagenComplemento: ImageView
    @FXML
    lateinit var volverButton:Button
    @FXML
    lateinit var continuarButton: Button
    @FXML
    lateinit var filtroNombre:TextField
    @FXML
    lateinit var tablaComplementos:TableView<Complemento>
    @FXML
    lateinit var nombreColumna: TableColumn<Complemento,String>
    @FXML
    lateinit var precioColumna: TableColumn<Complemento,String>
    @FXML
    lateinit var filtroPrecio:ComboBox<String>

    private var complemmentosSeleccionas:MutableList<Complemento> = mutableListOf()
    private var complemennto:Complemento?=null

    @FXML
    fun initialize(){
        logger.debug { "inicializando controller seleccionar Complemento" }
        initDefaultEvents()
        initReactiveProperties()
        initDefaultValues()
    }

    private fun initDefaultValues() {
        tablaComplementos.items=
            FXCollections.observableList(viewCompl.state.value.complementos)
        nombreColumna.cellValueFactory = PropertyValueFactory("id")
        precioColumna.cellValueFactory = PropertyValueFactory("precio")

        filtroPrecio.items = FXCollections.observableList(viewCompl.state.value.precios)
        filtroPrecio.value ="All"
    }

    private fun initReactiveProperties() {
        viewCompl.state.addListener { _,_,newValue->
            tipoTextfield.text=newValue.tipo
            nombreTextfield.text=newValue.nombre
            precioTextfield.text=newValue.precio
            imagenComplemento.image = newValue.imagen
            complemmentosSeleccionas=newValue.complementosSeleccionados
            complemennto=newValue.complemento
        }
    }

    private fun initDefaultEvents() {
        volverButton.setOnAction { volverOnAction() }
        continuarButton.setOnAction { continuarOnAction() }
        tablaComplementos.selectionModel.selectedItemProperty().addListener { _,_,newValue->
            newValue?.let { onTableSelected(it) }
        }
        filtroPrecio.selectionModel.selectedItemProperty().addListener { _,_,newValue->
            newValue?.let { onComboSelected() }
        }
        filtroNombre.setOnKeyReleased {
            it?.let { onTextAction() }
        }
        añadirComplementoButton.setOnAction { añadirOnAction(complemennto!!) }
        
    }

    private fun añadirOnAction(complememnto:Complemento) {
        añadirComplemmento(complememnto)
    }

    private fun añadirComplemmento(complemento:Complemento) {
        if (complemento.id != "NOVALIDO" && complemmentosSeleccionas.size < 3){
            complemmentosSeleccionas.add(complemento)
            println(complemmentosSeleccionas.size)
            viewCompl.actualizarSeleccionados(complemmentosSeleccionas)
        }else{
            RoutesManager.alerta("Maximo 3 Complmenetos","El maximo de complemmentos que puedes seleccionar son 3")
        }

    }

    private fun onTextAction() {
        filtrarTabla()
    }

    private fun filtrarTabla() {
        tablaComplementos.items= FXCollections.observableList(
            viewCompl.filtrarComplementos(filtroPrecio.value,filtroNombre.text.uppercase())
        )
    }

    private fun onComboSelected() {
        filtrarTabla()
    }

    private fun onTableSelected(complemento: Complemento) {
        viewCompl.updateState(complemento)
    }

    private fun continuarOnAction() {
        RoutesManager.changeScene(view = RoutesManager.View.CARRITO, title = "Carrito")
    }

    private fun volverOnAction(){
        viewCompl.borrarSeleccionado()
        RoutesManager.changeScene(view = RoutesManager.View.SELECBUTACAS, title = "Seleccionar Butaca", height = 650.00)
    }

}