package madridshops.tomasm.com.domain.model

import java.io.Serializable

data class Shop(val id: Int, val name: String, val address: String,
                val description: String, val lat: Double, val lon: Double, val imgUrl:String,
                val logoImgUrl: String, val openingHours: String) : Serializable

data class Shops(var shops: MutableList<Shop>): AggregateInterface<Shop> {

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