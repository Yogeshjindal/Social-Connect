import Model.Reel
import adapters.reelAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instaclone.Utils.REEL_FOLDER
import com.example.instaclone.databinding.FragmentReelBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class reel : Fragment() {
    private lateinit var binding: FragmentReelBinding
    private lateinit var adapter: reelAdapter
    private var reellist = ArrayList<Reel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReelBinding.inflate(layoutInflater, container, false)
        adapter = reelAdapter(requireContext(), reellist)
        binding.viewpager11.adapter = adapter

        Firebase.firestore.collection(REEL_FOLDER).get().addOnSuccessListener { querySnapshot ->
            val tempList = ArrayList<Reel>()
            for (document in querySnapshot.documents) {
                val reel = document.toObject<Reel>()
                if (reel != null) {
                    tempList.add(reel)
                }
            }
            reellist.clear()
            reellist.addAll(tempList.reversed())

            adapter.notifyDataSetChanged()
        }

        return binding.root
    }
}
