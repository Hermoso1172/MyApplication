package com.example.myapplication.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDashboardBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.deviceCardLink.setOnClickListener {
            // Step 1: Navigate to the destination
            findNavController().navigate(R.id.navigation_home)

            // Step 2: Get the BottomNavigationView from the activity
            // This assumes the BottomNavView is in your MainActivity and has the ID 'nav_view'
            val bottomNavView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)

            // Step 3: Manually set the selected item in the menu
            bottomNavView.selectedItemId = R.id.navigation_home
        }

        binding.accessCardLink.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_accessFragment)

        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}