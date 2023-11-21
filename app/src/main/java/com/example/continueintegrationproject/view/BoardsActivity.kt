package com.example.continueintegrationproject.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.continueintegrationproject.R
import com.example.continueintegrationproject.databinding.ActivityBoardsBinding
import com.example.continueintegrationproject.databinding.ActivityMainBinding
import com.example.continueintegrationproject.viewmodel.BoardsViewModel

class BoardsActivity : AppCompatActivity() {
    private var viewModel = BoardsViewModel()
    private val manager = supportFragmentManager
    private var fragmentLoaded: String? = null
    val binding = ActivityBoardsBinding.inflate(layoutInflater)
    companion object{
        var boardId: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        var fragment: Fragment

        fragmentLoaded = "board"

        binding.btnContinue.setOnClickListener {
            when (fragmentLoaded) {
                "board" -> saveBoard()
                "extra info" -> {
                    //updateBoard()
                    startActivity(Intent(this, MainActivity::class.java))
                    this.finish()
                }
            }
        }
    }
    /*fun replaceFragment(fragment:Fragment){
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragmentBoard, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }*/

    fun saveBoard() {
        val name = binding.inputBoardName.text.toString()
        val amount = binding.inputBoardAmount.text.toString()
        val current: Double? = 0.0

        if (name.isEmpty()) {
            binding.inputBoardName.error = "This field is required"

        } else if (amount <= "0" || amount.isEmpty()) {
            binding.inputBoardAmount.error = "This field is required"

        } else {
            //replaceFragment(ExtraInfo())
            Toast.makeText(applicationContext, "Board added", Toast.LENGTH_LONG).show()

            Thread(Runnable {
                boardId = viewModel.saveBoardData(name, amount.toDouble(), current!!).toString()
            }).start()

        }
    }

    /*fun updateBoard(){
        Thread(Runnable {
            viewModel.updateBoards()
        }).start()
    }*/
}