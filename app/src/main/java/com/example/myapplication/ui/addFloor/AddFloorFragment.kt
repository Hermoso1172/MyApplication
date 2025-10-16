package com.example.myapplication.ui.addFloor

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.result.contract.ActivityResultContracts
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.AddFloorBinding
import com.example.myapplication.databinding.PopupSuccessBinding
import com.journeyapps.barcodescanner.CaptureActivity

class AddFloorFragment : Fragment() {

    private var _binding: AddFloorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddFloorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.connectToExistingFloor.setOnClickListener {
            binding.initialStateGroup.visibility = View.GONE
            binding.scanStateGroup.visibility = View.VISIBLE
            binding.textViewTitleAddFloor.text = "Connect to Floor"
        }

        binding.backButton.setOnClickListener {
            binding.scanStateGroup.visibility = View.GONE
            binding.initialStateGroup.visibility = View.VISIBLE
            binding.textViewTitleAddFloor.text = "Add New Floor"

        }

        // Add the click listener for the newFloorButton
        binding.newFloorButton.setOnClickListener {
            // This function will handle the custom popup
            showSuccessPopupAndNavigate()
        }

        binding.scanQrButton.setOnClickListener {
            launchScanner()
        }
    }

    private fun showSuccessPopupAndNavigate() {
        // Get the root view of the fragment to add the popup to it
        val rootView = requireActivity().findViewById<FrameLayout>(android.R.id.content)
        val inflater = LayoutInflater.from(requireContext())

        // Inflate the popup layout using ViewBinding
        val popupBinding = PopupSuccessBinding.inflate(inflater, null, false)

        // Add the popup view to the root
        rootView.addView(popupBinding.root)

        // Animate the popup to fade in
        popupBinding.root.alpha = 0f
        popupBinding.root.animate().alpha(1f).setDuration(300).start()

        // Use a Handler to delay the fade-out and navigation
        Handler(Looper.getMainLooper()).postDelayed({
            // Animate the fade-out
            popupBinding.root.animate().alpha(0f).setDuration(300).withEndAction {
                // After the fade-out animation ends, remove the view and navigate
                rootView.removeView(popupBinding.root)
                findNavController().navigate(R.id.action_addFloorFragment_to_navigation_home)
            }.start()
        }, 2500) // 1.5-second delay before fading out
    }

    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents == null) {
            // This happens if the user presses the back button
            Toast.makeText(requireContext(), "Scan cancelled", Toast.LENGTH_LONG).show()
        } else {
            // The QR code was successfully scanned
            // The scanned content is in result.contents
            Toast.makeText(requireContext(), "Scanned: ${result.contents}", Toast.LENGTH_LONG).show()

            // You can now use the scanned code. For example, fill the search bar:
            binding.searchBarLayout.editText?.setText(result.contents)
        }
    }

    private fun launchScanner() {


        val options = ScanOptions()

        options.captureActivity = CaptureActivity::class.java
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan a floor QR code")
        options.setCameraId(0)  // Use a specific camera of the device
        options.setBeepEnabled(true)
        options.setBarcodeImageEnabled(true)
        options.setOrientationLocked(false)
        barcodeLauncher.launch(options)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
