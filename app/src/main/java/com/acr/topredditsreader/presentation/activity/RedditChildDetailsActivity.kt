package com.acr.topredditsreader.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.acr.topredditsreader.R
import com.acr.topredditsreader.databinding.ActivityRedditChildDetailsBinding
import com.acr.topredditsreader.domain.model.RedditChildData
import com.bumptech.glide.Glide

class RedditChildDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRedditChildDetailsBinding

    companion object {
        const val REDDIT_DETAILLS = "REDDIT_DETAILLS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val redditDetails = intent.extras?.getParcelable(REDDIT_DETAILLS) as? RedditChildData

        binding = DataBindingUtil.setContentView(this, R.layout.activity_reddit_child_details)
        redditDetails?.let {

            binding.redditData = it

            Glide.with(this)
                .load(redditDetails.url_overridden_by_dest)
                .centerCrop()
                .error(R.drawable.no_image_available)
                .into(binding.imageView)
        }

        setContentView(binding.root)
    }
}