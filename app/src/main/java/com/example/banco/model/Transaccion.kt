package com.example.banco.model

import java.util.*

class Transaccion {
    var id=0
    var created_at:Date?=null
    var updated_at:Date?=null
    var saldo_actual:Double=0.0
    var nro_transaccion:String=""
    var valor:Double=0.0
    var movimiento:Movimiento?=null
}