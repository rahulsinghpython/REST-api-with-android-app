package api.restful.com.newrestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


public class GetActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageButton btn_getall;
    TextView textView, idview;
    private static String baseUrl = "http://10.0.2.2:5000/";
    String url;
    RequestQueue requestQueue;
    Spinner spinner;
    ArrayAdapter<Employee> dataAdapter;
    List<Employee> employees = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);

        this.spinner = (Spinner) findViewById(R.id.spinner) ;
        this.idview = (TextView)findViewById(R.id.idview) ;

        this.btn_getall = (ImageButton) findViewById(R.id.btn_getall);
        this.textView = (TextView) findViewById(R.id.get_textview);
        requestQueue = Volley.newRequestQueue(this);

        this.textView.setMovementMethod(new ScrollingMovementMethod());


        spinner.setOnItemSelectedListener(this);


        List<Employee> categories = new ArrayList<>();
      /*  categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");*/


        dataAdapter = new ArrayAdapter<Employee>(this, android.R.layout.simple_spinner_item, categories);


        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
        getAllList();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        Employee emp = (Employee)parent.getItemAtPosition(position);

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: "+ emp.getId() + " | " + emp.getEmail() + " | " + emp.getRole(), Toast.LENGTH_LONG).show();
        clearRepoList();
        addToList(emp.getId(), emp.getName(),emp.getEmail(),emp.getRole());
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private void clearRepoList() {
        this.textView.setText("");
    }

    private void addToList(int id, String name, String email, String role) {
        String strRow = id + " / " + name + " / " + email + " / " + role;
        String currentText = textView.getText().toString();
        this.textView.setText(currentText + "\n\n" + strRow);


        requestQueue = Volley.newRequestQueue(this);
    }

    private void setListbox(String str) {
        this.textView.setText(str);
    }

    public void getallinfo(View v) {
        clearRepoList();
     //   getAllList();
        for(Employee emp : employees) {
            addToList(emp.getId(), emp.getName(),emp.getEmail(),emp.getRole());
        }
    }

    public void getAllList() {
        String url = baseUrl + "employee" ;

        // xource : https://developer.android.com/training/volley/index.html
        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        dataAdapter.clear();
                        employees.clear();
                        if (response.length() > 0) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObj = response.getJSONObject(i);
                                    String email = jsonObj.get("email").toString();
                                    String name = jsonObj.get("name").toString();
                                    String role = jsonObj.get("role").toString();
                                    int id = (int) jsonObj.get("id");
                                    Employee emp = new Employee(email,name,role,id);
                                    dataAdapter.add(emp);
                                   // empMap.put(name,emp);
                                  //  addToList(name, email, role);
                                    employees.add(emp);

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
                        setListbox("Error while calling REST API");
                        Log.e("Volley", error.toString());
                    }
                }
        );
        requestQueue.add(arrReq);
    }



    public void increaseid(View v){
        int currentid = Integer.valueOf(idview.getText().toString());
        currentid += 1;
        idview.setText("" + currentid);
    }

    public void decreaseid(View v){
        int currentid = Integer.valueOf(idview.getText().toString());
        if (currentid >= 0) {
            currentid -= 1;
        }
        idview.setText("" + currentid);
    }

    public void submit(View v) {
        int currentid = Integer.valueOf(idview.getText().toString());
        if (employees.isEmpty()) {
            System.out.println("SQL DATABASE DID NOT LOAD");
        } else {
            Employee emp = employees.get(currentid - 1);
            clearRepoList();
            String newemp = emp.getName() + " | " + emp.getEmail() + " | " + emp.getRole();
            setListbox(newemp);
        }
    }

}