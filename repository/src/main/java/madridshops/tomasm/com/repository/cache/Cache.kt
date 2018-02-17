package madridshops.tomasm.com.repository.cache

import madridshops.tomasm.com.repository.db.model.ShopEntity

internal interface Cache {
    fun getAllShops(success: (shops: List<ShopEntity>) -> Unit, error: (errorMessage:String) -> Unit)
    fun saveAllShops(shops: List<ShopEntity>, success: () -> Unit, error: (errorMessage: String) -> Unit)
    fun deleteAllShops(success: () -> Unit, error: (errorMessage: String) -> Unit)
}
