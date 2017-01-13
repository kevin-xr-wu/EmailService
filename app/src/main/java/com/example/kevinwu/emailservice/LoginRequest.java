package com.example.kevinwu.emailservice;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kevin on 1/12/2017.
 */

public class LoginRequest extends StringRequest {
    private static final String REQUEST_REQUEST_URL = "http://emailservice.000webhostapp.com/login.php";
    private Map<String, String> params;

    public LoginRequest(String username, String password, Response.Listener<String> listener){
        super(Method.POST, REQUEST_REQUEST_URL, listener, null);

        //volley needs the data parsed, I use a map
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
