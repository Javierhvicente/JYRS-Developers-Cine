package org.example.demo.view.controllers

import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.Hyperlink
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import org.example.demo.routes.RoutesManager
import org.example.demo.usuarios.models.Admin
import org.example.demo.usuarios.viewModel.LoginViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.Platform
import org.lighthousegames.logging.logging


private val logger= logging()
class LoginViewController:KoinComponent {

    val view: LoginViewModel by inject()
    @FXML
    lateinit var nombreLogin: TextField
    @FXML
    lateinit var contraseñaLogin:PasswordField
    @FXML
    lateinit var botonLogin:Button
    @FXML
    lateinit var botonRegister:Button
    @FXML
    lateinit var olvidaContraseñaLink:Hyperlink
    @FXML
    lateinit var imagenUser:ImageView
    @FXML
    lateinit var imagenUsers:ImageView
    @FXML
    lateinit var imagenKey:ImageView
    @FXML
    lateinit var imagenLogo:ImageView

    @FXML
    fun initialize(){
        logger.debug { "inicializando controller login" }
        initDefaultEvents()
    }

    private fun initDefaultEvents() {
        logger.debug { "inicializando eventos por defecto controller login" }
        botonLogin.setOnAction { botonLoginOnAction() }
        botonRegister.setOnAction { botonRegisterOnAction() }
        olvidaContraseñaLink.setOnAction { linkOlvidarContraseñaOnAction() }
    }

    private fun linkOlvidarContraseñaOnAction() {
        logger.debug { "olvidar contraseña desde login" }
        RoutesManager.changeScene(view = RoutesManager.View.OLVIDARCONTRA, title = "Recuperar Contraseña")
    }

    private fun botonRegisterOnAction() {
        logger.debug { "registrar desde login" }
        RoutesManager.changeScene(view = RoutesManager.View.REGISTRO, title = "Registro")
    }

    private fun botonLoginOnAction() {
       if (view.validarAdmin(nombreLogin.text, contraseñaLogin.text)){
           logger.debug { "admin logeado" }

           view.establecerUsuario(nombreLogin.text)
        RoutesManager.changeScene(view = RoutesManager.View.MENUADMIN, title = "Menu admin")
       }else{
           if (!view.validarUsuario(nombreLogin.text)){
               logger.error { "usuario no valido" }
               RoutesManager.alerta("Usuario","El usuario Introducido no es valido, Recuerda que debe estar Registrado")
           }
           if (!view.validarContraseña(nombreLogin.text,contraseñaLogin.text)){
               logger.error { "contraseña no valida" }
               RoutesManager.alerta("Contraseña","La contraseña no coincide")
           }
           if (view.validarContraseña(nombreLogin.text, contraseñaLogin.text)){
               logger.debug { "usuario ${nombreLogin.text} logeado" }
               view.establecerUsuario(nombreLogin.text)
               RoutesManager.changeScene(view = RoutesManager.View.SELECPELICULAS, title = "Seleccionar Pelicula")
          }
       }
    }


}