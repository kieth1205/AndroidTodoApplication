package com.example.todoapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapplication.adapters.NotesAdapter;
import com.example.todoapplication.callbacks.MainActionModeCallback;
import com.example.todoapplication.callbacks.NoteEventListener;
import com.example.todoapplication.databases.NotesDB;
import com.example.todoapplication.databases.NotesDao;
import com.example.todoapplication.models.Note;
import com.example.todoapplication.utils.NoteUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.example.todoapplication.EditNoteActivity.NOTE_EXTRA_Key;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<Note> notes;
    private NotesAdapter adapter;
    private NotesDao dao;
    private MainActionModeCallback actionModeCallback;
    private int checkedCount = 0;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        recyclerView = findViewById(R.id.notes_list);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        // init fab Button
//        fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onAddNewNote();
//            }
//        });
//        dao = NotesDB.getInstance(this).notesDao();
    }

    private void initViews() {
        Log.d(TAG, "initView: started");
        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_drawer);
        toolbar = findViewById(R.id.toolbar);

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.Home:
                Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
            case R.id.Todos:
                Toast.makeText(getApplicationContext(), "Todos", Toast.LENGTH_SHORT).show();
            case R.id.Tasks:
                Toast.makeText(getApplicationContext(), "Task list", Toast.LENGTH_SHORT).show();
            case R.id.Notes:
                Toast.makeText(getApplicationContext(), "Notes", Toast.LENGTH_SHORT).show();
            case R.id.TAGs:
                Toast.makeText(getApplicationContext(), "TAGS", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

//    private void loadNotes() {
//        this.notes = new ArrayList<>();
//        List<Note> list = dao.getNotes();// get All notes from DataBase
//        this.notes.addAll(list);
//        this.adapter = new NotesAdapter(this, this.notes);
//        // set listener to adapter
//        this.adapter.setListener(this);
//        this.recyclerView.setAdapter(adapter);
//        showEmptyView();
//
//        swipeToDeleteHelper.attachToRecyclerView(recyclerView);
//    }
//
//    private void showEmptyView() {
//        if (notes.size() == 0) {
//            this.recyclerView.setVisibility(View.GONE);
//            findViewById(R.id.empty_notes_view).setVisibility(View.VISIBLE);
//
//        } else {
//            this.recyclerView.setVisibility(View.VISIBLE);
//            findViewById(R.id.empty_notes_view).setVisibility(View.GONE);
//        }
//    }
//
//    private void onAddNewNote() {
//        startActivity(new Intent(this, EditNoteActivity.class));
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        loadNotes();
//    }
//
//    @Override
//    public void onNoteClick(Note note) {
//        Intent edit = new Intent(this, EditNoteActivity.class);
//        edit.putExtra(NOTE_EXTRA_Key, note.getId());
//        startActivity(edit);
//
//    }
//
//    @Override
//    public void onNoteLongClick(Note note) {
//        note.setChecked(true);
//        checkedCount = 1;
//        adapter.setMultiCheckMode(true);
//
//        // set new listener to adapter intend off MainActivity listener that we have implement
//        adapter.setListener(new NoteEventListener() {
//            @Override
//            public void onNoteClick(Note note) {
//                note.setChecked(!note.isChecked()); // inverse selected
//                if (note.isChecked())
//                    checkedCount++;
//                else checkedCount--;
//
//                if (checkedCount == 0) {
//                    //  finish multi select mode wen checked count =0
//                    actionModeCallback.getAction().finish();
//                }
//
//                actionModeCallback.setCount(checkedCount + "/" + notes.size());
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onNoteLongClick(Note note) {
//
//            }
//        });
//
//        actionModeCallback = new MainActionModeCallback() {
//            @Override
//            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
//                if (menuItem.getItemId() == R.id.action_delete_notes)
//                    onDeleteMultiNotes();
//                actionMode.finish();
//                return false;
//            }
//
//        };
//
//        // start action mode
//        startActionMode(actionModeCallback);
//        // hide fab button
//        fab.setVisibility(View.GONE);
//        actionModeCallback.setCount(checkedCount + "/" + notes.size());
//    }
//
//    private void onDeleteMultiNotes() {
//
//        List<Note> checkedNotes = adapter.getCheckedNotes();
//        if (checkedNotes.size() != 0) {
//            for (Note note : checkedNotes) {
//                dao.deleteNote(note);
//            }
//            // refresh Notes
//            loadNotes();
//            Toast.makeText(this, checkedNotes.size() + " Note(s) Delete successfully !", Toast.LENGTH_SHORT).show();
//        } else Toast.makeText(this, "No Note(s) selected", Toast.LENGTH_SHORT).show();
//        adapter.setMultiCheckMode(false);
//    }
//
//    @Override
//    public void onActionModeFinished(ActionMode mode) {
//        super.onActionModeFinished(mode);
//        adapter.setMultiCheckMode(false); // uncheck the notes
//        adapter.setListener(this); // set back the old listener
//        fab.setVisibility(View.VISIBLE);
//    }
//
//    // swipe to right or to left te delete
//    private ItemTouchHelper swipeToDeleteHelper = new ItemTouchHelper(
//            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//                @Override
//                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                    return false;
//                }
//
//                @Override
//                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//
//                    if (notes != null) {
//                        // get swiped note
//                        Note swipedNote = notes.get(viewHolder.getAdapterPosition());
//                        if (swipedNote != null) {
//                            swipeToDelete(swipedNote, viewHolder);
//                        }
//                    }
//                }
//            });
//
//    private void swipeToDelete(final Note swipedNote, final RecyclerView.ViewHolder viewHolder) {
//        new AlertDialog.Builder(MainActivity.this)
//                .setMessage("Delete Note?")
//                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dao.deleteNote(swipedNote);
//                        notes.remove(swipedNote);
//                        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
//                        showEmptyView();
//
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        recyclerView.getAdapter().notifyItemChanged(viewHolder.getAdapterPosition());
//
//                    }
//                })
//                .setCancelable(false)
//                .create().show();
//    }
}



