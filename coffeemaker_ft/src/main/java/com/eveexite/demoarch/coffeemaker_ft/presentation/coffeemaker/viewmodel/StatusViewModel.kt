package com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eveexite.demoarch.coffeemaker_ft.domain.listener.DomainSwitchEventListener
import com.eveexite.demoarch.coffeemaker_ft.domain.usecase.TurnOnCoffeeMaker
import com.eveexite.demoarch.coffeemaker_ft.presentation.util.ERROR
import java.lang.ref.WeakReference

class StatusViewModel(
    private val turnOnCoffeeMaker: TurnOnCoffeeMaker
):
    ViewModel(), DomainSwitchEventListener {

    private val msgLive = MutableLiveData<Pair<String, String>>()

    fun getMsgLive(): LiveData<Pair<String, String>> {
        return msgLive
    }

    fun checkCoffeeMakerStatus() {
        turnOnCoffeeMaker.execute(WeakReference(this))
    }

    override fun onCoffeeMakerTurnedOn() {
        //Do nothing
    }

    override fun onError(e: Exception) {
        e.message?.let {
            val pair: Pair<String, String> = Pair(ERROR, it)
            msgLive.postValue(pair)
        }
    }
}