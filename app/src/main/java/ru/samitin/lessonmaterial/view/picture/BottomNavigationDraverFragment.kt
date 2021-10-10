package ru.samitin.lessonmaterial.view.picture

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dz6.NotesActivity
import recyclerview.RecyclerActivity
import ru.samitin.lessonmaterial.R
import ru.samitin.lessonmaterial.databinding.BottomNavigationLayoutBinding
import ru.samitin.lessonmaterial.ui.api.AnimationsActivity
import ru.samitin.lessonmaterial.ui.api.ApiBottomActivity

class BottomNavigationDraverFragment : BottomSheetDialogFragment() {


    private var _binding: BottomNavigationLayoutBinding? = null
    val binding: BottomNavigationLayoutBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BottomNavigationLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setNavigationItemSelectedListener { it->
            when(it.itemId){
                R.id.navigation_one ->{
                    activity?.let {
                        startActivity(Intent(it, AnimationsActivity::class.java))
                    }
                }
                R.id.navigation_two ->{
                    activity?.let {
                        startActivity(Intent(it, ApiBottomActivity::class.java))
                    }
                }
                R.id.navigation_three ->{
                    activity?.let {
                        startActivity(Intent(it,RecyclerActivity::class.java))
                    }
                }
                R.id.navigation_four ->{
                    activity?.let {
                        startActivity(Intent(it, NotesActivity::class.java))
                    }
                }
            }
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
    companion object {
        fun newInstance() = BottomNavigationDraverFragment()
    }
}