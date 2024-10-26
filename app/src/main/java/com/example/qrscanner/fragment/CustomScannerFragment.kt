package com.example.qrscanner

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.qrscanner.databinding.FragmentCustomScannerBinding
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CompoundBarcodeView

class CustomScannerFragment : Fragment() {

    private lateinit var barcodeView: CompoundBarcodeView
    private var scanListener: OnScanResultListener? = null
    private var _binding: FragmentCustomScannerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomScannerBinding.inflate(inflater, container, false)

        var isFlashOn = false
        // Configurar BarcodeView
        barcodeView = binding.barcodeScanner
        barcodeView.setStatusText("${getString(R.string.Escaneando)}...")
        barcodeView.decodeContinuous(callback)

        // Botón para cancelar el escaneo
        binding.btnFlash.setOnClickListener {

            // Cambiar el estado del flash

            if (isFlashOn) {
                barcodeView.setTorchOff()

                binding.btnFlash.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.flash_off_24px, 0, 0, 0)
            } else {
                barcodeView.setTorchOn()
                binding.btnFlash.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.flash_on_24px, 0, 0, 0)

            }


            // Cambiar el texto del botón
            val text = if (!isFlashOn) getString(R.string.ApagarFlash) else getString(R.string.EncenderFlash)
            binding.btnFlash.text = text
            isFlashOn = !isFlashOn
        }

        return binding.root
    }

    // Callback para manejar el resultado del escaneo
    private val callback = BarcodeCallback { result: BarcodeResult? ->
        result?.let {
            barcodeView.pause()
            scanListener?.onScanCompleted(it.text) // Enviar el resultado al listener
        }
    }

    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnScanResultListener) {
            scanListener = context
        } else {
            throw RuntimeException("$context debe implementar OnScanResultListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        scanListener = null
    }

    // Interface para comunicar el resultado de escaneo a la actividad
    interface OnScanResultListener {
        fun onScanCompleted(result: String)
    }
}
