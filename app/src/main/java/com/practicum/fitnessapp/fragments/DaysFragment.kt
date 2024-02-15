package com.practicum.fitnessapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.fitnessapp.R
import com.practicum.fitnessapp.adapters.DayModel
import com.practicum.fitnessapp.adapters.DaysAdapter
import com.practicum.fitnessapp.adapters.ExerciseModel
import com.practicum.fitnessapp.databinding.FragmentDaysBinding
import com.practicum.fitnessapp.utils.DialogManager
import com.practicum.fitnessapp.utils.FragmentManager
import com.practicum.fitnessapp.utils.MainViewModel

@Suppress("DEPRECATION")
class DaysFragment : Fragment(), DaysAdapter.Listener {

    private lateinit var binding: FragmentDaysBinding
    private val model: MainViewModel by activityViewModels()
    private var ab: ActionBar? = null
    private lateinit var adapter: DaysAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentDaysBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.currentDayNumber = 0

        initRecyclerView()

        ab = (activity as AppCompatActivity).supportActionBar
        ab?.title = getString(R.string.traningDays)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.top_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.reset){
            DialogManager.showDialog(activity as AppCompatActivity,
            R.string.erase_уverything,
            object : DialogManager.Listener{
                override fun onClick() {
                    model.pref?.edit()?.clear()?.apply()
                    adapter.submitList(fillDaysArray())
                }
            }
            )
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerView() = with(binding){
        adapter = DaysAdapter(this@DaysFragment)
        rcViewDays.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        rcViewDays.adapter = adapter
        adapter.submitList(fillDaysArray())
    }

    private  fun fillDaysArray():ArrayList<DayModel>{
        val dayArray = ArrayList<DayModel>()
        var doneDaysCounter = 0
        resources.getStringArray(R.array.day_exercises).forEach {
            model.currentDayNumber++
            val exCounter = it.split(",").size
            dayArray.add(DayModel(it,model.getExerciseCount()==exCounter,0))
        }
        binding.progressBar.max = dayArray.size
        dayArray.forEach{
            if (it.isDone) doneDaysCounter++
        }
        updateRestDays(dayArray.size-doneDaysCounter,dayArray.size)

        return dayArray
    }

    private fun updateRestDays(restDays:Int, daysNumber:Int) = with(binding){
        val rDays = getString(R.string.remained) + " $restDays " + getString(R.string.days)
        progressText.text = rDays
        progressBar.progress = daysNumber - restDays
    }

    private fun fillExerciseList(day: DayModel){

        val tempList = ArrayList<ExerciseModel>()
        day.exercises.split(",").forEach {
            val exerciseList = resources.getStringArray(R.array.exercises)
            val exercise = exerciseList[it.toInt()]
            val exerciseArray = exercise.split("|")
            tempList.add(ExerciseModel(exerciseArray[0],exerciseArray[1],exerciseArray[2], false, exerciseArray[3]))
        }

        model.mutableListExercise.value = tempList
        model.mutableListExercise.observe(viewLifecycleOwner) {

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment()
    }

    override fun onClick(day: DayModel) {
        if (!day.isDone){
            fillExerciseList(day)
            model.currentDayNumber = day.dayNumber
            FragmentManager.setFragmentAsMain(ExerciseFragment.newInstance(), activity as AppCompatActivity)
        }else{
            DialogManager.showDialog(activity as AppCompatActivity,
                R.string.erase_day,
                object : DialogManager.Listener{
                    override fun onClick() {
                        model.savePref(day.dayNumber.toString(), 0)
                        fillExerciseList(day)
                        model.currentDayNumber = day.dayNumber
                        FragmentManager.setFragmentAsMain(ExerciseFragment.newInstance(), activity as AppCompatActivity)
                    }
                }
            )
        }
    }


}