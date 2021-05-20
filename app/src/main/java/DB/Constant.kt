package DB

object Constant {
    const val DATABASE_NAME = "oquv_markaz"
    const val DATABASE_VERSION = 1

    const val KURSLAR_TABLE = "kurslar"
    const val KURS_ID = "id"
    const val KURS_NOMI = "name"
    const val KURS_HAQIDA = "about_kurs"

    const val MENTOR_TABLE = "mentorlar"
    const val MENTOR_ID = "id"
    const val MENTOR_FAMILIYASI = "familiya"
    const val MENTOR_ISMI = "ismi"
    const val MENTOR_SHARIFI = "sharifi"
    const val MENTOR_KURS_ID = "kurs_id"

    const val GURUH_TABLE = "guruhlar"
    const val GURUH_ID = "id"
    const val GURUH_NOMI = "name"
    const val GURUH_MENTOR_ID = "mentor_id"
    const val GURUH_KUNLARI = "guruh_kunlari"
    const val GURUH_VAQTI = "guruh_vaqti"
    const val GURUH_KURS_ID = "guruh_id"

    const val TALABA_TABLE = "talabalar"
    const val TALABA_ID = "id"
    const val TALABA_FAMILIYASI = "familiya"
    const val TALABA_ISMI = "ismi"
    const val TALABA_NOMERI = "nomer"
    const val TALABA_REGISTRATRIYA_SANASI = "registratriya_sanasi"
    const val TALABA_GURUH_ID = "guruh_id"
}