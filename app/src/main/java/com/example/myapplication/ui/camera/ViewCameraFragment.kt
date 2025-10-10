package com.example.myapplication.ui.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentViewCameraBinding

class ViewCameraFragment : Fragment() {

    private var _binding: FragmentViewCameraBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.FromViewFloorFragmentToFragmentHome.setOnClickListener {
//            findNavController().popBackStack()
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}