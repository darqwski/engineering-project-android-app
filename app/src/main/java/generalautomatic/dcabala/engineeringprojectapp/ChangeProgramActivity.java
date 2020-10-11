package generalautomatic.dcabala.engineeringprojectapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import generalautomatic.dcabala.engineeringprojectapp.model.Program;

import android.content.Context;
import android.os.Bundle;

public class ChangeProgramActivity extends AppCompatActivity {
    Context appContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_program);
        appContext =this;
        Program[] programs = Store.programs.values().toArray(new Program[0]);
        ProgramsAdapter taskListAdapter = new ProgramsAdapter(appContext,programs,R.layout.single_program_list_item);
        ((RecyclerView)findViewById(R.id.programsList)).setLayoutManager(new LinearLayoutManager(appContext));
        ((RecyclerView)findViewById(R.id.programsList)).setAdapter(taskListAdapter);
    }
}