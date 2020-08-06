package com.example.kissapp.view


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class RecycleViewListener(private val linearLayoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    private val PAGE_SIZE = 20


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visible = linearLayoutManager.childCount
        val total = linearLayoutManager.itemCount
        val firstVisible = linearLayoutManager.findFirstVisibleItemPosition()

        if(!isLoading()){
            if((visible + firstVisible >=total)&&(total>=PAGE_SIZE)&&firstVisible>=0) load()
        }
    }

    abstract fun load()

    abstract fun isLoading():Boolean


}