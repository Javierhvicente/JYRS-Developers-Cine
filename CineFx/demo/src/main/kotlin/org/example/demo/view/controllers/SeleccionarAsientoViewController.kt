package org.example.demo.view.controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import org.example.demo.productos.models.Butaca
import org.example.demo.productos.models.Estado
import org.example.demo.productos.models.Ocupacion
import org.example.demo.productos.models.Tipo
import org.example.demo.routes.RoutesManager
import org.example.demo.usuarios.viewModel.SeleccionarAsientoViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging


private val logger= logging()
class SeleccionarAsientoViewController :KoinComponent{
    val viewButacas: SeleccionarAsientoViewModel by inject()

    @FXML
    lateinit var butacaImage1a:ImageView
    @FXML
    lateinit var butacaImage1b:ImageView
    @FXML
    lateinit var butacaImage1c:ImageView
    @FXML
    lateinit var butacaImage1d:ImageView
    @FXML
    lateinit var butacaImage1e:ImageView
    @FXML
    lateinit var butacaImage1f:ImageView
    @FXML
    lateinit var butacaImage1g:ImageView
    @FXML
    lateinit var butacaImage2a:ImageView
    @FXML
    lateinit var butacaImage2b:ImageView
    @FXML
    lateinit var butacaImage2c:ImageView
    @FXML
    lateinit var butacaImage2d:ImageView
    @FXML
    lateinit var butacaImage2e:ImageView
    @FXML
    lateinit var butacaImage2f:ImageView
    @FXML
    lateinit var butacaImage2g:ImageView
    @FXML
    lateinit var butacaImage3a:ImageView
    @FXML
    lateinit var butacaImage3b:ImageView
    @FXML
    lateinit var butacaImage3c:ImageView
    @FXML
    lateinit var butacaImage3d:ImageView
    @FXML
    lateinit var butacaImage3e:ImageView
    @FXML
    lateinit var butacaImage3f:ImageView
    @FXML
    lateinit var butacaImage3g:ImageView
    @FXML
    lateinit var butacaImage4a:ImageView
    @FXML
    lateinit var butacaImage4b:ImageView
    @FXML
    lateinit var butacaImage4c:ImageView
    @FXML
    lateinit var butacaImage4d:ImageView
    @FXML
    lateinit var butacaImage4e:ImageView
    @FXML
    lateinit var butacaImage4f:ImageView
    @FXML
    lateinit var butacaImage4g:ImageView
    @FXML
    lateinit var butacaImage5a:ImageView
    @FXML
    lateinit var butacaImage5b:ImageView
    @FXML
    lateinit var butacaImage5c:ImageView
    @FXML
    lateinit var butacaImage5d:ImageView
    @FXML
    lateinit var butacaImage5e:ImageView
    @FXML
    lateinit var butacaImage5f:ImageView
    @FXML
    lateinit var butacaImage5g:ImageView
    @FXML
    lateinit var butacaImage6a:ImageView
    @FXML
    lateinit var butacaImage6b:ImageView
    @FXML
    lateinit var butacaImage6c:ImageView
    @FXML
    lateinit var butacaImage6d:ImageView
    @FXML
    lateinit var butacaImage6e:ImageView
    @FXML
    lateinit var butacaImage6f:ImageView
    @FXML
    lateinit var butacaImage6g:ImageView
    @FXML
    lateinit var butacaImage7a:ImageView
    @FXML
    lateinit var butacaImage7b:ImageView
    @FXML
    lateinit var butacaImage7c:ImageView
    @FXML
    lateinit var butacaImage7d:ImageView
    @FXML
    lateinit var butacaImage7e:ImageView
    @FXML
    lateinit var butacaImage7f:ImageView
    @FXML
    lateinit var butacaImage7g:ImageView

    @FXML
    lateinit var botonVolver: Button
    @FXML
    lateinit var botonContinuar :Button

    private var lista:MutableList<Butaca> = mutableListOf()

    @FXML
    fun initialize(){
        logger.debug { "inicializando controller seleccionar butaca" }
        initDefaultEvents()
        initReactiveProperties()
        initDefaultValues()
    }

    private fun initReactiveProperties() {
        viewButacas.state.addListener { _,_,newValue->
            lista = newValue.butacasSeleccionadas
            //lista.size = newValue.butacasSeleccionadas.size

        }
    }

