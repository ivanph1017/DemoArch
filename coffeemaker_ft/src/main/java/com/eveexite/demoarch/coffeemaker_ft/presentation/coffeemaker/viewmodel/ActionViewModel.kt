package com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eveexite.demoarch.coffeemaker_ft.domain.listener.DomainCheckerEventListener
import com.eveexite.demoarch.coffeemaker_ft.domain.usecase.CheckCoffeeMakerSwitch
import com.eveexite.demoarch.coffeemaker_ft.presentation.util.WARNING
import java.lang.ref.WeakReference

class ActionViewModel(
    private val checkCoffeeMakerSwitch: CheckCoffeeMakerSwitch
):
    ViewModel(), DomainCheckerEventListener {

    private val msgLive = MutableLiveData<Pair<String, String>>()
    private val msgConfirmLive = MutableLiveData<Pair<String, String>>()

    fun getMsgLive(): LiveData<Pair<String, String>> {
        return msgLive
    }

    fun getMsgConfirmLive(): LiveData<Pair<String, String>> {
        return msgConfirmLive
    }

    fun checkAction() {
        checkCoffeeMakerSwitch.execute(WeakReference(this))
    }

    override fun onActionMessageReceived(actionMessage: String) {
        val pair: Pair<String, String> = Pair(WARNING, actionMessage)
        msgConfirmLive.postValue(pair)
    }

    override fun onError(e: Exception) {
        e.message?.let {
            val pair: Pair<String, String> = Pair(WARNING, it)
            msgLive.postValue(pair)
        }
    }
}