package api.restful.com.newrestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PutActivity extends AppCompatActivity {

    ReqResult mResultCallback = null;
    PopulateList populateList;
    String url = "http://10.0.2.2:5000/employee";
    private String TAG = "PutActivity";
    TextView put_new_name, put_new_email, put_new_role;
    TextView put_name, put_email, put_role;
    Button put_submit;
    EditText put_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put);

        put_id = (EditText) findViewById(R.id.put_id);
        put_name = (TextView) findViewById(R.id.put_name);
        put_email = (TextView) findViewById(R.id.put_email);
        put_role = (TextView) findViewById(R.id.put_role);

        put_new_name = (EditText)findViewById(R.id.put_new_name);
        put_new_email = (EditText)findViewById(R.id.put_new_email);
        put_new_role = (EditText)findViewById(R.id.put_new_role);

        requestQueue = Volley.newRequestQueue(this);

        initVolleyCallback();
        populateList = new PopulateList(mResultCallback, this);
        populateList.getDataVolley(url);


    }

    void initVolleyCallback(){
        mResultCallback = new ReqResult() {
            @Override
            public void notifySuccess(String requestType,JSONObject response) {
                Log.d(TAG, "Volley requester worked " + requestType);
                Log.d(TAG, "Volley JSON post" + response);
            }

            @Override
            public void notifyError(String requestType,VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }
    public void increaseputid(View v){
        int currentid = Integer.valueOf(put_id.getText().toString());
        currentid += 1;
        put_id.setText("" + currentid);
    }

    public void decreaseputid(View v){
        int currentid = Integer.valueOf(put_id.getText().toString());
        if (currentid > 0) {
            currentid -= 1;
        }
        put_id.setText("" + currentid);
    }
    private void clearboxes(){
        put_name.setText("");
        put_email.setText("");
        put_role.setText("");
    }

    public void put_retrieve(View v) {
            clearboxes();
            int currentid = Integer.valueOf(put_id.getText().toString());
            List<Employee> emp = PopulateList.getEmployees();
            if (emp.isEmpty()) {
                System.out.println("SQL DATABASE DIDNT LOAD");
            }else {
                String oldName = emp.get(currentid - 1).getName();
                String oldEmail = emp.get(currentid - 1).getEmail();
                String oldRole = emp.get(currentid - 1).getRole();

                put_name.setText(oldName);
                put_email.setText(oldEmail);
                put_role.setText(oldRole);
            }
    }

    public void putSubmit(View v){
//        String number = put_id.getText().toString();
        getputinfo(put_id.getText().toString());
    }

    RequestQueue requestQueue;
    private void getputinfo(final String number) {
        try {
            JSONObject jsonBody = new JSONObject();
            String name = put_new_name.getText().toString();
            String email = put_new_email.getText().toString();
            String role = put_new_role.getText().toString();
            jsonBody.put("name", name);
            jsonBody.put("email", email);
            jsonBody.put("role", role);
            System.out.println(jsonBody);


            url = url + "/" + number;
            final JsonObjectRequest newreq = new JsonObjectRequest(Request.Method.PUT, url, jsonBody,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    }
            );
            requestQueue.add(newreq);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
