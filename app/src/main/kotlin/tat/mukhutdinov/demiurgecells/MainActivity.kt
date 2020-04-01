package tat.mukhutdinov.demiurgecells

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import tat.mukhutdinov.demiurgecells.adapter.CellsAdapter
import tat.mukhutdinov.demiurgecells.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setupCells()

        setupClicks()
    }

    private fun setupCells() {
        val adapter = CellsAdapter()

        val layoutManager = LinearLayoutManager(this)

        binding.cells.setHasFixedSize(true)
        binding.cells.adapter = adapter
        binding.cells.layoutManager = layoutManager

        viewModel.cells.observe(this, Observer {
            adapter.submitList(it)

            layoutManager.scrollToPositionWithOffset(0, 0)
        })
    }

    private fun setupClicks() {
        binding.create.setOnClickListener {
            viewModel.create()
        }
    }
}
