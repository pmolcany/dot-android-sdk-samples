package com.innovatrics.dot.samples.eyegazeliveness

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.innovatrics.dot.face.image.BitmapFactory
import com.innovatrics.dot.face.liveness.eyegaze.SegmentImage

class EyeGazeLivenessViewModel : ViewModel() {

    private val mutableState: MutableLiveData<EyeGazeLivenessState> = MutableLiveData()
    val state: LiveData<EyeGazeLivenessState> = mutableState

    fun initializeState() {
        mutableState.value = EyeGazeLivenessState()
    }

    fun process(eyeGazeLivenessResult: List<SegmentImage>) {
        // At this point use the Digital Identity Service in order to evaluate the eye gaze liveness score.
        // See: https://developers.innovatrics.com/digital-onboarding/technical/remote/dot-dis/latest/documentation/#_eye_gaze_liveness_check
        val uiResult = createUiResult(eyeGazeLivenessResult)
        mutableState.value = state.value!!.copy(result = uiResult)
    }

    private fun createUiResult(eyeGazeLivenessResult: List<SegmentImage>): EyeGazeLivenessResult {
        return EyeGazeLivenessResult(
            eyeGazeLivenessResult.map { BitmapFactory.create(it.image) }
        )
    }
}
