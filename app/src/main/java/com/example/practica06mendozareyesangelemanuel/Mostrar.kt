package com.example.practica06mendozareyesangelemanuel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.TextView

class Mostrar : AppCompatActivity() {
    private lateinit var datos: TextView
    private lateinit var objConcierto: Concierto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar)

        datos = findViewById(R.id.txtDatos)
        objConcierto = Concierto()

        val infoRecibida = intent.extras

        objConcierto.codigo  = infoRecibida?.getInt("codigo")!!
        objConcierto.artista = infoRecibida?.getString("artista")!!
        objConcierto.lugar = infoRecibida?.getString("lugar")!!
        objConcierto.asiento = infoRecibida?.getString("asiento")!!
        objConcierto.costo = infoRecibida?.getDouble("costo")!!
        objConcierto.horario = infoRecibida?.getString("hora")!!

        var asiento : String? = null
        if(objConcierto.asiento == "normal") asiento = "Asiento Normal"
        if(objConcierto.asiento== "premium") asiento = "Asiento Premium"

        datos.text = "\n\nCodigo: "+objConcierto.codigo +
                "\n\nArtista: "+objConcierto.artista +
                "\n\nAsiento: "+objConcierto.asiento +
                "\n\nLugar: "+objConcierto.lugar +
                "\n\nCosto: "+objConcierto.costo+
                "\n\nHorario: "+objConcierto.horario
    }

    fun regresar(view: View){
        val intent = Intent(this, MainActivity::class.java)
        finish()
    }
}