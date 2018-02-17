package madridshops.tomasm.com.repository.db.dao

import android.database.Cursor

internal interface DAOWriteOperations<T> {
    fun insert(element: T) : Long
    fun update(id: Long, element: T): Long
    fun delete(element: T): Long
    fun delete(id: Long): Long
    fun deleteAll(): Boolean
}

internal interface DAOReadOperations<T> {
    fun query(id: Long): T
    fun query(): List<T>
    fun queryCursor(id: Long): Cursor
}

//Interfaz que define la comunicación entre una clase y la tabla correspondiente <T> sería Shop por ejemplo
internal interface DAOPersistable<T>: DAOReadOperations<T>, DAOWriteOperations<T>