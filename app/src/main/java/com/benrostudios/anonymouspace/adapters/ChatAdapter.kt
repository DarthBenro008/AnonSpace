package com.benrostudios.anonymouspace.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benrostudios.anonymouspace.R
import com.benrostudios.anonymouspace.data.models.MessageItem
import kotlinx.android.synthetic.main.incoming_chat_item.view.*
import kotlinx.android.synthetic.main.outgoing_chat_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(
    private val chats: List<MessageItem>,
    private val sender: String
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {


    companion object {
        private const val TYPE_RECEIVED = 0
        private const val TYPE_SENT = 1
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_RECEIVED -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.incoming_chat_item, parent, false)
                ReceivedViewHolder(view)
            }
            TYPE_SENT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.outgoing_chat_item, parent, false)
                SentViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }


    override fun getItemCount(): Int = chats.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val adapterElement = chats[position]
        when (holder) {
            is SentViewHolder -> holder.bind(adapterElement)
            is ReceivedViewHolder -> holder.bind(adapterElement)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val chatMessage = chats[position]
        return if (chatMessage.userid == sender) {
            TYPE_SENT
        } else {
            TYPE_RECEIVED
        }
    }

    class SentViewHolder(v: View) : BaseViewHolder<MessageItem>(v) {
        override fun bind(item: MessageItem) {
            with(itemView) {
                outgoing_message.text = item.content
                outgoing_time.text = dateParser(item.timestamp)
            }
        }

        fun dateParser(inputUnix: Long): String {
            val sdf = SimpleDateFormat("hh:mm")
            val date = Date(inputUnix * 1000L)
            return sdf.format(date)
        }
    }

    class ReceivedViewHolder(v: View) : BaseViewHolder<MessageItem>(v) {
        override fun bind(item: MessageItem) {
            with(itemView) {
                incoming_message.text = item.content
                incoming_timestamp.text = dateParser(item.timestamp)
                incoming_display_name.text = item.displayname
            }
        }

        fun dateParser(inputUnix: Long): String {
            val sdf = SimpleDateFormat("hh:mm")
            val date = Date(inputUnix * 1000L)
            return sdf.format(date)
        }

    }

}

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}