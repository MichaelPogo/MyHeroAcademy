package com.uppsale.myheroacademy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uppsale.myheroacademy.MainActivity
import com.uppsale.myheroacademy.viewModels.MainViewModel
import com.uppsale.myheroacademy.R
import com.uppsale.myheroacademy.adapters.HeroesAdapter
import com.uppsale.myheroacademy.util.Consts
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = (activity as MainActivity).viewModel
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = HeroesAdapter()
        rv_heroes.layoutManager = LinearLayoutManager(context)
        rv_heroes.adapter = adapter
        adapter.setItemClickListener { hero,position ->
            val bundle = Bundle().apply {
                putSerializable("heroPositionId",position)
                putSerializable("heroName",hero.name)
                putSerializable("publisher",hero.biography.publisher)
                putSerializable("imgUrl",hero.image.url)
            }
            (activity as MainActivity).nav_host_fragment_container.findNavController().navigate(R.id.action_searchFragment_to_detailsFragment,bundle)
        }
        viewModel.heroes.observe(viewLifecycleOwner, Observer { data ->
            adapter.setItems(ArrayList(data))
        })
        viewModel.isFetching.observe(viewLifecycleOwner, Observer { isFetching ->
            if (isFetching) pb_loading.visibility = View.VISIBLE else pb_loading.visibility =
                View.GONE
        })

        var typeDelayJob :Job? = null
        et_hero_search.addTextChangedListener{editable->
            typeDelayJob?.cancel()
            typeDelayJob = MainScope().launch {
                delay(Consts.TYPING_SEARCH_DELAY)
                editable?.let {
                    if(editable.toString().isNotEmpty()){
                        //removing all characters that are not letters for safe search and because names has only letters
                        val onlyLettersStr = editable.toString().replace(regex = "[^0-9a-zA-Z]+".toRegex(),"")
                        viewModel.fetchHeroBySearchName(onlyLettersStr)
                    }
                }
            }
        }
    }

}