package madridshops.tomasm.com.repository.cache

import madridshops.tomasm.com.repository.db.model.ShopEntity

internal interface Cache<T> {
    fun getAll(success: (items: List<T>) -> Unit, error: (errorMessage:String) -> Unit)
    fun saveAll(items: List<T>, success: () -> Unit, error: (errorMessage: String) -> Unit)
    fun deleteAll(success: () -> Unit, error: (errorMessage: String) -> Unit)
}
