package ru.samitin.lessonmaterial.ui.api

import android.os.Bundle
import androidx.transition.ChangeBounds
import android.view.animation.AnticipateOvershootInterpolator

import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import ru.samitin.lessonmaterial.R

import ru.samitin.lessonmaterial.databinding.ActivityAnimationsBonusStartBinding

class AnimationsActivity : AppCompatActivity() {

    private var show = false

    var _binding:ActivityAnimationsBonusStartBinding ?=null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityAnimationsBonusStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backgroundImage.setOnClickListener {
            if(show)
                hideComponents()
            else
                showComponents()
        }
    }

    private fun showComponents() {
        show = true

        val constraintSet = ConstraintSet()
        constraintSet.clone(this, R.layout.activity_animations_bonus_end)

        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)//позволяет добиться анимации отскока
        transition.duration = 1200

        TransitionManager.beginDelayedTransition(binding.constraintContainer, transition)
        constraintSet.applyTo(binding.constraintContainer)
    }

    private fun hideComponents() {
        show=false

        val constraintSet=ConstraintSet()
        constraintSet.clone(this, R.layout.activity_animations_bonus_start)
        val transaction=ChangeBounds()
        transaction.interpolator=AnticipateOvershootInterpolator(1.0f)//позволяет добиться анимации отскока
        transaction.duration=1200
        TransitionManager.beginDelayedTransition(binding.constraintContainer, transaction)
        constraintSet.applyTo(binding.constraintContainer)
    }
}