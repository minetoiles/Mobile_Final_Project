package dduw.com.mobile.finalreport

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dduw.com.mobile.finalreport.data.MovieDto
import dduw.com.mobile.finalreport.databinding.ListItemBinding

class MovieAdapter (var movieList: ArrayList<MovieDto>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.itemBinding.ivPhoto.setImageResource(movieList[position].photo)
        holder.itemBinding.tvTitle.text = movieList[position].title
        holder.itemBinding.tvDirector.text = movieList[position].director
    }
    override fun getItemCount(): Int {
        return movieList.size
    }
    inner class MovieViewHolder(val itemBinding: ListItemBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemBinding.root.setOnClickListener {
                click?.onItemClick(it, adapterPosition)
            }
            itemBinding.root.setOnLongClickListener {
                longClick?.onItemLongClick(it, adapterPosition) ?: true
            }
        }
    }


    var click : OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.click = listener
    }

    var longClick: OnItemLongClickListener? = null

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int): Boolean
    }
    fun setOnItemLongClickListener(listener: OnItemLongClickListener?) {
        this.longClick = listener
    }
}