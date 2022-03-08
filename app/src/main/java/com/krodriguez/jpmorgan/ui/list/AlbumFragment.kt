package com.krodriguez.jpmorgan.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.krodriguez.jpmorgan.R
import com.krodriguez.jpmorgan.data.remote.state.APIState
import com.krodriguez.jpmorgan.databinding.FragmentListBinding
import com.krodriguez.jpmorgan.di.DIComponent
import com.krodriguez.jpmorgan.ui.ToolbarExtension

class AlbumFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    private val albumAdapter: AlbumAdapter by lazy {
        AlbumAdapter(emptyList())
    }

    private val viewModel by lazy {
        DIComponent.provideViewModel(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initSwipeRefresh()
        initAdapter()
        observeAlbums()
    }

    private fun initAdapter() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = albumAdapter
        }
    }

    private fun observeAlbums() {
        viewModel.albumsLiveData.value?.let { dataState ->
            renderStates(dataState)
        } ?: run {
            viewModel.getAlbums()
        }

        viewModel.albumsLiveData.observe(viewLifecycleOwner) { dataState ->
            renderStates(dataState)
        }
    }

    private fun renderStates(dataState: APIState?) {
        when (dataState) {
            is APIState.Loading -> {
                binding.apply {
                    pbLoading.visibility = View.VISIBLE
                    tvError.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                }
            }
            is APIState.Success -> {
                if (dataState.data.isEmpty()) {
                    showErrorData(error = getString(R.string.empty_items))
                } else {
                    binding.apply {
                        pbLoading.visibility = View.GONE
                        tvError.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        srlList.isRefreshing = false
                    }

                    dataState.data.let { list ->
                        albumAdapter.updateDataSet(list)
                    }
                }
            }
            is APIState.Empty -> {
                showErrorData(error = dataState.error)
            }
            is APIState.Error -> {
                showErrorData(error = dataState.error)
            }
            else -> {
                showErrorData(error = "Unknown Error")
            }
        }
    }

    private fun showErrorData(error: String) {
        binding.apply {
            tvError.text = error
            tvError.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            pbLoading.visibility = View.GONE
            srlList.isRefreshing = false
        }
    }

    private fun initToolbar() {
        with(ToolbarExtension) {
            setupToolbar(
                requireActivity(),
                binding.include.toolbar,
                getString(R.string.menu_list),
                false
            )
        }
    }

    private fun initSwipeRefresh() {
        binding.srlList.apply {
            setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
            setOnRefreshListener {
                viewModel.getAlbums()
            }
        }
    }
}