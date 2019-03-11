package multilevel.multilevelmarkitning.User;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import multilevel.multilevelmarkitning.IdGenerator;
import multilevel.multilevelmarkitning.R;
import multilevel.multilevelmarkitning.Validation;

public class User_Register extends AppCompatActivity {

    EditText user_name,company,password,email,mobile,user_address;
    Button submit;
    AlertDialog.Builder builder;
    private ProgressDialog pDialog;
    RadioGroup radioGroupsex;
    RadioButton radioButtonsex;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__register);
        user_name=(EditText)findViewById(R.id.user_reg_id);
        company=(EditText)findViewById(R.id.user_reg_cname);
        email=(EditText)findViewById(R.id.user_reg_email);
        password=(EditText)findViewById(R.id.user_reg_pass);
        mobile=(EditText)findViewById(R.id.user_usermobile);
        submit=(Button)findViewById(R.id.user_sumitbtn);
        user_address=(EditText)findViewById(R.id.user_addres);
        radioGroupsex=(RadioGroup)findViewById(R.id.radiogroup);
        builder= new AlertDialog.Builder(this);

        requestQueue = Volley.newRequestQueue(User_Register.this);

        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                final String User_Name=user_name.getText().toString().trim();
                final String Id=IdGenerator.generateId(User_Name);
                String Company=company.getText().toString().trim();
                String Email=email.getText().toString().trim();
                String Password=password.getText().toString().trim();
                String Mobile=mobile.getText().toString().trim();
                String Address=user_address.getText().toString().trim();


                if( TextUtils.isEmpty(User_Name) || TextUtils.isEmpty(Password) || TextUtils.isEmpty(Company) || TextUtils.isEmpty(Password) || TextUtils.isEmpty(Mobile) ||TextUtils.isEmpty(Address))
                {
                   Toast.makeText(getApplicationContext(),"please fill all the fields",Toast.LENGTH_SHORT).show();
                }
                else
                {



                    if(Password.length()<6)
                    {
                        password.setError("Minimum 6 lengths required");
                    }
                    else if(!Validation.emailValidation(Email))
                    {
                        email.setError("Please Enter  valid Email address");
                    }
                    else if(!Validation.mobileValidation(Mobile))
                    {
                        mobile.setError("Please Enter the Valid Number");
                    }
                    else if(radioGroupsex.getCheckedRadioButtonId()==-1)
                    {
                        Toast.makeText(getApplicationContext(), "Please select Gender", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        int selectedId = radioGroupsex.getCheckedRadioButtonId();
                        radioButtonsex = (RadioButton)findViewById(selectedId);
                        String Gender=radioButtonsex.getText().toString().trim();

                        RegisterRequest registerRequest = new RegisterRequest(Id,User_Name, Company, Password,Email,Mobile,Gender,Address ,new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Response", response);

                                try
                                {
                                    if (new JSONObject(response).getString("status").equals("register"))
                                    {

                                        //  Toast.makeText(Admin_Register.this, "Account Successfully Created", Toast.LENGTH_LONG).show();

                                     //   final String idgenerated = UUID.randomUUID().toString();
                                      //  final String idgenerated = IdGenerator.generateId(User_Name);

                                        builder.setMessage("Your ID is "+Id);
                                        builder.setCancelable(true);
                                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                                Intent intent=new Intent(getApplicationContext(),User_Login.class);
                                                intent.putExtra("userid",Id);
                                                startActivity(intent);
                                            }
                                        });

                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.setTitle("Succesfully register");
                                        alertDialog.show();


                                        //finish();
                                    }
                                    else
                                        Toast.makeText(User_Register.this, "Something Has Happened. Please Try Again!", Toast.LENGTH_SHORT).show();
                                }
                                catch (JSONException e)
                                {
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



}
