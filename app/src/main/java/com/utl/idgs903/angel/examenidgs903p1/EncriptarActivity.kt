package com.utl.idgs903.angel.examenidgs903p1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class EncriptarActivity : AppCompatActivity() {
    private lateinit var edtMensaje : EditText
    private lateinit var edtDesplazamiento : EditText
    private lateinit var btnEncriptar : Button
    private lateinit var btnRegresar : Button
    private lateinit var txtOriginal : TextView
    private lateinit var txtResultado : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encriptar)
        setStatusBarColor(this, this, findViewById<ConstraintLayout>(R.id.main), window)

        edtMensaje = findViewById(R.id.edtMensaje)
        edtDesplazamiento = findViewById(R.id.edtDesplazamiento)
        btnEncriptar = findViewById(R.id.btnEncriptar)
        btnRegresar = findViewById(R.id.btnRegresar)
        txtOriginal = findViewById(R.id.txtOriginal)
        txtResultado = findViewById(R.id.txtResultado)

        btnEncriptar.setOnClickListener {
            encriptarMensaje()
        }

        btnRegresar.setOnClickListener {
            finish()
        }
    }

    private fun encriptarMensaje() {
        val mensaje = edtMensaje.text.toString()
        val desplazamientoTexto = edtDesplazamiento.text.toString()

        if (mensaje.isEmpty()) {
            Toast.makeText(this, "Escribe un mensaje", Toast.LENGTH_SHORT).show()
            return
        }

        if (desplazamientoTexto.isEmpty()) {
            Toast.makeText(this, "Escribe el desplazamiento", Toast.LENGTH_SHORT).show()
            return
        }

        val desplazamiento = desplazamientoTexto.toInt()
        if (desplazamiento <= 0) {
            Toast.makeText(this, "El desplazamiento debe ser positivo", Toast.LENGTH_SHORT).show()
            return
        }

        val encriptado = CesarArchivo.encriptar(mensaje, desplazamiento)
        val registro = MensajeGuardado(mensaje, desplazamiento, encriptado)
        CesarArchivo.guardar(this, registro)

        txtOriginal.text = "Mensaje original: $mensaje"
        txtResultado.text = "Mensaje encriptado: $encriptado"
        Toast.makeText(this, "Mensaje guardado", Toast.LENGTH_SHORT).show()
    }
}
