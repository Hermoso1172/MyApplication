package com.example.myapplication.ui.suspiciousact

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentSuspiciousActBinding

class SuspiousActFragment : Fragment() {

    private var _binding: FragmentSuspiciousActBinding? = null
    private val binding get() = _binding!!

    // STEP 1: Create a state variable to track if the details are visible.
    // 'false' means they are initially hidden.
    private var isDetailsVisible = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuspiciousActBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val suspiciousVid = binding.SuspiciousActVid
        val dimOverlay = binding.dimOverlay
        val detailsButton = binding.viewDetailsButton

        // STEP 2: Use a simple OnClickListener on the ImageView.
        suspiciousVid.setOnClickListener {
            // Invert the state flag on each click
            isDetailsVisible = !isDetailsVisible

            if (isDetailsVisible) {
                // If details should be visible, show them.
                dimOverlay.animate().alpha(1.0f).setDuration(200).start()
                detailsButton.visibility = View.VISIBLE
            } else {
                // If details should be hidden, hide them.
                dimOverlay.animate().alpha(0.0f).setDuration(200).start()
                detailsButton.visibility = View.INVISIBLE
            }
        }

        // This listener is for the "View Details" button itself.
        // It remains unchanged.
        detailsButton.setOnClickListener {
            Toast.makeText(requireContext(), "View Details Clicked!", Toast.LENGTH_SHORT).show()
            // TODO: Add your navigation logic here, e.g.,
            // findNavController().navigate(R.id.action_suspiousActFragment_to_details)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
