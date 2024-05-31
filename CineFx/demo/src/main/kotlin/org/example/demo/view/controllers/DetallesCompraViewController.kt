package org.example.demo.view.controllers

import com.vaadin.open.Open
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.image.ImageView
import org.example.demo.locale.toDefaultMoneyString
import org.example.demo.productos.models.Bebida
import org.example.demo.productos.models.Butaca
import org.example.demo.productos.models.Comida
import org.example.demo.productos.models.Complemento
import org.example.demo.routes.RoutesManager
import org.example.demo.usuarios.viewModel.CarritoViewModel
import org.example.demo.usuarios.viewModel.DetallesCompraViewModel
import org.example.demo.usuarios.viewModel.LoginViewModel
import org.example.demo.usuarios.viewModel.PagoViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging



private val logger= logging()
class DetallesCompraViewController: KoinComponent {

    val viewLogin: LoginViewModel by inject()
    val viewPago: PagoViewModel by inject()
    val view: DetallesCompraViewModel by inject()

    @FXML
    lateinit var imagenMenu: ImageView
    @FXML
    lateinit var nombreLabel: Label
    @FXML
    lateinit var correoLabel: Label
    @FXML
    lateinit var tituloLabel: Label
    @FXML
    lateinit var complementosLista: TextArea
    @FXML
    lateinit var butacasLista: TextArea
    @FXML
    lateinit var fxTotalLabel: Label
    @FXML
    lateinit var fxBotonCerrarSesion:Button
    @FXML
    lateinit var fxBotonSeleccionarPelicula:Button
    @FXML
    lateinit var fxBotonImprimirPdf:Button


    @FXML
    fun initialize(){
        logger.debug { "inicializando controller Detalles de compra" }
        initDefaultEvents()
        initDefaultValues()
    }

    private fun initDefaultValues() {
        logger.debug { "inicializando valores por defecto Detalles de compra" }

        nombreLabel.text = viewPago.state.value.venta.cliente.nombre
        correoLabel.text = viewPago.state.value.venta.cliente.email

        tituloLabel.text = viewPago.state.value.pelicula

        val complementos= mutableListOf<String>()
        val butacas= mutableListOf<String>()
        viewPago.state.value.venta.lineas.forEach {
            when(it.producto){
                is Butaca->butacas.add("Butaca ${it.producto.id}-${it.producto.precio.toDefaultMoneyString()}")
                is Complemento->{
                    complementos.add("Complemento ${it.producto.id}-${it.producto.precio.toDefaultMoneyString()}")
                }
            }
        }

        butacasLista.text = butacas.joinToString(separator = "\n")
        complementosLista.text = complementos.joinToString(separator = "\n")

        fxTotalLabel.text = ((viewPago.state.value.venta.total * 0.21) + viewPago.state.value.venta.total).toDefaultMoneyString()

    }

    private fun initDefaultEvents() {
        logger.debug { "inicializando eventos por defecto Detalles de compra" }
        imagenMenu.setOnMouseClicked { menuOnAction() }
        fxBotonSeleccionarPelicula.setOnAction { menuOnAction() }
        fxBotonCerrarSesion.setOnAction { cerrarSesionOnAction() }
        fxBotonImprimirPdf.setOnAction { pdfOnAction() }
    }

    private fun pdfOnAction() {
        logger.debug { "creando html Detalles de compra" }
        Open.open(view.guardarPdf(viewPago.state.value.venta, viewPago.state.value.pelicula))

    }

    private fun cerrarSesionOnAction() {
        logger.debug { "cerrando sesion Detalles de compra" }
        viewLogin.state.value = viewLogin.state.value.copy( usuario = null )
        RoutesManager.changeScene(view = RoutesManager.View.INICIO_SESION, title = "Inicio De Sesion")
    }

    private fun menuOnAction() {
        logger.debug { "cambiando a seleccionar pelicula desde Detalles de compra" }
        RoutesManager.changeScene(view = RoutesManager.View.SELECPELICULAS, title = "Seleccionar Pelicula")
    }


}