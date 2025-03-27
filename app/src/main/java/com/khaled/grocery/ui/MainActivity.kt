package com.khaled.grocery.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.khaled.grocery.R
import com.khaled.grocery.databinding.ActivityMainBinding
import com.khaled.grocery.ui.view_model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val homeFragment = HomeFragment()
    private val cartFragment = CartFragment()
    private val favFragment = FavFragment()
    private val accountFragment = AccountFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initFragment()
        bottomNavListener()
    }

    private fun bottomNavListener() {
        binding.bottomNavigationView.setOnItemSelectedListener {item ->
            when(item.itemId){
                R.id.page_home -> {
                    changeFragment(homeFragment, false)
                    true
                }
                R.id.page_cart ->{
                    changeFragment(cartFragment, false)
                    true
                }
                R.id.page_fav ->{
                    changeFragment(favFragment, false)
                    true
                }
                R.id.page_account ->{
                    changeFragment(accountFragment, false)
                    true
                }
                else -> false
            }
        }
    }

    private fun initFragment() {
        changeFragment(homeFragment, true)
    }

    private fun changeFragment(fragment: Fragment, isAdd : Boolean){
        val transaction = supportFragmentManager.beginTransaction()
        if (isAdd)
            transaction.add(R.id.fragment_container, fragment)
        else
            transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    /*fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        //binding.textView.visibility = if (show) View.GONE else View.VISIBLE
    }*/
}
