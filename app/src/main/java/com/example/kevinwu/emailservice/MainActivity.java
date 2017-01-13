package com.example.kevinwu.emailservice;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText myUsername;
    private EditText myPassword;
    private TextView firstNotification;
    private TextView secondNotification;
    private Button loginButton;
    private int attempts = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myUsername = (EditText) findViewById(R.id.login_username);
        myPassword = (EditText) findViewById(R.id.login_password);
        firstNotification = (TextView) findViewById(R.id.login_notification1);
        secondNotification = (TextView) findViewById(R.id.login_notification2);
        loginButton = (Button) findViewById(R.id.login_loginButton);

        //hide the notifications, no attempt has been made at this time
        firstNotification.setVisibility(View.INVISIBLE);
        secondNotification.setVisibility(View.INVISIBLE);
    }

    public void emailLogin (View view) {

        final String username = myUsername.getText().toString();
        final String password = myPassword.getText().toString();
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
                        Toast.makeText(getApplicationContext(), "login successful", Toast.LENGTH_LONG).show();
                        // get data here, pass it to another intent for use
                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Login failed")
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
        LoginRequest loginRequest = new LoginRequest(username, password, responseListener);

        //volley has a queue for handling requests, so we have to create this
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(loginRequest);
//        if(myUsername.getText().toString().equals("Kevin") && myPassword.getText().toString().equals("password")) {
//            Toast.makeText(getApplicationContext(), "Your login was successful", Toast.LENGTH_LONG).show();
//        } else {
//            if(attempts == 0) {
//                loginButton.setEnabled(false);
//                Toast.makeText(getApplicationContext(), "Too many tries. Please restart application", Toast.LENGTH_LONG).show();
//            } else {
//                attempts -= 1;
//                firstNotification.setVisibility(View.VISIBLE);
//                secondNotification.setVisibility(View.VISIBLE);
//            }
//        }
    }

    public void registerService (View view){
        Intent intent = new Intent(this, RegisterUser.class);
        startActivity(intent);
    }
}
