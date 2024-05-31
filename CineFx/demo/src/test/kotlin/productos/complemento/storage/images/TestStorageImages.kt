package productos.complemento.storage.images

import org.example.demo.config.Config
import org.example.demo.productos.complementos.errors.ComplementoError
import org.example.demo.productos.complementos.storage.images.StorageImageImpl
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class TestStorageImages {
    private val storage = StorageImageImpl(config = Config)
    private lateinit var myFile: File
    private lateinit var myFileError: File


    @BeforeEach
    fun setUp(){
        myFile = File("imagenes", "agua.png")
        myFileError = File("imagenes", "Flipendo.png")
    }

    @Test
    fun saveImage(){
        val result = storage.saveImage(myFile)
        assertTrue(result.isOk)
    }

    @Test
    fun saveImageError(){
        val result = storage.saveImage(myFileError)
        assertTrue(result.isErr)
    }

    @Test
    fun saveImageTemp(){
        val result = storage.saveImageTemp("imagenes", myFile)
        assertTrue(result.isOk)
    }

    @Test
    fun saveImageTempError(){
        val result = storage.saveImageTemp("imagenes", myFileError)
        assertTrue(result.isErr)
    }

    @Test
    fun loadImage(){
        val result = storage.loadImage("agua.png")
        assertTrue(result.isOk)
    }

    @Test
    fun loadImageError(){
        val result = storage.loadImage("Flipendo.png")
        assertTrue(result.isErr)
    }

   @Test
   fun updateImage(){
       val result = storage.updateImage("sodaTest.png", File("imagenes", "agua.png"))
       assertTrue(result.isOk)
   }

   @Test
   fun updateImageError(){
       val result = storage.updateImage("sodaLoca.png", File("imagenes", "aguaLoca.png"))
       assertTrue(result.isErr)
   }
}