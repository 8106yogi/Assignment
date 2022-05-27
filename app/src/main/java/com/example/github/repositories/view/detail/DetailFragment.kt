package com.example.github.repositories.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.github.repositories.R
import com.example.github.repositories.data.LocalDataStore
import com.example.github.repositories.databinding.FragmentDetailBinding
import com.example.github.repositories.model.RepositoryDTO
import com.example.github.repositories.view.user.UserFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var repository: RepositoryDTO
    private lateinit var fragmentDetailBinding: FragmentDetailBinding


    override fun onDetach() {
        super.onDetach()
        setFragmentResult("detailKey", bundleOf("refresh" to true))
        setFragmentResult("mainKey", bundleOf("refresh" to true))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        repository = requireArguments().getParcelable("repository")!!
        fragmentDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        fragmentDetailBinding.lifecycleOwner = viewLifecycleOwner
        return fragmentDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentDetailBinding.title!!.text = repository.name

        var ownerName =
            getString(
                R.string.repository_owner_,
                repository.owner!!.login ?: "NA",
                repository.created_at ?: "NA"
            )
        fragmentDetailBinding.detail!!.text = ownerName


        fragmentDetailBinding.url!!.text = repository.html_url

        fragmentDetailBinding.image!!.setImageResource(
            if (LocalDataStore.instance.getBookmarks().contains(repository))
                R.drawable.baseline_bookmark_black_24
            else
                R.drawable.baseline_bookmark_border_black_24
        )

        fragmentDetailBinding.image!!.setOnClickListener {
            val isBookmarked = LocalDataStore.instance.getBookmarks().contains(repository)
            LocalDataStore.instance.bookmarkRepo(repository, !isBookmarked)
            fragmentDetailBinding.image!!.setImageResource(if (!isBookmarked) R.drawable.baseline_bookmark_black_24 else R.drawable.baseline_bookmark_border_black_24)
        }
        fragmentDetailBinding.detail!!.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("user", repository.owner)
            activity?.supportFragmentManager?.beginTransaction()
                ?.setReorderingAllowed(true)
                ?.add(R.id.parent, UserFragment::class.java, bundle)
                ?.addToBackStack("user_fragment")
                ?.commit()

        }
    }
}