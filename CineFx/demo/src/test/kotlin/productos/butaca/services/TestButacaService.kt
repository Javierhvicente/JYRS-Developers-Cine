package productos.butaca.services

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import org.example.demo.cache.errors.CacheError
import org.example.demo.productos.butaca.cache.ButacasCacheImpl
import org.example.demo.productos.butaca.errors.ButacaError
import org.example.demo.productos.butaca.repositories.ButacaRepository
import org.example.demo.productos.butaca.services.ButacaServiceImpl
import org.example.demo.productos.butaca.storage.ButacaStorageImpl
import org.example.demo.productos.butaca.validator.ButacaValidator
import org.example.demo.productos.models.Butaca
import org.example.demo.productos.models.Estado
import org.example.demo.productos.models.Ocupacion
import org.example.demo.productos.models.Tipo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import java.io.File

@ExtendWith(MockitoExtension::class)
class TestButacaService {
    @Mock
    private lateinit var mockButacaRepository: ButacaRepository

    @Mock
    private lateinit var mockButacaCache: ButacasCacheImpl

    @Mock
    private lateinit var storage: ButacaStorageImpl

    @Mock
    private lateinit var mockButacaValidator: ButacaValidator


    @InjectMocks
    private lateinit var service: ButacaServiceImpl

    @Test
    fun getAllButacas() {
        Mockito.`when`(mockButacaRepository.findAll()).thenReturn(listOf())
        val results = service.getAll()
        assertTrue(results.isOk)
        verify(mockButacaRepository, times(1)).findAll()
    }


    @Test
    fun getButacaById() {
        val butaca = Butaca("A1", Estado.ACTIVA, Tipo.NORMAL, ocupacion =  Ocupacion.LIBRE, precio = 5.0)

        Mockito.`when`(mockButacaCache.get(butaca.id)).thenReturn(Ok(butaca))

        val result = service.getById(butaca.id)

        assertTrue(result.isOk)
        assertTrue(result.value == butaca)

        verify(mockButacaCache, times(1)).get(butaca.id)
        verify(mockButacaRepository, times(0)).findById(butaca.id)
        verify(mockButacaCache, times(0)).put(butaca.id, butaca)
    }

    @Test
    fun getButacaByIdErr() {
        val butaca = Butaca("A1", Estado.ACTIVA, Tipo.NORMAL, ocupacion =  Ocupacion.LIBRE, precio = 5.0)
        val message = "No existe el valor en la cache"

        Mockito.`when`(mockButacaCache.get(butaca.id)).thenReturn(Err(CacheError(message)))
        Mockito.`when`(mockButacaRepository.findById(butaca.id)).thenReturn(null)

        val result = service.getById(butaca.id)

        assertTrue(result.isErr)
        assertFalse(result.value == butaca)

        verify(mockButacaCache, times(1)).get(butaca.id)
        verify(mockButacaRepository, times(1)).findById(butaca.id)
        verify(mockButacaCache, times(0)).put(butaca.id, butaca)
    }

    @Test
    fun getByTipo(){
        val butaca = Butaca("A1", Estado.ACTIVA, Tipo.NORMAL, ocupacion =  Ocupacion.LIBRE, precio = 5.0)
        val butacaList = listOf(butaca)
        whenever(mockButacaRepository.findByTipo(butaca.tipo.toString())).thenReturn(butacaList)
        val result = service.getByTipo(butaca.tipo.toString())
        assertTrue(result.isOk)
        verify(mockButacaRepository, times(1)).findByTipo(butaca.tipo.toString())
    }

    @Test
    fun getByEstado(){
        val butaca = Butaca("A1", Estado.ACTIVA, Tipo.NORMAL, ocupacion =  Ocupacion.LIBRE, precio = 5.0)
        val butacaList = listOf(butaca)
        whenever(mockButacaRepository.findByEstado(butaca.estado.toString())).thenReturn(butacaList)
        val result = service.getByEstado(butaca.estado.toString())
        assertTrue(result.isOk)
        verify(mockButacaRepository, times(1)).findByEstado(butaca.estado.toString())
    }

    @Test
    fun getByOcupacion(){
        val butaca = Butaca("A1", Estado.ACTIVA, Tipo.NORMAL, ocupacion =  Ocupacion.LIBRE, precio = 5.0)
        val butacaList = listOf(butaca)
        whenever(mockButacaRepository.findByOcupacion(butaca.ocupacion.toString())).thenReturn(butacaList)
        val result = service.getByOcupacion(butaca.ocupacion.toString())
        assertTrue(result.isOk)
        verify(mockButacaRepository, times(1)).findByOcupacion(butaca.ocupacion.toString())
    }


