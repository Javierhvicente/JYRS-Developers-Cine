package org.example.demo.view.controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import org.example.demo.locale.toDefaultMoneyString
import org.example.demo.productos.models.*
import org.example.demo.routes.RoutesManager
import org.example.demo.view.viewModel.CarritoViewModel
import org.example.demo.view.viewModel.SeleccionarAsientoViewModel
import org.example.demo.view.viewModel.SeleccionarComplViewModel
import org.example.demo.view.viewModel.SeleccionarPeliculaViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.Platform
import org.lighthousegames.logging.logging


private val logger=logging()
class CarritoViewController:KoinComponent {

    val viewComp: SeleccionarComplViewModel by inject()
    val viewBut: SeleccionarAsientoViewModel by inject()
    val viewPeli: SeleccionarPeliculaViewModel by inject()
    val view:CarritoViewModel by inject()

    @FXML
    lateinit var productoLabel1: Label
    @FXML
    lateinit var productoLabel2: Label
    @FXML
    lateinit var productoLabel3: Label
    @FXML
    lateinit var productoLabel4: Label
    @FXML
    lateinit var productoLabel5: Label
    @FXML
    lateinit var productoLabel6: Label
    @FXML
    lateinit var productoLabel7: Label
    @FXML
    lateinit var productoLabel8: Label
    @FXML
    lateinit var precioLabel1: Label
    @FXML
    lateinit var precioLabel2: Label
    @FXML
    lateinit var precioLabel3: Label
    @FXML
    lateinit var precioLabel4: Label
    @FXML
    lateinit var precioLabel5: Label
    @FXML
    lateinit var precioLabel6: Label
    @FXML
    lateinit var precioLabel7: Label
    @FXML
    lateinit var precioLabel8: Label
    @FXML
    lateinit var fxLabelTotal: Label
    @FXML
    lateinit var botonContinuar:Button
    @FXML
    lateinit var botonVolver:Button

    private var butacas: MutableList<Butaca> = mutableListOf()
    private var complemetos: MutableList<Complemento> = mutableListOf()
    private var productos:MutableList<Producto> = mutableListOf()


    @FXML
    fun initialize(){
        logger.debug { "inicializando controller Carrito" }
        initDefaultEvents()
        initDefaultValues()
    }

