package DB

import Models.Guruh
import Models.Kurs
import Models.Mentor
import Models.Talaba
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDbHelper(context: Context?) :
    SQLiteOpenHelper(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION),
    DbServiceInterface {
    override fun onCreate(db: SQLiteDatabase?) {
        val mentorQuery =
            "CREATE TABLE ${Constant.MENTOR_TABLE} (${Constant.MENTOR_ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, ${Constant.MENTOR_FAMILIYASI} TEXT NOT NULL, ${Constant.MENTOR_ISMI} TEXT NOT NULL, ${Constant.MENTOR_SHARIFI} TEXT NOT NULL, ${Constant.MENTOR_KURS_ID} TEXT NOT NULL, FOREIGN KEY(${Constant.MENTOR_KURS_ID}) REFERENCES ${Constant.KURSLAR_TABLE} (${Constant.KURS_ID}));"
        db?.execSQL(mentorQuery)
        val kursQuery =
            "CREATE TABLE ${Constant.KURSLAR_TABLE} (${Constant.KURS_ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, ${Constant.KURS_NOMI} TEXT NOT NULL, ${Constant.KURS_HAQIDA} TEXT NOT NULL);"
        db?.execSQL(kursQuery)
        val guruhQuery =
            "CREATE TABLE ${Constant.GURUH_TABLE} (${Constant.GURUH_ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, ${Constant.GURUH_NOMI} TEXT NOT NULL, ${Constant.GURUH_MENTOR_ID} INTEGER NOT NULL, ${Constant.GURUH_KUNLARI} TEXT NOT NULL, ${Constant.GURUH_VAQTI} TEXT NOT NULL, ${Constant.GURUH_KURS_ID} TEXT NOT NULL, FOREIGN KEY(${Constant.GURUH_MENTOR_ID}) REFERENCES ${Constant.MENTOR_TABLE} (${Constant.MENTOR_ID}), FOREIGN KEY (${Constant.GURUH_KURS_ID}) REFERENCES ${Constant.KURSLAR_TABLE} (${Constant.KURS_ID}));"
        db?.execSQL(guruhQuery)
        val talabaQuery =
            "CREATE TABLE ${Constant.TALABA_TABLE} (${Constant.TALABA_ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, ${Constant.TALABA_FAMILIYASI} TEXT NOT NULL, ${Constant.TALABA_ISMI} TEXT NOT NULL, ${Constant.TALABA_NOMERI} TEXT NOT NULL, ${Constant.TALABA_REGISTRATRIYA_SANASI} TEXT NOT NULL, ${Constant.TALABA_GURUH_ID} INTEGER NOT NULL, FOREIGN KEY (${Constant.TALABA_GURUH_ID}) REFERENCES ${Constant.GURUH_TABLE} (${Constant.GURUH_ID}));"
        db?.execSQL(talabaQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    override fun addMentor(mentor: Mentor) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.MENTOR_FAMILIYASI, mentor.familiya)
        contentValues.put(Constant.MENTOR_ISMI, mentor.ism)
        contentValues.put(Constant.MENTOR_ISMI, mentor.ism)
        contentValues.put(Constant.MENTOR_SHARIFI, mentor.sharifi)
        contentValues.put(Constant.MENTOR_KURS_ID, mentor.kurs?.id)
        database.insert(Constant.MENTOR_TABLE, null, contentValues)
        database.close()
    }

    override fun deleteMentor(mentor: Mentor) {
        val database = this.writableDatabase
        for (guruh in getAllGuruh()) {
            if (guruh.mentor?.id == mentor.id){
                for (talaba in getAllTalaba()) {
                    if (talaba.guruh?.id == guruh.id){
                        database.delete(Constant.TALABA_TABLE, "${Constant.TALABA_ID} = ?", arrayOf(talaba.id.toString()))
                    }
                }
                database.delete(Constant.GURUH_TABLE, "${Constant.GURUH_ID} = ?", arrayOf(guruh.id.toString()))
            }
        }
        database.delete(Constant.MENTOR_TABLE, "${Constant.MENTOR_ID} = ?", arrayOf(mentor.id.toString()))
        database.close()
    }

    override fun editMentor(mentor: Mentor): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.MENTOR_ID, mentor.id)
        contentValues.put(Constant.MENTOR_FAMILIYASI, mentor.familiya)
        contentValues.put(Constant.MENTOR_ISMI, mentor.ism)
        contentValues.put(Constant.MENTOR_SHARIFI, mentor.sharifi)
        contentValues.put(Constant.MENTOR_KURS_ID, mentor.kurs?.id)

        return database.update(Constant.MENTOR_TABLE, contentValues, "${Constant.MENTOR_ID} = ?", arrayOf(mentor.id.toString()))
    }

    override fun getAllMentor(): List<Mentor> {
        val list = ArrayList<Mentor>()
        val query = "Select * from ${Constant.MENTOR_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val mentor = Mentor()
                mentor.id = cursor.getInt(0)
                mentor.familiya = cursor.getString(1)
                mentor.ism = cursor.getString(2)
                mentor.sharifi = cursor.getString(3)
                mentor.kurs = getKursById(cursor.getInt(4))
                list.add(mentor)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun addKurs(kurs: Kurs) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.KURS_NOMI, kurs.name)
        contentValues.put(Constant.KURS_HAQIDA, kurs.about)
        database.insert(Constant.KURSLAR_TABLE, null, contentValues)
        database.close()
    }

    override fun getAllKurs(): List<Kurs> {
        val list = ArrayList<Kurs>()
        val query = "Select * from ${Constant.KURSLAR_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val kurs = Kurs()
                kurs.id = cursor.getInt(0)
                kurs.name = cursor.getString(1)
                kurs.about = cursor.getString(2)
                list.add(kurs)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun getKursById(id: Int): Kurs {
        val database = this.readableDatabase
        val cursor = database.query(
            Constant.KURSLAR_TABLE,
            arrayOf(Constant.KURS_ID, Constant.KURS_NOMI, Constant.KURS_HAQIDA),
            "${Constant.MENTOR_ID} = ?",
            arrayOf(id.toString()), null, null, null
        )

        cursor.moveToFirst()
        return Kurs(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2)
        )
    }

    override fun addGuruh(guruh: Guruh) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.GURUH_NOMI, guruh.nomi)
        contentValues.put(Constant.GURUH_MENTOR_ID, guruh.mentor?.id)
        contentValues.put(Constant.GURUH_KUNLARI, guruh.kunlari)
        contentValues.put(Constant.GURUH_VAQTI, guruh.vaqt)
        contentValues.put(Constant.GURUH_KURS_ID, guruh.kurs?.id)
        database.insert(Constant.GURUH_TABLE, null, contentValues)
        database.close()
    }

    override fun deleteGuruh(guruh: Guruh) {
        val database = this.writableDatabase
        for (talaba in getAllTalaba()) {
            if (talaba.guruh?.id == guruh.id){
                database.delete(Constant.TALABA_TABLE, "${Constant.TALABA_ID} = ?", arrayOf(talaba.id.toString()))
            }
        }
        database.delete(Constant.GURUH_TABLE, "${Constant.GURUH_ID} = ?", arrayOf(guruh.id.toString()))
        database.close()
    }

    override fun editGuruh(guruh: Guruh): Int {
            val database = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put(Constant.GURUH_ID, guruh.id)
            contentValues.put(Constant.GURUH_NOMI, guruh.nomi)
            contentValues.put(Constant.GURUH_MENTOR_ID, guruh.mentor?.id)
            contentValues.put(Constant.GURUH_KUNLARI, guruh.kunlari)
            contentValues.put(Constant.GURUH_VAQTI, guruh.vaqt)
            contentValues.put(Constant.GURUH_KURS_ID, guruh.kurs?.id)

            return database.update(Constant.GURUH_TABLE, contentValues, "${Constant.GURUH_ID} = ?", arrayOf(guruh.id.toString()))
    }

    override fun getAllGuruh(): List<Guruh> {
        val list = ArrayList<Guruh>()
        val query = "Select * from ${Constant.GURUH_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val guruh = Guruh()
                guruh.id = cursor.getInt(0)
                guruh.nomi = cursor.getString(1)
                guruh.mentor = getMentorById(cursor.getInt(2))
                guruh.kunlari = cursor.getString(3)
                guruh.vaqt = cursor.getString(4)
                guruh.kurs = getKursById(cursor.getInt(5))
                list.add(guruh)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun getMentorById(id: Int): Mentor {
        val database = this.readableDatabase
        val cursor = database.query(
            Constant.MENTOR_TABLE,
            arrayOf(Constant.MENTOR_ID, Constant.MENTOR_FAMILIYASI, Constant.MENTOR_ISMI, Constant.MENTOR_SHARIFI, Constant.MENTOR_KURS_ID),
            "${Constant.MENTOR_ID} = ?",
            arrayOf(id.toString()), null, null, null
        )

        cursor.moveToFirst()
        return Mentor(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            getKursById(cursor.getInt(4))
        )
    }

    override fun addTalaba(talaba: Talaba) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.TALABA_FAMILIYASI, talaba.familiya)
        contentValues.put(Constant.TALABA_ISMI, talaba.ism)
        contentValues.put(Constant.TALABA_NOMERI, talaba.telefon)
        contentValues.put(Constant.TALABA_REGISTRATRIYA_SANASI, talaba.registratsiyaVaqti)
        contentValues.put(Constant.TALABA_GURUH_ID, talaba.guruh?.id)
        database.insert(Constant.TALABA_TABLE, null, contentValues)
        database.close()
    }

    override fun deleteTalaba(talaba: Talaba) {
        val database = this.writableDatabase
        database.delete(Constant.TALABA_TABLE, "${Constant.TALABA_ID} = ?", arrayOf(talaba.id.toString()))
        database.close()
    }

    override fun editTalaba(talaba: Talaba): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.TALABA_ID, talaba.id)
        contentValues.put(Constant.TALABA_FAMILIYASI, talaba.familiya)
        contentValues.put(Constant.TALABA_ISMI, talaba.ism)
        contentValues.put(Constant.TALABA_NOMERI, talaba.telefon)
        contentValues.put(Constant.TALABA_REGISTRATRIYA_SANASI, talaba.registratsiyaVaqti)
        contentValues.put(Constant.TALABA_GURUH_ID, talaba.guruh?.id)

        return database.update(Constant.GURUH_TABLE, contentValues, "${Constant.GURUH_ID} = ?", arrayOf(talaba.id.toString()))

    }

    override fun getAllTalaba(): List<Talaba> {
        val list = ArrayList<Talaba>()
        val query = "Select * from ${Constant.TALABA_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val talaba = Talaba(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    getGuruhById(cursor.getInt(5))
                )
                list.add(talaba)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun getGuruhById(id: Int): Guruh {
        val database = this.readableDatabase
        val cursor = database.query(
            Constant.GURUH_TABLE,
            arrayOf(Constant.GURUH_ID, Constant.GURUH_NOMI, Constant.GURUH_MENTOR_ID, Constant.GURUH_KUNLARI, Constant.GURUH_VAQTI, Constant.GURUH_KURS_ID),
            "${Constant.GURUH_ID} = ?",
            arrayOf(id.toString()), null, null, null
        )

        cursor.moveToFirst()
        return Guruh(
            cursor.getInt(0),
            cursor.getString(1),
            getMentorById(cursor.getInt(2)),
            cursor.getString(3),
            cursor.getString(4),
            getKursById(cursor.getInt(5))
        )
    }
}