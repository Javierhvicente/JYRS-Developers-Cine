package productos.complemento.storage

import org.example.demo.productos.complementos.storage.ComplementoStorageImpl
import org.example.demo.productos.models.Bebida
import org.example.demo.productos.models.Comida
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Files

class TestStorageComplemento {
    private  var storage = ComplementoStorageImpl()

    private lateinit var myFile: File
    private lateinit var myFileJson: File

    @BeforeEach
    fun setUp(){
        myFile = Files.createTempFile("butacas", ".csv").toFile()
        myFileJson = Files.createTempFile("prueba", ".json").toFile()
    }
    @AfterEach
    fun tearDown(){
        Files.deleteIfExists(myFile.toPath())
        Files.deleteIfExists(myFileJson.toPath())
    }

    @Test
    fun loadCsv() {
        val result = storage.loadCsv(myFile)
        assertTrue(result.isOk)
    }

    @Test
    fun loadCsvError(){
        val result = storage.loadJson(File("data", "fallo.csv"))
        assertTrue(result.isErr)
    }

    @Test
    fun saveJson(){
        val complementos = listOf(Bebida("sodaTest","REFRESCO", 2.0, "sodaTest" ))
        val result = storage.saveJson(myFileJson, complementos)
        assertTrue(result.isOk)
    }

    @Test
    fun loadJson(){
        val complementos = listOf(Bebida("sodaTest","REFRESCOS", 2.0, "sodaTest.png" ))
        storage.saveJson(myFileJson, complementos)
        val result = storage.loadJson(myFileJson)
        assertTrue(result.isOk)
    }

    @Test
    fun loadJsonError(){
        val result = storage.loadJson(myFile)
        assertTrue(result.isErr)
    }

    @Test
    fun saveCsv(){
        val complementos = listOf(Bebida("sodaTest","REFRESCO", 2.0, "sodaTest" ))
        val result = storage.saveCsv(myFile, complementos)
        assertTrue(result.isOk)
    }

    @Test
    fun saveCsvError(){
        val complementos = listOf(Bebida("sodaTest","REFRESCO", 2.0, "sodaTest" ))
        val result = storage.saveCsv(myFileJson, complementos)
        assertTrue(result.isOk)
    }

}