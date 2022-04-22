package com.thangcao.ghichu

import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.thangcao.ghichu.database.NotesDatabase
import com.thangcao.ghichu.entities.Notes
import com.thangcao.ghichu.util.NoteBottomSheetFragment
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class CreateNoteFragment : BaseFragment() {

    var selectedColor = "#CACACA"
    var currentDate: String? = null

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

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            BroadcastReceiver, IntentFilter("bottom_sheet_action")
        )


        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())
        tvDateTime.text = currentDate;

        imgDone.setOnClickListener {
            saveNote()
        }
        imgBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        imgMore.setOnClickListener {
            var noteBottomSheetFragment = NoteBottomSheetFragment.newInstance()
            noteBottomSheetFragment.show(
                requireActivity().supportFragmentManager,
                "Note Bottom Sheet Fragment"
            )
        }
    }

    private fun saveNote() {
        if (edNoteTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Tiêu đề trống!", Toast.LENGTH_SHORT).show()
            return
        }
        if (edNoteSubTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Mô tả trống!", Toast.LENGTH_SHORT).show()
            return
        }
        if (edNoteDesc.text.isNullOrEmpty()) {
            Toast.makeText(context, "Nội dung trống!", Toast.LENGTH_SHORT).show()
            return
        }
        launch {
            val notes = Notes()
            notes.title = edNoteTitle.text.toString()
            notes.subTitle = edNoteSubTitle.text.toString()
            notes.noteText = edNoteDesc.text.toString()
            notes.dateTime = currentDate
            notes.color = selectedColor
            context?.let {
                NotesDatabase.getDatabase(it).noteDao().insertNotes(notes)
                edNoteTitle.setText("")
                edNoteSubTitle.setText("")
                edNoteDesc.setText("")
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    fun replaceFragment(fragment: Fragment, istransition: Boolean) {
        val fragmentTransition = activity!!.supportFragmentManager.beginTransaction()

        if (istransition) {
            fragmentTransition.setCustomAnimations(
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left
            )
        }
        fragmentTransition.replace(R.id.frame_layout, fragment)
            .addToBackStack(fragment.javaClass.simpleName).commit()
    }

    private val BroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {

            var actionColor = p1!!.getStringExtra("action")

            when (actionColor!!) {

                "Blue" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }

                "Yellow" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }


                "Purple" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }


                "Green" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }


                "Orange" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }


                "Black" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }

//                "Image" -> {
//                    readStorageTask()
//                    layoutWebUrl.visibility = View.GONE
//                }
//
//                "WebUrl" -> {
//                    layoutWebUrl.visibility = View.VISIBLE
//                }
//                "DeleteNote" -> {
//                    //delete note
//                    deleteNote()
//                }
//
//
//                else -> {
//                    layoutImage.visibility = View.GONE
//                    imgNote.visibility = View.GONE
//                    layoutWebUrl.visibility = View.GONE
//                    selectedColor = p1.getStringExtra("selectedColor")!!
//                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
//
//                }
            }
        }

    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(BroadcastReceiver)
        super.onDestroy()
    }
}