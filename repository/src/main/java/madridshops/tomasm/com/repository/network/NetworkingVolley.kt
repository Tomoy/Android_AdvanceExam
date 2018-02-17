package madridshops.tomasm.com.repository.network

import android.content.Context
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import madridshops.tomasm.com.repository.ErrorCompletion
import madridshops.tomasm.com.repository.SuccessCompletion
import java.lang.ref.WeakReference


internal class NetworkingVolley(context:Context): NetworkingInterface {

    var weakContext:WeakReference<Context> = WeakReference(context) //Siempre mejor inyectar el contexto como dependencia y no accederlo como Singleton porque dependemos de esa clase en particular sino
    var requestQueue: RequestQueue? = null

    override fun execute(url: String, success: SuccessCompletion<String>, error: ErrorCompletion) {

        //create request (success, failure)
        val request = StringRequest(url, Response.Listener { //String Request Porque voy a obtener un JSON
            Log.d("JSON", it)
            success.successCompletion(it)
        }, Response.ErrorListener {
            Log.d("ERROR", it.localizedMessage)
            error.errorCompletion(it.localizedMessage)
        })

        //add request to queue
        getARequestQueue().add(request)
    }

    fun getARequestQueue(): RequestQueue {

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(weakContext.get())
        }

        return requestQueue!!
    }

}