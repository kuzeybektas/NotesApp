package com.example.notesapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notesapp.databinding.FragmentNotesBinding

class NotesFragment :Fragment(){
    val TAG = "NotesFragment"
    private var _binding:FragmentNotesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //variables
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        val view = binding.root
        val application = requireNotNull(this.activity).application
        val dao = NoteDatabase.getInstance(application).noteDao
        val viewModelFactory = NotesViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(NotesViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        //access variable to access clicked note
        fun notesClicked(noteId:Long){
            viewModel.onNoteClicked(noteId)
        }
        //delete note if "yes" is pressed from dialog
        fun yesPressed(noteId:Long){
            Log.d(TAG, "in yesPressed(): noteId = $noteId")
            viewModel.deleteById(noteId)
        }
        //show dialog when delete button is clicked
        fun deleteClicked(noteId:Long){
            ConfirmDeleteDialogFragment(noteId, ::yesPressed).show(childFragmentManager,
                ConfirmDeleteDialogFragment.TAG)
        }

        //adapter
        val adapter = NoteItemAdapter(::notesClicked, ::deleteClicked)
        binding.RecycleV.adapter = adapter

        //observe notes
        viewModel.notes.observe(viewLifecycleOwner, Observer{
            it?.let{
                adapter.submitList(it)
            }
        })
        //navigate to clicked note
        viewModel.navigateToNote.observe(viewLifecycleOwner, Observer { noteId->
            noteId?.let{
                val action = NotesFragmentDirections
                    .actionNotesFragmentToEditNoteFragment(noteId)
                this.findNavController().navigate(action)
                viewModel.onNoteNavigated()
            }
        })

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}