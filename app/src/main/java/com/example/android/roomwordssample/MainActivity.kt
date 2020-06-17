/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.roomwordssample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.example.android.roomwordssample.Database.Word
//import com.example.android.roomwordssample.Database.WordViewModel
import com.example.android.roomwordssample.DatabaseSQL.DBHandler
import com.example.android.roomwordssample.DatabaseSQL.WordAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var dbHandler: DBHandler
    }


    private val newWordActivityRequestCode = 1
//    private lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



//อันเก่า
//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
//        val adapter = WordListAdapter(this)
//
//     recyclerView.adapter = adapter
//       recyclerView.layoutManager = LinearLayoutManager(this)

        // Get a new or existing ViewModel from the ViewModelProvider.
//        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
//        wordViewModel.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
//            words?.let { adapter.setWords(it) }
//        })
//
//
//
//        val btn = findViewById<Button>(R.id.scanbutton)
//        btn.setOnClickListener {
//            val intent = Intent(this@MainActivity,ScanActivity::class.java)
//            startActivityForResult(intent,newWordActivityRequestCode)
//        }


        //start part database

        dbHandler = DBHandler(this,null,null,1)
        viewWord()

        fab.setOnClickListener {
            val i = Intent(this,ScanActivity::class.java)
            startActivity(i)
        }



    }

    //recycleview is id from activity_main

    private fun viewWord(){
      val wordslist = dbHandler.getWords(this)
        val adapter = WordAdapter(this,wordslist)
        val rv : RecyclerView = findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this,LinearLayout.VERTICAL,false) as RecyclerView.LayoutManager
        rv.adapter = adapter
    }


    override fun onResume() {
        viewWord()
        super.onResume()
    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
//        super.onActivityResult(requestCode, resultCode, intentData)
//
//        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
//            intentData?.let { data ->
//                val word = com.example.android.roomwordssample.DatabaseSQL.Word(data.getStringExtra(ScanActivity.EXTRA_REPLY))
//                wordViewModel.insert(word)
//                Unit
//            }
//        }
//        else {
//
//            Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_LONG).show()
//
//        }
//    }


}


