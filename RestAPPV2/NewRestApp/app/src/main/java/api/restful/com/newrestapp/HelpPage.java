package api.restful.com.newrestapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HelpPage extends AppCompatActivity {

    Button finished_help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);

        finished_help = findViewById(R.id.finished_help);
    }

    public void finished_help(View v){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
