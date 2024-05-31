package org.example.demo.view.controllers

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.FileChooser
import org.example.demo.routes.RoutesManager
import org.example.demo.usuarios.viewModel.ActualizarComplViewModel
import org.example.demo.usuarios.viewModel.GestionComplementoViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.io.File
import kotlin.math.log

private val logger= logging()
class ActualizarComplViewController:KoinComponent {

    val view: ActualizarComplViewModel by inject()
    val viewGest: GestionComplementoViewModel by inject()

    @FXML
    lateinit var fxTextFieldPrecio:TextField
    @FXML
    lateinit var fxtextFieldBebida:ComboBox<String>
    @FXML
    lateinit var fxTextFieldNombre:TextField
    @FXML
    lateinit var fxBotonGuardar:Button
    @FXML
    lateinit var fxBotonLimpiar:Button
    @FXML
    lateinit var fxBotonCancelar:Button
    @FXML
    lateinit var imagenComplemento:ImageView

    private var imagen=viewGest.state.value.complementoSeleccionado!!.imagen
    @FXML
    fun initialize(){
        logger.debug { "inicializando controller Actualizar complemento" }
        initDefaultEvents()
        initDefaulValues()
    }

    private fun initDefaulValues() {
        fxtextFieldBebida.items=FXCollections.observableList(view.state.value.tipos)
        fxTextFieldNombre.text=viewGest.state.value.complementoSeleccionado!!.id
        fxtextFieldBebida.value=viewGest.state.value.complementoSeleccionado!!.tipo
        fxTextFieldPrecio.text=viewGest.state.value.complementoSeleccionado!!.precio.toString()
        val imagen= File("imagenes",viewGest.state.value.complementoSeleccionado!!.imagen)
        imagenComplemento.image = Image(imagen.absoluteFile.toURI().toString())
        println(viewGest.state.value.complementoSeleccionado!!.imagen)
    }

    private fun initDefaultEvents() {
        fxBotonGuardar.setOnAction { guardarOnAction() }
        fxBotonLimpiar.setOnAction { limpiarOnAction() }
        imagenComplemento.setOnMouseClicked { onImageAction() }
    }

    private fun limpiarOnAction() {
        fxTextFieldPrecio.text=""
    }

    private fun onImageAction(){
        logger.debug { "onImageAction" }
        FileChooser().run {
            title = "Selecciona una imagen"
            extensionFilters.addAll(FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"))
            showOpenDialog(RoutesManager.activeStage)
        }?.let {
            imagen=it.name
        }
    }

    private fun guardarOnAction() {
        if (fxTextFieldNombre.text.isBlank())RoutesManager.alerta("Nombre","Recuerda que el nombre no puede estar vacio")
        if (fxTextFieldPrecio.text.isBlank())RoutesManager.alerta("Precio","Recuerda que el precio no puede estar vacio")
        if (fxTextFieldPrecio.text.toDoubleOrNull() == null)RoutesManager.alerta("Precio","Recuerda que el precio debe ser un double 0.0")
        if (fxTextFieldPrecio.text.isNotBlank() &&
            fxTextFieldNombre.text.isNotBlank() &&
            fxTextFieldPrecio.text.toDoubleOrNull()!= null){
            view.actualizar(id = fxTextFieldNombre.text, precio = fxTextFieldPrecio.text, tipo = fxtextFieldBebida.value, imagen =imagen )
            viewGest.initState(view.allComplementos())
        }
    }
}