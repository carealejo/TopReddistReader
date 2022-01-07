package com.acr.topredditsreader.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.acr.topredditsreader.R
import com.acr.topredditsreader.core.platform.BaseActivity
import com.acr.topredditsreader.databinding.ActivityRedditListBinding
import com.acr.topredditsreader.presentation.adapter.RedditAdapter
import com.acr.topredditsreader.presentation.viewmodel.RedditListViewModel
import javax.inject.Inject
import androidx.recyclerview.widget.LinearLayoutManager
import com.acr.topredditsreader.domain.model.RedditChildData

class RedditListActivity : BaseActivity(), RedditAdapter.Listener {

    @Inject
    lateinit var redditListViewModel: RedditListViewModel

    private lateinit var binding: ActivityRedditListBinding
    private var redditAdapter: RedditAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.inject(this)

        redditListViewModel.redditData.observe(this, {
            binding.swipeRefresh.isRefreshing = false
            binding.loader.visibility = View.GONE
            it.data.children.let { list ->
                redditAdapter?.updateRedditList(list)
            }
        })

        redditListViewModel.redditMoreData.observe(this, {
            binding.loader.visibility = View.GONE
            it.data.children.let { list ->
                redditAdapter?.updateMoreDataRedditList(list)
            }
        })

        redditListViewModel.onErrorRedditData.observe(this, {
            binding.loader.visibility = View.GONE
            showError(it)
        })

        redditListViewModel.onLocalErrorRedditData.observe(this, {
            binding.loader.visibility = View.GONE
            showError(resources.getString(it))
        })

        redditListViewModel.openRedditDetails.observe(this, { redditDetails ->
            val intent = Intent(this, RedditChildDetailsActivity::class.java).apply {
                putExtra(RedditChildDetailsActivity.REDDIT_DETAILLS, redditDetails)
            }
            startActivity(intent)
        })

        binding = DataBindingUtil.setContentView(this, R.layout.activity_reddit_list)
        binding.apply {
            redditAdapter = RedditAdapter(this@RedditListActivity)
            recyclerRedditData.adapter = redditAdapter

            swipeRefresh.setOnRefreshListener {
                binding.loader.visibility = View.GONE
                redditAdapter?.updateRedditList(arrayListOf())
                redditListViewModel.getRedditData()
            }

            recyclerRedditData.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    LinearLayoutManager::class.java.cast(recyclerView.layoutManager)?.let { layoutManager ->
                        val totalItemCount = layoutManager.itemCount
                        val lastVisible = layoutManager.findLastVisibleItemPosition()

                        if (totalItemCount > 0 && (lastVisible + 1 >= totalItemCount)) {
                            binding.loader.visibility = View.VISIBLE
                            redditListViewModel.getMoreRedditData()
                        }
                    }
                }
            })
        }

        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        redditListViewModel.initRedditDataBase()
    }

    override fun onResume() {
        super.onResume()

        if (redditAdapter?.itemCount == 0) {
            binding.loader.visibility = View.VISIBLE
            redditListViewModel.getRedditData()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        redditListViewModel.dispose()
    }

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun onItemClick(redditChildData: RedditChildData) {
        redditListViewModel.checkRedditDetails(redditChildData)
    }
}