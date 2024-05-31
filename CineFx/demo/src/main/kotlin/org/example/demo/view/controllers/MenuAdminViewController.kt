package org.example.demo.view.controllers

import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.Hyperlink
import javafx.stage.FileChooser
import org.example.demo.routes.RoutesManager
import org.example.demo.usuarios.viewModel.ZipViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger= logging()
class MenuAdminViewController:KoinComponent {
    val view: ZipViewModel by inject()

    @FXML
    lateinit var gestionButacas :Hyperlink
    @FXML
    lateinit var gestionComplementos :Hyperlink
    @FXML
    lateinit var estadoCine :Hyperlink
    @FXML
    lateinit var botonCerrarSesion:Button
    @FXML
    lateinit var zipButton: Button
    @FXML
    lateinit var botonUnzip:Button

    @FXML
    fun initialize(){
        logger.debug { "inicializando controller Menu Admin" }
        initDefaultEvents()
    }

    private fun initDefaultEvents() {
        gestionComplementos.setOnAction { complemenetosOnAction() }
        gestionButacas.setOnAction { butacasOnAction() }
        estadoCine.setOnAction { estadoOnAction() }
        botonCerrarSesion.setOnAction { cerrarSesionOnAction() }
        zipButton.setOnAction { zipOnAction() }
        botonUnzip.setOnAction { unzipOnAction() }

    }

    private fun unzipOnAction() {
        logger.debug { "onUnzipAction" }
        FileChooser().run {
            title = "Importar desde Zip"
            extensionFilters.add(FileChooser.ExtensionFilter("ZIP", "*.zip"))
            showOpenDialog(RoutesManager.activeStage)
        }?.let {
            logger.debug { "onAbrirAction: $it" }
            if (!view.unzip(it)){
                RoutesManager.alerta("Zip","El zip no a descomprimirse")
            }else{
                RoutesManager.alerta("Zip","Zip descomprimido con exito",Alert.AlertType.CONFIRMATION)
            }
        }
    }

    private fun zipOnAction() {
        logger.debug { "onZipAction" }
        FileChooser().run {
            title = "Exportar a Zip"
            extensionFilters.add(FileChooser.ExtensionFilter("ZIP", "*.zip"))
            showSaveDialog(RoutesManager.activeStage)
        }?.let {
            logger.debug { "onAbrirAction: $it" }

            if (!view.crearZip(it)){
                RoutesManager.alerta("Zip","El zip no a podido crearase")
            }else{
                RoutesManager.alerta("Zip","Zip creado con exito",Alert.AlertType.CONFIRMATION)
            }
        }

    }


    private fun estadoOnAction() {
        RoutesManager.changeScene(view = RoutesManager.View.ESTADOCINE, title = "Estado de cine", height = 650.0)
    }

    private fun butacasOnAction() {
        RoutesManager.changeScene(view = RoutesManager.View.GESTIONBUTACA, title = "Gestion de Butaca")
    }

    private fun cerrarSesionOnAction() {
        RoutesManager.changeScene(view = RoutesManager.View.INICIO_SESION, title = "Inicio de Sesion")
    }

    private fun complemenetosOnAction() {
        RoutesManager.changeScene(view = RoutesManager.View.GESTIONCOMPL, title = "Gestion De Complementos")
    }

}