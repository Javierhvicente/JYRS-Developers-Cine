package org.example.demo.productos.complementos.cache

import org.example.demo.cache.CacheImpl
import org.example.demo.productos.models.Complemento

class ComplementoCacheImpl(size:Int): CacheImpl<String, Complemento>(size)  {
}