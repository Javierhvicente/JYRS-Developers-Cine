package org.example.demo.view.controllers

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.ImageView
import javafx.stage.FileChooser
import org.example.demo.locale.toDefaultMoneyString
import org.example.demo.productos.models.Butaca
import org.example.demo.routes.RoutesManager
import org.example.demo.usuarios.viewModel.GestionButacaViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger= logging()
class GestionButacaViewController:KoinComponent {
    val view: GestionButacaViewModel by inject()
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

        columnaPrecio.setCellValueFactory {
            SimpleStringProperty(it.value.precio.toDefaultMoneyString())
        }

        filtroEstado.items=FXCollections.observableList(view.state.value.estados)
        filtroOcupacion.items=FXCollections.observableList(view.state.value.ocupaciones)
        filtroTipo.items=FXCollections.observableList(view.state.value.tipos)

        filtroTipo.value = "Todos"
        filtroOcupacion.value = "Todas"
        filtroEstado.value = "Todos"

        exportarJsonButton.text = "Exportar"

    }

    private fun initDefaultEvents() {
        menuAdmin.setOnMouseClicked { menuOnAction() }
        modificarButton.setOnAction { modificarOnAction() }
        importarButacasButton.setOnAction { importarOnAction() }
        exportarJsonButton.setOnAction { exportarOnAction() }

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

    private fun exportarOnAction() {
        logger.debug { "Exportar butacas" }
        FileChooser().run {
            title = "Importar Butacas"
            extensionFilters.add(FileChooser.ExtensionFilter("JSON", "*.json"))
            extensionFilters.add(FileChooser.ExtensionFilter("CSV", "*.csv"))
            showSaveDialog(RoutesManager.activeStage)
        }?.let {
            logger.debug { "Exportar Butacas: $it" }
            if (!view.exportar(it)){
                RoutesManager.alerta("Exportar","Las butacas no se han exportado")
            }else{
                RoutesManager.alerta("Exportar","Butacas exportadas con exito",Alert.AlertType.CONFIRMATION)
            }
        }
    }

    private fun importarOnAction() {
        logger.debug { "Importar butacas" }
        FileChooser().run {
            title = "Importar Butacas"
            extensionFilters.add(FileChooser.ExtensionFilter("CSV", "*.csv"))
            extensionFilters.add(FileChooser.ExtensionFilter("JSON", "*.json"))
            showOpenDialog(RoutesManager.activeStage)
        }?.let {
            logger.debug { "importarOnAction: $it" }
            if (!view.importarButacas(it)){
                RoutesManager.alerta("Importar","No se han importado las butacas")
            }else{
                RoutesManager.alerta("Importar","Butacas importadas con exito",Alert.AlertType.CONFIRMATION)
            }
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