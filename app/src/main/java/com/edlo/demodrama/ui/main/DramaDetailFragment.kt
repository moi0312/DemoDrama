package com.edlo.demodrama.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.edlo.demodrama.R
import com.edlo.demodrama.databinding.FragmentDramaDetailBinding
import com.edlo.demodrama.repository.local.Drama
import com.edlo.demodrama.ui.base.BaseFragment
import com.edlo.demodrama.ui.base.BaseViewModel
import com.edlo.demodrama.util.GlideApp
import com.edlo.demodrama.util.Utilities

class DramaDetailFragment: BaseFragment<BaseViewModel, FragmentDramaDetailBinding>() {

    companion object {
        val TAG = DramaDetailFragment::class.java.simpleName
        const val ARG_SERIALIZABLE_DRAMA = "drama"
        fun newInstance(drama: Drama): DramaDetailFragment {
            var frag = DramaDetailFragment()
            var args = Bundle()
            args.putSerializable(ARG_SERIALIZABLE_DRAMA, drama)
            frag.arguments = args
            return frag
        }
    }

    private var drama: Drama? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        drama = arguments?.getSerializable(ARG_SERIALIZABLE_DRAMA) as Drama

        initView()
        initViewModelObserve()
    }

    private fun initView() {
        drama?.let { item ->
            binding.txtName.text = item.name
            binding.txtTotalViews.text = item.totalViews
            binding.txtRating.text = Utilities.getFormatedString(item.rating.toDouble())
            binding.ratingBar.rating = item.rating
            binding.txtDate.text = Utilities.parseUTCDateString(
                item.createdAt,
                getString(R.string.dateFormat_yyyyMMdd_T_HHmmssSSS_Z),
                getString(R.string.dateFormat_yyyyMMdd)
            )

            GlideApp.with(binding.root.context)
                .load(item.thumb)
                .placeholder(R.drawable.shape_drama_thumb)
                .into(binding.imgDrama)
        }
    }

    private fun initViewModelObserve() {

    }

    override fun initViewModel(): BaseViewModel {
        return object: BaseViewModel(){ }
    }

    override fun initViewBinding(inflater: LayoutInflater,
             container: ViewGroup?, savedInstanceState: Bundle?): FragmentDramaDetailBinding {
        return FragmentDramaDetailBinding.inflate(inflater, container, false)
    }
}