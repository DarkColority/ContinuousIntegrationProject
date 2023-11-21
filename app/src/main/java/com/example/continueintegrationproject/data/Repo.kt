package com.example.continueintegrationproject.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.continueintegrationproject.model.Boards
import com.example.continueintegrationproject.view.BoardsActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList

class Repo {
    val db = FirebaseFirestore.getInstance()

    fun getBoardData(): LiveData<MutableList<Boards>>{
        val mutableData = MutableLiveData<MutableList<Boards>>()
        db.collection("Boards").orderBy("name").get().addOnSuccessListener {result ->

            val listData = mutableListOf<Boards>()
            for(document in result){
                val id = document.id
                val name = document.getString("name")
                val amount = document.getDouble("amount")?.toFloat()
                val current = document.getDouble("current")?.toFloat()
                val board = Boards(id, name!!, amount!!, current!!)
                listData.add(board)
            }

            mutableData.value = listData
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        return mutableData
    }
    fun getBoardById(id: String): LiveData<MutableList<Boards>>{

        val mutableData = MutableLiveData<MutableList<Boards>>()
        db.collection("Boards").document(id).get().addOnSuccessListener {result ->

            val listData = mutableListOf<Boards>()
            val name = result.getString("name")
            val amount = result.getDouble("amount")?.toFloat()
            val current = result.getDouble("current")?.toFloat()
            val board = Boards(id, name!!, amount!!, current!!)
            listData.add(board)


            mutableData.value = listData
        }
        return mutableData
    }

    fun saveData(name: String, amount: Double, current: Double): String{
        var id = ""
        val dispatch = HashMap<String, Any>()


        dispatch.put("name", name)
        dispatch.put("amount", amount)
        dispatch.put("current", current)


        db.collection("Boards")
            .add(dispatch)
            .addOnSuccessListener(object: OnSuccessListener<DocumentReference> {
                override fun onSuccess(documentReference:DocumentReference) {
                    id = documentReference.id
                    BoardsActivity.boardId = documentReference.id
                }
            })
            .addOnFailureListener(object: OnFailureListener {
                override fun onFailure(@NonNull e:Exception) {
                    Log.w("error", "Error adding document", e)
                }
            })
        return id
    }


    fun updateBoard(board: ArrayList<Boards>){


        val boards = db.collection("Boards")

        val data1 = hashMapOf(
            "name" to board[0].name,
            "amount" to board[0].total,
            "current" to board[0].current
        )
        boards.document(board[0].id!!).set(data1)



            /*val dispatch = HashMap<String, Any>()

            val id =  board[0].id!!
            dispatch["name"] = board[0].name
            dispatch["amount"] = board[0].total
            dispatch["current"] = board[0].current
            dispatch["objectives"] = board[0].objectives

            db.collection("Boards").document(id)
                .set(dispatch)*/
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        // [END set_document]
    }

    fun deleteBoard(boardId: String){
        db.collection("Boards").document(boardId)
            .delete()
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }
}