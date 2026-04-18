package com.example.whatsapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whatsapp.adapter.ChatScreenAdapter
import com.example.whatsapp.data.Chats
import com.example.whatsapp.data.Users
import com.example.whatsapp.databinding.ActivityChatBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private var chatList = mutableListOf<Chats>()
    private lateinit var chatAdapter: ChatScreenAdapter
    private var receiverId: String? = null
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.profileBackarrow.setOnClickListener {
            val intent_home = Intent(this, HomeActivity::class.java)
            startActivity(intent_home)
        }
        setRecyclerAdapter()
        receiverId = intent.getStringExtra("chats")
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {
                    val user: Users = i.getValue(Users::class.java)!!
                    if (user.profileId == receiverId) {
                        binding.toolBarName.text = user.profileName
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        binding.btnSendMsg.setOnClickListener {
            if (binding.typeMsg.text.isEmpty()){
                binding.typeMsg.error = "Can't Send Blank Text"
                return@setOnClickListener
            }
            val chatsMessage = Chats(
                binding.typeMsg.text.toString().trim(),
                System.currentTimeMillis().toString(),
                Firebase.auth.uid!!, receiverId!!
            )
            FirebaseDatabase.getInstance().reference.child("messages").push().setValue(chatsMessage)
            binding.typeMsg.setText("")
        }
        readMessages()
    }

    private fun readMessages() {
        FirebaseDatabase.getInstance().getReference("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    chatList.clear()
                    for (i in snapshot.children) {
                        val chat: Chats = i.getValue(Chats::class.java)!!

                        if ((chat.receiver == Firebase.auth.currentUser?.uid && chat.sender == receiverId)
                            || chat.receiver == receiverId && chat.sender == Firebase.auth.currentUser?.uid
                        ) {
                            chatList.add(chat)
                        }
                    }

                    chatAdapter.updateList(chatList)
                    if (chatList.isNotEmpty()) {
                        binding.recyclerChatCon.scrollToPosition(chatList.size - 1)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    private fun setRecyclerAdapter() {
        val linearLayoutManager = LinearLayoutManager(this)
        chatAdapter = ChatScreenAdapter(chatList)
        binding.recyclerChatCon.apply {
            layoutManager = linearLayoutManager
            adapter = chatAdapter
        }
    }
}
