package multilevel.multilevelmarkitning.Customer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import multilevel.multilevelmarkitning.IdGenerator;
import multilevel.multilevelmarkitning.R;
import multilevel.multilevelmarkitning.User.Create_Customer;
import multilevel.multilevelmarkitning.Validation;

public class customer_create_customer extends AppCompatActivity {

    Spinner spinner;
    String[] level;
    EditText name, location, profession, mobile, password, email;
    Button createbtn;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    RequestQueue requestQueue;
    String Level;
    AlertDialog.Builder builder;
    SharedPreferences pref;
    String id;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_create_customer);
        name = (EditText) findViewById(R.id.cus_user_name);
        builder = new AlertDialog.Builder(this);
        location = (EditText) findViewById(R.id.cus_user_address);
        profession = (EditText) findViewById(R.id.cus_user_profession);
        mobile = (EditText) findViewById(R.id.cus_user_mobile);

        //foruser find the who is parent
        pref = getSharedPreferences("userLogin", MODE_PRIVATE);
        editor = pref.edit();
        final String parent = pref.getString("username", null);


        //find the level of parent
        pref = getSharedPreferences("cusLogin", MODE_PRIVATE);
        editor = pref.edit();
        id = pref.getString("customerid", null);

        requestQueue = Volley.newRequestQueue(customer_create_customer.this);
        radioSexGroup = (RadioGroup) findViewById(R.id.cus_radiogrp);
        password = (EditText) findViewById(R.id.cus_user_password);
        email = (EditText) findViewById(R.id.cus_user_email);
        createbtn = (Button) findViewById(R.id.create_cus_btn);

        spinner = (Spinner) findViewById(R.id.level);

        spinnerfunction();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Level = ((TextView) view).getText().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        requestQueue = Volley.newRequestQueue(customer_create_customer.this);


        createbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString().trim();
                String Location = location.getText().toString().trim();
                String Profession = profession.getText().toString().trim();
                String Mobile = mobile.getText().toString().trim();
                String Password = password.getText().toString().trim();
                String Email = email.getText().toString().trim();


                if (TextUtils.isEmpty(Name) || TextUtils.isEmpty(Location) || TextUtils.isEmpty(Profession) || TextUtils.isEmpty(Mobile) || TextUtils.isEmpty(Password) || TextUtils.isEmpty(Email)) {
                    Toast.makeText(getApplicationContext(), "please fill all  the fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (Password.length() < 6) {

                        password.setError("password must be atleast 6 characters");
                    } else if (!Validation.emailValidation(Email)) {

                        email.setError("please enter a valid email address");
                    } else if (!Validation.mobileValidation(Mobile)) {
                        mobile.setError("please enter a valid mobile number");
                    } else if (radioSexGroup.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(getApplicationContext(), "Please select your gender", Toast.LENGTH_SHORT).show();
                    } else {
                        int selectedId = radioSexGroup.getCheckedRadioButtonId();
                        radioSexButton = (RadioButton) findViewById(selectedId);
                        String sex = radioSexButton.getText().toString().trim();

                        final String idgenerated = IdGenerator.generateId(Name);


                        CustomerRegister registerRequest = new CustomerRegister(idgenerated, Name, Mobile, Email, Password, Profession, Level, sex, Location, parent, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Response", response);

                                try {
                                    if (new JSONObject(response).getString("status").equals(("true"))) {

                                        Toast.makeText(customer_create_customer.this, "Account Successfully Created", Toast.LENGTH_LONG).show();

                                        builder.setMessage("Your ID is " + idgenerated);
                                        builder.setCancelable(true);
                                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                                Intent intent = new Intent(getApplicationContext(), Customer_Login.class);
                                                intent.putExtra("userid", idgenerated);
                                                startActivity(intent);
                                            }
                                        });

                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.setTitle("Succesfully register");
                                        alertDialog.show();


                                    } else
                                        Toast.makeText(customer_create_customer.this, "Something Has Happened. Please Try Again!", Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        requestQueue.add(registerRequest);


                    }
                }
            }
        });


    }

    class CustomerRegister extends StringRequest {

        private static final String REGISTER_URL = "https://veiled-heat.000webhostapp.com/MLM/Customer/customer_reg.php";
        private Map<String, String> parameters;

        public CustomerRegister(String id, String name, String mobile, String email, String password, String professsion, String level, String gender, String location, String Parent, Response.Listener<String> listener) {
            super(Method.POST, REGISTER_URL, listener, null);
            parameters = new HashMap<>();
            parameters.put("name", name);
            parameters.put("password", password);
            parameters.put("email", email);
            parameters.put("mobile", mobile);
            parameters.put("id", id);
            parameters.put("profession", professsion);
            parameters.put("level", level);
            parameters.put("gender", gender);
            parameters.put("location", location);
            parameters.put("parent", Parent);

        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return parameters;
        }
    }


    private void spinnerfunction()
    {

        pref = getSharedPreferences("cusLogin", MODE_PRIVATE);


        String id=pref.getString("customerid", null);

        CustomerId customerId=new CustomerId(id,new Response.Listener<String>(){
            @Override

            public void onResponse(String response) {
                Log.i("Response", response);
                // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                try
                {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject jsonObject=null;
                    String[] heroes = new String[jsonArray.length()];
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        jsonObject=jsonArray.getJSONObject(i);
                        heroes[i] = jsonObject.getString("Level");

                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(customer_create_customer.this, android.R.layout.simple_list_item_1, heroes);
                    spinner.setAdapter(arrayAdapter);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


//                try
//                {
//                   Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
//
//                }
//                catch (JSONException e)
//                {
//                    e.printStackTrace();
//                }
            }


        });

        requestQueue.add(customerId);







    }





    public class CustomerId extends StringRequest {

        private static final String REGISTER_URL = "https://veiled-heat.000webhostapp.com/MLM/Customer/customer_level.php";
        private Map<String, String> parameters;

        public CustomerId(String Id,Response.Listener<String> listener) {
            super(Method.POST, REGISTER_URL, listener, null);
            parameters = new HashMap<>();
            parameters.put("id",Id);
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError
        {
            return parameters;
        }
    }
}


