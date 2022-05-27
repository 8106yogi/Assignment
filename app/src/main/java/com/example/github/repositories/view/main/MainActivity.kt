package com.example.github.repositories.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.github.repositories.MyAlertDialogFragment
import com.example.github.repositories.R
import com.example.github.repositories.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityMainBinding.lifecycleOwner = this

        if (savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.parent, MainFragment::class.java, null)
                .commit()

        }


    }

    fun showRetryDialog(action: () -> Unit, errorMessage: String?) {
        val retryAlert = MyAlertDialogFragment.newInstance(errorMessage)
        supportFragmentManager.let { retryAlert.show(it, "retryAlert") }
        retryAlert.setRetryClick(object : MyAlertDialogFragment.OnRetryClicked {
            override fun onRetryClicked() {
                action()
            }
        })
    }

}