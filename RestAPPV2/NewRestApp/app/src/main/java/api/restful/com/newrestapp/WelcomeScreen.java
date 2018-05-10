package api.restful.com.newrestapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WelcomeScreen extends AppCompatActivity {
    private String TAG = "WelcomeScreen";
    Button get_started, get_help;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);


        //  All the buttons
        this.get_started = findViewById(R.id.get_started_button);
        this.get_help = findViewById(R.id.help_button);


    }
    public void MainPage(View v){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
    public void HelpPage(View v){
        startActivity(new Intent(getApplicationContext(), HelpPage.class));
    }
}
