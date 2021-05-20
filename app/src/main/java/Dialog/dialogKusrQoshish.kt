package Dialog

import DB.MyDbHelper
import Models.Kurs
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Toast
import com.ilhomjon.h532oquvmarkaz.databinding.DialogAddKursBinding

fun dialogKusrQoshish(context: Context?){
    val alertDialog = AlertDialog.Builder(context)
    val dialog = alertDialog.create()
    dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));


    val dialogView = DialogAddKursBinding.inflate(LayoutInflater.from(context))
    dialog.setView(dialogView.root)

    dialogView.txtYopish.setOnClickListener { dialog.hide() }
        dialogView.txtQoshish.setOnClickListener {
            val kursNomi = dialogView.edtAddKurs.text.toString().trim()
            val kursHaqida = dialogView.edtKursHaqida.text.toString().trim()

            if (kursNomi != "" && kursHaqida != "") {
                MyDbHelper(context!!).addKurs(Kurs(kursNomi, kursHaqida))
                Toast.makeText(context, "Add kurs", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "Edittext is empty", Toast.LENGTH_SHORT).show()
            }
            dialog.hide()
        }
    dialog.show()
}