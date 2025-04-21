package com.example.nutriumdemo

import android.app.ProgressDialog
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
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
    private lateinit var nameDetails:TextView
    private lateinit var rating:RatingBar
    private lateinit var about:TextView
    private lateinit var progressDialog:ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = ProgressDialog(context)
        imageDetails = view.findViewById(R.id.imageDetailsProfessional)
        nameDetails = view.findViewById(R.id.nameDetailsProfessional)
        rating = view.findViewById(R.id.ratingBar)
        about = view.findViewById(R.id.aboutProfessional)
        val args: SecondFragmentArgs by navArgs()
        progressDialog.setMessage(getString(R.string.downloading))
        progressDialog.setCancelable(false)
        progressDialog.show()
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
            progressDialog.dismiss()

            withContext(Dispatchers.Main) {
                imageDetails.visibility = VISIBLE
                imageDetails.load(professinals?.profile_picture_url) {
                    crossfade(true)
                    placeholder(R.drawable.user)
                    error(R.drawable.user)
                }
                nameDetails.setText(professinals?.name)
                nameDetails.visibility = VISIBLE
                rating.rating = professinals?.rating!!.toFloat()
                rating.visibility = VISIBLE
                about.text = professinals?.about_me
                about.visibility = VISIBLE
                makeTextViewResizable(about, maxLines = 3)
            }
        }
    }


    fun makeTextViewResizable(
        textView: TextView,
        maxLines: Int,
        expandText: String = "show more",
        collapseText: String = "show less",
        isExpanded: Boolean = false
    ) {
        if (textView.tag == null) {
            textView.tag = textView.text
        }

        textView.post {
            val fullText = textView.tag.toString()
            //TODO check acording to size of text
            if (fullText.count() > 40) {
                if (!isExpanded) {
                    val truncated = fullText.split(" ").take(30).joinToString(" ") + "... "
                    textView.text = SpannableStringBuilder(truncated).append(
                        SpannableString(expandText).apply {
                            setSpan(object : ClickableSpan() {
                                override fun onClick(widget: View) {
                                    makeTextViewResizable(
                                        textView,
                                        maxLines,
                                        expandText,
                                        collapseText,
                                        true
                                    )
                                }

                                override fun updateDrawState(ds: TextPaint) {
                                    super.updateDrawState(ds)
                                    ds.isUnderlineText = true
                                    ds.color =
                                        ContextCompat.getColor(textView.context, R.color.blue)

                                }
                            }, 0, expandText.length, 0)
                        }
                    )
                    textView.movementMethod = LinkMovementMethod.getInstance()
                } else {
                    textView.text = SpannableStringBuilder(fullText).append(
                        SpannableString(" $collapseText").apply {
                            setSpan(object : ClickableSpan() {
                                override fun onClick(widget: View) {
                                    makeTextViewResizable(
                                        textView,
                                        maxLines,
                                        expandText,
                                        collapseText,
                                        false
                                    )
                                }

                                override fun updateDrawState(ds: TextPaint) {
                                    super.updateDrawState(ds)
                                    ds.isUnderlineText = true
                                    ds.color =
                                        ContextCompat.getColor(textView.context, R.color.blue)
                                }
                            }, 0, collapseText.length + 1, 0)
                        }
                    )
                    textView.movementMethod = LinkMovementMethod.getInstance()
                }
            }else{
                textView.text = fullText
            }
        }
    }


}