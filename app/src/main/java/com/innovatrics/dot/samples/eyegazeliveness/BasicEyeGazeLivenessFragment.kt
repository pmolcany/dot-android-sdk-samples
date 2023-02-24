package com.innovatrics.dot.samples.eyegazeliveness

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.innovatrics.dot.face.liveness.eyegaze.EyeGazeLivenessFragment
import com.innovatrics.dot.face.liveness.eyegaze.EyeGazeLivenessState
import com.innovatrics.dot.face.liveness.eyegaze.SegmentImage
import com.innovatrics.dot.samples.MainViewModel
import com.innovatrics.dot.samples.R
import com.innovatrics.dot.samples.face.DotFaceViewModel
import com.innovatrics.dot.samples.face.DotFaceViewModelFactory

class BasicEyeGzeLivenessFragment : EyeGazeLivenessFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var dotFaceViewModel: DotFaceViewModel
    private val eyeGazeLivenessViewModel: EyeGazeLivenessViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDotFaceViewModel()
        setupEyeGazeLivenessViewModel()
    }

    override fun onFinished(p0: Float, livenessSegments: List<SegmentImage>) {
        eyeGazeLivenessViewModel.process(livenessSegments)
    }

    override fun onStateChanged(p0: EyeGazeLivenessState?) {

    }

    override fun onNoMoreSegments() {
        // unable to see face clearly, optional retry or terminate, this example shows unlimited retries
        start()
    }

    override fun onEyesNotDetected() {
        // unable to see face clearly, optional retry or terminate, this example shows unlimited retries
        start()
    }

    override fun onFaceTrackingFailed() {
        // unable to see face clearly, optional retry or terminate, this example shows unlimited retries
        start()
    }

    private fun setupDotFaceViewModel() {
        val dotFaceViewModelFactory = DotFaceViewModelFactory(requireActivity().application)
        dotFaceViewModel = ViewModelProvider(this, dotFaceViewModelFactory)[DotFaceViewModel::class.java]
        dotFaceViewModel.state.observe(viewLifecycleOwner) { state ->
            if (state.isInitialized) {
                start()
            }
            state.errorMessage?.let {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                dotFaceViewModel.notifyErrorMessageShown()
            }
        }
        dotFaceViewModel.initializeDotFaceIfNeeded()
    }

    private fun setupEyeGazeLivenessViewModel() {
        eyeGazeLivenessViewModel.initializeState()
        eyeGazeLivenessViewModel.state.observe(viewLifecycleOwner) { state ->
            state.result?.let {
                findNavController().navigate(R.id.action_BasicEyeGazeLivenessFragment_to_EyeGazeLivenessResultFragment)
            }
        }
    }

    override fun onNoCameraPermission() {
        mainViewModel.notifyNoCameraPermission()
    }

    override fun onStopped() {
    }
}
