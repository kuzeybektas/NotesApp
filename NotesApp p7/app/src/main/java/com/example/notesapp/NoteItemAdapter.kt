package com.example.notesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.R
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.FragmentEditNoteBinding
import com.example.notesapp.databinding.NoteItemBinding

class NoteItemAdapter (val clickListener : (note:Note) -> Unit,
    val deleteClickListener: (noteId:String) -> Unit)
    :ListAdapter<Note, NoteItemAdapter.NoteItemViewHolder>(NoteDiffItemCallback()){

    //inflate layout, bind data
    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int)
        :NoteItemViewHolder = NoteItemViewHolder.inflateFrom(parent)
    override fun onBindViewHolder(holder:NoteItemViewHolder, position:Int){
        val item = getItem(position)
        holder.bind(item, clickListener, deleteClickListener)
    }


    //bind data to items in RecyclerView
    class NoteItemViewHolder(val binding: NoteItemBinding)
        :RecyclerView.ViewHolder(binding.root){

            companion object {
                fun inflateFrom(parent: ViewGroup): NoteItemViewHolder {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding = NoteItemBinding.inflate(layoutInflater, parent, false)
                    return NoteItemViewHolder(binding)
                }
            }
                fun bind(item:Note, clickListener: (note:Note) -> Unit,
                         deleteClickListener: (noteId: String) -> Unit){
                    binding.note = item
                    binding.root.setOnClickListener{clickListener(item)}
                }
        }
}