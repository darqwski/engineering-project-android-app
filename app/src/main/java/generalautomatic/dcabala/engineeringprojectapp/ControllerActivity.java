package generalautomatic.dcabala.engineeringprojectapp;

import androidx.appcompat.app.AppCompatActivity;
import generalautomatic.dcabala.engineeringprojectapp.model.Program;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ControllerActivity extends AppCompatActivity {
    Context appContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
        ((TextView)findViewById(R.id.welcomeText)).setText("Witaj"+Store.login);
        appContext=this;
        findViewById(R.id.changeProgram).setOnClickListener(
            view -> startActivity(new Intent(appContext, ChangeProgramActivity.class))
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Request(appContext, new ResponseAction() {
            @Override
            public void action(int responseCode,final String response) {
                Log.wtf("programResponse", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray programs = jsonObject.getJSONArray("programs");
                    JSONObject userInfo = jsonObject.getJSONArray("userInfo").getJSONObject(0);
                    Store.programs = new HashMap<String, Program>();
                    Store.userInfo = new HashMap<String, String>();
                    for(int i = 0;i<programs.length();i++){
                        Program program = new Program();
                        program.getFromJSON(programs.getJSONObject(i));
                        Store.programs.put(program.getProgramId(), program);
                    }
                    Store.userInfo.put("programId",userInfo.getString("program"));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView)findViewById(R.id.programDescriptionValue))
                                    .setText(Store.programs.get(Store.userInfo.get("programId")).getProgramDescription());
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).execute("GET", "/API/program/", "");
    }
}