package com.innovatrics.dot.samples.eyegazeliveness

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.innovatrics.dot.samples.R

class EyeGazeLivenessResultFragment : Fragment(R.layout.fragment_eye_gaze_liveness_result) {

    private val smileLivenessViewModel: EyeGazeLivenessViewModel by activityViewModels()
    private lateinit var images_content: LinearLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews(view)
        setupFaceAutoCaptureViewModel()
    }

    private fun setViews(view: View) {
        images_content = view.findViewById(R.id.images_content)
    }

    private fun setupFaceAutoCaptureViewModel() {
        smileLivenessViewModel.state.observe(viewLifecycleOwner) { showResult(it.result!!) }
    }

    private fun showResult(result: EyeGazeLivenessResult) {
        result.faces.forEach {
            val imageView = ImageView(context).apply {
                setImageBitmap(it)
            }
            images_content.addView(imageView)
        }
    }
}
