package multilevel.multilevelmarkitning.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Admin_Login extends AppCompatActivity {

    EditText userid;
    EditText password;
    Button loginbtn;
    TextView signup;
    RequestQueue requestQueue;
    CheckBox checkBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    ProgressDialog progressDialog;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__login);
        userid=(EditText)findViewById(R.id.admin_id);
        password=(EditText)findViewById(R.id.admin_password);
        loginbtn=(Button) findViewById(R.id.admin_submit);
        signup=(TextView)findViewById(R.id.admin_sigup);
        checkBox=(CheckBox)findViewById(R.id.admin_checkBox);
        progressDialog=new ProgressDialog(this);
        loginPreferences=getSharedPreferences("adminLogin",MODE_PRIVATE);
        loginPrefsEditor=loginPreferences.edit();
        intent=getIntent();
        String intentid=intent.getStringExtra("userid");
        if(intentid!=null)
        {
            userid.setText(intentid);
        }
        else
        {
            userid.setText(loginPreferences.getString("username",null));
            password.setText(loginPreferences.getString("password",null));

        }



        requestQueue= Volley.newRequestQueue(Admin_Login.this);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Admin_Login.this,Admin_Register.class));

            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String UserID=userid.getText().toString().trim();
                String Password=password.getText().toString().trim();
                if(TextUtils.isEmpty(UserID) && TextUtils.isEmpty(Password))
                {
                    userid.setError("please fill the fields");
                    password.setError("please fill the fields");
                }

                else if(TextUtils.isEmpty(UserID))
                {
                    userid.setError("Please fill the UserId");

                }
                else if(TextUtils.isEmpty(Password))
                {
                    password.setError("Please fill the Password");
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
                        password.setError("Minimum 6 lengths required");
                    }
                    else
                    {
                        progressDialog.setTitle("Processing...");
                        progressDialog.setMessage("Please wait...");
                        progressDialog.setCancelable(false);
                        progressDialog.setIndeterminate(true);
                        progressDialog.show();
                        AdminLogin adminLogin=new AdminLogin(UserID,Password,new Response.Listener<String>(){
                          @Override

                          public void onResponse(String response) {
                              Log.i("Response", response);

                              try
                              {
                                  if (new JSONObject(response).getBoolean("success"))
                                  {

                                      progressDialog.dismiss();
                                      SharedPreferences pref = getSharedPreferences("Login", MODE_PRIVATE);
                                      SharedPreferences.Editor editor = pref.edit();
                                      editor.putString("RegId", UserID);
                                      editor.putBoolean("IsLogin", true);
                                      editor.commit();

                                      Intent intent=new Intent(getApplicationContext(), Admin_Panel.class);
                                      startActivity(intent);
                                  }
                                  else
                                  {
                                      progressDialog.dismiss();
                                      Toast.makeText(Admin_Login.this, "Invalid password or user id", Toast.LENGTH_SHORT).show();

                                  }
                              }

                              catch (JSONException e)
                              {
                                  e.printStackTrace();
                              }
                          }


                      });

                        requestQueue.add(adminLogin);

                    }

                }
            }
        });

    }

    public class AdminLogin extends StringRequest {

        private static final String REGISTER_URL = "https://veiled-heat.000webhostapp.com/MLM/Admin/Login.php ";
        private Map<String, String> parameters;

        public AdminLogin(String Id, String password, Response.Listener<String> listener) {
            super(Method.POST, REGISTER_URL, listener, null);
            parameters = new HashMap<>();
            parameters.put("adminid",Id);
            parameters.put("password", password);


        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError
        {
            return parameters;
        }
    }






}





