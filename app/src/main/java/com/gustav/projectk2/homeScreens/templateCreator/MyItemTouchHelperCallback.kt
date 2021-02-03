package com.gustav.projectk2.homeScreens.templateCreator

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class MyItemTouchHelperCallback(val moveSwipeListeners: List<MoveSwipeListener>, dragDirs: Int, swipeDirs: Int) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs)
{

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        moveSwipeListeners.forEach{it.swapItems(viewHolder.adapterPosition, target.adapterPosition)}
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        moveSwipeListeners.forEach{it.onSwiped(viewHolder,direction)}
    }


    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        moveSwipeListeners.forEach{it.onClearView()}
    }

}

interface MoveSwipeListener{
    fun swapItems( adapterPosition : Int, targetPosition: Int)
    fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
    fun onClearView()
}