package com.krodriguez.jpmorgan.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.krodriguez.jpmorgan.R
import com.krodriguez.jpmorgan.data.remote.model.RemoteAlbumItem
import com.krodriguez.jpmorgan.data.remote.state.APIState
import com.krodriguez.jpmorgan.databinding.FragmentDetailsBinding
import com.krodriguez.jpmorgan.di.DIPresentationComponent
import com.krodriguez.jpmorgan.presentation.AlbumItemViewModel
import com.krodriguez.jpmorgan.ui.ToolbarExtension
import java.lang.IllegalStateException

class AlbumDetailsFragment : Fragment() {

    private val args: AlbumDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailsBinding

    private val itemViewModel: AlbumItemViewModel by lazy {
        DIPresentationComponent.provideAlbumItemViewModel(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        populateData()
    }

    private fun populateData() {
        // Handle configuration changes
        itemViewModel.albumLiveData.value?.let { dataState ->
            renderStates(dataState)
        } ?: run {
            itemViewModel.getAlbumById(args.albumId)
        }

        itemViewModel.albumLiveData.observe(viewLifecycleOwner) { dataState ->
            renderStates(dataState)
        }
    }

    private fun renderStates(dataState: APIState) {
        when (dataState) {
            APIState.Loading -> renderLoadingState()
            is APIState.Error -> renderErrorState(dataState)
            is APIState.Success<*> -> renderSuccessState(dataState)
            else -> throw IllegalStateException(
                "${dataState::class.java.simpleName} state cannot be handle"
            )
        }
    }

    private fun <T>renderSuccessState(dataState: APIState.Success<T>) {
        binding.run {
            pbLoading.visibility = View.GONE
            tvTitle.visibility = View.VISIBLE
            tvTitle.text = (dataState.data as RemoteAlbumItem).title
        }
    }

    private fun renderErrorState(dataState: APIState.Error) {
        binding.run {
            pbLoading.visibility = View.GONE
            tvTitle.visibility = View.GONE
            tvError.text = dataState.error
        }
    }

    private fun renderLoadingState() {
        binding.run {
            pbLoading.visibility = View.VISIBLE
            tvTitle.visibility = View.GONE
            tvError.visibility = View.GONE
        }
    }

    private fun initToolbar() {
        with(ToolbarExtension) {
            setupToolbar(
                requireActivity(),
                binding.include.toolbar,
                getString(R.string.menu_item),
                true
            )
        }
    }
}