package madridshops.tomasm.com.repository

import madridshops.tomasm.com.repository.db.model.ActivityEntity
import madridshops.tomasm.com.repository.db.model.ShopEntity

interface Repository<T> {
    fun getAll(success: (items: List<T>) -> Unit, error: (errorMessage:String) -> Unit)
    fun deleteAll(success: () -> Unit, error: (errorMessage: String) -> Unit)
}