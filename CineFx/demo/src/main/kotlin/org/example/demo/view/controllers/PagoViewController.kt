package org.example.demo.view.controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import org.example.demo.routes.RoutesManager
import org.example.demo.view.viewModel.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger= logging()
class PagoViewController:KoinComponent {

    val viewButacas: SeleccionarAsientoViewModel by inject()
    val viewBCompl: SeleccionarComplViewModel by inject()
    val viewCarrito: CarritoViewModel by inject()
    val viewLogin: LoginViewModel by inject()
    val view: PagoViewModel by inject()

    @FXML
    lateinit var menuImagen: ImageView

    @FXML
    lateinit var fxNombreTextField: TextField

    @FXML
    lateinit var fxNumeroTextField: TextField

    @FXML
    lateinit var fxFechaTextField: TextField

    @FXML
    lateinit var fxCvcTextField: TextField

    @FXML
    lateinit var fxPagarButton: Button

    @FXML
    fun initialize() {
        logger.debug { "inicializando controller pago" }
        initDefaultEvents()
        initDefaultValues()
    }

    private fun initDefaultValues() {
        logger.debug { "inicializando valores por defecto de pago" }
        fxPagarButton.text = "Pagar ${viewCarrito.state.value.total}"
    }

    private fun initDefaultEvents() {
        logger.debug { "inicializando eventos por defecto de pago" }
        menuImagen.setOnMouseClicked { menuOnAction() }
        fxPagarButton.setOnAction { pagarOnAction() }
    }

    private fun pagarOnAction() {
        logger.debug { "inicializando pago" }
        if (!view.validarNumerTarjeta(fxNumeroTextField.text)) RoutesManager.alerta(
            "Numero de tarjeta",
            "Introduce un numero de tarjeta valido"
        )
        if (!view.validarFecha(fxFechaTextField.text)) RoutesManager.alerta(
            "Fecha caducidad",
            "Introduce una fecha valida"
        )
        if (!view.validarCvc(fxCvcTextField.text)) RoutesManager.alerta("CVC", "Introduce un CVC valido")
        if (view.validarTarjeta(fxNumeroTextField.text, fxFechaTextField.text, fxCvcTextField.text)) {
            if (view.crearVenta(
                    cliente = viewLogin.state.value.usuario!!,
                    listaComplementos = viewCarrito.state.value.complementos,
                    listaButaca = viewCarrito.state.value.butacas
                )
            ) {
                logger.debug { "venta creada" }
                view.state.value.pelicula = viewCarrito.state.value.pelicula
                viewButacas.actualizarButacas()
                viewBCompl.borrarSeleccionado()
                RoutesManager.changeScene(view = RoutesManager.View.TICKET, title = "Detalle de tu compra")
            } else {
                logger.debug { "venta no creada" }
                RoutesManager.alerta("Venta", "No se a podido crear la venta")
            }

        }

    }

    private fun menuOnAction() {
        logger.debug { "cambiando escena a carrito desde pago" }
        RoutesManager.changeScene(view = RoutesManager.View.CARRITO, title = "Carrito")
    }
}