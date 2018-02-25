package madridshops.tomasm.com.domain.model

import java.io.Serializable

data class Activity(val id: Int, override val name: String, override val address: String,
                    override val description: String, override val lat: Double,
                    override val lon: Double, val imgUrl: String,
                    val logoImgUrl: String, val openingHours: String): Item

data class Activities(var activities: MutableList<Activity>) : AggregateInterface<Activity>, Serializable {

    override fun count(): Int {
        return activities.size
    }

    override fun returnAll(): List<Activity> {
        return activities
    }

    override fun get(position: Int): Activity {
        return activities.get(position)
    }

    override fun add(element: Activity) {
        activities.add(element)
    }

    override fun delete(position: Int) {
        activities.removeAt(position)
    }

    override fun delete(element: Activity) {
        activities.remove(element)
    }
}
