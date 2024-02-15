package com.practicum.fitnessapp.utils

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.fitnessapp.adapters.ExerciseModel

class MainViewModel: ViewModel() {
    val mutableListExercise = MutableLiveData<ArrayList<ExerciseModel>>()
    var pref: SharedPreferences? = null
    var currentDayNumber = 0

    fun savePref(key: String,exCount: Int){
        pref?.edit()?.putInt(key,exCount)?.apply()
    }

    fun getExerciseCount(): Int{
        return pref?.getInt(currentDayNumber.toString(),0) ?: 0
    }
}