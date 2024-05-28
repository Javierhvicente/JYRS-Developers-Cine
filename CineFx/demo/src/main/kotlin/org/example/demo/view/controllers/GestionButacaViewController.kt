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
import org.example.demo.productos.models.Butaca
import org.example.demo.routes.RoutesManager
import org.example.demo.view.viewModel.GestionButacaViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger= logging()
class GestionButacaViewController:KoinComponent {
    val view:GestionButacaViewModel by inject()
    @FXML
    lateinit var menuAdmin:ImageView
    @FXML
    lateinit var estadoTextfield: TextField
    @FXML
    lateinit var textoTextfield: TextField
    @FXML
    lateinit var precioTextField: TextField
    @FXML
    lateinit var modificarButton: Button
    @FXML
    lateinit var butacaImage:ImageView
    @FXML
    lateinit var idTextField: TextField
    @FXML
    lateinit var ocupacionTextField: TextField
    @FXML
    lateinit var exportarJsonButton: Button
    @FXML
    lateinit var importarButacasButton: Button
    @FXML
    lateinit var filtroEstado:ComboBox<String>
    @FXML
    lateinit var filtroTipo:ComboBox<String>
    @FXML
    lateinit var filtroOcupacion:ComboBox<String>
    @FXML
    lateinit var idButaca:TextField
    @FXML
    lateinit var tablaButacas:TableView<Butaca>
    @FXML
    lateinit var columnaId:TableColumn<Butaca,String>
    @FXML
    lateinit var columnaEstado:TableColumn<Butaca,String>
    @FXML
    lateinit var columnaTipo:TableColumn<Butaca,String>
    @FXML
    lateinit var columnaPrecio:TableColumn<Butaca,String>
    @FXML
    lateinit var columnaOcupación:TableColumn<Butaca,String>

    @FXML
    fun initialize(){
        logger.debug { "inicializando controller Gestion de Butacas" }
        initDefaultEvents()
        initDefaultValues()
        initReactiveProperties()
    }

    private fun initReactiveProperties() {
        view.state.addListener { _,_,newState->
            idTextField.text=newState.id
            estadoTextfield.text=newState.estado
            textoTextfield.text = newState.tipo
            precioTextField.text = newState.precio
            ocupacionTextField.text = newState.ocupacion
            tablaButacas.items=FXCollections.observableList(newState.butacas)
        }
    }

    private fun initDefaultValues() {
        tablaButacas.items = FXCollections.observableList(view.state.value.butacas)
        columnaEstado.cellValueFactory= PropertyValueFactory("Estado")
        columnaId.cellValueFactory= PropertyValueFactory("Id")
        columnaOcupación.cellValueFactory= PropertyValueFactory("Ocupacion")
        columnaPrecio.cellValueFactory= PropertyValueFactory("Precio")
        columnaTipo.cellValueFactory= PropertyValueFactory("Tipo")

        filtroEstado.items=FXCollections.observableList(view.state.value.estados)
        filtroOcupacion.items=FXCollections.observableList(view.state.value.ocupaciones)
        filtroTipo.items=FXCollections.observableList(view.state.value.tipos)

        filtroTipo.value = "Todos"
        filtroOcupacion.value = "Todas"
        filtroEstado.value = "Todos"


    }

    private fun initDefaultEvents() {
        menuAdmin.setOnMouseClicked { menuOnAction() }
        modificarButton.setOnAction { modificarOnAction() }
        tablaButacas.selectionModel.selectedItemProperty().addListener { _,_,newValue->
            newValue?.let { onTableSelected(it) }
        }
        filtroEstado.selectionModel.selectedItemProperty().addListener { _,_,newValue->
            newValue?.let { filtrarTabla() }
        }
        filtroOcupacion.selectionModel.selectedItemProperty().addListener { _,_,newValue->
            newValue?.let { filtrarTabla() }
        }
        filtroTipo.selectionModel.selectedItemProperty().addListener { _,_,newValue->
            newValue?.let { filtrarTabla() }
        }
        idButaca.setOnKeyReleased {
            it?.let { filtrarTabla() }
        }
    }

    private fun filtrarTabla() {
        var estado=""
        if (filtroEstado.value != null){
            estado=filtroEstado.value
        }
        var tipo=""
        if (filtroTipo.value != null){
            tipo=filtroTipo.value
        }
        var ocupacion=""
        if (filtroOcupacion.value != null){
            ocupacion=filtroOcupacion.value
        }
        tablaButacas.items = FXCollections.observableList(
            view.filtrarButacas(
                id = idButaca.text.trim(),
                estado = estado,
                tipo = tipo,
                ocupacion = ocupacion
            )
        )
    }

    private fun onTableSelected(butaca: Butaca) {
        view.updateState(butaca)
    }

    private fun modificarOnAction() {
        if (view.state.value.butacaSeleccionada == null)RoutesManager.alerta("Butaca No Seleccionada","No puede modificar una butaca si no selecciona una previamente")
        else{
            RoutesManager.initDetalle(view = RoutesManager.View.MODBUTACA, title = "Menu Admin")
        }
    }

    private fun menuOnAction() {
        RoutesManager.changeScene(view = RoutesManager.View.MENUADMIN, title = "Menu Admin")
    }

}