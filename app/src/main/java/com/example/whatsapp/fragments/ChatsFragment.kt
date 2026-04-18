package com.example.whatsapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whatsapp.RecyclerViewItemClicked
import com.example.whatsapp.activities.ChatActivity
import com.example.whatsapp.adapter.ChatsAdapter
import com.example.whatsapp.data.Users
import com.example.whatsapp.databinding.FragmentChatsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatsFragment : Fragment(), RecyclerViewItemClicked {
    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!
    private lateinit var databaseReference: DatabaseReference
    private var userList = mutableListOf<Users>()
    private lateinit var userAdapter: ChatsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        
        // Ensure you are using the correct reference name "Users"
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (_binding == null) return
                
                userList.clear()
                for (i in snapshot.children) {
                    val user = i.getValue(Users::class.java)
                    // We only add other users to the list
                    if (user != null && user.profileId != null && user.profileId != currentUserId) {
                        userList.add(user)
                    }
                }
                
                // Pass a copy of the list to the adapter
                userAdapter.updateData(ArrayList(userList))
                
                // Show empty state if no OTHER users exist in the database
                if (userList.isEmpty()) {
                    binding.tvNoChats.visibility = View.VISIBLE
                    binding.tvNoChats.text = "No other users found in the database.\nTry adding another account."
                    binding.recyclerChats.visibility = View.GONE
                } else {
                    binding.tvNoChats.visibility = View.GONE
                    binding.recyclerChats.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                if (isAdded) {
                    Toast.makeText(context, "Database Error: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    
    private fun setupRecyclerView() {
        userAdapter = ChatsAdapter(userList, this)
        binding.recyclerChats.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }
    }
    
    override fun onItemClicked(position: Int, data: Any) {
        if (data is Users){
            val intent = Intent(requireActivity(), ChatActivity::class.java)
            intent.putExtra("chats", data.profileId)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
