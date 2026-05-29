package com.utl.idgs903.angel.examenidgs903p1

import android.content.Context
import android.util.Base64

data class MensajeGuardado(
    val original: String,
    val desplazamiento: Int,
    val encriptado: String
)

object CesarArchivo {
    private const val NOMBRE_ARCHIVO = "registros.txt"

    fun encriptar(mensaje: String, desplazamiento: Int): String {
        return transformar(mensaje, desplazamiento)
    }

    fun desencriptar(mensaje: String, desplazamiento: Int): String {
        return transformar(mensaje, -desplazamiento)
    }

    fun guardar(context: Context, mensaje: MensajeGuardado) {
        val linea = codificar(mensaje.original) + "|" +
            mensaje.desplazamiento + "|" +
            codificar(mensaje.encriptado) + "\n"

        context.openFileOutput(NOMBRE_ARCHIVO, Context.MODE_APPEND).use {
            it.write(linea.toByteArray())
        }
    }

    fun leer(context: Context): ArrayList<MensajeGuardado> {
        val lista = ArrayList<MensajeGuardado>()

        try {
            context.openFileInput(NOMBRE_ARCHIVO).bufferedReader().useLines { lineas ->
                lineas.forEach { linea ->
                    val partes = linea.split("|")
                    if (partes.size == 3) {
                        lista.add(
                            MensajeGuardado(
                                decodificar(partes[0]),
                                partes[1].toInt(),
                                decodificar(partes[2])
                            )
                        )
                    }
                }
            }
        } catch (_: Exception) {
            return lista
        }

        return lista
    }

    private fun transformar(mensaje: String, desplazamiento: Int): String {
        val resultado = StringBuilder()

        for (caracter in mensaje) {
            val nuevoCaracter = when (caracter) {
                in 'A'..'Z' -> moverCaracter(caracter, 'A', desplazamiento)
                in 'a'..'z' -> moverCaracter(caracter, 'a', desplazamiento)
                else -> caracter
            }
            resultado.append(nuevoCaracter)
        }

        return resultado.toString()
    }

    private fun moverCaracter(caracter: Char, inicio: Char, desplazamiento: Int): Char {
        val alfabeto = 26
        val posicion = caracter.code - inicio.code
        val nuevaPosicion = (posicion + desplazamiento).mod(alfabeto)
        return (inicio.code + nuevaPosicion).toChar()
    }

    private fun codificar(texto: String): String {
        return Base64.encodeToString(texto.toByteArray(), Base64.NO_WRAP)
    }

    private fun decodificar(texto: String): String {
        return String(Base64.decode(texto, Base64.NO_WRAP))
    }
}
