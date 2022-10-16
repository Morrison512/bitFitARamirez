package com.example.bitfitaramirez

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

private const val TAG = "MainActivity/"

class MainActivity : AppCompatActivity() {

    private val bitfits = mutableListOf<BitFit>()
    lateinit var newB: Button
    lateinit var bitFitRV: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bitFitRV = findViewById(R.id.bitFitRV)
        val bitfitAdapter = BitFitAdapter(this, bitfits)
        bitFitRV.adapter = bitfitAdapter
        bitFitRV.layoutManager = LinearLayoutManager(this)

        newB = findViewById(R.id.newButton)
        newB.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            this.startActivity(intent)
        }
        lifecycleScope.launch {
            (application as BitFItApplication).db.articleDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    BitFit(
                        entity.dayText,
                        entity.hoursSlept,
                    )
                }.also { mappedList ->
                    bitfits.clear()
                    bitfits.addAll(mappedList)
                    bitfitAdapter.notifyDataSetChanged()
                }
            }
        }

        val bitFit = intent.getSerializableExtra("EXTRA_ENTRY") as BitFit?

        if(bitFit != null) {
            Log.d(TAG, "got extra")
            lifecycleScope.launch(IO) {
                (application as BitFItApplication).db.articleDao().insert(
                    BitFitEntity(
                        dayText = bitFit.dayText,
                        hoursSlept = bitFit.hoursSlept
                    )
                )
            }
        }


    }
}