package com.example.qrscanner

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.qrscanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), CustomScannerFragment.OnScanResultListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Agregar el fragmento de escáner
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CustomScannerFragment())
            .commit()
    }

    // Manejar el resultado del escaneo
    override fun onScanCompleted(result: String) {
        Toast.makeText(this, "${getString(R.string.Escaneando)}: $result", Toast.LENGTH_LONG).show()
        abrirEnlaceEnNavegador(result)
    }


    // Función para abrir el enlace escaneado en el navegador
    private fun abrirEnlaceEnNavegador(url: String) {
        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
            startActivity(this)
        }
    }
}
