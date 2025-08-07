package com.example.sangam01

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class MemberAdapter(private val context: Context, private val members: List<Member>) : BaseAdapter() {

    override fun getCount(): Int = members.size

    override fun getItem(position: Int): Any = members[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder
         // Add this line


        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_member_adapter, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val member = members[position]
        holder.tvName.text = member.fullName
        holder.tvContact.text = "📞 ${member.contactNumber}"
        holder.tvAge.text = "🎂 Age: ${member.age}" // Add this line


        // Load member image from DataStore using Glide
        val imageUri = Uri.parse("android.resource://${context.packageName}/${member.photoResId}")
        Glide.with(context)
            .load(imageUri)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.ivProfile)


        return view
    }

    private class ViewHolder(view: View) {
        val tvName: TextView = view.findViewById(R.id.tvMemberName)
        val tvContact: TextView = view.findViewById(R.id.tvMemberContact)
        val tvAge: TextView = view.findViewById(R.id.tvMemberAge) // Add this line
        val ivProfile: ImageView = view.findViewById(R.id.ivMemberProfile)
    }

}