    @Test
    fun saveButaca() {
        val butaca = Butaca("A3", Estado.ACTIVA, Tipo.NORMAL, ocupacion =  Ocupacion.LIBRE, precio = 5.0)
        Mockito.`when`(mockButacaValidator.validarButaca(butaca)).thenReturn(Ok(butaca))
        Mockito.`when`(mockButacaRepository.save(butaca)).thenReturn(butaca)
        Mockito.`when`(mockButacaCache.put(butaca.id, butaca)).thenReturn(Ok(butaca))

        val result = service.create(butaca)

        assertTrue(result.isOk)
        assertTrue(result.value == butaca)

        verify(mockButacaValidator, times(1)).validarButaca(butaca)
        verify(mockButacaRepository, times(1)).save(butaca)
        verify(mockButacaCache, times(1)).put(butaca.id, butaca)
    }

    @Test
    fun updateButaca() {
        val butaca = Butaca("A1", Estado.ACTIVA, Tipo.VIP, ocupacion =  Ocupacion.OCUPADA, precio = 20.0)
        whenever(mockButacaValidator.validarButaca(butaca)).thenReturn(Ok(butaca))
        whenever(mockButacaRepository.update(butaca.id, butaca)).thenReturn(butaca)
        whenever(mockButacaCache.put(butaca.id, butaca)).thenReturn(Ok(butaca))

        val result = service.update(butaca.id, butaca)

        assertTrue(result.isOk)
        assertEquals( Ocupacion.OCUPADA, result.value.ocupacion)
        assertEquals(20.0, result.value.precio)

        verify(mockButacaValidator, times(1)).validarButaca(butaca)
        verify(mockButacaRepository, times(1)).update(butaca.id, butaca)
        verify(mockButacaCache, times(1)).put(butaca.id, butaca)
    }

    @Test
    fun updateButacaErr() {
        val butaca = Butaca("C6", Estado.ACTIVA, Tipo.NORMAL, ocupacion =  Ocupacion.LIBRE, precio = 5.0)
        Mockito.`when`(mockButacaValidator.validarButaca(butaca)).thenReturn(Ok(butaca))
        Mockito.`when`(mockButacaRepository.update(butaca.id, butaca)).thenReturn(null)

        val result = service.update(butaca.id, butaca)

        assertTrue(result.isErr)
        assertTrue(result.error is ButacaError.ButacaNoActualizadas)
        assertEquals("No se ha podido actualizar la butaca con id: ${butaca.id}", result.error.mensage)

        verify(mockButacaValidator, times(1)).validarButaca(butaca)
        verify(mockButacaRepository, times(1)).update(butaca.id, butaca)
        verify(mockButacaCache, times(0)).put(butaca.id, butaca)
    }

    @Test
    fun exportCsv(){
        val lista = listOf(Butaca("A1", Estado.ACTIVA, Tipo.NORMAL, ocupacion =  Ocupacion.LIBRE, precio = 5.0))
        val myFile = File("data", "butacas.csv")

       whenever(storage.saveCsv(myFile, lista)).thenReturn(Ok(lista.size.toLong()))

        val result = service.exportCsv(myFile, lista)

        assertTrue(result.isOk)
        assertEquals(lista.size.toLong(), result.value)

        verify(storage, times(1)).saveCsv(myFile, lista)
    }

    @Test
    fun importCsv(){
        val lista = listOf(Butaca("A1", Estado.ACTIVA, Tipo.NORMAL, ocupacion =  Ocupacion.LIBRE, precio = 5.0))
        val myFile = File("data", "butacas.csv")

        whenever(storage.loadCsv(myFile)).thenReturn(Ok(lista))

        val result = service.importCsv(myFile)

        assertTrue(result.isOk)
        assertEquals(result.value.size,lista.size)

        verify(storage, times(1)).loadCsv(myFile)
    }

    @Test
    fun exportJson(){
        val lista = listOf(Butaca("A1", Estado.ACTIVA, Tipo.NORMAL, ocupacion =  Ocupacion.LIBRE, precio = 5.0))
        val myFile = File("data", "butacas.json")

       whenever(storage.saveJson(myFile, lista)).thenReturn(Ok(lista.size.toLong()))

        val result = service.exportJson(myFile, lista)

        assertTrue(result.isOk)
        assertEquals(lista.size.toLong(), result.value)

        verify(storage, times(1)).saveJson(myFile, lista)
    }

    @Test
    fun importJson(){
        val lista = listOf(Butaca("A1", Estado.ACTIVA, Tipo.NORMAL, ocupacion =  Ocupacion.LIBRE, precio = 5.0))
        val myFile = File("data", "butacas.json")

        whenever(storage.loadJson(myFile)).thenReturn(Ok(lista))

        val result = service.importJson(myFile)

        assertTrue(result.isOk)
        assertEquals(lista.size, result.value.size)

        verify(storage, times(1)).loadJson(myFile)
    }
}
