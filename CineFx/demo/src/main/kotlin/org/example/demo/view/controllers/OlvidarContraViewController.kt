package org.example.demo.view.controllers

import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import org.example.demo.routes.RoutesManager
import org.example.demo.usuarios.viewModel.OlvidarContraViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger= logging()
class OlvidarContraViewController :KoinComponent{
    private val view: OlvidarContraViewModel by inject()
    @FXML
    lateinit var textEmail: TextField
    @FXML
    lateinit var botonVolver: Button
    @FXML
    lateinit var newPassword: PasswordField
    @FXML
    lateinit var botonRestablecer: Button
    @FXML
    lateinit var confirmPassword: PasswordField

    @FXML
    fun initialize(){
        logger.debug { "inicializando controller olvidar contraseña" }
        initDefaultEvents()
    }

    private fun initDefaultEvents() {
        botonRestablecer.setOnAction { restablecerOnAction() }
        botonVolver.setOnAction { volverOnAction() }
    }

    private fun volverOnAction() {
        RoutesManager.changeScene(title = "Iniciar Sesion", view = RoutesManager.View.INICIO_SESION)
    }

    private fun restablecerOnAction() {
        if (newPassword.text != confirmPassword.text)RoutesManager.alerta("Contraseña Incorrecta","Las contraseñas deben coincidir")
        if (textEmail.text.isBlank())RoutesManager.alerta("E-mail","El email no debe estar vacio")
        if (newPassword.text.isBlank())RoutesManager.alerta("Contraseña","La contraseña no debe estar vacia")
        if (newPassword.text == confirmPassword.text){
            if (!view.cambiarContraseña(textEmail.text,confirmPassword.text)){
                RoutesManager.alerta("Usuario","El usuario debe estar registrado")
            }
        }
        if (
            textEmail.text.isNotBlank()&&
            newPassword.text.isNotBlank()&&
            newPassword.text == confirmPassword.text&&
            view.cambiarContraseña(textEmail.text,confirmPassword.text)
        ){
            RoutesManager.alerta("Contraseña cambiada","La contraseña a sido cambiadsa con exito",Alert.AlertType.CONFIRMATION)
            RoutesManager.changeScene(view = RoutesManager.View.INICIO_SESION, title = "Inicio Sesion")
        }

    }
}