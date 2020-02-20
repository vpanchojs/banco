package com.example.banco

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banco.adaptadores.TransaccionAdapter
import com.example.banco.api.CallBackApi
import com.example.banco.api.RetrofitApi
import com.example.banco.model.Cuenta
import com.example.banco.model.Transaccion
import com.example.banco.model.UserInfo
import kotlinx.android.synthetic.main.activity_transacciones.*
import kotlinx.android.synthetic.main.content_main.*

class TransaccionesActivity : AppCompatActivity() {
    var api: RetrofitApi?=null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transacciones)

        //Se obtiene los datos enviados desde la activdad anterior
        val external_id:String = intent.getStringExtra("external_id")
        val nombre_completo:String = intent.getStringExtra("nombre_completo")

        api= RetrofitApi()

        obtenerDatosCuenta(external_id,nombre_completo);

        obtenerTransacciones(external_id);

        btn_salir.setOnClickListener{ view->
            this.finish();
        }
    }

    fun obtenerDatosCuenta(external_id:String,nombre_completo:String){

        api!!.ObtenerCuenta(external_id,object:
            CallBackApi<Cuenta>{
            override fun correcto(cuenta: Cuenta) {

                tv_numero_cuenta.text=cuenta.nro_cuenta
                tv_saldo_cuenta.text=cuenta.saldo.toString()
                tv_nombre_completo.text=nombre_completo
            }

            override fun error(error: String) {
                Toast.makeText(this@TransaccionesActivity,error, Toast.LENGTH_SHORT).show();
                Log.e("cuenta",error.toString())
            }

        });
    }


    fun obtenerTransacciones(external_id: String){

        api!!.ObtenerTransacciones(external_id,object:
            CallBackApi<List<Transaccion>>{

            override fun correcto(transacciones: List<Transaccion>) {

                rv_transaccion.layoutManager=LinearLayoutManager(this@TransaccionesActivity)
                rv_transaccion.adapter=TransaccionAdapter(transacciones)
            }

            override fun error(error: String) {
                Toast.makeText(this@TransaccionesActivity,error, Toast.LENGTH_SHORT).show();
                Log.e("cuenta",error.toString())
            }

        });
    }
}
