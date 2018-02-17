package madridshops.tomasm.com.repository.db.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import madridshops.tomasm.com.repository.db.DBConstants
import madridshops.tomasm.com.repository.db.DBHelper
import madridshops.tomasm.com.repository.db.convert
import madridshops.tomasm.com.repository.db.model.ShopEntity

internal class ShopDAO
(
        val dbHelper: DBHelper  //Constructor que me permite que me inyecten un dbHelper que es lo que necesito.
) : DAOPersistable<ShopEntity> {

    //Accesos a la base de datos en modo solo lectura y en modo lectura/scritura
    private val dbReadOnlyConnection: SQLiteDatabase = dbHelper.readableDatabase
    private val dbReadWriteConnection: SQLiteDatabase = dbHelper.writableDatabase

    override fun insert(shopEntity: ShopEntity): Long {
        var id: Long = -1

        id = dbReadWriteConnection.insert(DBConstants.TABLE_SHOP, null, getContentValuesFrom(shopEntity))

        return id
    }

    //Convierte la entidad shop en un ContentValues (Diccionario Key -> Value que necesita el insert)
    fun getContentValuesFrom(shopEntity: ShopEntity): ContentValues {
        val content = ContentValues()

        content.put(DBConstants.KEY_SHOP_ID_JSON, shopEntity.id)
        content.put(DBConstants.KEY_SHOP_NAME, shopEntity.name)
        content.put(DBConstants.KEY_SHOP_DESCRIPTION, shopEntity.description)
        content.put(DBConstants.KEY_SHOP_LATITUDE, shopEntity.latitude)
        content.put(DBConstants.KEY_SHOP_LONGITUDE, shopEntity.longitude)
        content.put(DBConstants.KEY_SHOP_IMAGE_URL, shopEntity.img)
        content.put(DBConstants.KEY_SHOP_LOGO_IMAGE_URL, shopEntity.logo_img)
        content.put(DBConstants.KEY_SHOP_ADDRESS, shopEntity.address)
        content.put(DBConstants.KEY_SHOP_OPENING_HOURS, shopEntity.opening_hours_en)

        return content
    }

    override fun update(id: Long, shopEntity: ShopEntity): Long {

        val numberOfRecordsUpdated = dbReadWriteConnection.update(DBConstants.TABLE_SHOP,
                 getContentValuesFrom(shopEntity) ,
                 DBConstants.KEY_SHOP_DATABASE_ID + " = ?",
                 arrayOf(id.toString()))   //Que actualice la tabla shop donde el id sea este y los valores a actualizar los del content value
         return numberOfRecordsUpdated.toLong()
    }

    override fun delete(element: ShopEntity): Long {
        return delete(element.databaseId)
    }

    override fun delete(id: Long): Long {

        return dbReadWriteConnection.delete(
                DBConstants.TABLE_SHOP,
                DBConstants.KEY_SHOP_DATABASE_ID + " = ?",
                arrayOf(id.toString())).toLong()
    }

    override fun deleteAll(): Boolean {
        return dbReadWriteConnection.delete(
                DBConstants.TABLE_SHOP,
                null,
                null
        ).toLong() >= 0  //Si estaba vacia o habia mas de un registro, ha ido bien
    }

    override fun query(id: Long): ShopEntity {
        val cursor = queryCursor(id)

        cursor.moveToFirst() //Porque arranca en el before first y no tiene elemento ahi

        return getEntityFromCursor(cursor)!!
    }

    override fun query(): List<ShopEntity> {
        val queryResult = ArrayList<ShopEntity>()

        val cursor = dbReadOnlyConnection.query(
                DBConstants.TABLE_SHOP,
                DBConstants.ALL_COLUMNS,
                null,
                null,
                "",
                "",
                DBConstants.KEY_SHOP_DATABASE_ID)

        while (cursor.moveToNext()) {
            queryResult.add(getEntityFromCursor(cursor)!!)
        }

        return queryResult
    }

    fun getEntityFromCursor(cursor: Cursor) : ShopEntity? {

        if (cursor.isAfterLast || cursor.isBeforeFirst) {
            return null
        }

        return ShopEntity(
                cursor.getLong(cursor.getColumnIndex(DBConstants.KEY_SHOP_ID_JSON)),
                cursor.getLong(cursor.getColumnIndex(DBConstants.KEY_SHOP_DATABASE_ID)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_NAME)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_LATITUDE)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_LONGITUDE)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_IMAGE_URL)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_LOGO_IMAGE_URL)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_OPENING_HOURS)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_ADDRESS))
        )
    }

    override fun queryCursor(id: Long): Cursor {

        val cursor = dbReadOnlyConnection.query(
                DBConstants.TABLE_SHOP,
                DBConstants.ALL_COLUMNS,
                DBConstants.KEY_SHOP_DATABASE_ID + " = ?",
                arrayOf(id.toString()),
                "",
                "",
                DBConstants.KEY_SHOP_DATABASE_ID //Order
        )

        return cursor
    }

}