package madridshops.tomasm.com.repository.db.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import madridACTIVITYs.tomasm.com.repository.db.ActivityDBConstants
import madridshops.tomasm.com.repository.db.ShopDBConstants
import madridshops.tomasm.com.repository.db.DBHelper
import madridshops.tomasm.com.repository.db.model.ActivityEntity
import madridshops.tomasm.com.repository.db.model.ShopEntity

internal class ActivityDAO
(
        val dbHelper: DBHelper
) : DAOPersistable<ActivityEntity> {

    //Accesos a la base de datos en modo solo lectura y en modo lectura/scritura
    private val dbReadOnlyConnection: SQLiteDatabase = dbHelper.readableDatabase
    private val dbReadWriteConnection: SQLiteDatabase = dbHelper.writableDatabase

    override fun insert(activityEntity: ActivityEntity): Long {
        var id: Long = -1

        id = dbReadWriteConnection.insert(ActivityDBConstants.TABLE_ACTIVITY, null, getContentValuesFrom(activityEntity))

        return id
    }

    //Convierte la entidad shop en un ContentValues (Diccionario Key -> Value que necesita el insert)
    fun getContentValuesFrom(activityEntity: ActivityEntity): ContentValues {
        val content = ContentValues()

        content.put(ActivityDBConstants.KEY_ACTIVITY_ID_JSON, activityEntity.id)
        content.put(ActivityDBConstants.KEY_ACTIVITY_NAME, activityEntity.name)
        content.put(ActivityDBConstants.KEY_ACTIVITY_DESCRIPTION, activityEntity.description)
        content.put(ActivityDBConstants.KEY_ACTIVITY_LATITUDE, activityEntity.latitude)
        content.put(ActivityDBConstants.KEY_ACTIVITY_LONGITUDE, activityEntity.longitude)
        content.put(ActivityDBConstants.KEY_ACTIVITY_IMAGE_URL, activityEntity.img)
        content.put(ActivityDBConstants.KEY_ACTIVITY_LOGO_IMAGE_URL, activityEntity.logo_img)
        content.put(ActivityDBConstants.KEY_ACTIVITY_ADDRESS, activityEntity.address)
        content.put(ActivityDBConstants.KEY_ACTIVITY_OPENING_HOURS, activityEntity.opening_hours_en)

        return content
    }

    override fun update(id: Long, activityEntity: ActivityEntity): Long {

        val numberOfRecordsUpdated = dbReadWriteConnection.update(ShopDBConstants.TABLE_SHOP,
                getContentValuesFrom(activityEntity) ,
                ActivityDBConstants.KEY_ACTIVITY_DATABASE_ID + " = ?",
                arrayOf(id.toString()))
        return numberOfRecordsUpdated.toLong()
    }

    override fun delete(element: ActivityEntity): Long {
        return delete(element.databaseId)
    }

    override fun delete(id: Long): Long {

        return dbReadWriteConnection.delete(
                ActivityDBConstants.TABLE_ACTIVITY,
                ActivityDBConstants.KEY_ACTIVITY_DATABASE_ID + " = ?",
                arrayOf(id.toString())).toLong()
    }

    override fun deleteAll(): Boolean {
        return dbReadWriteConnection.delete(
                ActivityDBConstants.TABLE_ACTIVITY,
                null,
                null
        ).toLong() >= 0
    }

    override fun query(id: Long): ActivityEntity {
        val cursor = queryCursor(id)

        cursor.moveToFirst()

        return getEntityFromCursor(cursor)!!
    }

    override fun query(): List<ActivityEntity> {
        val queryResult = ArrayList<ActivityEntity>()

        val cursor = dbReadOnlyConnection.query(
                ActivityDBConstants.TABLE_ACTIVITY,
                ActivityDBConstants.ALL_COLUMNS,
                null,
                null,
                "",
                "",
                ActivityDBConstants.KEY_ACTIVITY_DATABASE_ID)

        while (cursor.moveToNext()) {
            queryResult.add(getEntityFromCursor(cursor)!!)
        }

        return queryResult
    }

    fun getEntityFromCursor(cursor: Cursor) : ActivityEntity? {

        if (cursor.isAfterLast || cursor.isBeforeFirst) {
            return null
        }

        return ActivityEntity(
                cursor.getLong(cursor.getColumnIndex(ActivityDBConstants.KEY_ACTIVITY_ID_JSON)),
                cursor.getLong(cursor.getColumnIndex(ActivityDBConstants.KEY_ACTIVITY_DATABASE_ID)),
                cursor.getString(cursor.getColumnIndex(ActivityDBConstants.KEY_ACTIVITY_NAME)),
                cursor.getString(cursor.getColumnIndex(ActivityDBConstants.KEY_ACTIVITY_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(ActivityDBConstants.KEY_ACTIVITY_LATITUDE)),
                cursor.getString(cursor.getColumnIndex(ActivityDBConstants.KEY_ACTIVITY_LONGITUDE)),
                cursor.getString(cursor.getColumnIndex(ActivityDBConstants.KEY_ACTIVITY_IMAGE_URL)),
                cursor.getString(cursor.getColumnIndex(ActivityDBConstants.KEY_ACTIVITY_LOGO_IMAGE_URL)),
                cursor.getString(cursor.getColumnIndex(ActivityDBConstants.KEY_ACTIVITY_OPENING_HOURS)),
                cursor.getString(cursor.getColumnIndex(ActivityDBConstants.KEY_ACTIVITY_ADDRESS))
        )
    }

    override fun queryCursor(id: Long): Cursor {

        val cursor = dbReadOnlyConnection.query(
                ActivityDBConstants.TABLE_ACTIVITY,
                ActivityDBConstants.ALL_COLUMNS,
                ActivityDBConstants.KEY_ACTIVITY_DATABASE_ID + " = ?",
                arrayOf(id.toString()),
                "",
                "",
                ActivityDBConstants.KEY_ACTIVITY_DATABASE_ID
        )

        return cursor
    }

}