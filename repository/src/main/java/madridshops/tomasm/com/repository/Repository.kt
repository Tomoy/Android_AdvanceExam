package madridshops.tomasm.com.repository

import madridshops.tomasm.com.repository.db.model.ShopEntity

interface Repository {
    fun getAllShops(success: (shops: List<ShopEntity>) -> Unit, error: (errorMessage:String) -> Unit)
    fun deleteAllShops(success: () -> Unit, error: (errorMessage: String) -> Unit)
}