package com.utl.idgs903.angel.examenidgs903p1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var btnEncriptar : Button
    private lateinit var btnDesencriptar : Button
    private lateinit var btnSalir : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setStatusBarColor(this, this, findViewById<ConstraintLayout>(R.id.main), window)

        btnEncriptar = findViewById(R.id.btnEncriptar)
        btnDesencriptar = findViewById(R.id.btnDesencriptar)
        btnSalir = findViewById(R.id.btnSalir)

        btnEncriptar.setOnClickListener {
            val intent = Intent(this, EncriptarActivity::class.java)
            startActivity(intent)
        }

        btnDesencriptar.setOnClickListener {
            val intent = Intent(this, DesencriptarActivity::class.java)
            startActivity(intent)
        }

        btnSalir.setOnClickListener {
            finishAffinity()
        }
    }
}
