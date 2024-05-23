package adapters

import Model.Reel
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instaclone.R
import com.example.instaclone.databinding.ReelDgBinding
import com.squareup.picasso.Picasso

class reelAdapter(var context: Context,var reelList: ArrayList<Reel>)
    :RecyclerView.Adapter<reelAdapter.Viewholder>()


{
    inner class Viewholder(var binding:ReelDgBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
    var binding=ReelDgBinding.inflate(LayoutInflater.from(context),parent,false)
    return Viewholder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        Picasso.get().load(reelList.get(position).profileLink).placeholder(R.drawable.user).into(holder.binding.profile11)
        holder.binding.caption11.setText((reelList.get(position).caption))
        holder.binding.videoView.setVideoPath(reelList.get(position).reelUrl)
        holder.binding.videoView.setOnPreparedListener{
            holder.binding.progressBar2.visibility= View.GONE
            holder.binding.videoView.start()
        }
    }

}
