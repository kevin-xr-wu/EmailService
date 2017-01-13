package com.example.kevinwu.emailservice;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterUser extends AppCompatActivity {

    private EditText reg_username;
    private EditText reg_password;
    private EditText reg_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        reg_username = (EditText) findViewById(R.id.register_username);
        reg_password = (EditText) findViewById(R.id.register_password);
        reg_age = (EditText) findViewById(R.id.register_age);
    }

    public void registerUser (View view) {

        final String username = reg_username.getText().toString();
        final String password = reg_password.getText().toString();
        final int age = Integer.parseInt(reg_age.getText().toString());
        Log.d("MY TAG", "button clicked");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //volley will return a json string response, so we need to change it to a json object to use it
                Log.d("MY TAG", response);
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    Log.d("MY TAG", "response: " + success);

                    if(success){
                        Log.d("MY TAG", "response succeeded");
                        Intent intent = new Intent(RegisterUser.this, MainActivity.class);
                        startActivity(intent);
                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterUser.this);
                        builder.setMessage("Register failed")
                                .setNegativeButton("retry", null)
                                .create()
                                .show();

                    }

                }catch (JSONException e){
                    Toast.makeText(getApplicationContext(), "On response error", Toast.LENGTH_LONG).show();
                }
            }
        };

        Log.d("MY TAG", "do i ever get here");
        RegisterRequest registerRequest = new RegisterRequest(username, password, age, responseListener);

        //volley has a queue for handling requests, so we have to create this
        RequestQueue queue = Volley.newRequestQueue(RegisterUser.this);
        queue.add(registerRequest);
    }

}
