package com.example.shrinkitreaddata
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    //private var userModalArrayList: ArrayList<User>? = null
    //private var userRVAdapter: RvAdapter? = null
    //private var userRV: RecyclerView? = null
    lateinit var recyclerView:RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var recyclerAdapter: RvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getDataFromAPI()
    }
    private fun getDataFromAPI(){
        recyclerView= findViewById(R.id.idRVUsers)
        layoutManager= LinearLayoutManager(this)
        var userModalArrayList = arrayListOf<User>()
        var url = "https://script.google.com/macros/s/AKfycbyGVJB0oSn8UogxjHIQAsO-ldPmqBhiVdoDhKzf4GVQlEZS0F0oS_bqKehoeFdd7fGTtw/exec?action=get"
        val queue=Volley.newRequestQueue(this)
        val jsonObjectRequest=object :JsonObjectRequest(
            Request.Method.GET,url,null,
            Response.Listener {
                //readProgressLayout.visibility=View.GONE
                //readProgressBar.visibility=View.GONE
                val data=it.getJSONArray("items")
                for(i in 0 until 5){
                    val userJsonObject=data.getJSONObject(i)
                    val userObject=User(
                        //userJsonObject.getString("id"),
                        userJsonObject.getString("name"),
                        userJsonObject.getString("teamName"),
                        userJsonObject.getString("steps")
                    )
                    userModalArrayList.add(userObject)
                }
                recyclerAdapter= RvAdapter(this,userModalArrayList)
                recyclerView.adapter=recyclerAdapter
                recyclerView.layoutManager=layoutManager
            },Response.ErrorListener {
                //readProgressLayout.visibility=View.GONE
                //readProgressBar.visibility=View.GONE
                Toast.makeText(this@MainActivity, it.toString(), Toast.LENGTH_SHORT).show()
            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                return super.getHeaders()
            }
        }
        queue.add(jsonObjectRequest)


    }

}