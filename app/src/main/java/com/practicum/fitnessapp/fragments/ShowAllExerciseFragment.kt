package com.practicum.fitnessapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.fitnessapp.R
import com.practicum.fitnessapp.adapters.AllExerciseAdapter
import com.practicum.fitnessapp.adapters.DayModel
import com.practicum.fitnessapp.adapters.DaysAdapter
import com.practicum.fitnessapp.adapters.ExerciseAdapter
import com.practicum.fitnessapp.adapters.ExerciseModel
import com.practicum.fitnessapp.databinding.FragmentAllExercisesBinding
import com.practicum.fitnessapp.databinding.FragmentDaysBinding
import com.practicum.fitnessapp.databinding.FragmentExerciseBinding
import com.practicum.fitnessapp.utils.MainViewModel

class ShowAllExerciseFragment : Fragment() {

    private lateinit var binding: FragmentAllExercisesBinding
    private lateinit var adapter: AllExerciseAdapter
    private val model: MainViewModel by activityViewModels()
    private var ab: ActionBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentAllExercisesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillExerciseList()
        init()
        model.mutableListExercise.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        ab = (activity as AppCompatActivity).supportActionBar
        ab?.title = getString(R.string.allEercises)

    }

    private fun init() = with(binding){
        adapter = AllExerciseAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }


    companion object {
        @JvmStatic
        fun newInstance() = ShowAllExerciseFragment()
    }

    private fun fillExerciseList(){

        val arrayExercises = resources.getStringArray(R.array.exercises)
        val tempList = ArrayList<ExerciseModel>()
        for (i in 0..arrayExercises.size-1) {
            val exerciseList = resources.getStringArray(R.array.exercises)
            val exercise = exerciseList[i]
            val exerciseArray = exercise.split("|")
            tempList.add(ExerciseModel(exerciseArray[0],exerciseArray[1],exerciseArray[2], false, exerciseArray[3]))
        }

        model.mutableListExercise.value = tempList
        model.mutableListExercise.observe(viewLifecycleOwner) {

        }
    }

}