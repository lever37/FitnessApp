package com.practicum.fitnessapp.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.practicum.fitnessapp.R
import com.practicum.fitnessapp.fragments.DaysFragment
import com.practicum.fitnessapp.fragments.ExerciseFragment
import com.practicum.fitnessapp.fragments.FinishDayFragment
import com.practicum.fitnessapp.fragments.ShowOneExerciseFragment

object FragmentManager {

    var currrentFragment: Fragment? = null

    fun setFragment(newFragment: Fragment,activity: AppCompatActivity){
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.replace(R.id.placeHolder,newFragment)
        transaction.commit()
        currrentFragment = newFragment
    }

    fun setFragmentAsMain(newFragment: Fragment,activity: AppCompatActivity){
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.replace(R.id.mainConstraint,newFragment)
        transaction.commit()
        currrentFragment = newFragment
    }

    fun closeFragment(fragment: Fragment, activity: AppCompatActivity) {

        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)

        // Закрываем фрагмент
        activity.supportFragmentManager.beginTransaction()
            .remove(fragment)
            .commit()

        // Открываем другой фрагмент
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.placeHolder, DaysFragment.newInstance())
            .commit()
    }

    fun checkAndClose (activity: AppCompatActivity) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        val currentFragment = activity.supportFragmentManager.findFragmentById(R.id.mainConstraint)
        if (currentFragment is FinishDayFragment)  FragmentManager.closeFragment(currentFragment,activity as AppCompatActivity)
    }


}