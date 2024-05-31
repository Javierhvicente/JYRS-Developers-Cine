package ventas.storage

import org.example.demo.locale.toDefaultMoneyString
import org.example.demo.locale.toShortSpanishFormat
import org.example.demo.productos.models.*
import org.example.demo.usuarios.models.Cliente
import org.example.demo.venta.models.LineaVenta
import org.example.demo.venta.models.Venta
import org.example.demo.venta.storage.VentaStorageHtml
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Files
import java.util.*

class VentasStorageHtmlTest {
    private lateinit var storageHtml: VentaStorageHtml
    private lateinit var myFile: File

    @BeforeEach
    fun setUp(){
        storageHtml = VentaStorageHtml()
        myFile = Files.createTempFile("recaudacion", "html").toFile()
    }

    @AfterEach
    fun tearDown(){
        Files.deleteIfExists(myFile.toPath())
    }

    @Test
    fun exportHtml(){
        val cliente = Cliente(id = 2L, "User", "user", "!6635GxH#&aV", "user@gmail.com")

        val producto = Butaca("A1", Estado.ACTIVA, Tipo.NORMAL, ocupacion =  Ocupacion.LIBRE, precio = 5.0)

        val listaLineas: List<LineaVenta> = listOf(LineaVenta(UUID.randomUUID(), precio = 20.0, cantidad = 1, producto =producto))

        val venta = Venta(UUID.randomUUID(), cliente, listaLineas)

        val result = storageHtml.export(venta = venta, myFile, "Garfield")
        val htmlLines = myFile.readLines()
        val htmlText = myFile.readText()

        val lineasHtml = venta.lineas.joinToString("") {
            "<tr>" +
                    "<td>${
                        when (it.producto) {
                            is Butaca -> "Butaca: ${(it.producto as Butaca).id}"
                            is Bebida -> "Bebida: ${(it.producto as Bebida).id}"
                            is Comida -> "Comida: ${(it.producto as Comida).id}"
                            else -> ""
                        }
                    }</td>" +
                    "<td>${it.precio.toDefaultMoneyString()}</td>" +
                    "</tr>"
        }

        val actualHtml = """
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Venta</title>
                        <style>
                            body {
                                font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
                                background-color: #f4f4f9;
                                color: #333;
                                margin: 0;
                                padding: 20px;
                            }
                            .container {
                                max-width: 800px;
                                margin: 0 auto;
                                background: #fff;
                                padding: 20px;
                                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                                border-radius: 8px;
                            }
                            h1 {
                                color: #4a90e2;
                            }
                            p {
                                line-height: 1.6;
                            }
                            table {
                                width: 100%;
                                border-collapse: collapse;
                                margin-top: 20px;
                            }
                            table, th, td {
                                border: 1px solid #ddd;
                            }
                            th, td {
                                padding: 12px;
                                text-align: left;
                            }
                            th {
                                background-color: #f4f4f9;
                            }
                            tr:nth-child(even) {
                                background-color: #f9f9f9;
                            }
                            .total {
                                font-weight: bold;
                                font-size: 1.2em;
                                color: #e94e77;
                            }
                        </style>
                    </head>
                    <body>
                        <div class="container">
                            <h1>Venta</h1>
                            <p>Fecha: ${venta.createdAt.toShortSpanishFormat()}</p>
                            <p>Cliente: ${venta.cliente.nombre}</p>
                            <p>Película: garfield</p>
                            <p>Productos:</p>
                            <table>
                                <thead>
                                    <tr>
                                        <th>Nombre</th>
                                        <th>Precio Unitario</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    $lineasHtml
                                </tbody>
                            </table>
                            <p>Total: <span class="total">${venta.total.toDefaultMoneyString()}</span></p>
                        </div>
                    </body>
                    </html>
                    """.trimIndent()
        assertTrue(result.isOk)
        assertEquals( 75, htmlLines.size)
    }

}