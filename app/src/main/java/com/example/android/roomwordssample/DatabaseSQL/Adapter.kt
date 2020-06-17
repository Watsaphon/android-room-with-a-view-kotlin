package com.example.android.roomwordssample.DatabaseSQL

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.lo_words.view.*
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roomwordssample.MainActivity
import com.example.android.roomwordssample.R

class WordAdapter(mCtx : Context , val words :ArrayList<Word>) : RecyclerView.Adapter<WordAdapter.ViewHolder>() {

    val mCtx = mCtx

    //กำหนดค่า layout ที่ต้องารแสดง เช่น ScanResult
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var scanword = itemView.txtscan_resulut
        val updatebutton = itemView.btnupdate
        val deletebutton = itemView.btndelete

    }



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): WordAdapter.ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.lo_words, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return words.size
    }

    override fun onBindViewHolder(p0: WordAdapter.ViewHolder, p1: Int) {
        val word : Word = words[p1]
        p0.scanword.text = word.wordName

        p0.deletebutton.setOnClickListener {
            val word_delete = word.wordName


            var alertDialog = AlertDialog.Builder(mCtx)
                    .setTitle("Warning")
                    .setMessage("Are you sure to Delete : $word_delete ?")
                    .setPositiveButton("Yes",DialogInterface.OnClickListener{dialog, which ->
                        if (MainActivity.dbHandler.deleteWord(word.wordID)){
                            words.removeAt(p1)
                            notifyItemRemoved(p1)
                            notifyItemRangeChanged(p1,words.size)
                        Toast.makeText(mCtx," Word $word_delete Deleted " ,Toast.LENGTH_SHORT).show()
                        }
                        else
                        {
                         Toast.makeText(mCtx,"Error Deleting",Toast.LENGTH_SHORT).show()
                        }
                    })
                    .setNegativeButton("No",DialogInterface.OnClickListener { dialog, which ->  })
                    .setIcon(R.drawable.ic_baseline_warning_24)
                    .show()

            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(Color.WHITE)
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN)
//           alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setPadding(20)
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(Color.WHITE)
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED)
        }
    }
}