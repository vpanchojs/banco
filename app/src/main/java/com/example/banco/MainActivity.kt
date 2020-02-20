package com.example.banco

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.banco.api.CallBackApi
import com.example.banco.api.RetrofitApi
import com.example.banco.model.UserInfo

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_transacciones.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    var api: RetrofitApi?=null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Inicializando la clase de retrofit para realizar las peticiones al servicio web
        api= RetrofitApi()


        //Asignar un evento de click al elemento visual que tiene como id btn_ingresar.
        btn_ingresar.setOnClickListener { view ->

            //Obtenemos las credenciales para el inicio de sesion
            var cuenta= et_cuenta.text.toString();
            var pin =et_pin.text.toString();

            if(cuenta.equals("") || pin.equals("")){

                if(cuenta.isBlank()){
                    et_cuenta.error="Campo Requerido"
                }
                if(pin.isBlank()){
                    et_pin.error="Campo Requerido"
                }
            }else{
                btn_ingresar.isEnabled=false
                et_cuenta.isEnabled=false;
                et_pin.isEnabled=false;
                api!!.IniciarSession(cuenta,pin,object:CallBackApi<UserInfo> {

                    override fun correcto(user: UserInfo) {
                        //Se limpia el formulario
                        et_cuenta.setText("")
                        et_pin.setText("")
                        //Inicie Session correctamente
                        Log.e("iniciar session",user.external_id)

                        Toast.makeText(this@MainActivity,"Bienvenido ${user.nombres}",Toast.LENGTH_SHORT).show()

                        var intent = Intent(this@MainActivity, TransaccionesActivity::class.java)
                        intent.putExtra("external_id", user.external_id)
                        intent.putExtra("nombre_completo", user.nombres +" "+ user.apellidos)

                        startActivity(intent)
                        btn_ingresar.isEnabled=true
                        et_cuenta.isEnabled=true
                        et_pin.isEnabled=true
                    }

                    override fun error(error: String) {
                        //Problemas al iniciar session
                        btn_ingresar.isEnabled=true
                        et_cuenta.isEnabled=true
                        et_pin.isEnabled=true
                        Toast.makeText(this@MainActivity,error,Toast.LENGTH_SHORT).show();
                        Log.e("iniciar session",error.toString())
                    }

                })
            }
        }
    }
}
