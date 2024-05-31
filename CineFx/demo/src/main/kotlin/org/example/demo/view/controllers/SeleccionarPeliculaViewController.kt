package org.example.demo.view.controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import org.example.demo.routes.RoutesManager
import org.example.demo.usuarios.viewModel.SeleccionarPeliculaViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging


private val logger= logging()
class SeleccionarPeliculaViewController :KoinComponent{

    val view: SeleccionarPeliculaViewModel by inject()

    @FXML
    lateinit var simioImage: ImageView
    @FXML
    lateinit var garfieldImage:ImageView
    @FXML
    lateinit var tarotImage:ImageView
    @FXML
    lateinit var especialistaImage:ImageView
    @FXML
    lateinit var cerrarSesionBoton:Button

    @FXML
    fun initialize(){
        logger.debug { "inicializando controller Seleccionar Pelicula" }
        initDefaultValues()
        initDefaultEvents()
    }

    private fun initDefaultEvents() {
        simioImage.setOnMouseClicked { simioOnAction() }
        garfieldImage.setOnMouseClicked { garfieldOnAction() }
        tarotImage.setOnMouseClicked { tarotOnAction() }
        especialistaImage.setOnMouseClicked { especialistaOnAction() }
        cerrarSesionBoton.setOnAction { cerrarSesionOnAction() }
    }

    private fun cerrarSesionOnAction() {
        RoutesManager.changeScene(view = RoutesManager.View.INICIO_SESION, title = "Inicio Sesion")
    }

    private fun simioOnAction() {
        view.state.value = view.state.value.copy(pelicula = "El Reino del Planeta De Los Simios")
        RoutesManager.changeScene(view = RoutesManager.View.SELECBUTACAS, title = "Seleccion Butaca Simio", height = 650.00)
    }

    private fun garfieldOnAction() {
        view.state.value = view.state.value.copy(pelicula = "Garfield La Pelicula")
        RoutesManager.changeScene(view = RoutesManager.View.SELECBUTACAS, title = "Seleccion Butaca Garfield", height = 650.00)
    }

    private fun tarotOnAction() {
        view.state.value = view.state.value.copy(pelicula = "Tarot")
        RoutesManager.changeScene(view = RoutesManager.View.SELECBUTACAS, title = "Seleccion Butaca Tarot", height = 650.00)
    }

    private fun especialistaOnAction() {
        view.state.value = view.state.value.copy(pelicula = "El Especialista")
        RoutesManager.changeScene(view = RoutesManager.View.SELECBUTACAS, title = "Seleccion Butaca Especialista", height = 650.00)
    }


    private fun initDefaultValues() {
        simioImage.image = Image(RoutesManager.getResourceAsStream("images/simios.jpeg"))
        garfieldImage.image = Image(RoutesManager.getResourceAsStream("images/garfield.jpeg"))
        tarotImage.image = Image(RoutesManager.getResourceAsStream("images/TAROT.jpg"))
        especialistaImage.image = Image(RoutesManager.getResourceAsStream("images/especialista.jpeg"))
    }
}