    private fun initDefaultValues() {
        logger.debug { "inicializando valores por defecto" }
        butacas= viewBut.state.value.butacasSeleccionadas
        complemetos = viewComp.state.value.complementosSeleccionados
        view.asignarProductos(butacas,complemetos)
        añadirProductos(butacas,complemetos)

        if (productos.size > 0){
            when(productos[0]){
                is Butaca->{
                    productoLabel1.text= "Butaca : ${(productos[0] as Butaca).id}"
                    precioLabel1.text= (productos[0] as Butaca).precio.toDefaultMoneyString()
                }
                is Bebida->{
                    productoLabel1.text= "Complemento : ${(productos[0] as Bebida).id}"
                    precioLabel1.text= (productos[0] as Bebida).precio.toDefaultMoneyString()
                }
                is Comida->{
                    productoLabel1.text= "Complemento : ${(productos[0] as Comida).id}"
                    precioLabel1.text= (productos[0] as Comida).precio.toDefaultMoneyString()
                }
            }

        }
        if (productos.size > 1){
            when(productos[1]){
                is Butaca->{
                    productoLabel2.text= "Butaca : ${(productos[1] as Butaca).id}"
                    precioLabel2.text= (productos[1] as Butaca).precio.toDefaultMoneyString()
                }
                is Bebida->{
                    productoLabel2.text= "Complemento : ${(productos[1] as Bebida).id}"
                    precioLabel2.text= (productos[1] as Bebida).precio.toDefaultMoneyString()
                }
                is Comida->{
                    productoLabel2.text= "Complemento : ${(productos[1] as Comida).id}"
                    precioLabel2.text= (productos[1] as Comida).precio.toDefaultMoneyString()
                }
            }
        }
        if (productos.size > 2){
            when(productos[2]){
                is Butaca->{
                    productoLabel3.text= "Butaca : ${(productos[2] as Butaca).id}"
                    precioLabel3.text= (productos[2] as Butaca).precio.toDefaultMoneyString()
                }
                is Bebida->{
                    productoLabel3.text= "Complemento : ${(productos[2] as Bebida).id}"
                    precioLabel3.text= (productos[2] as Bebida).precio.toDefaultMoneyString()
                }
                is Comida->{
                    productoLabel3.text= "Complemento : ${(productos[2] as Comida).id}"
                    precioLabel3.text= (productos[2] as Comida).precio.toDefaultMoneyString()
                }
            }
        }
        if (productos.size > 3){
            when(productos[3]){
                is Butaca->{
                    productoLabel4.text= "Butaca : ${(productos[3] as Butaca).id}"
                    precioLabel4.text= (productos[3] as Butaca).precio.toDefaultMoneyString()
                }
                is Bebida->{
                    productoLabel4.text= "Complemento : ${(productos[3] as Bebida).id}"
                    precioLabel4.text= (productos[3] as Bebida).precio.toDefaultMoneyString()
                }
                is Comida->{
                    productoLabel4.text= "Complemento : ${(productos[3] as Comida).id}"
                    precioLabel4.text= (productos[3] as Comida).precio.toDefaultMoneyString()
                }
            }
        }
        if (productos.size > 4){
            when(productos[4]){
                is Butaca->{
                    productoLabel5.text= "Butaca : ${(productos[4] as Butaca).id}"
                    precioLabel5.text= (productos[4] as Butaca).precio.toDefaultMoneyString()
                }
                is Bebida->{
                    productoLabel5.text= "Complemento : ${(productos[4] as Bebida).id}"
                    precioLabel5.text= (productos[4] as Bebida).precio.toDefaultMoneyString()
                }
                is Comida->{
                    productoLabel5.text= "Complemento : ${(productos[4] as Comida).id}"
                    precioLabel5.text= (productos[4] as Comida).precio.toDefaultMoneyString()
                }
            }
        }

        if (productos.size > 5){
            when(productos[5]){
                is Butaca->{
                    productoLabel6.text= "Butaca : ${(productos[5] as Butaca).id}"
                    precioLabel6.text= (productos[5] as Butaca).precio.toDefaultMoneyString()
                }
                is Bebida->{
                    productoLabel6.text= "Complemento : ${(productos[5] as Bebida).id}"
                    precioLabel6.text= (productos[5] as Bebida).precio.toDefaultMoneyString()
                }
                is Comida->{
                    productoLabel6.text= "Complemento : ${(productos[5] as Comida).id}"
                    precioLabel6.text= (productos[5] as Comida).precio.toDefaultMoneyString()
                }
            }
        }
        if (productos.size > 6){
            when(productos[6]){
                is Butaca->{
                    productoLabel7.text= "Butaca : ${(productos[6] as Butaca).id}"
                    precioLabel7.text= (productos[6] as Butaca).precio.toDefaultMoneyString()
                }
                is Bebida->{
                    productoLabel7.text= "Complemento : ${(productos[6] as Bebida).id}"
                    precioLabel7.text= (productos[6] as Bebida).precio.toDefaultMoneyString()
                }
                is Comida->{
                    productoLabel7.text= "Complemento : ${(productos[6] as Comida).id}"
                    precioLabel7.text= (productos[6] as Comida).precio.toDefaultMoneyString()
                }
            }
        }
        if (productos.size > 7){
            when(productos[7]){
                is Butaca->{
                    productoLabel8.text= "Butaca : ${(productos[7] as Butaca).id}"
                    precioLabel8.text= (productos[7] as Butaca).precio.toDefaultMoneyString()
                }
                is Bebida->{
                    productoLabel8.text= "Complemento : ${(productos[7] as Bebida).id}"
                    precioLabel8.text= (productos[7] as Bebida).precio.toDefaultMoneyString()
                }
                is Comida->{
                    productoLabel8.text= "Complemento : ${(productos[7] as Comida).id}"
                    precioLabel8.text= (productos[7] as Comida).precio.toDefaultMoneyString()
                }
            }
        }
        fxLabelTotal.text= totalDinero(productos).toDefaultMoneyString()
        view.asignarTotal(fxLabelTotal.text)
        view.asignarPelicula(viewPeli.state.value.pelicula)
    }

    private fun totalDinero(listaTotal: MutableList<Producto>): Double {
        logger.debug { "calculando total del Carrito" }
        return listaTotal.map {
            when(it){
                is Butaca->it.precio
                is Bebida->it.precio
                is Comida->it.precio
                else -> 0.0
            }
        }.sumOf { it }
    }

    private fun añadirProductos(butacas: MutableList<Butaca>, complemetos: MutableList<Complemento>) {
        butacas.forEach {
            productos.add(it)
        }
        complemetos.forEach {
            productos.add(it)
        }
    }

    private fun initDefaultEvents() {
        logger.debug { "inicializando eventos por defecto carrito" }
        botonVolver.setOnAction { volverOnAction() }
        botonContinuar.setOnAction { continuarOnAction() }
    }

    private fun continuarOnAction() {
        logger.debug { "boton continuar Pulsado carrito" }
        RoutesManager.changeScene(view = RoutesManager.View.PAGO, title = "Pagar", width = 350.0 , height = 378.0 )
    }

    private fun volverOnAction() {
        logger.debug { "boton volver Pulsado carrito" }
        RoutesManager.changeScene(view = RoutesManager.View.SELECCOMPL, title = "Seleccionar Complemento")
    }

}