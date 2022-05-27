package com.example.github.repositories.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.example.github.repositories.R
import com.example.github.repositories.adapter.RepositoryAdapter
import com.example.github.repositories.databinding.FragmentMainBinding
import com.example.github.repositories.model.BaseResponse
import com.example.github.repositories.model.RepositoryDTO
import com.example.github.repositories.view.detail.DetailFragment
import com.example.github.repositories.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() {
    lateinit var fragmentMainBinding: FragmentMainBinding
    private val list = mutableListOf<RepositoryDTO>();
    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("mainKey") { requestKey, bundle ->
           if(bundle.get("refresh") as Boolean)
            fragmentMainBinding.newsList.adapter?.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        fragmentMainBinding.lifecycleOwner = viewLifecycleOwner
        return fragmentMainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }

    private fun initialization() {

        mainViewModel =
            ViewModelProvider(this)[MainViewModel::class.java]

        fragmentMainBinding.mainViewModel = mainViewModel
        val adapter = RepositoryAdapter(
            list,
            ::onItemClick
        )
        fragmentMainBinding.newsList.adapter = adapter

        fetchItems()

        mainViewModel.repositories.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.SUCCESS -> {
                    list.clear()
                    it.data?.let { it1 -> list.addAll(it1.toMutableList()) }
                    (fragmentMainBinding.newsList.adapter as RepositoryAdapter).submitList(list.toMutableList())
                }
                is BaseResponse.ERROR -> {
                    Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is BaseResponse.NO_NETWORK -> {
                    (activity as MainActivity).showRetryDialog(::fetchItems, it.errorMessage)
                }
            }
        }
    }


    private fun fetchItems() {
        mainViewModel.fetchItems()
    }

    private fun onItemClick(repositoryDTO: RepositoryDTO) {
        val bundle = Bundle()
        bundle.putParcelable("repository", repositoryDTO)

        activity?.supportFragmentManager?.beginTransaction()
            ?.setReorderingAllowed(true)
            ?.add(R.id.parent, DetailFragment::class.java, bundle)
            ?.addToBackStack("detail_fragment")
            ?.commit()

    }


}


