package com.cwc.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.TextView
import org.jetbrains.anko.find

class MainActivity : AppCompatActivity() {
    private lateinit var tvSubmit: TextView
    private lateinit var edtInput: EditText
    private lateinit var tvContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvSubmit = find(R.id.tv_submit)
        edtInput = find(R.id.edt_input)
        tvContent = find(R.id.tv_content)
        tvSubmit.setOnClickListener {
            val text = edtInput.text.toString()
            if (text.isNotEmpty()) {
                submit(text)
                hideInput()
            }
        }
    }

    private var downY = 0f
    private var downRange = 80
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when(event.action){
                MotionEvent.ACTION_DOWN -> downY = it.y
                MotionEvent.ACTION_MOVE -> handleMoveEvent(event.y)
            }
        }
        return super.onTouchEvent(event)
    }

    private fun handleMoveEvent(endY: Float){
        if (downY - endY > downRange) {
            showInput()
        } else if (downY - endY < -downRange) {
            hideInput()
        }
    }

    private fun hideInput() {
        if (tvSubmit.visibility == View.VISIBLE) {
            tvSubmit.animation = AnimationUtils.loadAnimation(this, R.anim.hide)
            tvSubmit.animation.start()
            edtInput.animation = AnimationUtils.loadAnimation(this, R.anim.hide)
            edtInput.animation.start()
            tvSubmit.visibility = View.GONE
            edtInput.visibility = View.GONE
        }
    }

    private fun showInput(){
        if (tvSubmit.visibility == View.GONE) {
            tvSubmit.animation = AnimationUtils.loadAnimation(this, R.anim.show)
            tvSubmit.animation.start()
            edtInput.animation = AnimationUtils.loadAnimation(this, R.anim.show)
            edtInput.animation.start()
            tvSubmit.visibility = View.VISIBLE
            edtInput.visibility = View.VISIBLE
        }
    }

    private fun submit(text: String){
        tvContent.text = text
    }
}
