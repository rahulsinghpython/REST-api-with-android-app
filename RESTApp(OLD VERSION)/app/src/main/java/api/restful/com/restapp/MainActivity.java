package api.restful.com.restapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {


    EditText employeename, userid; // This will be a reference to our GitHub username input
    Button btnGetRepos, btnGetInfo, pushbutton;  // This is a reference to the "Get Repos" button.
    TextView listbox, listbox2;  // This will reference our repo list text box.
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests
    String baseUrl = "http://10.0.2.2:5000/";  // This is the API base URL (GitHub API)
    String url;  // This will hold the full URL which will include the username entered in the etGitHubUser.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.userid = (EditText) findViewById(R.id.employee_id);
        this.btnGetInfo = (Button) findViewById(R.id.btn_get_info2) ;
        this.listbox2 = (TextView) findViewById(R.id.tv_employee_list2);
        this.listbox2.setMovementMethod(new ScrollingMovementMethod());

        this.employeename = (EditText) findViewById(R.id.employee_name);
        this.btnGetRepos = (Button) findViewById(R.id.btn_get_info);
        this.listbox = (TextView) findViewById(R.id.tv_employee_list);
        this.listbox.setMovementMethod(new ScrollingMovementMethod());
        requestQueue = Volley.newRequestQueue(this);

        this.pushbutton = (Button) findViewById(R.id.push_button);


    }



    private void clearRepoList() {
        this.listbox.setText("");
    }

    private void addToRepoList(String name, String email, String role) {
        String strRow = name + " / " + email + " / " + role;
        String currentText = listbox.getText().toString();
        this.listbox.setText(currentText + "\n\n" + strRow);


        requestQueue = Volley.newRequestQueue(this);
    }

    private void setRepoListText(String str) {
        this.listbox.setText(str);
    }

    private void getAllList(final String username) {
        this.url = this.baseUrl + "employee/" + username;

        // xource : https://developer.android.com/training/volley/index.html
        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() > 0) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObj = response.getJSONObject(i);
                                    String email = jsonObj.get("email").toString();
                                    String name = jsonObj.get("name").toString();
                                    String role = jsonObj.get("role").toString();
                                    addToRepoList(name, email, role);
                                } catch (JSONException e) {
                                    Log.e("Volley", "Invalid JSON Object.");
                                }

                            }
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // If there a HTTP error then add a note to our repo list.
                        setRepoListText("Error while calling REST API");
                        Log.e("Volley", error.toString());
                    }
                }
        );
        requestQueue.add(arrReq);

    }

    public void getallinfo(View v) {
        clearRepoList();
        getAllList(employeename.getText().toString());
    }
    public void getIdClicked(View v) {
        clearIdList();
        getInfo(userid.getText().toString());
    }

    private void clearIdList() {
        this.listbox2.setText("");
    }

    private void AddtoIdList(String name, String email, String role) {
        String strRow = name + " / " + email + " / " + role;
        String currentText = listbox2.getText().toString();
        this.listbox2.setText(currentText + "\n\n" + strRow);
        //adding this because virtual device starts with the keyboard and might block the view
        this.listbox.setText(currentText + "\n\n" + strRow);
    }

    private void getInfo(final String number) {
        this.url = this.baseUrl + "employee/" + number;
        final JsonObjectRequest newreq = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//                            JSONObject jsonobj = response.getJSONObject("name");
                            String name = response.getString("name");
                            String email = response.getString("email");
                            String role = response.getString("role");
                            AddtoIdList(name, email, role);
                        }catch (JSONException e){
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
    public void NextPage(View v){
        startActivity(new Intent(getApplicationContext(), MainActivity2.class));
    }


}
