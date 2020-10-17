package com.benrostudios.anonymouspace.ui.pages

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import com.benrostudios.anonymouspace.R
import com.benrostudios.anonymouspace.ui.ChatActivity
import kotlinx.android.synthetic.main.fragment_first.*


class First : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        join_button.setOnGenericMotionListener { view, motionEvent ->
            requireActivity().startActivity(Intent(requireActivity(),ChatActivity::class.java))
            true
        }
        base_motion.setTransitionListener(object: MotionLayout.TransitionListener{
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                Log.d("hello","motion started")
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                Log.d("hello","motion started")
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                if(p1 == R.id.create_anim){
                    requireActivity().startActivity(Intent(requireActivity(),ChatActivity::class.java))
                    //requireActivity().finish()
                }

            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
                Log.d("hello","motion started")
            }

        })

    }


}