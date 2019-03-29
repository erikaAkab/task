package com.example.task

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setListeners()
    }

    private fun setListeners() {
        buttonSave.setOnClickListener {
            handleSave()
        }
    }

    private fun handleSave() {

    }
}