    private fun initDefaultValues() {
        lista = viewButacas.state.value.butacasSeleccionadas

        asignarImagen("A1",butacaImage1a)
        asignarImagen("B1",butacaImage1b)
        asignarImagen("C1",butacaImage1c)
        asignarImagen("D1",butacaImage1d)
        asignarImagen("E1",butacaImage1e)
        asignarImagen("F1",butacaImage1f)
        asignarImagen("G1",butacaImage1g)

        asignarImagen("A2",butacaImage2a)
        asignarImagen("B2",butacaImage2b)
        asignarImagen("C2",butacaImage2c)
        asignarImagen("D2",butacaImage2d)
        asignarImagen("E2",butacaImage2e)
        asignarImagen("F2",butacaImage2f)
        asignarImagen("G2",butacaImage2g)

        asignarImagen("A3",butacaImage3a)
        asignarImagen("B3",butacaImage3b)
        asignarImagen("C3",butacaImage3c)
        asignarImagen("D3",butacaImage3d)
        asignarImagen("E3",butacaImage3e)
        asignarImagen("F3",butacaImage3f)
        asignarImagen("G3",butacaImage3g)

        asignarImagen("A4",butacaImage4a)
        asignarImagen("B4",butacaImage4b)
        asignarImagen("C4",butacaImage4c)
        asignarImagen("D4",butacaImage4d)
        asignarImagen("E4",butacaImage4e)
        asignarImagen("F4",butacaImage4f)
        asignarImagen("G4",butacaImage4g)

        asignarImagen("A5",butacaImage5a)
        asignarImagen("B5",butacaImage5b)
        asignarImagen("C5",butacaImage5c)
        asignarImagen("D5",butacaImage5d)
        asignarImagen("E5",butacaImage5e)
        asignarImagen("F5",butacaImage5f)
        asignarImagen("G5",butacaImage5g)

        asignarImagen("A6",butacaImage6a)
        asignarImagen("B6",butacaImage6b)
        asignarImagen("C6",butacaImage6c)
        asignarImagen("D6",butacaImage6d)
        asignarImagen("E6",butacaImage6e)
        asignarImagen("F6",butacaImage6f)
        asignarImagen("G6",butacaImage6g)

        asignarImagen("A7",butacaImage7a)
        asignarImagen("B7",butacaImage7b)
        asignarImagen("C7",butacaImage7c)
        asignarImagen("D7",butacaImage7d)
        asignarImagen("E7",butacaImage7e)
        asignarImagen("F7",butacaImage7f)
        asignarImagen("G7",butacaImage7g)
    }

    private fun seleccionarButaca(id: String, image: ImageView) {
        image.setOnMouseClicked {
            val butaca = viewButacas.buscarButacaId(id)!!

            if (butaca.estado != Estado.MANTENIMIENTO
                && butaca.estado != Estado.OCUPADA
                && !lista.contains(viewButacas.buscarButacaId(butaca.id))
            ) {
                if (lista.size < 5) {
                    image.image = Image(RoutesManager.getResourceAsStream("images/seleccionada.png"))
                    viewButacas.actualizarButaca(
                        butaca.id,
                        butaca.estado,
                        butaca.tipo,
                        butaca.create,
                        Ocupacion.SELECCIONADA,
                        butaca.precio
                    )
                    val butaca2 = viewButacas.buscarButacaId(id)!!
                    añadirButaca(butaca2.id)
                    viewButacas.actualizarButacasSeleccionadas(lista)
                } else {
                    RoutesManager.alerta("5 butacas", "Máximo 5 butacas por cliente")
                }
            } else if (lista.contains(viewButacas.buscarButacaId(id))) {
                viewButacas.actualizarButaca(
                    id,
                    Estado.ACTIVA,
                    butaca.tipo,
                    butaca.create,
                    Ocupacion.LIBRE,
                    butaca.precio
                )
                val butaca2 = viewButacas.buscarButacaId(id)!!
                eliminarButaca(butaca2.id)
                asignarImagen(butaca2.id, image)
            }
        }
    }

    private fun añadirButaca(id: String) {
        lista.add(viewButacas.buscarButacaId(id)!!)
    }
    private fun eliminarButaca(id: String) {
        val lista2:MutableList<Butaca> = mutableListOf()
        lista.forEach {
            if (it.id != id){
                lista2.add(it)
            }
        }
        viewButacas.actualizarButacasSeleccionadas(lista2)
    }

