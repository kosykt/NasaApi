package com.example.nasaapi.ui.pictureofthedayfragment

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.nasaapi.R
import com.example.nasaapi.databinding.FragmentPictureOfTheDayBinding
import com.example.nasaapi.ui.datepickerdialogfragment.DatePickerDialogFragment
import com.example.nasaapi.utils.NetworkObserver
import com.example.nasaapi.utils.ViewModelFactory
import com.example.nasaapi.utils.imageloader.AppImageLoader
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class PictureOfTheDayFragment : Fragment() {

    @Inject
    lateinit var appImageLoader: AppImageLoader

    @Inject
    lateinit var networkObserver: NetworkObserver

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: PictureOfTheDayFragmentViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[PictureOfTheDayFragmentViewModel::class.java]
    }

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding: FragmentPictureOfTheDayBinding
        get() = _binding ?: throw RuntimeException("FragmentPictureOfTheDayBinding? = null")

    override fun onAttach(context: Context) {
        (requireActivity().application as PodSubcomponentProvider)
            .initPodSubcomponent()
            .inject(this)
        super.onAttach(context)
    }

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
        presettingWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun presettingWebView() {
        with(binding.podFragmentWebView){
            webChromeClient = WebChromeClient()
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        }
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
        binding.podFragmentImageView.visibility = View.VISIBLE
        binding.podFragmentWebView.visibility = View.GONE
        binding.podFragmentWebView.loadUrl("about:blank")
    }

    private fun renderData(state: PictureOfTheDayFragmentState) {
        when (state) {
            is PictureOfTheDayFragmentState.Success -> {
                binding.podFragmentFavoriteCheckBox.isChecked = viewModel.checkIfFavorite()
                binding.podFragmentExplanation.text = state.response.explanation
                binding.podFragmentTitle.text = state.response.title
                binding.podFragmentDate.text = state.response.date
                initShareButton(state.response.url)
                checkMediaType(state.response.mediaType, state.response.url)
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

    private fun initShareButton(url: String) {
        binding.podFragmentShareButton.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, url)
                type = "text/plain"
            }
            try {
                startActivity(sendIntent)
            } catch (e: ActivityNotFoundException) {
                Log.e(this::class.java.simpleName, e.message.toString())
            }
        }
    }

    private fun checkMediaType(mediaType: String, url: String) {
        when (mediaType) {
            IMAGE_TYPE -> {
                appImageLoader.loadInto(url, binding.podFragmentImageView)
            }
            VIDEO_TYPE -> {
                binding.podFragmentImageView.visibility = View.GONE
                binding.podFragmentWebView.visibility = View.VISIBLE
                binding.podFragmentWebView.loadUrl(url)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        lifecycleScope.coroutineContext.cancelChildren()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val IMAGE_TYPE = "image"
        private const val VIDEO_TYPE = "video"
    }
}