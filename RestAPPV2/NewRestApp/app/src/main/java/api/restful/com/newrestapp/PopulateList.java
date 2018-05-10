package api.restful.com.newrestapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PopulateList {

    private ReqResult mResultCallback = null;
    private Context mContext;
    private static List<Employee> employees = new ArrayList<>();

    PopulateList(ReqResult resultCallback, Context context) {
        mResultCallback = resultCallback;
        mContext = context;
    }

    public static List<Employee> getEmployees(){
        return employees;
    }


    public void postDataVolley(final String requestType, String url, JSONObject sendObj) {
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);

            JsonObjectRequest jsonObj = new JsonObjectRequest(url, sendObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (mResultCallback != null)
                        mResultCallback.notifySuccess(requestType, response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (mResultCallback != null)
                        mResultCallback.notifyError(requestType, error);
                }
            });

            queue.add(jsonObj);

        } catch (Exception e) {

        }
    }

    public void getDataVolley(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        employees.clear();
                        if (response.length() > 0) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObj = response.getJSONObject(i);
                                    String email = jsonObj.get("email").toString();
                                    String name = jsonObj.get("name").toString();
                                    String role = jsonObj.get("role").toString();
                                    int id = (int) jsonObj.get("id");
                                    Employee emp = new Employee(email, name, role, id);
                                    // empMap.put(name,emp);
                                    //  addToList(name, email, role);
                                    employees.add(emp);
                                    System.out.println("We have a " + emp);

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
                        Log.e("Volley", error.toString());
                    }
                }
        );
        requestQueue.add(arrReq);
    }
}









//OLD CODE LEFT HERE FOR REFERENCE
//package api.restful.com.newrestapp;
//
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.widget.ArrayAdapter;
//import android.widget.Spinner;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class PopulateList extends AppCompatActivity{
//
//    private static String baseUrl = "http://10.0.2.2:5000/";
//
//    private static List<Employee> employees = new ArrayList<>();
//
//    public static List<Employee> getEmployees() {
//        return employees;
//    }
//
//    public static void getAllList() {
//        String url = baseUrl + "employee" ;
//        RequestQueue requestQueue =  Volley.newRequestQueue(this);
//        // xource : https://developer.android.com/training/volley/index.html
//        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        dataAdapter.clear();
//                        employees.clear();
//                        if (response.length() > 0) {
//                            for (int i = 0; i < response.length(); i++) {
//                                try {
//                                    JSONObject jsonObj = response.getJSONObject(i);
//                                    String email = jsonObj.get("email").toString();
//                                    String name = jsonObj.get("name").toString();
//                                    String role = jsonObj.get("role").toString();
//                                    int id = (int) jsonObj.get("id");
//                                    Employee emp = new Employee(email,name,role,id);
//                                    dataAdapter.add(emp);
//                                    // empMap.put(name,emp);
//                                    //  addToList(name, email, role);
//                                    employees.add(emp);
//
//                                } catch (JSONException e) {
//                                    Log.e("Volley", "Invalid JSON Object.");
//                                }
//
//                            }
//                        }
//                    }
//                },
//
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // If there a HTTP error then add a note to our repo list.
//                        setListbox("Error while calling REST API");
//                        Log.e("Volley", error.toString());
//                    }
//                }
//        );
//        requestQueue.add(arrReq);
//
//    }
//}
