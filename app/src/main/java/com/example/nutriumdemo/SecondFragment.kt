package com.example.nutriumdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.nutriumdemo.data.repository.ProfessionalsRepository
import com.example.nutriumdemo.databinding.FragmentSecondBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment() : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var imageDetails: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageDetails = view.findViewById(R.id.imageDetailsProfessional)


        val args: SecondFragmentArgs by navArgs()
        val userId = args.userId.also {
            getProfessionalById(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getProfessionalById(userId:String) {
        GlobalScope.launch {
           var professinals =
                ProfessionalsRepository.getInstance().getProfessionalDetails(userId)
            withContext(Dispatchers.Main) {

            }
            withContext(Dispatchers.Main) {
                imageDetails.load(professinals?.profile_picture_url) {
                    crossfade(true)
                    placeholder(R.drawable.user)
                    error(R.drawable.user)
                }
            }
        }
    }

}