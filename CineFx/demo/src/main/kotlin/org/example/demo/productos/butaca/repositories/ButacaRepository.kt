package org.example.demo.productos.butaca.repositories

import org.example.demo.productos.models.Butaca
import org.example.demo.productos.models.Ocupacion

interface ButacaRepository {
        fun findAll(): List<Butaca>
        fun save(producto: Butaca): Butaca
        fun findById(id: String): Butaca?
        fun findByTipo(tipo: String): List<Butaca>
        fun update(id: String, butaca: Butaca): Butaca?
        fun findByEstado(estado: String): List<Butaca>
        fun findByOcupacion(ocupacion: String): List<Butaca>
        fun deleteAll()
}