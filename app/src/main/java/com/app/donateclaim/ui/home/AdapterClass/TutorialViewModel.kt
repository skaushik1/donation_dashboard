package com.app.donateclaim.Ui.home.AdapterClass

import androidx.lifecycle.ViewModel
import com.app.donateclaim.helper.AppConstant


/**
 * This is Tutorial fragment screen view model
 */
class TutorialViewModel : ViewModel() {

    val TAG = "TutorialViewModel"

    /**
     * This method is used to get tutorial list
     */
    fun getTutorialList(): ArrayList<Int> {
        return ArrayList<Int>().apply {
            add(AppConstant.TutorialScreen.Welcome)
            add(AppConstant.TutorialScreen.CertifiedCars)
            add(AppConstant.TutorialScreen.EasyWayToFindRide)
        }
    }


}