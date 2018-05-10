package api.restful.com.restapp;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONException;


public class MainActivity2 extends AppCompatActivity {

    RequestQueue requestQueue;
    EditText id_text, push_id, post_name, post_email, post_role;
    Button push_button, post_button, delete_button;
    TextView delete_data;
    String baseUrl = "http://10.0.2.2:5000/";  // This is the API base URL (GitHub API)
    String url;  // This will hold the full URL which will include the username entered in the etGitHubUser.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        this.id_text = (EditText) findViewById(R.id.id_text);
        this.post_button = (Button) findViewById(R.id.Post_Button);
        this.delete_button = (Button) findViewById(R.id.delete_button);
        this.delete_data = (TextView) findViewById(R.id.textView);
        requestQueue = Volley.newRequestQueue(this);

        this.push_button = (Button) findViewById(R.id.push_button);

        this.push_id = (EditText) findViewById(R.id.put_id);
        this.post_name = (EditText) findViewById(R.id.post_name);
        this.post_email = (EditText) findViewById(R.id.post_email);
        this.post_role = (EditText) findViewById(R.id.post_role);

    }


    public void deleteid(View v) {
        clearIdList();
        getInfo(id_text.getText().toString());
    }

    private void clearIdList() {
        // This will clear the repo list (set it as a blank string).
        this.delete_data.setText("");
    }

    private void AddtoIdList(String name, String email, String role) {
        // This will add a new repo to our list.
        // It combines the repoName and lastUpdated strings together.
        // And then adds them followed by a new line (\n\n make two new lines).
        String strRow = name + " / " + email + " / " + role;
        String currentText = delete_data.getText().toString();
        this.delete_data.setText(currentText + "\n\n" + strRow);
    }


    private void getInfo(final String number) {
        this.url = this.baseUrl + "employee/" + number;
        final JsonObjectRequest newreq = new JsonObjectRequest(Request.Method.DELETE, url,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//                            JSONObject jsonobj = response.getJSONObject("name");
                            String name = response.getString("name");
                            String email = response.getString("email");
                            String role = response.getString("role");
                            AddtoIdList(name, email, role);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

    public void postid(View v) {
        clearIdList();
        getpostinfo("employee");
        delete_data.setText("POSTED REQUEST");
    }


    private void addpushlist(String name, String email, String role) {
        // This will add a new repo to our list.
        // It combines the repoName and lastUpdated strings together.
        // And then adds them followed by a new line (\n\n make two new lines).
        String strRow = name + " / " + email + " / " + role;
        String currentText = delete_data.getText().toString();
        this.delete_data.setText(currentText + "\n\n" + strRow);
    }

    private void getpostinfo(final String number) {
        try {
            JSONObject jsonBody = new JSONObject();
            String name = post_name.getText().toString();
            String email = post_email.getText().toString();
            String role = post_role.getText().toString();
            jsonBody.put("name", name);
            jsonBody.put("email", email);
            jsonBody.put("role", role);

            this.url = this.baseUrl + number;
            final JsonObjectRequest newreq = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
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
    public void pushid(View v) {
        clearIdList();
        getputinfo(push_id.getText().toString());
        System.out.println("Using PUT REQUEST FOR ID: " + push_id.getText().toString());
        delete_data.setText("USING PUT METHOD FOR ID: " + push_id.getText().toString());
    }

    private void getputinfo(final String number) {
        try {
            JSONObject jsonBody = new JSONObject();
            String name = post_name.getText().toString();
            String email = post_email.getText().toString();
            String role = post_role.getText().toString();
            jsonBody.put("name", name);
            jsonBody.put("email", email);
            jsonBody.put("role", role);
            System.out.println(jsonBody);

            this.url = this.baseUrl + "employee/" + number;
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

