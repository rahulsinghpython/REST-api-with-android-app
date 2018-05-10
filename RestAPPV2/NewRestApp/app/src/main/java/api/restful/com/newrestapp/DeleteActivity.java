package api.restful.com.newrestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class DeleteActivity extends AppCompatActivity {

    Button delete_add, delete_minus, delete_id_button, delete_retrieve;
    EditText delete_id;
    TextView delete_view;

    RequestQueue requestQueue;
    ReqResult mResultCallback = null;
    PopulateList populateList;
    String url = "http://10.0.2.2:5000/employee";
    private String TAG = "DeleteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        delete_add = findViewById(R.id.delete_plus);
        delete_minus = findViewById(R.id.delete_minus);
        delete_id_button = findViewById(R.id.delete_id_button);
        delete_retrieve = findViewById(R.id.delete_retrieve);

        delete_view = findViewById(R.id.delete_view);

        requestQueue = Volley.newRequestQueue(this);

        delete_id = findViewById(R.id.delete_id);

        initVolleyCallback();
        populateList = new PopulateList(mResultCallback, this);
        populateList.getDataVolley(url);

    }
    public void increasedeleteid(View v){
        int currentid = Integer.valueOf(delete_id.getText().toString());
        currentid += 1;
        delete_id.setText("" + currentid);
    }

    public void decreasedeleteid(View v){
        int currentid = Integer.valueOf(delete_id.getText().toString());
        if (currentid > 0) {
            currentid -= 1;
        }
        delete_id.setText("" + currentid);
    }

    public void delete_retrieve(View v) {
        delete_view.setText("");
        int currentid = Integer.valueOf(delete_id.getText().toString());
        List<Employee> emp = PopulateList.getEmployees();
        if (emp.isEmpty()) {
            System.out.println("SQL DATABASE DIDNT LOAD");
        } else {
            String deleteName = emp.get(currentid - 1).getName();
            String deleteEmail = emp.get(currentid - 1).getEmail();
            String deleteRole = emp.get(currentid - 1).getRole();
            String allInfo = deleteName + " | " + deleteEmail + " | " + deleteRole;
            delete_view.setText(allInfo);
        }
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

    public void del_user(View v){
        System.out.println("deleting user");
        delete_info(delete_id.getText().toString());
    }

    private void delete_info(final String number) {
        this.url = url + "/" + number;
        final JsonObjectRequest newreq = new JsonObjectRequest(Request.Method.DELETE, url,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//                            JSONObject jsonobj = response.getJSONObject("name");
                            String name = response.getString("name");
                            String email = response.getString("email");
                            String role = response.getString("role");
                            System.out.println(name + email + role);
                            delete_view.setText("");
                            String deleteduser = "You have deleted \n" + name + " | "+ email + " | " + role;
                            delete_view.setText(deleteduser);
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
}
