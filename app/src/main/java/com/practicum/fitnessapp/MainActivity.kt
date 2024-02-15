package com.practicum.fitnessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.practicum.fitnessapp.utils.MainViewModel
import androidx.fragment.app.activityViewModels
import com.practicum.fitnessapp.adapters.DayModel
import com.practicum.fitnessapp.adapters.ExerciseModel
import com.practicum.fitnessapp.databinding.ActivityMainBinding
import com.practicum.fitnessapp.fragments.DaysFragment
import com.practicum.fitnessapp.fragments.ExerciseFragment
import com.practicum.fitnessapp.fragments.FinishDayFragment
import com.practicum.fitnessapp.fragments.ShowAllExerciseFragment
import com.practicum.fitnessapp.fragments.ShowOneExerciseFragment
import com.practicum.fitnessapp.utils.FragmentManager

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model.pref = getSharedPreferences("main", MODE_PRIVATE)

        FragmentManager.setFragment(DaysFragment.newInstance(),this)

        binding.bottomNavigationPanel.selectedItemId = R.id.showSportProgramm

        binding.bottomNavigationPanel.setOnNavigationItemSelectedListener{
            when(it.itemId) {

                R.id.showSportProgramm-> {
                    FragmentManager.setFragment(DaysFragment.newInstance(),this)
                }

                R.id.showAllExercises -> {
                    FragmentManager.setFragment(ShowAllExerciseFragment.newInstance(),this)
                }
            }
            true
        }
    }

    override fun onBackPressed() {

        if (FragmentManager.currrentFragment is DaysFragment) super.onBackPressed()
        else {
            FragmentManager.setFragment(DaysFragment.newInstance(),this)
        }

        // Получаем текущий открытый фрагмент
        val currentFragment = supportFragmentManager.findFragmentById(R.id.mainConstraint)
        // Проверяем, является ли текущий фрагмент тем, который вы хотите закрыть
        if ((currentFragment is ExerciseFragment) ||
            (currentFragment is ShowOneExerciseFragment)||
            (currentFragment is FinishDayFragment)) {
            closeFragment(currentFragment)
        }
    }

    private fun closeFragment(fragment: Fragment) {
        // Закрываем фрагмент
        supportFragmentManager.beginTransaction()
            .remove(fragment)
            .commit()

        // Открываем другой фрагмент
        supportFragmentManager.beginTransaction()
            .replace(R.id.placeHolder, DaysFragment.newInstance())
            .commit()
    }


}

