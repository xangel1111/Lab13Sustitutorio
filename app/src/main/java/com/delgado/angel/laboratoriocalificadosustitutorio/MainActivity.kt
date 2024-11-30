package com.delgado.angel.laboratoriocalificadosustitutorio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.delgado.angel.laboratoriocalificadosustitutorio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var listPost: List<PostResponse> = emptyList()

    private val adapter by lazy {
        PostAdapter(listPost,
            onItemClick = { post ->
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("smsto:+51925137361")
                    putExtra("sms_body", post.title)
                }
                startActivity(intent)
            },
            onItemLongClick = { post ->
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    setType("text/plain");
                    data = Uri.parse("mailto:victor.saico@tecsup.edu.pe")
                    putExtra(Intent.EXTRA_SUBJECT, "Post Information")
                    putExtra(Intent.EXTRA_TEXT, post.body)
                    putExtra("body", post.body)
                }
                startActivity(intent)
            }
        )
    }

    private val viewModel by lazy { MainViewModel() }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvPost.layoutManager = LinearLayoutManager(this)
        binding.rvPost.adapter = adapter
        observeValues();
    }

    private fun observeValues() {
        viewModel.postList.observe(this) { postList ->
            adapter.list = postList
            adapter.notifyDataSetChanged()
        }

        viewModel.errorApi.observe(this) { errorMessage ->
            showMessage(errorMessage)
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}