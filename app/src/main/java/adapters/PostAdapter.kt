package adapters

import Model.Post
import Model.User
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instaclone.R
import com.example.instaclone.Utils.USER_NODE
import com.example.instaclone.databinding.PostRvBinding
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class PostAdapter(var context:Context, var postList: ArrayList<Post>):RecyclerView.Adapter<PostAdapter.Myholder>() {

    inner class Myholder(var binding: PostRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val binding = PostRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Myholder(binding)
    }


    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: Myholder, position: Int) {
        Firebase.firestore.collection(USER_NODE).document(postList[position].uid)
            .get().addOnSuccessListener {
            try {
                val user=it.toObject<User>()
                Glide.with(context).load(user!!.image).placeholder(R.drawable.user).into(holder.binding.imageView8)
                holder.binding.name99.text=user.name
            }
            catch(e :Exception) {

            }

            }
        Glide.with(context).load(postList.get(position).postUrl).placeholder(R.drawable.loading).into(holder.binding.imageView11)
        try {
            val text = TimeAgo.using(postList.get(position).time.toLong())
            holder.binding.time99.text=text
        }
        catch (e:Exception){
            holder.binding.time99.text=""
        }
        holder.binding.share.setOnClickListener{
            var i=Intent(Intent.ACTION_SEND)
            i.type="text/plain"
            i.putExtra(Intent.EXTRA_TEXT,postList.get(position).postUrl)
            context.startActivity(i)
        }
        holder.binding.caption.text=postList.get(position).caption
        var isliked=false
        holder.binding.imageView13.setOnClickListener {
            if (isliked) {
                holder.binding.imageView11.setImageResource(R.drawable.heart)
            } else {
                holder.binding.imageView13.setImageResource(R.drawable.liked)
            }
            isliked=!isliked
        }
    }


}