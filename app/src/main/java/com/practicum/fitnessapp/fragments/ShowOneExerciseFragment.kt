package com.practicum.fitnessapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.practicum.fitnessapp.adapters.ExerciseModel
import com.practicum.fitnessapp.databinding.FragmentInfoBinding
import com.practicum.fitnessapp.utils.FragmentManager
import com.practicum.fitnessapp.utils.MainViewModel
import pl.droidsonroids.gif.GifDrawable

class ShowOneExerciseFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding
    private val model: MainViewModel by activityViewModels()
    private var counterExercises = 0
    private var listExercises: ArrayList<ExerciseModel>? = null
    private var ab: ActionBar? = null
    private var currentDay = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentInfoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentDay = model.currentDayNumber

        counterExercises = model.getExerciseCount()

        ab = (activity as AppCompatActivity).supportActionBar

        model.mutableListExercise.observe(viewLifecycleOwner){
            listExercises = it
            nextExercise()
        }

        binding.buttonNextExercise.setOnClickListener {
            nextExercise()
        }
    }

    private fun nextExercise(){
        if(counterExercises < listExercises?.size!!){
            val exercise = listExercises?.get(counterExercises++) ?: return
            showExercise(exercise)
            progressBarMove()
        } else{
            counterExercises++
            FragmentManager.setFragmentAsMain(FinishDayFragment.newInstance(),activity as AppCompatActivity)

        }
    }

    private fun showExercise(exerciseModel: ExerciseModel) = with(binding){
        showExerciseImage.setImageDrawable(GifDrawable(root.context.assets,exerciseModel.image))
        textNameExercise.text =exerciseModel.name
        textSets.text = exerciseModel.repeat
        textRelaxTime.text = exerciseModel.relaxTime
        val title = "$counterExercises / ${listExercises?.size}"
        ab?.title= title
    }

    private fun progressBarMove()= with(binding){
        progressBarExercise.max = listExercises?.size!!
        progressBarExercise.progress = counterExercises
    }

    override fun onDetach() {
        super.onDetach()
        model.savePref(currentDay.toString(), counterExercises-1)

    }

    companion object {
        @JvmStatic
        fun newInstance() = ShowOneExerciseFragment()
    }


}