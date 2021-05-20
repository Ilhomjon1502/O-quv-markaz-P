package Adapters

import Models.Mentor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilhomjon.h532oquvmarkaz.databinding.ItemRvMentorBinding

class RvMentorAdapter(val list: List<Mentor>, var rvMentorClick: RvMentorClick)
    : RecyclerView.Adapter<RvMentorAdapter.Vh>(){

    inner class Vh(var itembinding: ItemRvMentorBinding):RecyclerView.ViewHolder(itembinding.root){
        fun onBind(mentor: Mentor, position:Int){
            itembinding.txtMentorNF.text = "${mentor.familiya} ${mentor.ism}"
            itembinding.txtMentorSh.text = mentor.sharifi

            itembinding.root.setOnClickListener {
                rvMentorClick.itemClick(mentor, position)
            }
            itembinding.edit.setOnClickListener {
                rvMentorClick.editClick(mentor, position)
            }
            itembinding.delete.setOnClickListener {
                rvMentorClick.deleteClick(mentor, position)
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
interface RvMentorClick{
    fun itemClick(mentor: Mentor, position: Int)
    fun editClick(mentor: Mentor, position: Int)
    fun deleteClick(mentor: Mentor, position: Int)
}