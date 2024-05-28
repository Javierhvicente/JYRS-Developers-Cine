package org.example.demo.view.controllers

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.ImageView
import org.example.demo.productos.models.Complemento
import org.example.demo.routes.RoutesManager
import org.example.demo.view.viewModel.GestionComplementoViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging


private val logger= logging()
class GestionComplViewController:KoinComponent {
    val view:GestionComplementoViewModel by inject()
    @FXML
    lateinit var menuAdmin: ImageView
    @FXML
    lateinit var imagenComplemento :ImageView
    @FXML
    lateinit var tipoTextfield: TextField
    @FXML
    lateinit var nombreTextfield: TextField
    @FXML
    lateinit var precioTextfield: TextField
    @FXML
    lateinit var añadirButton:Button
    @FXML
    lateinit var modificarButton: Button
    @FXML
    lateinit var eliminarButton: Button
    @FXML
    lateinit var filtroNombre:TextField
    @FXML
    lateinit var tablaComplementos:TableView<Complemento>
    @FXML
    lateinit var nombreColumna:TableColumn<Complemento,String>
    @FXML
    lateinit var precioColumna:TableColumn<Complemento,String>
    @FXML
    lateinit var filtroPrecio:ComboBox<String>

    @FXML
    fun initialize(){
        logger.debug { "inicializando controller Gestion de Complementos" }
        initDefaultEvents()
        initDefaultValues()
        initReactiveProperties()
    }

    private fun initDefaultValues() {
        tablaComplementos.items = FXCollections.observableList(view.state.value.complementos)

        nombreColumna.cellValueFactory = PropertyValueFactory("id")
        precioColumna.cellValueFactory = PropertyValueFactory("precio")

        filtroPrecio.items = FXCollections.observableList(view.state.value.precios)
        filtroPrecio.value = "All"
    }
    private fun initReactiveProperties() {
        view.state.addListener { _,_,newValue->
            tipoTextfield.text=newValue.tipo
            nombreTextfield.text=newValue.nombre
            precioTextfield.text=newValue.precio
            imagenComplemento.image = newValue.imagen
            tablaComplementos.items = FXCollections.observableList(newValue.complementos)
        }
    }
    private fun initDefaultEvents() {
        menuAdmin.setOnMouseClicked { menuOnAction() }
        añadirButton.setOnAction { añadirOnAction()  }
        modificarButton.setOnAction { modificarOnAction() }
        eliminarButton.setOnAction { elimarOnAction() }
        tablaComplementos.selectionModel.selectedItemProperty().addListener { _,_,newValue->
            newValue?.let { onTableSelected(it) }
        }
        filtroPrecio.selectionModel.selectedItemProperty().addListener { _,_,newValue->
            newValue?.let { filtrarTabla() }
        }
        filtroNombre.setOnKeyReleased {
            it?.let { filtrarTabla() }
        }
    }

    private fun elimarOnAction() {
        if (view.state.value.complementoSeleccionado == null)RoutesManager.alerta("Complememnto No Seleccionado","No puedes eliminar un complemento si no seleccionas uno")
        else{
            if (RoutesManager.alertaEliminar(view.state.value.complementoSeleccionado!!.id)){
                view.eliminarComplemento(view.state.value.complementoSeleccionado!!.id)
                view.actualizarComplementos()
            }
        }
    }

    private fun filtrarTabla() {
        tablaComplementos.items= FXCollections.observableList(
            view.filtrarComplementos(precio = filtroPrecio.value, nombre = filtroNombre.text.uppercase())
        )
    }

    private fun onTableSelected(it: Complemento) {
        view.updateState(it)
    }

    private fun añadirOnAction() {
        RoutesManager.initDetalle(view = RoutesManager.View.NEWCOMPL, title = "Añadir Complemento")
    }

    private fun modificarOnAction() {
        if (view.state.value.complementoSeleccionado == null)RoutesManager.alerta("Complememnto No Seleccionado","No puedes modificar un complemento si no seleccionas uno")
        else{
            RoutesManager.initDetalle(view = RoutesManager.View.ACTUALIZARCOMPL, title = "Actualizar Complemento")
        }
    }

    private fun menuOnAction() {
        RoutesManager.changeScene(view = RoutesManager.View.MENUADMIN, title = "Menu Admin")
    }

}