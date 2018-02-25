package madridshops.tomasm.com.domain.model

import java.io.Serializable

data class Shop(val id: Int, override val name: String, override val address: String,
                override val description: String, override val lat: Double, override val lon: Double, val imgUrl:String,
                val logoImgUrl: String, val openingHours: String) : Item

data class Shops(var shops: MutableList<Shop>): AggregateInterface<Shop>, Serializable {

    override fun count(): Int {
        return shops.size
    }

    override fun returnAll(): List<Shop> {
        return shops
    }

    override fun get(position: Int): Shop {
        return shops.get(position)
    }

    override fun add(element: Shop) {
        shops.add(element)
    }

    override fun delete(position: Int) {
        shops.removeAt(position)
    }

    override fun delete(element: Shop) {
        shops.remove(element)
    }

}