package com.example.nasaapi.ui.pictureofthedayfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.nasaapi.databinding.FragmentPictureOfTheDayBinding
import com.example.nasaapi.utils.NetworkObserver
import com.example.nasaapi.utils.imageloader.CoilImageLoader
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import java.time.LocalDate

class PictureOfTheDayFragment : Fragment() {

    private val viewModel by viewModels<PictureOfTheDayFragmentViewModel>()
    private val networkObserver by lazy { NetworkObserver(requireContext()) }
    private val appImageLoader by lazy { CoilImageLoader() }

    private var actualDate = LocalDate.now().minusDays(1)
    private var previousDayCount = 0L
    private var secondDayCount = 0L

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding: FragmentPictureOfTheDayBinding
        get() = _binding ?: throw RuntimeException("FragmentPictureOfTheDayBinding? = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getPod()
        initButtons()
    }

    private fun getPod(date: String = actualDate.toString()) {
        lifecycleScope.launchWhenCreated {
            networkObserver.networkIsAvailable()
                .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
                .distinctUntilChanged()
                .collectLatest {
                    viewModel.getPod(it, date)
                }
        }
    }

    private fun initButtons() {
        binding.podFragmentPreviousDay.setOnClickListener {
            previousDayCount += 1
            getPod(actualDate.minusDays(previousDayCount).toString())
        }
        binding.podFragmentSecondDay.setOnClickListener {
            secondDayCount += 1
            getPod(actualDate.plusDays(secondDayCount).toString())
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            viewModel.pod
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collectLatest {
                    with(binding) {
                        podFragmentTitle.text = it.title
                        podFragmentDate.text = it.date
                        appImageLoader.loadInto(it.url, podFragmentImageView)
                    }
                }
        }
    }
}