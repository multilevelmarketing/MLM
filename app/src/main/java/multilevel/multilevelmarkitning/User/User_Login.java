package multilevel.multilevelmarkitning.User;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import multilevel.multilevelmarkitning.R;

public class User_Login extends AppCompatActivity {
    EditText username;
    EditText userpassword;
    TextView usersigup;
    Button userbtn;
    Intent intent;
    String usrid;
    CheckBox checkBox;
    ProgressDialog progressDialog;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    RequestQueue requestQueue;
    ActionBar actionBar;
    //Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__login);
        intent=getIntent();
        username=(EditText)findViewById(R.id.user_userid);
        checkBox=(CheckBox)findViewById(R.id.user_checkBox);
        progressDialog=new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);
        loginPreferences=getSharedPreferences("userLogin",MODE_PRIVATE);
        loginPrefsEditor=loginPreferences.edit();
        userpassword=(EditText)findViewById(R.id.user_userpassword);
        usersigup=(TextView)findViewById(R.id.user_usersignup);
        userbtn=(Button)findViewById(R.id.user_usersubmit);
        actionBar=getSupportActionBar();
        actionBar.setTitle("UserLogin");
        actionBar.setDisplayHomeAsUpEnabled(true);
        if(intent.getStringExtra("userid")!=null) {
            //usrid = intent.getStringExtra("userid");
            //Toast.makeText(getApplicationContext(),usrid,Toast.LENGTH_SHORT).show();
            username.setText(intent.getStringExtra("userid"));
        }
        else
        {
            username.setText(loginPreferences.getString("username",null));
            userpassword.setText(loginPreferences.getString("password",null));
        }

        usersigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),User_Register.class));
            }
        });


        userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserID=username.getText().toString().trim();
                String Password=userpassword.getText().toString().trim();
                if(TextUtils.isEmpty(UserID) && TextUtils.isEmpty(Password))
                {
                    username.setError("please fill the fields");
                    userpassword.setError("please fill the fields");
                }
               else if(TextUtils.isEmpty(UserID) )
                {
                    username.setError("please fill the fields");
                    //userpassword.setError("please fill the fields");
                }
                else if(TextUtils.isEmpty(Password))
                {
                    userpassword.setError("please fill the fields");
                }

                else
                {
                    if(checkBox.isChecked())
                    {
                        loginPrefsEditor.putString("username",UserID);
                        loginPrefsEditor.putString("password",Password);
                        loginPrefsEditor.commit();
                    }
                    if(Password.length()<6)
                    {
                        userpassword.setError("Minimum 6 lengths required");
                    }
                    else
                    {
                        progressDialog.setTitle("Processing...");
                        progressDialog.setMessage("Please wait...");
                        progressDialog.setCancelable(false);
                        progressDialog.setIndeterminate(true);
                        progressDialog.show();



                        UserLogin userLogin=new UserLogin(UserID,Password,new Response.Listener<String>(){
                            @Override

                            public void onResponse(String response) {
                                Log.i("Response", response);

                                try
                                {
                                    if (new JSONObject(response).get("success").equals("true"))
                                    {
                                        progressDialog.dismiss();
                                        Intent intent=new Intent(getApplicationContext(), User_Show.class);
                                        startActivity(intent);
                                        //Toast.makeText(Admin_Login.this, "Account Successfully Created", Toast.LENGTH_SHORT).show();
                                        //finish();
                                    }
                                    else
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Invalid password or user id ", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }


                        });

                        requestQueue.add(userLogin);




                    }

                }
            }
        });
    }

    public class UserLogin extends StringRequest {

        private static final String REGISTER_URL = "https://veiled-heat.000webhostapp.com/MLM/User/user_login.php";
        private Map<String, String> parameters;

        public UserLogin(String Id,String  password, Response.Listener<String> listener) {
            super(Method.POST, REGISTER_URL, listener, null);
            parameters = new HashMap<>();
            parameters.put("loginid",Id);
            parameters.put("password", password);


        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError
        {
            return parameters;
        }
    }




}

