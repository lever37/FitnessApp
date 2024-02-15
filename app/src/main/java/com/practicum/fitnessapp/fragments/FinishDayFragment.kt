package com.practicum.fitnessapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.practicum.fitnessapp.R
import com.practicum.fitnessapp.databinding.FragmentFinishDayBinding
import com.practicum.fitnessapp.utils.FragmentManager
import pl.droidsonroids.gif.GifDrawable

class FinishDayFragment : Fragment() {

    private lateinit var binding: FragmentFinishDayBinding
    private var ab: ActionBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentFinishDayBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ab = (activity as AppCompatActivity).supportActionBar
        ab?.title = getString(R.string.finishActionBar)

        binding.imageFinishDay.setImageDrawable(
            GifDrawable(
                (activity as AppCompatActivity).assets,
                "cup.gif"
            )
        )

        binding.buttonFinish.setOnClickListener {
            FragmentManager.checkAndClose(activity as AppCompatActivity)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FinishDayFragment()
    }

}