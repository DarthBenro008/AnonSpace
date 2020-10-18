package com.benrostudios.anonymouspace.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.benrostudios.anonymouspace.R
import com.benrostudios.anonymouspace.utils.GlideApp
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_on_boarding.*


class OnBoardingFragment(
    private val imageRes: Int = R.drawable.ic_onboarding_1st,
    private val description: String = "The hackathon will be focusing on the feasibility, application, resourcefulness and fundability of each project idea presented by the participant"
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_on_boarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView = iv_onboarding_image
        Glide.with(view)
            .load(imageRes)
            .into(imageView)
        tv_description.text = description
    }

}