package com.thangcao.ghichu

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.thangcao.ghichu.database.NotesDatabase
import com.thangcao.ghichu.entities.Notes
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class CreateNoteFragment : BaseFragment() {

    var currentDate:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_note, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CreateNoteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())
        tvDateTime.text = currentDate;

        imgDone.setOnClickListener{
            saveNote()
        }
        imgBack.setOnClickListener {
            replaceFragment(HomeFragment.newInstance(), false)
        }
    }

    private fun saveNote() {
        if(edNoteTitle.text.isNullOrEmpty()){
            Toast.makeText(context,"Tiêu đề trống!", Toast.LENGTH_SHORT).show()
            return
        }
        if (edNoteSubTitle.text.isNullOrEmpty()){
            Toast.makeText(context,"Mô tả trống!", Toast.LENGTH_SHORT).show()
            return
        }
        if (edNoteDesc.text.isNullOrEmpty()){
            Toast.makeText(context,"Nội dung trống!", Toast.LENGTH_SHORT).show()
            return
        }
        launch {
            val notes = Notes()
            notes.title = edNoteTitle.text.toString();
            notes.subTitle = edNoteSubTitle.text.toString();
            notes.noteText = edNoteDesc.text.toString();
            notes.dateTime = currentDate;
            Toast.makeText(context, "" + notes.subTitle + notes.dateTime, Toast.LENGTH_SHORT).show()
            context?.let {
                NotesDatabase.getDatabase(it).noteDao().insertNotes(notes)
                edNoteTitle.setText("")
                edNoteSubTitle.setText("")
                edNoteDesc.setText("")
            }
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    fun replaceFragment(fragment:Fragment, istransition:Boolean){
        val fragmentTransition = activity!!.supportFragmentManager.beginTransaction()

        if (istransition){
            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransition.replace(R.id.frame_layout,fragment).addToBackStack(fragment.javaClass.simpleName).commit()
    }
}