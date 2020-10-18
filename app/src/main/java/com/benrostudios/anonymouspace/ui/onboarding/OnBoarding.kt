package com.benrostudios.anonymouspace.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.benrostudios.anonymouspace.R
import com.benrostudios.anonymouspace.ui.Auth
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoarding : AppCompatActivity() {
    private var currentPos = 0
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        initializeUi()
    }

    private fun initializeUi() {
        val viewPager = vp_photos_viewpager as ViewPager2
        val adapter = OnboardingAdapter(this@OnBoarding)
        viewPager.adapter = adapter
        TabLayoutMediator(tl_tab_layout, viewPager) { _, _ ->
            //Some implementation
        }.attach()
        runnable = Runnable {
            currentPos = (currentPos + 1) % 3
            runOnUiThread {
                viewPager.currentItem = currentPos
                handler.postDelayed(runnable, 4000)
            }
        }
        handler = Handler(Looper.getMainLooper())
        viewPager.isUserInputEnabled = false
        btn_skip.setOnClickListener {
            startActivity(Intent(this, Auth::class.java))
            finish()
        }
    }


    class OnboardingAdapter(val activity: OnBoarding) :
        FragmentStateAdapter(activity) {

        private val descriptionList = listOf(activity.resources.getStringArray(R.array.on_boarding_array))[0]

        companion object {
            private const val NUM_ITEMS = 3

        }

        override fun getItemCount(): Int {
            return NUM_ITEMS
        }

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> OnBoardingFragment(R.drawable.ic_onboarding_1st, descriptionList[0])
                1 -> OnBoardingFragment(R.drawable.ic_onboarding_2nd, descriptionList[1])
                else -> OnBoardingFragment(R.drawable.ic_onboarding_3rd, descriptionList[2])
            }
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        initializeUi()
        handler.postDelayed(runnable, 4000)
    }
}
