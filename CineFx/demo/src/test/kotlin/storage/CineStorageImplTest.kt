package storageS

import com.github.michaelbull.result.Ok
import org.example.demo.config.Config
import org.example.demo.productos.butaca.storage.ButacaStorageImpl
import org.example.demo.productos.complementos.storage.ComplementoStorage
import org.example.demo.productos.complementos.storage.ComplementoStorageImpl
import org.example.demo.productos.complementos.storage.images.StorageImageImpl
import org.example.demo.productos.models.*
import org.example.demo.storage.CineStorageImpl
import org.example.demo.usuarios.models.Cliente
import org.example.demo.usuarios.storage.UsuarioStorageImpl
import org.example.demo.venta.models.LineaVenta
import org.example.demo.venta.models.Venta
import org.example.demo.venta.storage.VentaStorageHtml
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.File
import java.nio.file.Files
import java.util.*

class CineStorageTest {
    @Mock
    private lateinit var butacasStorag: ButacaStorageImpl

    @Mock
    private lateinit var complementoStorageImpl: ComplementoStorageImpl

    @Mock
    private lateinit var UsuarioStorageImpl: UsuarioStorageImpl

    @Mock
    private lateinit var VentaStorageHtml: VentaStorageHtml

    @Mock
    private lateinit var storageImageImpl: StorageImageImpl

    @InjectMocks
    private lateinit var storageZip: CineStorageImpl
    private lateinit var myFile: File

    @BeforeEach
    fun setUp() {
        storageZip = CineStorageImpl(ButacaStorageImpl(), ComplementoStorageImpl(), UsuarioStorageImpl(), VentaStorageHtml(), StorageImageImpl(Config))
        myFile = Files.createTempFile("cine", ".zip").toFile()
    }

    @AfterEach
    fun tearDown(){
        Files.deleteIfExists(myFile.toPath())
    }

    @Test
    fun exportZip(){
        val productos = listOf<Complemento>(Comida("Patatas", "PATATAS", 2.0, "patatas-fritas-png"))
        val butacas = listOf<Butaca>( Butaca(id = "A1", tipo = Tipo.NORMAL, estado = Estado.ACTIVA, precio = 5.0, ocupacion = Ocupacion.LIBRE))
        val clientes = listOf<Cliente>(Cliente(2, email = " a@gmail.com", nombre = "a", apellidos = "a", contraseña = "!6635GxH#&aV"))
        val ventas = listOf<Venta>(Venta(UUID.randomUUID(), clientes.first(), lineas = listOf(LineaVenta(cantidad = 1, precio = 5.0, producto = butacas.first()))))

        whenever(storageImageImpl.saveImage(myFile)).thenReturn(Ok(myFile))
        whenever(butacasStorag.saveJson(File("JYRSCinemaZip","butacas.json"), butacas))
        whenever(complementoStorageImpl.saveJson((File("JYRSCinemaZip","complementos.json")), productos)).thenReturn(Ok(productos.size.toLong()))
        val result = storageZip.exportToZip(file =  myFile, config =  Config, butacas = butacas, complementos = productos, usuarios = clientes, ventas = ventas )

        assertTrue(result.isOk)
        assertEquals("zip", result.value.extension)
        assertTrue(result.value.isFile)

        verify(storageImageImpl, times(1)).saveImage(myFile)
        verify(butacasStorag, times(1)).saveJson(File("JYRSCinemaZip","butacas.json"), butacas)
        verify(complementoStorageImpl, times(1)).saveJson((File("JYRSCinemaZip","complementos.json")), productos)
    }
}