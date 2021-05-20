package com.ilhomjon.h532oquvmarkaz

import Adapters.OnCLickRecycleView
import Adapters.RecycleViewRoyhatAdapter
import DB.MyDbHelper
import Dialog.dialogKusrQoshish
import Models.Kurs
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ilhomjon.h532oquvmarkaz.databinding.ActivityMainBinding.inflate
import com.ilhomjon.h532oquvmarkaz.databinding.DialogAddKursBinding
import com.ilhomjon.h532oquvmarkaz.databinding.FragmentBarchaRoyhatBinding
import com.ilhomjon.h532oquvmarkaz.databinding.ItemRvBarchaRoyhatBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BarchaRoyhatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BarchaRoyhatFragment : Fragment() {
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

    lateinit var recycleViewRoyhatAdapter: RecycleViewRoyhatAdapter
    lateinit var kursList:ArrayList<Kurs>
    lateinit var binding: FragmentBarchaRoyhatBinding
    lateinit var myDbHelper: MyDbHelper
    var hasKurs = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBarchaRoyhatBinding.inflate(LayoutInflater.from(context))



        return binding.root
    }

    override fun onStart() {
        super.onStart()
        hasKurs = arguments?.getInt("key")!!
        myDbHelper = MyDbHelper(context)
        kursList = myDbHelper.getAllKurs() as ArrayList<Kurs>
        println(kursList)
        recycleViewRoyhatAdapter = RecycleViewRoyhatAdapter(kursList, object :OnCLickRecycleView{
            override fun onItemClick(kurs: Kurs, position: Int) {
                val bundle = bundleOf("keyKurs" to kurs)
                when(hasKurs){
                    1 ->{
                        findNavController().navigate(R.id.kursHaqidaFragment, bundle)
                    }
                    2 ->{
                        findNavController().navigate(R.id.guruhRoyhatFragment, bundle)
                    }
                    3 ->{
                        findNavController().navigate(R.id.mentorRoyhatFragment, bundle)
                    }
                }
            }
        })
        binding.rvBarchaRoyhat.adapter = recycleViewRoyhatAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (hasKurs == 1)
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        dialogKusrQoshish(context)
        return super.onOptionsItemSelected(item)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BarchaRoyhatFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BarchaRoyhatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

