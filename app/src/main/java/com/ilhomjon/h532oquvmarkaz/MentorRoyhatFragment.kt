package com.ilhomjon.h532oquvmarkaz

import Adapters.RvMentorAdapter
import Adapters.RvMentorClick
import DB.MyDbHelper
import Dialog.dialogKusrQoshish
import Dialog.dialogMentorQoshish
import Models.Kurs
import Models.Mentor
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ilhomjon.h532oquvmarkaz.databinding.DialogAddKursBinding
import com.ilhomjon.h532oquvmarkaz.databinding.FragmentMentorRoyhatBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MentorRoyhatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MentorRoyhatFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding : FragmentMentorRoyhatBinding
    lateinit var mentorAdapter: RvMentorAdapter
    lateinit var listMentor:ArrayList<Mentor>
    lateinit var myDbHelper: MyDbHelper
    lateinit var kurs: Kurs
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMentorRoyhatBinding.inflate(inflater)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        listMentor = ArrayList()
        myDbHelper = MyDbHelper(context)

        var list = myDbHelper.getAllMentor()
        kurs = arguments?.getSerializable("keyKurs") as Kurs

        for (mentor in list) {
            if (mentor.kurs?.name == kurs.name){
                listMentor.add(mentor)
            }
        }
        mentorAdapter = RvMentorAdapter(listMentor, object : RvMentorClick {
            override fun itemClick(mentor: Mentor, position: Int) {

            }

            override fun editClick(mentor: Mentor, position: Int) {
                val alertDialog = AlertDialog.Builder(context)
                val dialog = alertDialog.create()
                dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));


                val dialogView = DialogAddKursBinding.inflate(LayoutInflater.from(context))
                dialog.setView(dialogView.root)

                dialogView.edtAddKurs.setText(mentor.familiya)
                dialogView.edtKursHaqida.setText(mentor.ism)
                dialogView.edtAddsharifi.setText(mentor.sharifi)
                dialogView.edtAddsharifi.visibility = View.VISIBLE

                dialogView.txtYopish.setOnClickListener { dialog.hide() }
                dialogView.txtQoshish.setOnClickListener {

                    mentor.familiya = dialogView.edtAddKurs.text.toString()
                    mentor.ism = dialogView.edtKursHaqida.text.toString()
                    mentor.sharifi = dialogView.edtAddsharifi.text.toString()

                    myDbHelper.editMentor(mentor)
                    Toast.makeText(
                        context,
                        "${kurs.name} ga mentor tahrirlandi",
                        Toast.LENGTH_SHORT
                    ).show()
                    onStart()
                    dialog.hide()
                }

                dialog.show()
            }

            override fun deleteClick(mentor: Mentor, position: Int) {
                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setMessage(
                    "${mentor.ism} mentor o'chirilsinmi?\n" +
                            "Agar ushbu mentor o'chirilsa undagi guruhlar va guruhdagi talabalar ma'lumotlari ham o'chib ketadi!!!"
                )
                alertDialog.setPositiveButton(
                    "Ha roziman"
                ) { dialog, which ->
                    myDbHelper.deleteMentor(mentor)
                    onStart()
                    Toast.makeText(
                        context,
                        "${mentor.ism} ning barcha ma'lumotlari o'chirildi",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                alertDialog.setNegativeButton("Bekor qilish")
                {dialog, which ->

                }
           alertDialog.show()
            }
        })
        binding.rvMentorRoyhat.adapter = mentorAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
            inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
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
            onStart()
            dialog.hide()
        }

        dialog.show()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MentorRoyhatFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MentorRoyhatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}