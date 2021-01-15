package com.evanamargain.draganddropsample

class Item(val name: String) {
    companion object {
        private var itemId = 0
        fun createItemList(numItems: Int): ArrayList<Item> {
            val items = ArrayList<Item>()
            for (i in 1..numItems) {
                items.add(Item("Item ${++itemId}"))
            }
            return items
        }
    }
}
