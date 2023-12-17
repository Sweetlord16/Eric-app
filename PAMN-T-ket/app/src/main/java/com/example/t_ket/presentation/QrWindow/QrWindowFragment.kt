package com.example.t_ket.presentation.QrWindow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.t_ket.R
import com.example.t_ket.core.domain.usecase.TicketInteractorImpl
import com.example.t_ket.databinding.FragmentQrWindowBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class QrWindowFragment : Fragment( ) {

    private var _binding: FragmentQrWindowBinding? = null
    private val binding get() = _binding!!
    private lateinit var barcodeView: DecoratedBarcodeView
    @Inject
    lateinit var ticketInteractor: TicketInteractorImpl
    private val qrResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val scanningResult = IntentIntegrator.parseActivityResult(result.resultCode, result.data)
            if (scanningResult.contents == null) {
                Toast.makeText(requireContext(), "Cancelado", Toast.LENGTH_LONG).show()
                initScanner()
            } else {

                lifecycleScope.launch {
                    Toast.makeText(requireContext(), "TOTO PIOLA ", Toast.LENGTH_LONG).show()
                    ticketInteractor.checkTicket(scanningResult.contents)
                }
            }
            // Volver a iniciar el escáner después de mostrar el resultado
            initScanner()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQrWindowBinding.inflate(layoutInflater, container, false)
        barcodeView = binding.barcodeScannerView

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initScanner()
    }

    private fun initScanner() {
        val integrator = IntentIntegrator.forSupportFragment(this)
        barcodeView.barcodeView.decoderFactory = DefaultDecoderFactory(listOf(BarcodeFormat.QR_CODE))
        barcodeView.initializeFromIntent(activity?.intent)
        barcodeView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                result?.let {
                    qrResultLauncher.launch(integrator.createScanIntent())
                    //Inicia la actividad para el resultado del escaneo
                }
            }

            override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
                // Puedes implementar lógica adicional si es necesario
            }
        })
        barcodeView.resume()
    }

    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}