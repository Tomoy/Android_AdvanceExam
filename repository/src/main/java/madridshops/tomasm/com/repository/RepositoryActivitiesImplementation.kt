package madridshops.tomasm.com.repository

import android.content.Context
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import madridshops.tomasm.com.repository.cache.ActivitiesCacheImplementation
import madridshops.tomasm.com.repository.cache.Cache
import madridshops.tomasm.com.repository.db.model.ActivityEntity
import madridshops.tomasm.com.repository.db.model.ActivityResponseEntity
import madridshops.tomasm.com.repository.network.NetworkingInterface
import madridshops.tomasm.com.repository.network.NetworkingVolley
import madridshops.tomasm.com.repository.network.json.JSONEntitiesParser
import java.lang.ref.WeakReference

class RepositoryActivitiesImplementation(context: Context): Repository<ActivityEntity> {

    override fun getAll(success: (items: List<ActivityEntity>) -> Unit, error: (errorMessage: String) -> Unit) {
        //read all shops from cache
        cache.getAll(
                success = {
                    //If there are shops in cache -> return them
                    success(it)
                }, error = {
            //if there are no shops in cache -> Network request
            populateCache(success, error)
        })    }

    override fun deleteAll(success: () -> Unit, error: (errorMessage: String) -> Unit) {
        cache.deleteAll(success, error)
    }

    private val weakContext = WeakReference<Context>(context)
    private val cache: Cache<ActivityEntity> = ActivitiesCacheImplementation(weakContext.get()!!)

    private fun populateCache(success: (shops: List<ActivityEntity>) -> Unit, error: (errorMessage: String) -> Unit) {
        //Perform network request
        val jsonManager: NetworkingInterface = NetworkingVolley(weakContext.get()!!)
        jsonManager.execute(BuildConfig.MADRID_ACTIVITIES_SERVER_URL, success = object: SuccessCompletion<String> {
            override fun successCompletion(e: String) {
                //If request is successful, parse the string to Shops entity
                val parser = JSONEntitiesParser()
                var responseEntity: ActivityResponseEntity

                try {
                    responseEntity = parser.parse<ActivityResponseEntity>(e)
                } catch (e: InvalidFormatException) {
                    error("Error parsing JSON")
                    return
                }

                //Store result in cache
                cache.saveAll(responseEntity.result, success = {
                    success(responseEntity.result)
                }, error = {
                    error("There was a problem while saving the activities")
                })

            }

        }, error = object: ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {
            }

        })


    }
}