package madridshops.tomasm.com.domain.interactor.getAll

import android.content.Context
import android.util.Log
import madridshops.tomasm.com.domain.interactor.ErrorCompletion
import madridshops.tomasm.com.domain.interactor.SuccessCompletion
import madridshops.tomasm.com.domain.model.Activities
import madridshops.tomasm.com.domain.model.Activity
import madridshops.tomasm.com.repository.Repository
import madridshops.tomasm.com.repository.RepositoryActivitiesImplementation
import madridshops.tomasm.com.repository.db.model.ActivityEntity
import java.lang.ref.WeakReference

class GetAllActivitiesInteractorImplementation(context: Context) : GetAllInteractor<Activities> {

    private val weakContext = WeakReference<Context>(context)
    private val repository: Repository<ActivityEntity> = RepositoryActivitiesImplementation(weakContext.get()!!)

    override fun execute(success: SuccessCompletion<Activities>, error: ErrorCompletion) {
        repository.getAll(success = {
            val activities: Activities = mapEntityToActivities(it)
            success.successCompletion(activities)
        }, error = {
            error(it)
        })
    }

    private fun mapEntityToActivities(list: List<ActivityEntity>): Activities {

        val tempList = ArrayList<Activity>()
        list.forEach {

            var activityLat:Double
            var activityLong:Double

            try {

                activityLat = it.latitude.toDouble()
                activityLong = it.longitude.toDouble()

                val activity = Activity(
                        it.id.toInt(),
                        it.name,
                        it.address,
                        it.description,
                        activityLat,
                        activityLong,
                        it.img,
                        it.logo_img,
                        it.opening_hours_en
                )
                tempList.add(activity)

            }catch(e:NumberFormatException) {
                Log.d("MapError", "There was an error mapping activity long or lat, ignoring it")
            }

        }

        return Activities(tempList)
    }
}


