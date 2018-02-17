package madridshops.tomasm.com.repository.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//Internal porque todas estas clases o funciones, se acceden solo internamente en el módulo
internal fun build(context: Context, name: String, version: Int): DBHelper {
    return DBHelper(context, name, null, version)
}

internal class DBHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int)
    : SQLiteOpenHelper(context, name, factory, version) {

    //Recibo una conexión a la database en db
    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)

        //Configuro la db para que se pueda borrar en cascada
        db?.setForeignKeyConstraintsEnabled(true)

    }

    //Recibo una conexión a la database en db
    override fun onCreate(db: SQLiteDatabase?) {
        //Le digo a la db que ejecuto el código SQL de cada elemento en el array CREATE_DATABASE_SCRIPTS
        DBConstants.CREATE_DATABASE_SCRIPTS.forEach { db?.execSQL(it) }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Lo que tendríamos que hacer en el caso de cambiar la version de la db
        val updateChangesFrom1Tov2 = {

        }

        if (oldVersion == 1 && newVersion == 2) {
            updateChangesFrom1Tov2()
        }
    }
}

//Helpers

fun convert(boolean: Boolean) : Int {
    if (boolean) {
        return 1
    }
    return 0
}

fun convert(int: Int): Boolean {
    if (int == 0) {
        return false
    }
    return true
}