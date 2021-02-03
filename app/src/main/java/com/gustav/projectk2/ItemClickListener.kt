package com.gustav.projectk2

class ItemClickLIstener(val clickListener: (itemId: Long) -> Unit) {
    fun onClick(id: Long) = clickListener(id)
}