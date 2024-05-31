package org.example.demo.routes


import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.ButtonType
import javafx.scene.control.ScrollPane
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.stage.Window
import javafx.stage.WindowEvent
import org.koin.core.context.startKoin
import org.lighthousegames.logging.logging
import java.io.InputStream
import java.lang.RuntimeException
import java.net.URL

private val logger= logging()
object RoutesManager {
    lateinit var mainStage:Stage
    lateinit var _activeStage:Stage
    val activeStage:Stage
        get() = _activeStage
    lateinit var app:Application

    enum class View(val fxml:String){
        INICIO_SESION("views/inicio-sesion.fxml"),
        REGISTRO("views/registro.fxml"),
        SELECPELICULAS("views/selec-peliculas.fxml"),
        OLVIDARCONTRA("views/olvidar-contra.fxml"),
        SELECBUTACAS("views/asientosCine.fxml"),
        SELECCOMPL("views/selec-compl.fxml"),
        CARRITO("views/carrito.fxml"),
        PAGO("views/pago.fxml"),
        TICKET("views/ticket.fxml"),
        MENUADMIN("views/menu-admin.fxml"),
        GESTIONCOMPL("views/gestion-compl.fxml"),
        ACERCA_DE("views/acerca-de.fxml"),
        ACTUALIZARCOMPL("views/actualizar-compl.fxml"),
        NEWCOMPL("views/nuevoComplemento.fxml"),
        GESTIONBUTACA("views/gestionButaca.fxml"),
        MODBUTACA("views/modButaca.fxml"),
        ESTADOCINE("views/estadoCine.fxml")
    }
    
    fun initMainStage(stage:Stage){
        logger.debug { "Inicializando Inicio de sesion" }
        val fxmlLoader=FXMLLoader(getResource(View.INICIO_SESION.fxml))
        val parentRoot = fxmlLoader.load<Pane>()
        val mainScene = Scene(parentRoot,700.0,500.0)

        stage.apply {
            title="Inicio Sesion"
            isResizable=false
            scene=mainScene
            _activeStage=stage
            mainStage=stage
            icons.add(Image(getResourceAsStream("images/jyrs-cinema.png")))
            setOnCloseRequest { onAppExit(event = it) }
            centerOnScreen()
        }.show()
    }

    fun changeScene(
        myStage: Stage = activeStage,
        view: View,
        title:String,
        width: Double = 700.0,
        height: Double = 500.0,
    ) {
        logger.debug { "Cambiando escena a $title" }
        var parentRoot: Parent
        if (view==View.SELECPELICULAS) parentRoot = FXMLLoader.load<ScrollPane>(this.getResource(view.fxml))
        else parentRoot = FXMLLoader.load<Pane>(this.getResource(view.fxml))

        val scene = Scene(parentRoot, width, height)
        myStage.scene = scene
        myStage.title= title
    }

    fun initDetalle(
        view: View,
        title: String
    ) {
        logger.debug { "Inicializando Detalle $title" }

        val fxmlLoader = FXMLLoader(getResource(view.fxml))
        val parentRoot = fxmlLoader.load<Pane>()
        val scene = Scene(parentRoot, 230.0, 340.0)
        val stage = Stage()
        stage.title = title
        stage.scene = scene
        stage.initOwner(mainStage)
        stage.initModality(Modality.WINDOW_MODAL)
        stage.isResizable = false
        stage.icons.add(Image(getResourceAsStream("images/logo.png")))
        stage.show()
    }


    fun initAcercaDeStage(){
        logger.debug { "Iniciando AcercaDe" }
        val fxmlLoader=FXMLLoader(getResource(View.ACERCA_DE.fxml))
        val parentRoot=fxmlLoader.load<Pane>()
        val mainScene=Scene(parentRoot,400.0,200.0)
        Stage().apply {
            title="Acerca de"
            scene=mainScene
            isResizable=false
            initOwner(mainStage)
            initModality(Modality.WINDOW_MODAL)
            centerOnScreen()
            icons.add(Image(getResourceAsStream("icons/informacion.png")))
        }.show()
    }


    fun onAppExit(
        title:String="Salir de ${mainStage.title}",
        headerText:String="Va a salir de ${mainStage.title}, ¿estas seguro?",
        contentText:String="Si sale, perdera la informacion no guardada",
        event: WindowEvent?=null
    ) {
        logger.debug { "Cerrando App" }
        Alert(Alert.AlertType.CONFIRMATION).apply {
            this.title=title
            this.contentText=contentText
            this.headerText=headerText
        }.showAndWait().ifPresent {
            if(it== ButtonType.OK) Platform.exit()
            else event?.consume()
        }

    }
    fun alerta(mensaje:String,contenido:String,alertType: AlertType=AlertType.ERROR){
        logger.debug { "Alerta $mensaje" }
        Alert(alertType).apply {
            this.title= mainStage.title
            this.headerText="$mensaje "
            this.contentText=contenido
        }.showAndWait()
    }
    fun alertaEliminar(nombre:String):Boolean{

        var eliminar:Boolean=false
        Alert(Alert.AlertType.ERROR).apply {
            this.title="Elimar Complemento"
            this.headerText="Eliminar $nombre"
            this.contentText="Si elimina $nombre perdera toda su informacion"
        }.showAndWait().ifPresent {opcion->
            if (opcion == ButtonType.OK) {
                logger.debug { "Elimnar Complemento $nombre" }
                eliminar=true
            }
        }
        return eliminar
    }

    fun getResourceAsStream(icon: String): InputStream {
        logger.debug { "Obteniendo $icon" }
        return app::class.java.getResourceAsStream(icon)
            ?:throw RuntimeException("No se a podido cargar $icon")

    }



    fun getResource(fxml: String): URL{
        logger.debug { "Obteniendo $fxml" }
        return app::class.java.getResource(fxml)
            ?:throw RuntimeException("No se a podido cargar $fxml")
    }
}