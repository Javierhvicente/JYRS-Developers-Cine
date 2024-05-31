package ventas.repositories

import org.example.demo.config.Config
import org.example.demo.database.SqlDelightManager
import org.example.demo.locale.toShortSpanishFormat
import org.example.demo.productos.butaca.repositories.ButacaRepository
import org.example.demo.productos.butaca.repositories.ButacaRepositoryImpl
import org.example.demo.productos.complementos.repositories.ComplementoRepository
import org.example.demo.productos.complementos.repositories.ComplementoRepositoryImpl
import org.example.demo.productos.models.*
import org.example.demo.usuarios.models.Cliente
import org.example.demo.usuarios.repositories.UserRepositoryImpl
import org.example.demo.venta.models.LineaVenta
import org.example.demo.venta.models.Venta
import org.example.demo.venta.repositories.VentasRepository
import org.example.demo.venta.repositories.VentasRepositoryImpl
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension::class)
class TestVentaRepository {
    private lateinit var dbManager: SqlDelightManager

    @InjectMocks
    private lateinit var ventaRepo: VentasRepository

    @Mock
    private lateinit var butacaRepo: ButacaRepository

    @Mock
    private lateinit var complementoRepo: ComplementoRepository

    @Mock
    private lateinit var clienteRepo: UserRepositoryImpl

    private lateinit var cliente: Cliente
    private lateinit var complemento: Complemento
    private lateinit var butaca: Butaca
    private lateinit var lineaVenta1: LineaVenta
    private lateinit var lineaVenta2: LineaVenta
    private lateinit var lineaVenta3: LineaVenta

    @BeforeEach
    fun setUpAll(){

        dbManager= SqlDelightManager(Config)
        butacaRepo= ButacaRepositoryImpl(dbManager)
        complementoRepo= ComplementoRepositoryImpl(dbManager)
        clienteRepo= UserRepositoryImpl(dbManager)
        ventaRepo = VentasRepositoryImpl(dbManager, clienteRepo, butacaRepo, complementoRepo)

        cliente = Cliente(
            id = 55,
            nombre="User",
            apellidos = "user",
            email = "user@gmail.com",
            contraseña = "!6635GxH#&aV"
        )
        dbManager.databaseQueries.insertUser(cliente.email,cliente.nombre,cliente.apellidos,"cliente",cliente.contraseña)
        complemento = Bebida(
            id="AGUA",
            tipo = CategoriaBebida.AGUA.toString(),
            imagen = "agua.png",
            precio = 2.0
        )
        dbManager.databaseQueries.insertComplemento("BEBIDA",complemento.id, precio = 2.0.toLong(), imagen = "agua.png", nombre = "AGUA"
        )
        butaca = Butaca(
            id="A1",
            estado = Estado.ACTIVA,
            tipo = Tipo.NORMAL,
            ocupacion = Ocupacion.LIBRE,
            precio = 5.0
        )
        dbManager.databaseQueries.insertarbutaca(butaca.id,butaca.estado.toString(),butaca.tipo.toString(),butaca.ocupacion.toString(),butaca.precio,butaca.create.toShortSpanishFormat())
        lineaVenta1 = LineaVenta(
            producto = butaca,
            cantidad = 1,
            precio = 5.0,
        )
        lineaVenta2 = LineaVenta(
            producto = complemento,
            cantidad = 1,
            precio = 3.0,
        )
        lineaVenta3 = LineaVenta(
            producto = complemento,
            cantidad = 2,
            precio = 3.0,
        )
    }

    @AfterEach
    fun deletData(){
        dbManager.clearData()
    }

    @Test
    fun findAll(){
        ventaRepo.save(Venta(
            cliente = cliente, lineas = listOf(lineaVenta1,lineaVenta2,lineaVenta3)
        ))
        val ventas=ventaRepo.getAll()

        assertEquals(1,ventas.size)
    }

    @Test
    fun save() {
        val ventaSaved = ventaRepo.save(
            Venta(
                cliente=cliente,
                lineas = listOf(lineaVenta1,lineaVenta2),
            )
        )

        assertEquals(cliente,ventaSaved.cliente)
        assertEquals(lineaVenta1,ventaSaved.lineas[0])
        assertEquals(lineaVenta2,ventaSaved.lineas[1])
    }



    @Test
    fun delete() {
        ventaRepo.save(Venta(id = UUID.fromString("62b80c18-7e69-47b2-8a5f-524a1a7ec32d"),cliente, lineas = listOf(lineaVenta3)))
        val venta = ventaRepo.delete(UUID.fromString("62b80c18-7e69-47b2-8a5f-524a1a7ec32d"))

        assertEquals(UUID.fromString("62b80c18-7e69-47b2-8a5f-524a1a7ec32d"), venta?.id)
    }

    @Test
    fun deleteErr() {
        val venta = ventaRepo.delete(UUID.fromString("5e3e8c14-4a69-4f72-888a-92c7a3b0e5b0"))

        assertEquals(null, venta)
    }
    @Test
    fun update() {
        ventaRepo.save(Venta(id = UUID.fromString("b7c0f6e5-77ae-4a56-90b1-487d2f07be35"),cliente, lineas = listOf(lineaVenta3)))
        val venta = ventaRepo.update(
            UUID.fromString("b7c0f6e5-77ae-4a56-90b1-487d2f07be35"),
            Venta(
                id = UUID.fromString("4b2b7b48-62c6-43b2-9a94-cb8a5f3c6d4e"),
                cliente=cliente,
                lineas = listOf(lineaVenta1,lineaVenta2,lineaVenta3),
            )
        )

        assertEquals(UUID.fromString("4b2b7b48-62c6-43b2-9a94-cb8a5f3c6d4e"),venta?.id)
        assertEquals(cliente,venta?.cliente)
        assertEquals(lineaVenta1,venta!!.lineas[0])
        assertEquals(lineaVenta2, venta.lineas[1])
        assertEquals(lineaVenta3, venta.lineas[2])
    }

    @Test
    fun updateNull() {
        val venta = ventaRepo.update(
            UUID.fromString("d8e4e7b6-1c4f-4cf8-9d88-7a6a70c489a2"),
            Venta(
                id = UUID.fromString("3fc8d2e3-cc78-4b7f-8828-5c2e7a53f6f4"),
                cliente=cliente,
                listOf(lineaVenta1,lineaVenta2,lineaVenta3),
            )
        )
        assertEquals(null,venta)
    }


    @Test
    fun findById() {
        ventaRepo.save(Venta(id = UUID.fromString("a14b8b77-91d2-4ed4-8c2e-9b0f7c2a6f52"),cliente, lineas = listOf(lineaVenta3)))
        val venta=ventaRepo.getById(
            UUID.fromString("a14b8b77-91d2-4ed4-8c2e-9b0f7c2a6f52")
        )

        assertEquals("a14b8b77-91d2-4ed4-8c2e-9b0f7c2a6f52", venta?.id.toString())
    }

    @Test
    fun findByIdNotFound() {
        val venta = ventaRepo.getById(
            UUID.fromString("00c712fb-1111-4f33-a744-0fdb65cd9dcf")
        )

        assertEquals(null, venta)
    }


}