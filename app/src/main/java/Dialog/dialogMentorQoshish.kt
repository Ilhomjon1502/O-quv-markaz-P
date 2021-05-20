package Dialog

import DB.MyDbHelper
import Models.Kurs
import Models.Mentor
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.ilhomjon.h532oquvmarkaz.MentorRoyhatFragment
import com.ilhomjon.h532oquvmarkaz.databinding.DialogAddKursBinding
import com.ilhomjon.h532oquvmarkaz.databinding.FragmentMentorRoyhatBinding

fun dialogMentorQoshish(context: Context?, mentor: Mentor, kurs: Kurs, myDbHelper: MyDbHelper, binding: MentorRoyhatFragment){

    val alertDialog = AlertDialog.Builder(context)
    val dialog = alertDialog.create()
    dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));


    val dialogView = DialogAddKursBinding.inflate(LayoutInflater.from(context))
    dialog.setView(dialogView.root)

    dialogView.edtAddKurs.hint = "Familiyasi"
    dialogView.edtKursHaqida.hint = "Ismi"
    dialogView.edtAddsharifi.hint = "Sharifi"
    dialogView.edtAddsharifi.visibility = View.VISIBLE

    dialogView.txtYopish.setOnClickListener { dialog.hide() }
    dialogView.txtQoshish.setOnClickListener {
        myDbHelper.addMentor(
            Mentor(
                dialogView.edtAddKurs.text.toString(),
                dialogView.edtKursHaqida.text.toString(),
                dialogView.edtAddsharifi.text.toString(),
                kurs
            )
        )
        Toast.makeText(context, "${kurs.name} ga mentor qo'shildi", Toast.LENGTH_SHORT).show()
        binding.onStart()
        dialog.hide()
    }

    dialog.show()
}