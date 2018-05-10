package api.restful.com.newrestapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Starting");


    }

    public void GetPage(View v){
        startActivity(new Intent(getApplicationContext(), GetActivity.class));
    }
    public void PutPage(View v){
        startActivity(new Intent(getApplicationContext(), PostActivity.class));
    }
    public void PushPage(View v){
        startActivity(new Intent(getApplicationContext(), PutActivity.class));
    }
    public void DeletePage(View v){
        startActivity(new Intent(getApplicationContext(), DeleteActivity.class));
    }

}
