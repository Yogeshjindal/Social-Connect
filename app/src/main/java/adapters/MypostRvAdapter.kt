package adapters

import Model.Post
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instaclone.databinding.MyPostRvDesignBinding

class MypostRvAdapter(var context: Context, var PostList: ArrayList<Post>) :
    RecyclerView.Adapter<MypostRvAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: MyPostRvDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MyPostRvDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return PostList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(PostList[position].postUrl)
            .into(holder.binding.postImage)
    }
}
