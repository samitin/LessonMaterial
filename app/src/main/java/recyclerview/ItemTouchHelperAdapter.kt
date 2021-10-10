package recyclerview

interface ItemTouchHelperAdapter{
    fun onItemMove(fromPosition : Int,toPosiyion : Int)
    fun onItemDismiss(position : Int)
}