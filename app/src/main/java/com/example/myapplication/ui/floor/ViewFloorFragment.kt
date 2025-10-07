package com.example.myapplication.ui.floor // Or your chosen package

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentViewFloorBinding
import com.example.myapplication.R

class ViewFloorFragment : Fragment() {

    private var _binding: FragmentViewFloorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewFloorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.FromViewFloorFragmentToFragmentHome.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.cardViewKitchen.setOnClickListener {
            findNavController().navigate(R.id.action_viewFloorFragment_to_viewDoorFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
