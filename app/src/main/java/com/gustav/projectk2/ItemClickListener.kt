package com.gustav.projectk2

import android.view.View

class ItemClickListener(val clickListener: (itemId: Long) -> Unit) {
    fun onClick(id: Long) = clickListener(id)
}

class EditNoteClickListener(val clickListener: (itemId: Long, view: View) -> Unit){
    fun onClicked(id:Long, view:View) = clickListener(id, view)
}