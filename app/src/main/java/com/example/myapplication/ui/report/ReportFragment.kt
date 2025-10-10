package com.example.myapplication.ui.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentReportBinding

class ReportFragment : Fragment() {

    private var _binding: FragmentReportBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentReportBinding.inflate(inflater, container, false)
        val root: View = binding.root


//        binding.confirmingPermissionCard.setOnClickListener {
//
//            findNavController().navigate(R.id.confirming_for_permission)
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}