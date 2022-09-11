package com.example.kotlindemo.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.kotlindemo.R
import com.example.kotlindemo.model.User
import com.squareup.picasso.Picasso

class UserAdapter (private val context: Activity, private val arrayList: ArrayList<User>): ArrayAdapter<User>(context, R.layout.user_card, arrayList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.user_card, null)

        val imageView: ImageView = view.findViewById(R.id.civProfilePic)
        val cardNickname: TextView = view.findViewById(R.id.tvCardNickname)
        val bio: TextView = view.findViewById(R.id.tvBio)

        Picasso.get().load(arrayList[position].photo)
            .placeholder(R.drawable.placeholder).error(R.drawable.placeholder)
            .into(imageView)
        cardNickname.text = arrayList[position].nickName
        bio.text = arrayList[position].bio

        return view
    }
}