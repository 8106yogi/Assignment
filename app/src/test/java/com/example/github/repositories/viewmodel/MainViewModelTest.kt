package com.example.github.repositories.viewmodel

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.github.repositories.di.NetworkModule
import com.example.github.repositories.di.NetworkModule_ProvidesRetrofitFactory
import com.example.github.repositories.network.GitHubEndpoints
import com.example.github.repositories.repository.MainRepository
import com.google.common.truth.Truth
import junit.framework.TestCase
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

class MainViewModelTest:TestCase(){

    lateinit var viewModel: MainViewModel

    @Before
    public override fun setUp() {
    }

    @After
    @Throws(IOException::class)
    fun close() {
    }



}