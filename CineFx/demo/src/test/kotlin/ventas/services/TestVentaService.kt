package ventas.services

import com.github.michaelbull.result.Ok
import org.example.demo.productos.butaca.repositories.ButacaRepository
import org.example.demo.productos.complementos.repositories.ComplementoRepository
import org.example.demo.productos.complementos.repositories.ComplementoRepositoryImpl
import org.example.demo.productos.models.Bebida
import org.example.demo.productos.models.Butaca
import org.example.demo.productos.models.CategoriaBebida
import org.example.demo.productos.models.Complemento
import org.example.demo.usuarios.models.Cliente
import org.example.demo.usuarios.repositories.UserRepositoryImpl
import org.example.demo.venta.models.LineaVenta
import org.example.demo.venta.models.Venta
import org.example.demo.venta.repositories.VentasRepository
import org.example.demo.venta.repositories.VentasRepositoryImpl
import org.example.demo.venta.services.VentasService
import org.example.demo.venta.services.VentasServiceImpl
import org.example.demo.venta.storage.VentasStorage
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import java.util.*

@ExtendWith(MockitoExtension::class)
class TestVentaService {

    @Mock
    private lateinit var butacaRepo: ButacaRepository
    @Mock
    private lateinit var complementoRepo: ComplementoRepository
    @Mock
    lateinit var ventasSotrageHtml: VentasStorage
    @Mock
    private lateinit var ventaRepo: VentasRepository
    @InjectMocks
    lateinit var serviceVentas: VentasServiceImpl

    @Test
    fun getById() {
        val venta = Venta(
            id = UUID.fromString("d41a3b56-2a8e-49fa-9f1e-7f0b8b4e3f72"),
            cliente = Cliente(
                id = 55,
                nombre="User",
                apellidos = "user",
                email = "user@gmail.com",
                contraseña = "!6635GxH#&aV"
            ),
            lineas = listOf(
                LineaVenta(
                    id = UUID.fromString("d41a3b56-2a8e-49fa-9f1e-7f0b8b4e3f72"),
                    producto = Bebida(
                        id="AGUA",
                        tipo = CategoriaBebida.AGUA.toString(),
                        imagen = "agua.png",
                        precio = 2.0
                    ),
                    cantidad = 1,
                    precio =  5.0
                )
            )
        )

        whenever(ventaRepo.getById(UUID.fromString("d41a3b56-2a8e-49fa-9f1e-7f0b8b4e3f72"))).thenReturn(venta)

        val ventaService=serviceVentas.getById(UUID.fromString("d41a3b56-2a8e-49fa-9f1e-7f0b8b4e3f72"))

        assertTrue(ventaService.isOk)
        assertTrue(ventaService.value == venta)

        verify(ventaRepo, times(1)).getById(UUID.fromString("d41a3b56-2a8e-49fa-9f1e-7f0b8b4e3f72"))
    }

    @Test
    fun getByIdError(){
        whenever(ventaRepo.getById(UUID.fromString("b6f2e3c4-3d5b-4af2-8c3a-f1b0e4d5a6b7"))).thenReturn(null)

        val resul = serviceVentas.getById(UUID.fromString("b6f2e3c4-3d5b-4af2-8c3a-f1b0e4d5a6b7"))

        assertTrue(resul.isErr)
        assertEquals(resul.error.message, "No se ha encontrado la venta con id: b6f2e3c4-3d5b-4af2-8c3a-f1b0e4d5a6b7")

        verify(ventaRepo,times(1)).getById(UUID.fromString("b6f2e3c4-3d5b-4af2-8c3a-f1b0e4d5a6b7"))

    }

    @Test
    fun createVenta() {
        val venta = Venta(
            id = UUID.fromString("c2a1f6d9-7b4e-41d8-9e1b-0f8a3b5d6e7c"),
            cliente = Cliente(
                id = 55,
                nombre="User",
                apellidos = "user",
                email = "user@gmail.com",
                contraseña = "!6635GxH#&aV"
            ),
            lineas = listOf(
                LineaVenta(
                    id = UUID.fromString("c2a1f6d9-7b4e-41d8-9e1b-0f8a3b5d6e7c"),
                    producto = Bebida(
                        id="AGUA",
                        tipo = CategoriaBebida.AGUA.toString(),
                        imagen = "agua.png",
                        precio = 2.0
                    ),
                    cantidad = 1,
                    precio =  5.0
                )
            )
        )

        whenever( serviceVentas.validateCliente(venta.cliente)).thenReturn(Ok(venta.cliente))
        whenever(serviceVentas.validateLineas(venta.lineas)).thenReturn(Ok(venta.lineas))
        whenever(venta.lineas.forEach{ when(it.producto){
            is Complemento-> complementoRepo.findById((it.producto as Complemento).id)
            is Butaca -> butacaRepo.findById((it.producto as Butaca).id)
        }
        }).thenReturn(venta.lineas.forEach { it.producto })
        whenever(ventaRepo.save(venta)).thenReturn(Ok(venta).value)

        val ventaSer = serviceVentas.create(venta)

        assertTrue(ventaSer.isOk)
        assertTrue(ventaSer.value == venta)
        assertTrue(ventaSer.value.cliente == venta.cliente)

        verify(serviceVentas,times(1)).validateCliente(venta.cliente)
        verify(serviceVentas,times(1)).validateLineas(venta.lineas)
        verify(ventaRepo, times(1)).save(venta)
    }
}