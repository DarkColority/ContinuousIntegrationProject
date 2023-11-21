package com.example.continueintegrationproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.continueintegrationproject.data.Repo
import com.example.continueintegrationproject.model.Boards

class BoardsViewModel : ViewModel() {
    val repo = Repo()
    fun fetchBoardData(): LiveData<MutableList<Boards>> {
        val mutableData = MutableLiveData<MutableList<Boards>>()
        repo.getBoardData().observeForever{
            mutableData.value = it
        }

        return mutableData
    }
    fun fetchGoalById(id:String): LiveData<MutableList<Boards>> {
        val mutableData = MutableLiveData<MutableList<Boards>>()
        repo.getBoardById(id).observeForever{
            mutableData.value = it
        }

        return mutableData
    }
    fun saveBoardData(name: String, amount: Double, current: Double){

        repo.saveData(name, amount, current)
    }

    fun updateBoard(board: ArrayList<Boards>) {
        repo.updateBoard(board)
    }

}