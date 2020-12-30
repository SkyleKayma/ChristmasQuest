package fr.skyle.christmasquest.ui.enigma2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.skyle.christmasquest.databinding.EventItemLayoutBinding

class AdapterEnigma2Step2Events(
    private var events: List<String>
) : RecyclerView.Adapter<AdapterEnigma2Step2Events.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder =
        EventViewHolder(EventItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]

        holder.bind(event)
    }

    override fun getItemCount(): Int =
        events.size

    class EventViewHolder(val binding: EventItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: String) {
            binding.textViewEnigma2Step3Event.text = event
        }
    }
}
