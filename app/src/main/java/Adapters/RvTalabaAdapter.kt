package Adapters

import Models.Mentor
import Models.Talaba
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilhomjon.h532oquvmarkaz.databinding.ItemRvMentorBinding

class RvTalabaAdapter (val list: List<Talaba>, var rvTalabaClick: RvTalabaClick)
    :RecyclerView.Adapter<RvTalabaAdapter.Vh>(){
    inner class Vh(var itembinding: ItemRvMentorBinding): RecyclerView.ViewHolder(itembinding.root){
        fun onBind(talaba: Talaba, position:Int){
            itembinding.txtMentorNF.text = "${talaba.familiya} ${talaba.ism}"
            itembinding.txtMentorSh.text = talaba.telefon

            itembinding.edit.setOnClickListener {
                rvTalabaClick.edit(talaba)
            }
            itembinding.delete.setOnClickListener {
                rvTalabaClick.delete(talaba)
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
interface RvTalabaClick{
    fun edit(talaba: Talaba)
    fun delete(talaba: Talaba)
}