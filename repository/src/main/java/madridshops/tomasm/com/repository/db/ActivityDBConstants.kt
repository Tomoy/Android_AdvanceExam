package madridACTIVITYs.tomasm.com.repository.db

object ActivityDBConstants {

    val TABLE_ACTIVITY = "ACTIVITY"

    // Table field constants
    val KEY_ACTIVITY_DATABASE_ID = "_id"
    val KEY_ACTIVITY_ID_JSON = "ID_JSON"
    val KEY_ACTIVITY_NAME = "NAME"
    val KEY_ACTIVITY_IMAGE_URL = "IMAGE_URL"
    val KEY_ACTIVITY_LOGO_IMAGE_URL = "LOGO_IMAGE_URL"

    val KEY_ACTIVITY_ADDRESS = "ADDRESS"
    val KEY_ACTIVITY_URL = "URL"
    val KEY_ACTIVITY_DESCRIPTION = "DESCRIPTION"

    val KEY_ACTIVITY_LATITUDE = "LATITUDE"
    val KEY_ACTIVITY_LONGITUDE = "LONGITUDE"

    val KEY_ACTIVITY_OPENING_HOURS = "OPENING_HOURS"

    val ALL_COLUMNS = arrayOf(
            KEY_ACTIVITY_DATABASE_ID,
            KEY_ACTIVITY_ID_JSON,
            KEY_ACTIVITY_NAME, KEY_ACTIVITY_IMAGE_URL,
            KEY_ACTIVITY_LOGO_IMAGE_URL, KEY_ACTIVITY_ADDRESS,
            KEY_ACTIVITY_URL, KEY_ACTIVITY_DESCRIPTION,
            KEY_ACTIVITY_LATITUDE, KEY_ACTIVITY_LONGITUDE,
            KEY_ACTIVITY_OPENING_HOURS)

    val SQL_SCRIPT_CREATE_ACTIVITY_TABLE = (
            "create table " + TABLE_ACTIVITY
                    + "( "
                    + KEY_ACTIVITY_DATABASE_ID + " integer primary key autoincrement, "
                    + KEY_ACTIVITY_ID_JSON + " integer, "
                    + KEY_ACTIVITY_NAME + " text not null,"
                    + KEY_ACTIVITY_IMAGE_URL + " text, "
                    + KEY_ACTIVITY_LOGO_IMAGE_URL + " text, "
                    + KEY_ACTIVITY_ADDRESS + " text,"
                    + KEY_ACTIVITY_URL + " text,"
                    + KEY_ACTIVITY_LATITUDE + " real,"
                    + KEY_ACTIVITY_LONGITUDE + " real, "
                    + KEY_ACTIVITY_DESCRIPTION + " text, "
                    + KEY_ACTIVITY_OPENING_HOURS + " text "
                    + ");")

    val DROP_DATABASE_SCRIPTS = ""

    val CREATE_DATABASE_SCRIPTS = arrayOf(SQL_SCRIPT_CREATE_ACTIVITY_TABLE)
}
