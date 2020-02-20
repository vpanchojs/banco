package com.example.banco.api

import com.example.banco.model.Cuenta
import com.example.banco.model.Transaccion
import com.example.banco.model.UserInfo
import retrofit2.Call
import retrofit2.http.*

//Declaro las rutas a las que voy a utilizar
interface ItemsApi {

 @Headers(value = ["Content-type: application/json"])
 @POST(value = "inicio_sesion")
 fun iniciarSesion(@Body credenciales: HashMap<String, Any>):Call<UserInfo>


 @Headers(value = ["Content-type: application/json"])
 @GET(value = "usuario/consulta_cuenta/{external_persona}")
 fun consultarEstadoCuenta(@Path("external_persona") external_persona:String):Call<Cuenta>

 @Headers(value = ["Content-type: application/json"])
 @GET(value = "usuario/transaccion/{external_persona}")
 fun consultarTransacciones(@Path("external_persona") external_persona:String):Call<List<Transaccion>>

}