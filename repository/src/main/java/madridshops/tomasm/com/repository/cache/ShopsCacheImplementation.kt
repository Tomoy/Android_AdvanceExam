package madridshops.tomasm.com.repository.cache

import android.content.Context
import madridshops.tomasm.com.repository.db.DBHelper
import madridshops.tomasm.com.repository.db.build
import madridshops.tomasm.com.repository.db.dao.ShopDAO
import madridshops.tomasm.com.repository.db.model.ShopEntity
import madridshops.tomasm.com.repository.thread.DispatchOnMainThread
import java.lang.ref.WeakReference

internal class ShopsCacheImplementation(context:Context): Cache<ShopEntity> {

    val context = WeakReference<Context>(context)

    override fun getAll(success: (items: List<ShopEntity>) -> Unit, error: (errorMessage: String) -> Unit) {
        //Hacerlo en segundo plano (background thread) porque va a tardar
        Thread(Runnable {
            val shopsList = ShopDAO(cacheDBHelper()).query()

            //Estas llamadas tienen que volver por el hilo principal, uso mi clase auxiliar para ejecutar el código en el MainThread
            DispatchOnMainThread(Runnable {
                if (shopsList.count() > 0) {
                    success(shopsList)
                } else {
                    error("No Shops available")
                }
            })

        }).run()
    }

    override fun saveAll(items: List<ShopEntity>, success: () -> Unit, error: (errorMessage: String) -> Unit) {
        //Hacerlo en segundo plano (background thread) porque va a tardar
        Thread(Runnable {

            try {
                items.forEach { ShopDAO(cacheDBHelper()).insert(it) }
                //Estas llamadas tienen que volver por el hilo principal, uso mi clase auxiliar para ejecutar el código en el MainThread
                DispatchOnMainThread(Runnable {
                    success()
                })
            } catch (e: Exception) {

                //Estas llamadas tienen que volver por el hilo principal, uso mi clase auxiliar para ejecutar el código en el MainThread
                DispatchOnMainThread(Runnable {
                    error("Error saving shops: " + e.message)
                })
            }


        }).run()
    }

    override fun deleteAll(success: () -> Unit, error: (errorMessage: String) -> Unit) {
        //En este caso se le dice a la db que elimine todos los shops pero la implementación podría ser otra, local o archivo etc
        //Hacerlo en segundo plano (background thread) porque va a tardar
        Thread(Runnable {
            val deletionSuccess = ShopDAO(cacheDBHelper()).deleteAll()

            //Estas llamadas tienen que volver por el hilo principal, uso mi clase auxiliar para ejecutar el código en el MainThread
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