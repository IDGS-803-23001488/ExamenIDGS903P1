package com.utl.idgs903.angel.examenidgs903p1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class DesencriptarActivity : AppCompatActivity() {
    private lateinit var spMensajes : Spinner
    private lateinit var edtDesplazamiento : EditText
    private lateinit var btnDesencriptar : Button
    private lateinit var btnRegresar : Button
    private lateinit var txtEncriptado : TextView
    private lateinit var txtResultado : TextView
    private lateinit var txtComparacion : TextView
    private var mensajes = ArrayList<MensajeGuardado>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desencriptar)
        setStatusBarColor(this, this, findViewById<ConstraintLayout>(R.id.main), window)

        spMensajes = findViewById(R.id.spMensajes)
        edtDesplazamiento = findViewById(R.id.edtDesplazamiento)
        btnDesencriptar = findViewById(R.id.btnDesencriptar)
        btnRegresar = findViewById(R.id.btnRegresar)
        txtEncriptado = findViewById(R.id.txtEncriptado)
        txtResultado = findViewById(R.id.txtResultado)
        txtComparacion = findViewById(R.id.txtComparacion)

        cargarMensajes()

        btnDesencriptar.setOnClickListener {
            desencriptarMensaje()
        }

        btnRegresar.setOnClickListener {
            finish()
        }
    }

    private fun cargarMensajes() {
        mensajes = CesarArchivo.leer(this)
        val opciones = ArrayList<String>()

        if (mensajes.isEmpty()) {
            opciones.add("No hay mensajes guardados")
        } else {
            for (mensaje in mensajes) {
                opciones.add(mensaje.encriptado)
            }
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spMensajes.adapter = adapter
    }

    private fun desencriptarMensaje() {
        if (mensajes.isEmpty()) {
            Toast.makeText(this, "Primero encripta un mensaje", Toast.LENGTH_SHORT).show()
            return
        }

        val desplazamientoTexto = edtDesplazamiento.text.toString()
        if (desplazamientoTexto.isEmpty()) {
            Toast.makeText(this, "Escribe el desplazamiento", Toast.LENGTH_SHORT).show()
            return
        }

        val desplazamiento = desplazamientoTexto.toInt()
        if (desplazamiento <= 0) {
            Toast.makeText(this, "El desplazamiento debe ser positivo", Toast.LENGTH_SHORT).show()
            return
        }

        val mensaje = mensajes[spMensajes.selectedItemPosition]
        val desencriptado = CesarArchivo.desencriptar(mensaje.encriptado, desplazamiento)
        val correcto = if (desencriptado == mensaje.original) {
            "Los mensajes coinciden"
        } else {
            "No coincide el mensaje"
        }

        txtEncriptado.text = "Mensaje encriptado: ${mensaje.encriptado}"
        txtResultado.text = "Mensaje desencriptado: $desencriptado"
        txtComparacion.text = "Comparacion: $correcto"
    }
}
