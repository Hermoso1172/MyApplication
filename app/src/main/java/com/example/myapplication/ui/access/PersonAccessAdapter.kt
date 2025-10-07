package com.example.myapplication.ui.access

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemPersonAccessBinding // This will be generated from your item_person_access.xml

class PersonAccessAdapter(private val people: List<Person>) :
    RecyclerView.Adapter<PersonAccessAdapter.PersonViewHolder>() {

    // This class holds the views for a single item in the list.
    // The binding object gives us direct access to the views in item_person_access.xml.
    inner class PersonViewHolder(val binding: ItemPersonAccessBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Called by the RecyclerView to create a new ViewHolder.
    // It inflates the item layout and creates the ViewHolder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val binding = ItemPersonAccessBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PersonViewHolder(binding)
    }

    // Called by the RecyclerView to display the data at a specific position.
    // This is where you bind the person's data to the views.
    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = people[position]
        holder.binding.textPersonName.text = person.name
        holder.binding.imagePerson.setImageResource(person.imageResId)
    }

    // Returns the total number of items in the list.
    override fun getItemCount(): Int {
        return people.size
    }
}
