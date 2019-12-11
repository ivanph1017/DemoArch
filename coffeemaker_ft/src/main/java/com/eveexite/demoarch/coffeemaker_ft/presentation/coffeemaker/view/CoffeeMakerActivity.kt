package com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.view

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.eveexite.demoarch.coffeemaker_ft.DaggerFeatureComponent
import com.eveexite.demoarch.coffeemaker_ft.R
import com.eveexite.demoarch.coffeemaker_ft.databinding.ActivityCoffeeMakerBinding
import com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.model.AnimUi
import com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.model.InfoUi
import com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.viewmodel.CoffeeMakerViewModel
import com.eveexite.demoarch.coffeemaker_ft.presentation.ViewModelFactory
import com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.viewmodel.ActionViewModel
import com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.viewmodel.StatusViewModel
import com.eveexite.demoarch.core.CoreComponent
import com.eveexite.demoarch.core.CoreComponentProvider
import com.eveexite.demoarch.core.presentation.CoreBaseActivity
import kotlinx.android.synthetic.main.activity_coffee_maker.vMain
import kotlinx.android.synthetic.main.activity_coffee_maker.vTitle
import kotlinx.android.synthetic.main.activity_coffee_maker.vWaterLevel
import kotlinx.android.synthetic.main.activity_coffee_maker.tvTimer
import kotlinx.android.synthetic.main.activity_coffee_maker.fabPower

@LayoutRes private val LAYOUT: Int = R.layout.activity_coffee_maker

class CoffeeMakerActivity : CoreBaseActivity<ViewModelFactory, ActivityCoffeeMakerBinding>() {

    private lateinit var coffeeMakerViewModel: CoffeeMakerViewModel
    private lateinit var actionViewModel: ActionViewModel
    private lateinit var statusViewModel: StatusViewModel
    private lateinit var msgLive: MediatorLiveData<Pair<String, String>>

    override fun getLayout(): Int = LAYOUT

    override fun initDependencyInjection() {
        val coreComponentProvider = application as CoreComponentProvider
        val coreComponent: CoreComponent = coreComponentProvider.provideCoreComponent()
        DaggerFeatureComponent.factory()
            .create(coreComponent)
            .inject(this)
        runOnUiThread { initView() }
    }

    override fun initView() {
        msgLive = MediatorLiveData()
        setupViewModels()
        coffeeMakerViewModel.loadCoffeeMaker()
        vMain.setAnimation("coffee_maker.json")
        setupLayoutObservers()
        fabPower.setOnClickListener { actionViewModel.checkAction() }
        setupMsgObservers()
    }

    private fun setupViewModels() {
        coffeeMakerViewModel = ViewModelProviders.of(this, viewModelFactory).get(CoffeeMakerViewModel::class.java)
        actionViewModel = ViewModelProviders.of(this, viewModelFactory).get(ActionViewModel::class.java)
        statusViewModel = ViewModelProviders.of(this, viewModelFactory).get(StatusViewModel::class.java)
    }

    private fun setupLayoutObservers() {
        coffeeMakerViewModel.getInfoLive().observe(this,  infoChanges)
        coffeeMakerViewModel.getStatusTextLive().observe(this, statusTextChanges)
        coffeeMakerViewModel.getAnimLive().observe(this, animChanges)
    }

    private fun setupMsgObservers() {
        msgLive.addSource(actionViewModel.getMsgLive(), sourceChanges)
        msgLive.addSource(statusViewModel.getMsgLive(), sourceChanges)
        msgLive.observe(this, msgChanges)

        actionViewModel.getMsgConfirmLive().observe(this, msgConfirmChanges)
        coffeeMakerViewModel.getMsgFinishLive().observe(this, msgFinishChanges)
    }

    private val infoChanges = Observer<InfoUi> {
        it?.let {
            vWaterLevel.waterLevel = it.waterLevel
            tvTimer.text = it.timer
        }
    }

    private val statusTextChanges = Observer<String> {
        it?.let {
            vTitle.text = it
            vTitle.invalidate()
        }
    }

    private val animChanges = Observer<AnimUi> {
        it?.let {
            vMain.clearAnimation()
            vMain.setAnimation(it.fileUri)
            vMain.progress = 0.0F
            if (it.canPlayAnim) vMain.playAnimation()
        }
    }

    private val sourceChanges = Observer<Pair<String, String>> {
        it?.let {
            msgLive.value = it
        }
    }

    private val msgChanges = Observer<Pair<String, String>> {
        it?.let {
            showMessage(it.first, it.second) {}
        }
    }

    private val msgConfirmChanges = Observer<Pair<String, String>> {
        it?.let {
            showMsgConfirm(it.first, it.second)
        }
    }

    private val msgFinishChanges = Observer<Pair<String, String>> {
        it?.let {
            showMessage(it.first, it.second) { finishAffinity() }
        }
    }

    private fun <Unit> showMessage(title: String, message: String, callback: () -> Unit) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val view: View = layoutInflater.inflate(R.layout.dialog_alert, null)
        view.findViewById<TextView>(R.id.tvWindowTitle).text = title
        view.findViewById<TextView>(R.id.tvMessage).text = message
        val alertDialog: AlertDialog = builder.setView(view).create()
        view.findViewById<Button>(R.id.btnDismiss).setOnClickListener {
            alertDialog.dismiss()
            run(callback)
        }
        alertDialog.show()
    }

    private fun showMsgConfirm(title: String, message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val view: View = layoutInflater.inflate(R.layout.dialog_alert_confirmation, null)
        view.findViewById<TextView>(R.id.tvWindowTitle).text = title
        view.findViewById<TextView>(R.id.tvMessage).text = message
        val alertDialog: AlertDialog = builder.setView(view).create()
        view.findViewById<Button>(R.id.btnAccept).setOnClickListener {
            alertDialog.dismiss()
            statusViewModel.checkCoffeeMakerStatus()
        }
        view.findViewById<Button>(R.id.btnCancel).setOnClickListener { alertDialog.cancel() }
        alertDialog.show()
    }

}