package com.example.t_ket.presentation.QrWindow

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.t_ket.MainActivity
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
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class QrWindowFragment : Fragment( ) {

    private var _binding: FragmentQrWindowBinding? = null
    private val binding get() = _binding!!
    private lateinit var barcodeView: DecoratedBarcodeView
    private lateinit var main: AppCompatActivity
    private lateinit var integrator: IntentIntegrator
    private var group: Boolean = false
    private var torch: Boolean = false

    private lateinit var camera:CameraManager
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
                    Toast.makeText(requireContext(), "Escaner correcto ", Toast.LENGTH_LONG).show()
                    if(group){
                        val result = getGroupId(scanningResult.contents)
                        Log.d("AAAAAAAAAAAAAAAAA", "$result")
                        Toast.makeText(requireContext(),  "$result", Toast.LENGTH_LONG).show()
                        ticketInteractor.getGroupTickets(result)
                    }
                    ticketInteractor.checkTicket(scanningResult.contents)
                }
            }
            // Volver a iniciar el escáner después de mostrar el resultado
            initScanner()
        }

    private fun getGroupId(jsonString: String): String {
        val regex = "\"gid\":\"(\\d+)\"".toRegex()
        val matchResult = regex.find(jsonString)
        Toast.makeText(requireContext(), " ", Toast.LENGTH_LONG).show()

        return matchResult?.groupValues?.getOrNull(1) ?: ""
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
        camera = requireContext().getSystemService(Context.CAMERA_SERVICE) as CameraManager
        initScanner()
        (requireActivity() as? MainActivity)?.bind?.qrGroup?.setOnClickListener() {
            group = !group
            if(group){
                Toast.makeText(requireContext(), "group activated ", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(requireContext(), "group deactivated ", Toast.LENGTH_LONG).show()
            }

        }
        (requireActivity() as? MainActivity)?.bind?.lantern?.setOnClickListener() {
            torch = !torch
            Log.d("AAAAAAAAAAAAAAAAA", "$torch")
            if(torch){
                barcodeView.setTorchOn()
            }else{
                barcodeView.setTorchOff()
            }
        }
    }

    private fun initScanner() {
        integrator = IntentIntegrator.forSupportFragment(this)
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