package com.example.banco.api

import com.example.banco.model.Cuenta
import com.example.banco.model.Transaccion
import com.example.banco.model.UserInfo
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.HashMap

///Libreria para consumir el Api
class RetrofitApi {

    //Ubicamos la ruta principal del servicio web
    companion object {
        var PATH_API="http://192.168.1.7/agenda2/public/index.php/";
    }

    //Inicializamos un objeto retrofit
    var retrofit= Retrofit.Builder()
        .baseUrl(PATH_API)
        .addConverterFactory(GsonConverterFactory.create( GsonBuilder()
            .setDateFormat("yyyy-MM-dd hh:mm:ss")
            .create()))
        .build();

    //Declara un objeto para hacer las peticiones.
    var request=retrofit.create(ItemsApi::class.java);


    //Metodo de iniciar sesion
    //@param Cuenta
    //@param pin
    //Object Callback
    fun IniciarSession(cuenta:String,pin:String,respuesta: CallBackApi<UserInfo>){

        var credenciales=HashMap<String,Any>();
        credenciales.set("cuenta",cuenta);
        credenciales.set("pin",pin);

        //Aqui se hace la peticion al servicio web
        request.iniciarSesion(credenciales).enqueue(object :Callback<UserInfo>{

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                respuesta.error(t.toString())
            }

            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {

                if(response.code()==200){
                    respuesta.correcto(response.body()!!)
                }else{
                    if(response.code()==404){
                        respuesta.error("Credenciales Incorrectas, es posible que la cuenta no exista")
                    }
                }

            }

        })
    }


    //Obtener la cuenta
    fun ObtenerCuenta(external_id:String, respuesta: CallBackApi<Cuenta>){

        request.consultarEstadoCuenta(external_id).enqueue(object : Callback<Cuenta>{

            override fun onFailure(call: Call<Cuenta>, t: Throwable) {
                respuesta.error(t.toString())
            }

            override fun onResponse(call: Call<Cuenta>, response: Response<Cuenta>) {
                if(response.code()==200){
                    respuesta.correcto(response.body()!!)
                }else{
                    if(response.code()==404){
                        respuesta.error("Cuenta no disponible, es posible que la cuenta no exista")
                    }
                }
            }

        }
        )
    }

    fun ObtenerTransacciones(external_id:String, respuesta: CallBackApi<List<Transaccion>>){

        request.consultarTransacciones(external_id).enqueue(object : Callback<List<Transaccion>>{

            override fun onFailure(call: Call<List<Transaccion>>, t: Throwable) {
                respuesta.error(t.toString())
            }

            override fun onResponse(call: Call<List<Transaccion>>, response: Response<List<Transaccion>>) {
                if(response.code()==200){
                    respuesta.correcto(response.body()!!)
                }else{
                    if(response.code()==404){
                        respuesta.error("Transacciones no disponibles, es posible que la cuenta no exista")
                    }
                }
            }

        }
        )
    }





}

