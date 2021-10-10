package dz6

import android.view.View
import androidx.recyclerview.widget.RecyclerView


abstract class BaseViewHolder(view : View): RecyclerView.ViewHolder(view) {
    abstract fun bind(data : Pair<NoteData,Boolean>)

}