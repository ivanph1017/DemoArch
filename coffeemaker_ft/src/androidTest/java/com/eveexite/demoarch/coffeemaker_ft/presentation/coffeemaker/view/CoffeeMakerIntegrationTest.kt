package com.eveexite.demoarch.coffeemaker_ft.presentation.coffeemaker.view

import android.content.Context
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.airbnb.lottie.LottieAnimationView
import com.eveexite.demoarch.coffeemaker_ft.CoffeeMakerAppTest
import com.eveexite.demoarch.coffeemaker_ft.R
import com.eveexite.demoarch.coffeemaker_ft.data.rest.TestDispatcher
import com.eveexite.demoarch.coffeemaker_ft.presentation.widget.TitleView
import com.eveexite.demoarch.coffeemaker_ft.presentation.widget.WaterLevelView
import com.eveexite.demoarch.core.device.JsonUtil
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CoffeeMakerIntegrationTest {

    @Rule
    @JvmField
    var activityScenarioRule: ActivityScenarioRule<CoffeeMakerActivity> = ActivityScenarioRule(CoffeeMakerActivity::class.java)

    private val waterLevel2: Int = 2
    private val waterLevel1: Int = 1
    private val waterLevel0: Int = 0

    companion object {

        private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        private val appTest: CoffeeMakerAppTest = appContext as CoffeeMakerAppTest
        private val jsonUtil: JsonUtil = appTest.jsonUtil
        private val mockWebServer: MockWebServer = appTest.mockWebServer
        private val dispatcher: Dispatcher = TestDispatcher(jsonUtil)
    }

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(appTest.resource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(appTest.resource)
    }

    @Test
    fun testAcoffeeMakerUnplugged() {
        mockWebServer.dispatcher = dispatcher

        testTitleView(R.string.coffee_maker_unplugged)
        testWaterLevelView(waterLevel2)
        testLottieAnimationView(false)
    }

    @Test
    fun testBcoffeeMakerReady() {
        mockWebServer.dispatcher = dispatcher

        testTitleView(R.string.coffee_maker_ready)
        testWaterLevelView(waterLevel2)
        testLottieAnimationView(false)
    }

    @Test
    fun testCnotEnoughWater() {
        mockWebServer.dispatcher = dispatcher

        testTitleView(R.string.not_enough_water)
        testWaterLevelView(waterLevel0)
        testLottieAnimationView(false)
    }

    @Test
    fun testDcoffeeReady() {
        mockWebServer.dispatcher = dispatcher

        testTitleView(R.string.coffee_ready)
        testWaterLevelView(waterLevel1)
        testLottieAnimationView(true)
    }

    @Test
    fun testEpreparingCoffeeWithCoffeeMakerReady() {
        mockWebServer.dispatcher = dispatcher

        testTitleView(R.string.preparing_coffee)
        testWaterLevelView(waterLevel2)
        testLottieAnimationView(true)
    }

    @Test
    fun testFpreparingCoffeeWithCoffeeMakerNotReady() {
        mockWebServer.dispatcher = dispatcher

        testTitleView(R.string.preparing_coffee)
        testWaterLevelView(waterLevel2)
        testLottieAnimationView(true)
    }

    @Test
    fun testGcoffeeMakerResting5() {
        mockWebServer.dispatcher = dispatcher

        testTitleView(R.string.coffee_resting_5)
        testWaterLevelView(waterLevel1)
        testLottieAnimationView(false)
    }

    @Test
    fun testHcoffeeMakerResting4() {
        mockWebServer.dispatcher = dispatcher

        testTitleView(R.string.coffee_resting_4)
        testWaterLevelView(waterLevel1)
        testLottieAnimationView(false)
    }

    @Test
    fun testICoffeeMakerResting3() {
        mockWebServer.dispatcher = dispatcher

        testTitleView(R.string.coffee_resting_3)
        testWaterLevelView(waterLevel1)
        testLottieAnimationView(false)
    }

    @Test
    fun testJcoffeeMakerResting2() {
        mockWebServer.dispatcher = dispatcher

        testTitleView(R.string.coffee_resting_2)
        testWaterLevelView(waterLevel1)
        testLottieAnimationView(false)
    }

    @Test
    fun testKcoffeeMakerResting1() {
        mockWebServer.dispatcher = dispatcher

        testTitleView(R.string.coffee_resting_1)
        testWaterLevelView(waterLevel1)
        testLottieAnimationView(false)
    }

    private fun testTitleView(@StringRes stringResourceId: Int) {
        Espresso.onView(ViewMatchers.withId(R.id.vTitle))
            .check { view, _ ->
                val titleView = view as TitleView
                Assert.assertEquals(appContext.resources.getString(stringResourceId), titleView.text)
            }
    }

    private fun testWaterLevelView(waterLevel: Int) {
        Espresso.onView(ViewMatchers.withId(R.id.vWaterLevel))
            .check { view, _ ->
                val waterLevelView = view as WaterLevelView
                Assert.assertEquals(waterLevel, waterLevelView.waterLevel)
            }
    }

    private fun testLottieAnimationView(canAnimate: Boolean) {
        Espresso.onView(ViewMatchers.withId(R.id.vMain))
            .check { view, _ ->
                val lottieAnimationView = view as LottieAnimationView
                if (!canAnimate) {
                    Assert.assertFalse(lottieAnimationView.isAnimating)
                }
            }
    }
}