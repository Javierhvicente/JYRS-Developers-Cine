package org.example.demo.usuarios.cache

import org.example.demo.cache.CacheImpl
import org.example.demo.usuarios.models7.Usuario


class CacheUsuario(size: Int): CacheImpl<Long, Usuario>(size) {
}