package com.example.kissapp.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.kissapp.MainActivity
import com.example.kissapp.R
import com.example.kissapp.databinding.CharacterViewFragmentBinding
import com.example.kissapp.viewModel.CharacterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf



class CharacterView : Fragment() {

    private lateinit var binding: CharacterViewFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.character_view_fragment, container, false
        )
        val position = arguments!!.getInt("position")

        val characterDetailsViewModel: CharacterViewModel by viewModel {
            parametersOf(position + 1)
        }
        binding.characterDetailsViewModel = characterDetailsViewModel
        binding.lifecycleOwner = activity
        this.bindUiData(characterDetailsViewModel)
        return binding.root
    }

    private fun bindUiData(characterDetailsViewModel: CharacterViewModel) {
        characterDetailsViewModel.toast.observe(viewLifecycleOwner, Observer { t ->
            MainActivity.showToast(context!!,t)
        })
    }



}
