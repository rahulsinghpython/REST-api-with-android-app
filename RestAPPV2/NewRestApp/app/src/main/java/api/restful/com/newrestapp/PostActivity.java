package api.restful.com.newrestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class PostActivity extends AppCompatActivity {

    EditText post_name, post_email, post_role;
    TextView textView;
    RequestQueue requestQueue;
    String baseUrl = "http://10.0.2.2:5000/";
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        this.post_name = findViewById(R.id.post_name);
        this.post_email = findViewById(R.id.post_email);
        this.post_role = findViewById(R.id.post_role);
        this.textView = findViewById(R.id.post_view);
        requestQueue = Volley.newRequestQueue(this);
    }

    public void post_submit(View v){
        getpostinfo();
    }

    private void getpostinfo() {
        try {
            JSONObject jsonBody = new JSONObject();
            String name = post_name.getText().toString();
            String email = post_email.getText().toString();
            String role = post_role.getText().toString();
            jsonBody.put("name", name);
            jsonBody.put("email", email);
            jsonBody.put("role", role);
//            String newdisplay = jsonBody.get(name).toString() + "|" + jsonBody.get(email).toString() + "|" + jsonBody.get(role).toString();
//            textView.setText(newdisplay);

            this.url = this.baseUrl + "employee";
            final JsonObjectRequest newreq = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String newdisplay = response.getString("name") + " | " + response.getString("email")+ " | " + response.getString("role");
                                textView.setText(newdisplay);
                            }
                            catch (JSONException e) {
                                Log.e("Volley", "Invalid JSON Object.");
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
        catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
