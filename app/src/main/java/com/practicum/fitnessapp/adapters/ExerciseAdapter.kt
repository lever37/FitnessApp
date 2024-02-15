package com.practicum.fitnessapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.practicum.fitnessapp.R
import com.practicum.fitnessapp.databinding.ExerciseListItemBinding
import pl.droidsonroids.gif.GifDrawable

class ExerciseAdapter() : ListAdapter<ExerciseModel, ExerciseAdapter.ExerciseHolder>(comparator()) {

    class ExerciseHolder(view: View): ViewHolder(view){

        private val binding = ExerciseListItemBinding.bind(view)

        fun setData(exerciseModel: ExerciseModel) = with(binding){

            nameExercise.text = exerciseModel.name
            counterRepetitions.text= exerciseModel.repeat
            relaxTime.text = exerciseModel.relaxTime + " relax"
            isItDoneCheckBox.isChecked = exerciseModel.isDone
            imageExercise.setImageDrawable(GifDrawable(root.context.assets, exerciseModel.image))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
        val view = LayoutInflater.from(parent.context).
        inflate(R.layout.exercise_list_item, parent, false)
        return ExerciseHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        holder.setData(getItem(position))
    }

    class comparator: DiffUtil.ItemCallback<ExerciseModel>(){
        override fun areItemsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem == newItem
        }
    }


}