package com.example.kissapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kissapp.MainActivity
import com.example.kissapp.R
import com.example.kissapp.databinding.CharacterListViewFragmentBinding
import com.example.kissapp.model.CharacterModel
import com.example.kissapp.viewModel.CharacterListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterListView : Fragment(), RecyclerAdapter.OnItemClickListener {


    private val characterListViewModel: CharacterListViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var progressBar: ProgressBar

    var dataSet: MutableList<CharacterModel> = ArrayList()
    var isLoading: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<CharacterListViewFragmentBinding>(
            inflater,
            R.layout.character_list_view_fragment, container, false
        )

        progressBar = binding.root.findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        recyclerView = binding.characterRecyclerView
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = RecyclerAdapter(dataSet, this)
        recyclerView.addOnScrollListener(object : RecycleViewListener(layoutManager) {


            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun load() {
                characterListViewModel.loadNextCharacters()
            }

        })




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindUIData()
        getDataFromApi()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun bindUIData() {
        characterListViewModel.characters.observe(viewLifecycleOwner, Observer { characterList ->
            progressBar.visibility = View.INVISIBLE
            dataSet.clear()
            dataSet.addAll(characterList.characterModels)
            recyclerView.adapter!!.notifyDataSetChanged()
        })

        characterListViewModel.isLoading.observe(viewLifecycleOwner, Observer { t ->
            isLoading = t
        })

        characterListViewModel.toast.observe(viewLifecycleOwner, Observer { t ->
            MainActivity.showToast(context!!, t)
        })

    }

    private fun getDataFromApi() {
        characterListViewModel.getCharacterList()
        characterListViewModel.getApiInfo()

    }

    override fun onItemClicked(position: Int) {

        setFragmentResult("requestKey", bundleOf("bundleKey" to position + 1))

        val characterView = CharacterView()
        val bundle = Bundle()
        bundle.putInt("position", position)
        characterView.arguments = bundle

        (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, characterView, CharacterView()::class.java.toString())
            .addToBackStack(null).commit()
    }


}