    private fun asignarImagen(id: String, image: ImageView) {
        logger.debug { "asignando imagen a $id" }
        val butaca=viewButacas.buscarButacaId(id)!!
        if (butaca.estado == Estado.MANTENIMIENTO) {
            image.image = Image(RoutesManager.getResourceAsStream("images/mentenimiento.png"))
        }else if (butaca.estado == Estado.OCUPADA){
            image.image = Image(RoutesManager.getResourceAsStream("images/ocupada.png"))
        }else if (butaca.ocupacion == Ocupacion.OCUPADA){
            image.image = Image(RoutesManager.getResourceAsStream("images/ocupada.png"))
        }else if (butaca.ocupacion == Ocupacion.SELECCIONADA){
            image.image = Image(RoutesManager.getResourceAsStream("images/seleccionada.png"))
        }else if (butaca.tipo == Tipo.NORMAL){
            image.image = Image(RoutesManager.getResourceAsStream("images/libre.png"))
        }else if (butaca.tipo == Tipo.VIP){
            image.image = Image(RoutesManager.getResourceAsStream("images/vip.png"))
        }else if(butaca.ocupacion == Ocupacion.LIBRE){
            image.image = Image(RoutesManager.getResourceAsStream("images/libre.png"))
        } else{
            image.image = Image(RoutesManager.getResourceAsStream("images/libre.png"))
        }
        logger.debug { "asignada imagen a $id / ${image.image}" }
    }

    private fun initDefaultEvents() {
        botonVolver.setOnAction { botonVolveronAction() }
        botonContinuar.setOnAction { botonContinuarOnAction() }


            seleccionarButaca("A1",butacaImage1a)
            seleccionarButaca("B1",butacaImage1b)
            seleccionarButaca("C1",butacaImage1c)
            seleccionarButaca("D1",butacaImage1d)
            seleccionarButaca("E1",butacaImage1e)
            seleccionarButaca("F1",butacaImage1f)
            seleccionarButaca("G1",butacaImage1g)

            seleccionarButaca("A2",butacaImage2a)
            seleccionarButaca("B2",butacaImage2b)
            seleccionarButaca("C2",butacaImage2c)
            seleccionarButaca("D2",butacaImage2d)
            seleccionarButaca("E2",butacaImage2e)
            seleccionarButaca("F2",butacaImage2f)
            seleccionarButaca("G2",butacaImage2g)

            seleccionarButaca("A3",butacaImage3a)
            seleccionarButaca("B3",butacaImage3b)
            seleccionarButaca("C3",butacaImage3c)
            seleccionarButaca("D3",butacaImage3d)
            seleccionarButaca("E3",butacaImage3e)
            seleccionarButaca("F3",butacaImage3f)
            seleccionarButaca("G3",butacaImage3g)

            seleccionarButaca("A4",butacaImage4a)
            seleccionarButaca("B4",butacaImage4b)
            seleccionarButaca("C4",butacaImage4c)
            seleccionarButaca("D4",butacaImage4d)
            seleccionarButaca("E4",butacaImage4e)
            seleccionarButaca("F4",butacaImage4f)
            seleccionarButaca("G4",butacaImage4g)

            seleccionarButaca("A5",butacaImage5a)
            seleccionarButaca("B5",butacaImage5b)
            seleccionarButaca("C5",butacaImage5c)
            seleccionarButaca("D5",butacaImage5d)
            seleccionarButaca("E5",butacaImage5e)
            seleccionarButaca("F5",butacaImage5f)
            seleccionarButaca("G5",butacaImage5g)

            seleccionarButaca("A6",butacaImage6a)
            seleccionarButaca("B6",butacaImage6b)
            seleccionarButaca("C6",butacaImage6c)
            seleccionarButaca("D6",butacaImage6d)
            seleccionarButaca("E6",butacaImage6e)
            seleccionarButaca("F6",butacaImage6f)
            seleccionarButaca("G6",butacaImage6g)

            seleccionarButaca("A7",butacaImage7a)
            seleccionarButaca("B7",butacaImage7b)
            seleccionarButaca("C7",butacaImage7c)
            seleccionarButaca("D7",butacaImage7d)
            seleccionarButaca("E7",butacaImage7e)
            seleccionarButaca("F7",butacaImage7f)
            seleccionarButaca("G7",butacaImage7g)


    }

    private fun botonContinuarOnAction() {
        if (viewButacas.state.value.butacasSeleccionadas.size > 0){
            RoutesManager.changeScene(view = RoutesManager.View.SELECCOMPL, title = "Seleccionar Complemento")
        }else{
            RoutesManager.alerta("Butaca Nos eleccionada","No has seleccionado ninguna Butaca")
        }
    }

    private fun botonVolveronAction() {
        viewButacas.reasignarButacas()
        viewButacas.state.value.butacasSeleccionadas.clear()
        RoutesManager.changeScene(view = RoutesManager.View.SELECPELICULAS, title = "Seleccionar pelicula")
    }

}