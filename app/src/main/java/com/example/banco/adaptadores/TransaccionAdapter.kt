package com.example.banco.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banco.R
import com.example.banco.model.Transaccion
import kotlinx.android.synthetic.main.item_transaccion.view.*

class TransaccionAdapter(var lista: List<Transaccion>) : RecyclerView.Adapter<ViewHolder>() {

    //Definir nuestra mini vista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaccion, parent, false)
        return ViewHolder(view)
    }

    //Devolvernos el numero de items que tiene la lista
    override fun getItemCount(): Int {
        return lista.size
    }

    //Establecer los valores en la vista.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var transaccion = lista.get(position)

        holder.view.tv_tipo.text = transaccion.movimiento!!.nombre
        holder.view.tv_fecha2.text = transaccion.created_at.toString()
        holder.view.tv_numero2.text = transaccion.nro_transaccion
        holder.view.tv_saldo2.text = transaccion.saldo_actual.toString()
        holder.view.tv_valor2.text = transaccion.valor.toString()
    }

}

class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

}