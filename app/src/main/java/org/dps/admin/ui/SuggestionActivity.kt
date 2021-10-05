package org.dps.admin.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.dps.admin.R
import org.dps.admin.model.SuggestionAddress
import org.dps.admin.mvvm.MainViewModel
import org.dps.admin.utils.showSnack
import org.dps.admin.utils.toast
import kotlinx.android.synthetic.main.activity_search_suggestion.*
import org.koin.android.viewmodel.ext.android.viewModel


class SuggestionActivity : AppCompatActivity() {

    private val searchViewModel: MainViewModel by viewModel()
    var suggestionList = mutableListOf<SuggestionAddress>()
    private lateinit var mSuggestionListAdapter:SuggestionListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_suggestion)
        setUpUI()
        setupViewModel()
    }

    private fun setupViewModel(){
        searchViewModel.getSuggestions().observe(this, Observer {
            if(it!=null) {
                progressHideShow(false)
                suggestionList = it as MutableList<SuggestionAddress>
                notifyAdapter()
            }
        })

        searchViewModel.getErrorMessage().observe(this, Observer {
            progressHideShow(false)
            showSnack(it)
        })
    }

    private fun notifyAdapter(){
        mSuggestionListAdapter=SuggestionListAdapter(suggestionList)
        suggestion_list.adapter = mSuggestionListAdapter
        mSuggestionListAdapter.notifyDataSetChanged()
    }



    private fun setUpUI(){
        suggestion_list.layoutManager = LinearLayoutManager(this)
        search_suggest.requestFocus();
        search_suggest.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if(s.length>1) {
                    setSuggestionQuery(s.toString())
                    in_clear.visibility = View.VISIBLE
                    progressHideShow(true)

                }else{
                    closeSuggestion()
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })

        search_suggest.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                return@OnEditorActionListener true
            }
            false
        })

        in_clear.setOnClickListener {
            closeSuggestion()
            search_suggest.text.clear()
        }
    }

    fun setSuggestionQuery(query:String){
        searchViewModel.setSuggestions(query)
    }

    fun closeSuggestion(){
        if (::mSuggestionListAdapter.isInitialized) {
            suggestionList.clear()
            mSuggestionListAdapter.notifyDataSetChanged()
        }
        in_clear.visibility = View.GONE
    }

    fun progressHideShow(flag:Boolean){
        if(flag){
            progress_circular.visibility=View.VISIBLE
        }else{
            progress_circular.visibility=View.GONE
        }
    }

}
