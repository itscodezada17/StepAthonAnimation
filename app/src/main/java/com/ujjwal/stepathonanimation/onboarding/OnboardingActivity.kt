package com.ujjwal.stepathonanimation.onboarding

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.ujjwal.stepathonanimation.R
import com.ujjwal.stepathonanimation.databinding.ActivityOnboardingBinding
import com.ujjwal.stepathonanimation.onboarding.adapter.OnboardingViewPagerAdapter
import com.ujjwal.stepathonanimation.onboarding.utils.EventObserver

class OnboardingActivity : AppCompatActivity() {

    private var _binding: ActivityOnboardingBinding ? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<OnBoardingViewModel>()
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOnboardingBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        onViewCreated()
    }

    private fun onViewCreated() {
        initClickListeners()
        initObservers()
        initDots()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initDots() {
        viewPager = binding.viewPager
        viewPager.adapter = OnboardingViewPagerAdapter()

//        TabLayoutMediator(binding.tabDots, viewPager) { tab, position ->
//        }.attach()
    }

    private fun initObservers() {
        viewModel.pageNumber.observe(this, EventObserver {
            viewPager.currentItem = it
            binding.buttonNxt.text = when (it) {
                2 -> "Let's Go!"
                else -> "Next"
            }
        })
    }

    private fun initClickListeners() {
        binding.buttonNxt.setOnClickListener {
            when (viewModel.pageNumber.value?.peekContent()) {
                0 -> {
                    binding.main.transitionToState(R.id.second)
                    viewModel.updatePageNumber(1)
                }
                1 -> {
                    binding.main.transitionToState(R.id.third)
                    viewModel.updatePageNumber(2)
                }
            }
        }

        binding.main.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {
                updatePageNumber(motionLayout?.currentState)
            }

            override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {
                updatePageNumber(motionLayout?.currentState)
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                updatePageNumber(motionLayout?.currentState)
            }

            override fun onTransitionTrigger(motionLayout: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float) {
                // No implementation needed
            }

            private fun updatePageNumber(state: Int?) {
                state?.let {
                    val pageNumber = when (it) {
                        R.id.first -> 0
                        R.id.second -> 1
                        R.id.third -> 2
                        else -> return
                    }
                    viewModel.updatePageNumber(pageNumber)
                }
            }
        })
    }
}