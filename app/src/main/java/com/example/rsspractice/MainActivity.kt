package com.example.rsspractice

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    var topQuestions = mutableListOf<Question>()
    lateinit var rvmain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvmain=findViewById(R.id.rvMain)
        FetchRecentQuestions().execute()
    }

    private inner class FetchRecentQuestions : AsyncTask<Void, Void, MutableList<Question>>() {
        val parser = XMLParser()
        override fun doInBackground(vararg params: Void?): MutableList<Question> {
            val url = URL("https://stackoverflow.com/feeds")
            val urlConnection = url.openConnection() as HttpURLConnection
            topQuestions =
                urlConnection.getInputStream()?.let {
                    parser.parse(it)
                }
                        as MutableList<Question>
            return topQuestions
        }

        override fun onPostExecute(result: MutableList<Question>?) {
            super.onPostExecute(result)
            val adapter =RVAdapter(topQuestions)
            rvmain.adapter = adapter
            rvmain.layoutManager = LinearLayoutManager(this@MainActivity)
        }

    }
}