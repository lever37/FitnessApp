package com.practicum.fitnessapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.fitnessapp.R
import com.practicum.fitnessapp.adapters.DayModel
import com.practicum.fitnessapp.adapters.DaysAdapter
import com.practicum.fitnessapp.adapters.ExerciseAdapter
import com.practicum.fitnessapp.adapters.ExerciseModel
import com.practicum.fitnessapp.databinding.FragmentDaysBinding
import com.practicum.fitnessapp.databinding.FragmentExerciseBinding
import com.practicum.fitnessapp.utils.MainViewModel

class ExerciseFragment : Fragment() {

    private lateinit var binding: FragmentExerciseBinding
    private lateinit var adapter: ExerciseAdapter
    private val model: MainViewModel by activityViewModels()
    private var ab: ActionBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentExerciseBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        model.mutableListExercise.observe(viewLifecycleOwner){
            for(i in 0 until model.getExerciseCount()){
                it[i]=it[i].copy(isDone = true)
            }
            adapter.submitList(it)
        }

        ab = (activity as AppCompatActivity).supportActionBar
        ab?.title = getString(R.string.traningProgram)
    }

    private fun init() = with(binding){
        adapter = ExerciseAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        buttonStart.setOnClickListener {
            com.practicum.fitnessapp.utils.FragmentManager.
            setFragmentAsMain(ShowOneExerciseFragment.newInstance(),activity as AppCompatActivity)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExerciseFragment()
    }


}