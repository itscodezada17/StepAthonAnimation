package com.ujjwal.stepathonanimation.onboarding.bindings

import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ujjwal.stepathonanimation.R

@BindingAdapter("setTitleText")
fun setTitleText(view: AppCompatTextView, page: Int) {
    when (page) {
        0 -> view.text = "Step Up and Score"
        1 -> view.text = "Claim the Throne"
        2 -> view.text = "Score Big!!"
    }
}

@BindingAdapter("setSubtitleText")
fun setSubtitleText(view: AppCompatTextView, page: Int) {
    when (page) {
        0 -> view.text = "Join the Race, Lace Up, and Embrace Health"
        1 -> view.text = "Compare with your collegeagues for the Top Ranks"
        2 -> view.text = "Victory Unlocks Spectacular Vouchers, Cashbacks, and Beyond"
    }
}

@BindingAdapter("updateBackgroundWithAnimation")
fun updateBackgroundWithAnimation(view: ConstraintLayout, page: Int) {
    when (page) {
        0 -> view.setBackgroundResource(R.drawable.gradient_first)
        1 -> view.setBackgroundResource(R.drawable.gradient_second)
        2 -> view.setBackgroundResource(R.drawable.gradient_third)
    }
}

@BindingAdapter("loadWebpImageAndStartAnimation")
fun loadWebpImageAndStartAnimation(view: AppCompatImageView, page: Int) {
    when (page) {
        0 -> {
            view.visibility = View.GONE
            view.clearAnimation()
        }

        else -> {
            view.visibility = View.VISIBLE
            animateImage(view)
        }
    }
}

@BindingAdapter("animateShiner")
fun animateShiner(view: AppCompatImageView, dummyText: String) {
    animateImage(view)
}

fun animateImage(view: AppCompatImageView) {
    Glide.with(view.context)
        .load(R.drawable.shiner)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(view)

    val rotateAnimation = RotateAnimation(
        0f,
        359f,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    )
    rotateAnimation.duration = 10000
    rotateAnimation.repeatCount = Animation.INFINITE
    rotateAnimation.interpolator = LinearInterpolator()

    view.startAnimation(rotateAnimation)
}
