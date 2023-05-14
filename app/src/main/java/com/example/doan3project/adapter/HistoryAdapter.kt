import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.doan3project.Model.ProductModel
import com.example.doan3project.R

class HistoryAdapter(private val ds :List<String>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private lateinit var bListener: OnClickListener

    interface OnClickListener{
        fun onClickBlog(position: Int)
    }

    fun setOnItemClickListener(clickListener: OnClickListener){
        bListener = clickListener
    }
    class ViewHolder(itemView: View, clickListener: OnClickListener): RecyclerView.ViewHolder(itemView){
        var imageView: TextView? = null
        init {
            imageView = itemView.findViewById(R.id.search_his);
            itemView.setOnClickListener {
                clickListener.onClickBlog(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return ViewHolder(view, bListener)
    }

    override fun getItemCount(): Int {
        return ds.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

        }
    }


}