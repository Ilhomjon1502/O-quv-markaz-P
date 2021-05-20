package Adapters

import DB.MyDbHelper
import Models.Guruh
import Models.Talaba
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilhomjon.h532oquvmarkaz.databinding.ItemRvMentorBinding

class RvGuruhAdapter(val myDbHelper: MyDbHelper, var list: List<Guruh>, var rvGuruhOnClick: RvGuruhOnClick)
    :RecyclerView.Adapter<RvGuruhAdapter.Vh>(){

    inner class Vh(var itemBining:ItemRvMentorBinding):RecyclerView.ViewHolder(itemBining.root){
        fun onBind(guruh: Guruh, position: Int){
            itemBining.show.visibility = View.VISIBLE
            itemBining.txtMentorNF.text = guruh.nomi
            var talabaList = myDbHelper.getAllTalaba()
            var talabaGuruhList = ArrayList<Talaba>()
            for (talaba in talabaList) {
                if (talaba.guruh?.id == guruh.id){
                    talabaGuruhList.add(talaba)
                }
            }
            itemBining.txtMentorSh.text = "O'quvchilar soni ${talabaGuruhList.size} ta"

            itemBining.root.setOnClickListener {
                rvGuruhOnClick.itemClick(guruh, talabaGuruhList,  position)
            }
            itemBining.delete.setOnClickListener {
                rvGuruhOnClick.delete(guruh, position)
            }
            itemBining.edit.setOnClickListener {
                rvGuruhOnClick.edit(guruh, position)
            }
            itemBining.show.setOnClickListener {
                rvGuruhOnClick.show(guruh, talabaGuruhList, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvMentorBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}