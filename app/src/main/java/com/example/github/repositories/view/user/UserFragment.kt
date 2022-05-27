package com.example.github.repositories.view.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.example.github.repositories.R
import com.example.github.repositories.adapter.RepositoryAdapter
import com.example.github.repositories.databinding.FragmentUserBinding
import com.example.github.repositories.model.BaseResponse
import com.example.github.repositories.model.OwnerDTO
import com.example.github.repositories.model.RepositoryDTO
import com.example.github.repositories.view.detail.DetailFragment
import com.example.github.repositories.view.main.MainActivity
import com.example.github.repositories.viewmodel.UserViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {
    private lateinit var user: OwnerDTO
    private val list = mutableListOf<RepositoryDTO>()

    private lateinit var userViewModel: UserViewModel

    private lateinit var fragmentUserBinding: FragmentUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("detailKey") { requestKey, bundle ->
            if(bundle.get("refresh") as Boolean)
                fragmentUserBinding.list.adapter?.notifyDataSetChanged()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        user = requireArguments().getParcelable("user")!!
        fragmentUserBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        fragmentUserBinding.lifecycleOwner = viewLifecycleOwner
        return fragmentUserBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel =
            ViewModelProvider(this)[UserViewModel::class.java]

        fragmentUserBinding.userViewModel = userViewModel
        val adapter = RepositoryAdapter(
            list,
            ::onItemClick
        )
        fragmentUserBinding.list.adapter = adapter

        fragmentUserBinding.title!!.text = user.login
        Picasso.get().load(user.avatar_url.toUri()).into(fragmentUserBinding.image)

        userViewModel.fetchUser(user.login)

        userViewModel.user.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.SUCCESS -> {
                    var tUserText =
                        getString(R.string.twitter_handle_user_, it.data?.twitter_username ?: "NA")
                    fragmentUserBinding.detail.text = tUserText
                    userViewModel.fetchRepositories()
                }
                is BaseResponse.ERROR -> {
                    Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is BaseResponse.NO_NETWORK -> {
                    (activity as MainActivity).showRetryDialog(::fetchRepositories, it.errorMessage)
                }
            }


        }
        userViewModel.repositories.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.SUCCESS -> {
                    list.clear()
                    it.data?.let { it1 -> list.addAll(it1) }
                    fragmentUserBinding.list.adapter?.notifyDataSetChanged()

                }
                is BaseResponse.ERROR -> {
                    Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is BaseResponse.NO_NETWORK -> {
                    (activity as MainActivity).showRetryDialog(::fetchRepositories, it.errorMessage)
                }
            }
        }
    }

    private fun fetchRepositories() {
        userViewModel.fetchRepositories()
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




