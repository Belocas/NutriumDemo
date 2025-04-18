package com.example.nutriumdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.nutriumdemo.data.dto.Professional

class ProfessionalsAdapter (private val dataSet: MutableList<Professional>): RecyclerView.Adapter<ProfessionalsAdapter.ViewHolder>(){


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameProfessional: TextView
        val expertiseProfessional: TextView
        val languaguesProfessional: TextView
        val imageProfessional:ImageView
        init {
            // Define click listener for the ViewHolder's View
            nameProfessional = view.findViewById(R.id.nameProfessional)
            expertiseProfessional = view.findViewById(R.id.professionalExpertise)
            languaguesProfessional = view.findViewById(R.id.languageProfessionals)
            imageProfessional = view.findViewById(R.id.imageProfessional)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.professional_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.nameProfessional.text = dataSet[position].name
        viewHolder.expertiseProfessional.text = dataSet[position].expertise.toString()
        viewHolder.languaguesProfessional.text = dataSet[position].languages.toString()

        viewHolder.imageProfessional.load(dataSet[position].profile_picture_url) {
            crossfade(true)
            placeholder(R.drawable.user)
            error(R.drawable.user)
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun updateData(newItems: Array<Professional>) {
        dataSet.clear()
        dataSet.addAll(newItems)
        notifyDataSetChanged() // Atualiza a lista inteira
    }

}