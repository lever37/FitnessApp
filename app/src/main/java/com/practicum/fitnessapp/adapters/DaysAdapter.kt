package com.practicum.fitnessapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.practicum.fitnessapp.R
import com.practicum.fitnessapp.databinding.DaysListItemBinding

class DaysAdapter(var listener: Listener) : ListAdapter<DayModel, DaysAdapter.DayHolder>(comparator()) {

    class DayHolder(view: View): ViewHolder(view){

        private val binding = DaysListItemBinding.bind(view)

        fun setData(dayModel: DayModel, listener: Listener) = with(binding){

            val sting: String = root.context.getString(R.string.day) + " ${adapterPosition + 1}"
            exerciseName.text = sting

            val exerciseCounter = dayModel.exercises.split(",").size.toString()
            exerciseCounterText.text = root.context.getString(R.string.number_of_exercises) + " " + exerciseCounter

            checkBox.isChecked = dayModel.isDone

            itemView.setOnClickListener {
                listener.onClick(dayModel.copy(dayNumber = adapterPosition + 1))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
        val view = LayoutInflater.from(parent.context).
        inflate(R.layout.days_list_item, parent, false)
        return DayHolder(view)
    }

    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    class comparator: DiffUtil.ItemCallback<DayModel>(){
        override fun areItemsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
            return oldItem == newItem
        }
    }

    interface Listener{
        fun onClick(day: DayModel)
    }


}