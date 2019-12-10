package com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eveexite.demoarch.coffeemaker_ft.domain.listener.DomainGetterEventListener
import com.eveexite.demoarch.coffeemaker_ft.domain.model.CoffeeMaker
import com.eveexite.demoarch.coffeemaker_ft.domain.usecase.GetCoffeeMaker
import com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.mapper.MapToCoffeeMakerUi
import com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.model.AnimUi
import com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.model.CoffeeMakerUi
import com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.model.InfoUi
import com.eveexite.demoarch.coffeemaker_ft.presentation.util.APP_WILL_BE_CLOSED
import com.eveexite.demoarch.coffeemaker_ft.presentation.util.FATAL_ERROR
import java.lang.ref.WeakReference

class CoffeeMakerViewModel(
    private val getCoffeeMaker: GetCoffeeMaker,
    private val mapper: MapToCoffeeMakerUi
):
    ViewModel(),
    DomainGetterEventListener {

    private val infoLive = MutableLiveData<InfoUi>()
    private val statusTextLive = MutableLiveData<String>()
    private val animLive = MutableLiveData<AnimUi>()
    private val msgFinishLive = MutableLiveData<Pair<String, String>>()

    fun getInfoLive(): LiveData<InfoUi> {
        return infoLive
    }

    fun getStatusTextLive(): LiveData<String> {
        return statusTextLive
    }

    fun getAnimLive(): LiveData<AnimUi> {
        return animLive
    }

    fun getMsgFinishLive(): LiveData<Pair<String, String>> {
        return msgFinishLive
    }

    fun loadCoffeeMaker() {
        getCoffeeMaker.execute(WeakReference(this))
    }

    private fun postStatusText(statusText: String) {
        if (statusTextLive.value == null) {
            statusTextLive.postValue(statusText)
        } else if (!statusTextLive.value!!.equals(statusText, true)) {
            statusTextLive.postValue(statusText)
        }
    }

    private fun postAnim(anim: AnimUi) {
        if (animLive.value == null) {
            animLive.postValue(anim)
        } else if (!animLive.value!!.fileUri.equals(anim.fileUri, true)
            || animLive.value!!.canPlayAnim != anim.canPlayAnim) {
            animLive.postValue(anim)
        }
    }

    override fun onCoffeeMakerObtained(coffeeMaker: CoffeeMaker) {
        val coffeeMakerUi: CoffeeMakerUi = mapper.map(coffeeMaker)
        infoLive.postValue(coffeeMakerUi.infoUi)
        postStatusText(coffeeMakerUi.statusUi.text)
        postAnim(coffeeMakerUi.animUi)
    }

    override fun onError(e: Exception) {
        e.message?.let {
            val pair: Pair<String, String> = Pair(FATAL_ERROR, "${it}\n\n${APP_WILL_BE_CLOSED}")
            msgFinishLive.postValue(pair)
        }
    }
}