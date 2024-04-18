package com.example.practica06mendozareyesangelemanuel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    val tamanio = 10
    var objConcierto = Array<Concierto?>(tamanio) {null}
    var contadorConciertos = 0
    private var encontrado: Boolean = false

    private lateinit var seach: EditText
    private lateinit var artist: Spinner
    private lateinit var seat: RadioGroup
    private lateinit var normal: RadioButton
    private lateinit var gold: RadioButton
    private lateinit var place: ListView
    private lateinit var cost: EditText
    private lateinit var horary: Spinner
    private var artistSel: String = "Peso Pluma"
    private var placeSel: String = "Auditorio Telmex"
    private var horarySel: String = "08:00pm"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seach = findViewById(R.id.editSeach)
        artist = findViewById(R.id.spnArtista)
        seat = findViewById(R.id.rgpAsientos)
        normal = findViewById(R.id.rbNormal)
        gold = findViewById(R.id.rbPremium)
        place = findViewById(R.id.ltvLugar)
        cost = findViewById(R.id.editCosto)
        horary = findViewById(R.id.spnHorario)

        val lstArtista = resources.getStringArray(R.array.listaArtistas)
        val adaptadorSpn = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,lstArtista)
        artist.adapter = adaptadorSpn
        artist.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                artistSel = lstArtista[p2]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val lstPlace = listOf("Auditorio Telmex","Teatro Diana","Estadio Akron","Estadio Jalisco")
        val adaptador = ArrayAdapter(this,android.R.layout.simple_list_item_1,lstPlace)
        place.adapter = adaptador
        place.setOnItemClickListener { parent, view, position, id ->
            placeSel = parent.getItemAtPosition(position).toString()
            Toast.makeText(this,"Lugar: $placeSel",Toast.LENGTH_LONG).show()
        }

        val lstHorario = resources.getStringArray(R.array.listaHorario)
        val adaptadorSpn2 = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,lstHorario)
        horary.adapter = adaptadorSpn2
        horary.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                horarySel = lstHorario[p2]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    fun onClick(v: View?){
        when(v?.id){
            R.id.ibtnAgregar -> agregar()
            R.id.ibtnBuscar -> buscar()
            R.id.ibtnEliminar -> eliminar()
            R.id.ibtnLimpiar -> limpiar()
        }
    }

    private fun agregar() {
        if (seach.text.isNotEmpty()) {
            val codigo = seach.text.toString().toInt()
            if (!codigoExiste(codigo)) {
                if (contadorConciertos < tamanio) {
                    val objetoConcierto = Concierto()
                    objetoConcierto.codigo = codigo
                    objetoConcierto.artista = artistSel
                    if (normal.isChecked) objetoConcierto.asiento = "normal"
                    if (gold.isChecked) objetoConcierto.asiento = "premium"
                    objetoConcierto.lugar = placeSel
                    objetoConcierto.horario = horarySel
                    objetoConcierto.costo = cost.text.toString().toDouble()
                    objConcierto[contadorConciertos] = objetoConcierto
                    contadorConciertos++
                    limpiar()
                    Toast.makeText(this, "Se ha registrado correctamente ", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "El arreglo está lleno", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "El código ya existe, no se puede registrar nuevamente", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "No se pudo registrar correctamente", Toast.LENGTH_LONG).show()
        }
    }

    private fun eliminar() {
        val idEliminar = seach.text.toString().toIntOrNull()

        if (idEliminar != null) {
            val encontrado = buscarElemento(idEliminar)
            if (encontrado) {
                Toast.makeText(this, "se elimino correctamente", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "No se pudo encontrar el elemento con ese código $idEliminar", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Ingresa un código correctamente", Toast.LENGTH_LONG).show()
        }
    }

    private fun buscarElemento(id: Int): Boolean {
        for (i in 0 until contadorConciertos) {
            val objetoConciertoEncontrado = objConcierto[i]
            if (objetoConciertoEncontrado?.codigo == id) {
                for (j in i until contadorConciertos - 1) {
                    objConcierto[j] = objConcierto[j + 1]
                }
                objConcierto[contadorConciertos - 1] = null
                contadorConciertos--
                return true
            }
        }
        return false
    }

    private fun buscar() {
        val idBuscar = seach.text.toString()
        if (idBuscar.isNotEmpty()) {
            val id = idBuscar.toInt()
            var encontrado = false
            for (i in 0 until contadorConciertos) {
                val objetoConciertoEncontrado = objConcierto[i]
                if (objetoConciertoEncontrado?.codigo == id) {
                    encontrado = true
                    val intent = Intent(this, Mostrar::class.java)
                    intent.putExtra("codigo", objetoConciertoEncontrado?.codigo)
                    intent.putExtra("artista", objetoConciertoEncontrado?.artista)
                    intent.putExtra("asiento", objetoConciertoEncontrado?.asiento)
                    intent.putExtra("lugar", objetoConciertoEncontrado?.lugar)
                    intent.putExtra("costo", objetoConciertoEncontrado?.costo)
                    intent.putExtra("hora", objetoConciertoEncontrado?.horario)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "No se encontro el elemento con ese codigo", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(this, "Debe ingresar un código correctamente", Toast.LENGTH_LONG).show()
        }
    }

    private fun codigoExiste(codigo: Int): Boolean {
        for (i in 0 until contadorConciertos) {
            val objetoConcierto = objConcierto[i]
            if (objetoConcierto?.codigo == codigo) {
                return true
            }
        }
        return false
    }

    private fun limpiar() {
        seach.text.clear()
        seat.clearCheck()
        cost.text.clear()
        seach.requestFocus()
    }
}
