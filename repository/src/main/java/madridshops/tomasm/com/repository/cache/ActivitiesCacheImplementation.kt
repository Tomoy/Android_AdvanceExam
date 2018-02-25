package madridshops.tomasm.com.repository.cache

import android.content.Context
import madridshops.tomasm.com.repository.db.DBHelper
import madridshops.tomasm.com.repository.db.build
import madridshops.tomasm.com.repository.db.dao.ActivityDAO
import madridshops.tomasm.com.repository.db.dao.ShopDAO
import madridshops.tomasm.com.repository.db.model.ActivityEntity
import madridshops.tomasm.com.repository.thread.DispatchOnMainThread
import java.lang.ref.WeakReference

internal class ActivitiesCacheImplementation(context:Context): Cache<ActivityEntity> {

    val context = WeakReference<Context>(context)

    override fun getAll(success: (items: List<ActivityEntity>) -> Unit, error: (errorMessage: String) -> Unit) {
        //Hacerlo en segundo plano (background thread) porque va a tardar
        Thread(Runnable {
            val activitiesList = ActivityDAO(cacheDBHelper()).query()

            //Estas llamadas tienen que volver por el hilo principal, uso mi clase auxiliar para ejecutar el código en el MainThread
            DispatchOnMainThread(Runnable {
                if (activitiesList.count() > 0) {
                    success(activitiesList)
                } else {
                    error("No Shops available")
                }
            })

        }).run()
    }

    override fun saveAll(items: List<ActivityEntity>, success: () -> Unit, error: (errorMessage: String) -> Unit) {
        Thread(Runnable {

            try {
                items.forEach { ActivityDAO(cacheDBHelper()).insert(it) }
                DispatchOnMainThread(Runnable {
                    success()
                })
            } catch (e: Exception) {

                DispatchOnMainThread(Runnable {
                    error("Error saving shops: " + e.message)
                })
            }


        }).run()
    }

    override fun deleteAll(success: () -> Unit, error: (errorMessage: String) -> Unit) {

        Thread(Runnable {
            val deletionSuccess = ShopDAO(cacheDBHelper()).deleteAll()

            DispatchOnMainThread(Runnable {
                if (deletionSuccess) {
                    success()
                } else {
                    error("Error deleting")
                }
            })

        }).run()
    }

    private fun cacheDBHelper(): DBHelper {
        return build(context.get()!!, "MadridShops.sqlite", 1)
    }
}