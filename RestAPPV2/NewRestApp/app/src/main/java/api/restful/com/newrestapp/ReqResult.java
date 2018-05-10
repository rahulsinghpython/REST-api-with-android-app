package api.restful.com.newrestapp;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface ReqResult {
    public void notifySuccess(String requestType,JSONObject response);
    public void notifyError(String requestType,VolleyError error);
}
