package com.example.nasaapi.ui.pictureofthedayfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.nasaapi.R
import com.example.nasaapi.databinding.FragmentPictureOfTheDayBinding
import com.example.nasaapi.ui.datepickerdialogfragment.DatePickerDialogFragment
import com.example.nasaapi.utils.NetworkObserver
import com.example.nasaapi.utils.imageloader.CoilImageLoader
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged

class PictureOfTheDayFragment : Fragment() {

    private val viewModel by viewModels<PictureOfTheDayFragmentViewModel>()
    private val networkObserver by lazy { NetworkObserver(requireContext()) }
    private val appImageLoader by lazy { CoilImageLoader() }

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding: FragmentPictureOfTheDayBinding
        get() = _binding ?: throw RuntimeException("FragmentPictureOfTheDayBinding? = null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDialogResultListener()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPod()
        initListeners()
    }

    private fun initDialogResultListener() {
        setFragmentResultListener(DatePickerDialogFragment.REQUEST_KEY) { _, result: Bundle ->
            val array: IntArray = result.getIntArray(DatePickerDialogFragment.KEY_RESPONSE)
                ?: throw RuntimeException("DialogFragment result is null")
            viewModel.setDate(array[0], array[1], array[2])
            getPod(viewModel.actualDate.toString())
        }
    }

    private fun getPod(date: String = viewModel.actualDate.toString()) {
        lifecycleScope.launchWhenCreated {
            networkObserver.networkIsAvailable()
                .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
                .distinctUntilChanged()
                .collectLatest {
                    viewModel.getPod(it, date)
                }
        }
    }

    private fun initListeners() {
        binding.podFragmentPreviousDay.setOnClickListener {
            viewModel.minusDay()
            getPod(viewModel.actualDate.toString())
        }
        binding.podFragmentSecondDay.setOnClickListener {
            viewModel.plusDay()
            getPod(viewModel.actualDate.toString())
        }
        binding.podFragmentDate.setOnClickListener {
            val action = PictureOfTheDayFragmentDirections
                .actionPictureOfTheDayFragmentToDatePickerDialogFragment(
                    intArrayOf(
                        viewModel.actualDate.year,
                        viewModel.actualDate.month.value,
                        viewModel.actualDate.dayOfMonth
                    )
                )
            findNavController().navigate(action)
        }
        binding.podFragmentFavoriteCheckBox.setOnClickListener {
            binding.podFragmentFavoriteCheckBox.isChecked = viewModel.favoriteClickHandler()
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            viewModel.pod
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collectLatest {
                    cleanData()
                    renderData(it)
                }
        }
    }

    private fun cleanData() {
        binding.podFragmentExplanation.text = ""
        binding.podFragmentTitle.text = ""
        binding.podFragmentFavoriteCheckBox.isChecked = false
        binding.podFragmentDate.text = viewModel.actualDate.toString()
    }

    private fun renderData(state: PictureOfTheDayFragmentState) {
        when (state) {
            is PictureOfTheDayFragmentState.Success -> {
                binding.podFragmentFavoriteCheckBox.isChecked = viewModel.checkIfFavorite()
                binding.podFragmentExplanation.text = state.response.explanation
                binding.podFragmentTitle.text = state.response.title
                binding.podFragmentDate.text = state.response.date
                appImageLoader.loadInto(state.response.url, binding.podFragmentImageView)
            }
            is PictureOfTheDayFragmentState.Loading -> {
                binding.podFragmentTitle.text = requireContext().getText(R.string.loading_state)
            }
            is PictureOfTheDayFragmentState.Error -> {
                binding.podFragmentTitle.text = requireContext().getText(R.string.error_state)
                binding.podFragmentExplanation.text = state.error
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}