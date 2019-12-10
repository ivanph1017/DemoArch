package com.eveexite.demoarch.core.domain

interface DomainErrorEventListener {

    fun onError(e: Exception)
}