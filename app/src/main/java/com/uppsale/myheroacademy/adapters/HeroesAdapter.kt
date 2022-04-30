package com.uppsale.myheroacademy.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.signature.ObjectKey
import com.uppsale.myheroacademy.R
import com.uppsale.myheroacademy.models.Hero
import com.uppsale.myheroacademy.util.Utils
import kotlinx.android.synthetic.main.hero_list_item.view.*


class HeroesAdapter: RecyclerView.Adapter<HeroesAdapter.HeroesViewHolder>() {

    private var heroes:List<Hero> = ArrayList<Hero>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroesViewHolder {
        return HeroesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.hero_list_item,parent,false))
    }

    override fun onBindViewHolder(holder: HeroesViewHolder, position: Int) {
        val hero = heroes[position]
        holder.itemView.apply {
            //loading thumb by scaling the image for 1\4 of it size and adding 1 day expiration
            Glide.with(this).load(hero.image.url).signature(ObjectKey(Utils.getSignatureIntForADay())).sizeMultiplier(0.25f).centerCrop().into(holder.itemView.iv_hero_img)
            this.tv_hero_name.text = hero.name
            setOnClickListener{
                onItemClickListener?.let {
                    it(hero,position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return heroes.size
    }

    fun setItems(list:List<Hero>){
        val  oldList = heroes
         val diffResult = DiffUtil.calculateDiff(HeroItemDiffCallback(oldList,list))
        heroes = list
        diffResult.dispatchUpdatesTo(this)
    }

    private var onItemClickListener:((Hero,Int)->Unit)?=null
    fun setItemClickListener(listener:(Hero,Int)->Unit){
        onItemClickListener = listener
    }

    inner class HeroesViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){}

    inner class HeroItemDiffCallback(
        val oldList:List<Hero>,
        val newList:List<Hero>
    ):DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id

        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == (newList[newItemPosition])
        }

    }
}