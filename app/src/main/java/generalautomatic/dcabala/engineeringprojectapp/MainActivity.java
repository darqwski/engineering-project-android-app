package generalautomatic.dcabala.engineeringprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Context appContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appContext = this;

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = ((TextView)findViewById(R.id.loginInput)).getText().toString();
                String password = ((TextView)findViewById(R.id.passwordInput)).getText().toString();
                if(login.length() == 0 && password.length() == 0){
                    Toast.makeText(appContext, "Proszę podać dane logowania", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(password.length() == 0){
                    Toast.makeText(appContext, "Proszę podać hasło", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(login.length() == 0){
                    Toast.makeText(appContext, "Proszę podać login", Toast.LENGTH_SHORT).show();
                    return;
                }
                new Request(appContext, new ResponseAction() {
                    @Override
                    public void action(int responseCode, String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.has("message")){
                                String message = jsonObject.getString("message");
                                Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show();
                            } else {
                                Store.login = jsonObject.getString("login");
                                Store.userId = jsonObject.getString("userId");
                                Toast.makeText(appContext, "Zalogowano pomyślnie", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(appContext, ControllerActivity.class));
                            }
                        } catch (JSONException e) {
                            Log.wtf("loginResponse", response);
                            e.printStackTrace();
                        }
                    }
                }).execute("POST", "/API/login/","login="+login+"&password="+password);
            }
        });
    }
}