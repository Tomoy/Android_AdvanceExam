package madridshops.tomasm.com.domain.model

data class Shop(val id: Int, val name: String, val address: String)

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