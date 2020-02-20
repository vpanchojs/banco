package com.example.banco.api

interface CallBackApi<T> {
    fun correcto(respuesta:T)
    fun error(error:String)
}