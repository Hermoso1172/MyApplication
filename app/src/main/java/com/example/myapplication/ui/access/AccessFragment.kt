package com.example.myapplication.ui.access

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAccessBinding
import com.example.myapplication.ui.access.Person
import com.example.myapplication.ui.access.PersonAccessAdapter

class AccessFragment : Fragment() {

    private var _binding: FragmentAccessBinding? = null
    private val binding get() = _binding!!
    private var isKitchenExpanded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupKitchenSection()
    }

    private fun setupKitchenSection() {
        // 1. Create dummy data for the list of people.
        val peopleWithAccess = listOf(
            Person("Clarence"),
            Person("Alice"),
            Person("Bob"),
            Person("Cathy", R.drawable.ic_profile_icon) // Example with a different icon
        )

        // 2. Create an instance of the adapter and give it the data.
        val adapter = PersonAccessAdapter(peopleWithAccess)

        // 3. Set up the RecyclerView with a LayoutManager and the Adapter.
        binding.recyclerViewKitchenAccess.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewKitchenAccess.adapter = adapter

        // 4. Set up the click listener to expand/collapse.
        binding.cardKitchenHeader.setOnClickListener {
            isKitchenExpanded = !isKitchenExpanded // Toggle the state

            // Add a smooth animation
            TransitionManager.beginDelayedTransition(binding.root as ViewGroup)

            if (isKitchenExpanded) {
                // Expand: make the list visible and rotate the arrow
                binding.recyclerViewKitchenAccess.visibility = View.VISIBLE
                binding.imageKitchenArrow.animate().rotation(180f).start()
            } else {
                // Collapse: make the list gone and rotate the arrow back
                binding.recyclerViewKitchenAccess.visibility = View.GONE
                binding.imageKitchenArrow.animate().rotation(0f).start()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
