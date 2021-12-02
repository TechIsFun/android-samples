package com.github.techisfun.lottiesample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView

private const val ARG_LOTTIE_ANIMATION_ID = "lottieAnimationId"
private const val ARG_MESSAGE = "message"

class LottieFragment : Fragment() {
    private var lottieAnimationId: Int = 0
    private var message: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            lottieAnimationId = it.getInt(ARG_LOTTIE_ANIMATION_ID)
            message = it.getString(ARG_MESSAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lottie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lottieAnimationView = view.findViewById<LottieAnimationView>(R.id.animation)
        lottieAnimationView.setAnimation(lottieAnimationId)

        val messageTextView = view.findViewById<TextView>(R.id.message)
        messageTextView.text = message
    }

    companion object {
        @JvmStatic
        fun newInstance(lottieAnimationId: Int, message: String) =
            LottieFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_LOTTIE_ANIMATION_ID, lottieAnimationId)
                    putString(ARG_MESSAGE, message)
                }
            }
    }
}