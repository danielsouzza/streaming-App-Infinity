package com.example.infinity.activitys


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.infinity.R
import com.example.infinity.databinding.ActivityMainBinding
import com.example.infinity.fragments.FragmentHome
import com.example.infinity.fragments.FragmentProfile
import com.example.infinity.fragments.FragmentSearch

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(FragmentHome())

        binding.navBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(FragmentHome())
                R.id.search -> replaceFragment(FragmentSearch())
                R.id.profile -> replaceFragment(FragmentProfile())
            }
            true
        }
    }

    private fun replaceFragment(fragmentHome: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragmentHome)
        fragmentTransaction.commit()
    }
}