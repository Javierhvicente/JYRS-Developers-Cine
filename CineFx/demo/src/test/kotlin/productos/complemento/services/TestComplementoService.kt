package productos.complemento.services

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import org.example.demo.cache.errors.CacheError
import org.example.demo.productos.complementos.cache.ComplementoCacheImpl
import org.example.demo.productos.complementos.repositories.ComplementoRepository
import org.example.demo.productos.complementos.repositories.ComplementoRepositoryImpl
import org.example.demo.productos.complementos.services.ComplementoService
import org.example.demo.productos.complementos.services.ComplementoServiceImpl
import org.example.demo.productos.complementos.storage.ComplementoStorage
import org.example.demo.productos.complementos.storage.ComplementoStorageImpl
import org.example.demo.productos.models.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class TestComplementoService {

    @Mock
    private lateinit var mockComplementoRepository: ComplementoRepositoryImpl

    @Mock
    private lateinit var mockProductosCache: ComplementoCacheImpl

    @Mock
    private lateinit var storage:ComplementoStorageImpl

    @InjectMocks
    private lateinit var service : ComplementoServiceImpl



    @Test
    fun getAllComplementos() {
        Mockito.`when`(mockComplementoRepository.findAll()).thenReturn(listOf())

        val result = service.getAll()

        assertTrue(result.isOk)

        verify(mockComplementoRepository, times(1)).findAll()
    }

    @Test
    fun getById() {
        val complemento = Comida("PATATAS", CategoriaComida.PATATAS.toString(), precio = 2.0, imagen = "patatas-fritas.png" )

        Mockito.`when`(mockProductosCache.get(complemento.id)).thenReturn(Ok(complemento))

        val result = service.getById(complemento.id)

        assertTrue(result.isOk)
        assertTrue(result.value == complemento)

        verify(mockProductosCache, times(1)).get(complemento.id)
        verify(mockComplementoRepository, times(0)).findById(complemento.id)
        verify(mockProductosCache, times(0)).put(complemento.id, complemento)
    }

    @Test
    fun getByIdErr() {
        val complemento = Bebida("REFRESCO", CategoriaBebida.REFRESCOS.toString(), precio = 2.0, "soda.png")
        val message = "No existe el valor en la cache"

        Mockito.`when`(mockProductosCache.get(complemento.id)).thenReturn(Err(CacheError(message)))
        Mockito.`when`(mockComplementoRepository.findById(complemento.id)).thenReturn(null)

        val result = service.getById(complemento.id)

        assertTrue(result.isErr)
        assertFalse(result.value == complemento)

        verify(mockProductosCache, times(1)).get(complemento.id)
        verify(mockComplementoRepository, times(1)).findById(complemento.id)
        verify(mockProductosCache, times(0)).put(complemento.id, complemento)
    }


    @Test
    fun save() {
        val complemento = Bebida("REFRESCO", CategoriaBebida.REFRESCOS.toString(), precio = 2.0, "soda.png")
        Mockito.`when`(mockComplementoRepository.save(complemento)).thenReturn(complemento)
        Mockito.`when`(mockProductosCache.put(complemento.id, complemento)).thenReturn(Ok(complemento))

        val result = service.create(complemento)

        assertTrue(result.isOk)
        assertTrue(result.value == complemento)

        verify(mockComplementoRepository, times(1)).save(complemento)
        verify(mockProductosCache, times(1)).put(complemento.id, complemento)
    }

    @Test
    fun updateComplemento() {
        val complemento = Bebida("REFRESCO", CategoriaBebida.REFRESCOS.toString(), 2.0, imagen = "soda.png")
        Mockito.`when`(mockComplementoRepository.update(complemento.id, complemento, imagen = "soda.png")).thenReturn(complemento)
        Mockito.`when`(mockProductosCache.put(complemento.id, complemento)).thenReturn(Ok(complemento))

        val result = service.update(complemento.id, complemento, imagen = "soda.png")

        assertTrue(result.isOk)
        assertTrue(result.value == complemento)

        verify(mockComplementoRepository, times(1)).update(complemento.id, complemento, imagen = "soda.png")
        verify(mockProductosCache, times(1)).put(complemento.id, complemento)
    }

    @Test
    fun deleteComplemento() {
        val complemento = Bebida("REFRESCO", CategoriaBebida.REFRESCOS.toString(), precio = 2.0, imagen = "soda.png")
        Mockito.`when`(mockComplementoRepository.delete(complemento.id)).thenReturn(complemento)
        Mockito.`when`(mockProductosCache.remove(complemento.id)).thenReturn(Ok(complemento))

        val result = service.delete(complemento.id)

        assertTrue(result.isOk)
        assertTrue(result.value == complemento)

        verify(mockComplementoRepository, times(1)).delete(complemento.id)
        verify(mockProductosCache, times(1)).remove(complemento.id)
    }


}