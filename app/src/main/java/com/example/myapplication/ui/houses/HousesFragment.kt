package com.example.myapplication.ui.houses

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHousesBinding

class HousesFragment : Fragment() {

    private var _binding: FragmentHousesBinding? = null
    private val binding get() = _binding!!
    private var isDeleteModeActive = false

    // Enum to manage the different screens/states of the fragment
    private enum class ViewState {
        LIST_VIEW,  // The default view with the list of houses
        ADD_MENU,   // The menu with "Connect" and "Create" buttons
        CREATE_FORM // The form with inputs for floor count and house name
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHousesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- SETUP ALL CLICK LISTENERS ---

        // Listener for the header delete icon
        binding.deleteDoorDevice.setOnClickListener {
            // Don't allow entering delete mode if we are in an "add" state
            if (binding.addStateContainer.isVisible) return@setOnClickListener

            isDeleteModeActive = !isDeleteModeActive
            toggleDeleteIconsVisibility(isDeleteModeActive)
        }

        // Listener for the header "add" icon
        binding.addDoorDevice.setOnClickListener {
            // If we are in delete mode, exit it first before showing the add menu
            if (isDeleteModeActive) {
                isDeleteModeActive = false
                toggleDeleteIconsVisibility(false)
            }
            // Switch to the "Add Menu" state
            toggleViews(ViewState.ADD_MENU)
        }

        // Listener for the "Back" button (its behavior is managed in toggleViews)
        binding.backButtonAddHouse.setOnClickListener {
            // Default action is to go to the list view
            toggleViews(ViewState.LIST_VIEW)
        }

        // Listener for the "Create new Home" button
        binding.createNewHouseButton.setOnClickListener {
            // Switch to the "Create Form" state
            toggleViews(ViewState.CREATE_FORM)
        }
    }

    /**
     * Central function to manage which UI state is visible. This is the single
     * source of truth for showing and hiding views.
     */
    private fun toggleViews(state: ViewState) {
        val isListView = state == ViewState.LIST_VIEW

        // --- Toggle visibility of all view groups based on the state ---

        // 1. Toggle titles and header icons
        binding.textView.isVisible = isListView        // "Your House" title
        binding.iconLayout.isVisible = isListView      // Add/Delete icons
        binding.addHouseTitle.isVisible = !isListView  // "Add/Create New House" title

        // 2. Toggle the main content areas
        binding.houseListScrollView.isVisible = isListView
        binding.addStateContainer.isVisible = !isListView // The container for the other states

        // 3. Toggle the specific state within the container
        binding.addHouseGroup.isVisible = state == ViewState.ADD_MENU
        binding.createHouseGroup.isVisible = state == ViewState.CREATE_FORM

        // --- Update dynamic text and back button logic ---
        when (state) {
            ViewState.CREATE_FORM -> {
                binding.addHouseTitle.text = "Create New House"
                // When on the create form, the back button should go to the ADD_MENU
                binding.backButtonAddHouse.setOnClickListener {
                    toggleViews(ViewState.ADD_MENU)
                }
            }
            ViewState.ADD_MENU -> {
                binding.addHouseTitle.text = "Add New House"
                // When on the add menu, the back button should go to the LIST_VIEW
                binding.backButtonAddHouse.setOnClickListener {
                    toggleViews(ViewState.LIST_VIEW)
                }
            }
            ViewState.LIST_VIEW -> {
                // No specific logic needed here for back button
            }
        }
    }

    // This function for handling the individual delete icons is correct.
    private fun toggleDeleteIconsVisibility(isVisible: Boolean) {
        val container = binding.housesContainer
        container.children.forEach { cardView ->
            val deleteIcon = cardView.findViewById<View>(R.id.delete_icon_in_card)
            deleteIcon?.isVisible = isVisible

            if (isVisible) {
                deleteIcon?.setOnClickListener {
                    showDeleteConfirmationDialog(cardView)
                }
            } else {
                deleteIcon?.setOnClickListener(null)
            }
        }

        val tintColor = if (isVisible) {
            requireContext().getColor(android.R.color.holo_orange_dark)
        } else {
            requireContext().getColor(android.R.color.holo_red_dark)
        }
        binding.deleteDoorDevice.setColorFilter(tintColor)
    }

    // This function for the confirmation dialog is also correct.
    private fun showDeleteConfirmationDialog(cardToDelete: View) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete House")
            .setMessage("Are you sure you want to delete this house? This action cannot be undone.")
            .setIcon(R.drawable.ex_ic_delete)
            .setPositiveButton("Delete") { _, _ ->
                binding.housesContainer.removeView(cardToDelete)
                // TODO: Add logic to delete from a database if needed
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
