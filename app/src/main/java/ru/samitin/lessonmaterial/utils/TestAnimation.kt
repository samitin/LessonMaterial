package ru.samitin.lessonmaterial.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.samitin.lessonmaterial.R
import ru.samitin.lessonmaterial.databinding.AnimTestStartBinding


class TestAnimation : AppCompatActivity() {

    private var show = false

    var _binding: AnimTestStartBinding?=null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= AnimTestStartBinding.inflate(layoutInflater)
        setContentView(binding.root)


       binding.fab.setOnClickListener {
            if(show)
                showComponents(R.layout.fragment_main_start,binding.constraintConteiner,this)
            else
                showComponents(R.layout.fragment_main_end,binding.constraintConteiner,this)
            show= !show
        }
    }

}