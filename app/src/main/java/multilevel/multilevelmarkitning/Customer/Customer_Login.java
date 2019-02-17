package multilevel.multilevelmarkitning.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Customer_Login extends AppCompatActivity {

    EditText userid;
    EditText password;
    Button loginbtn;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__login);
        userid=(EditText)findViewById(R.id.cus_login_id);
        password=(EditText)findViewById(R.id.cus_login_password);
        loginbtn=(Button) findViewById(R.id.cus_login_btn);
        requestQueue = Volley.newRequestQueue(Customer_Login.this);



        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserID=userid.getText().toString().trim();
                String Password=password.getText().toString().trim();
                if(TextUtils.isEmpty(UserID) || TextUtils.isEmpty(Password))
                {
                    userid.setError("please fill the fields");
                    password.setError("please fill the fields");
                }
                else
                {
                    if(Password.length()<6)
                    {
                        password.setError("Minimum 6 lengths required");
                    }
                    else
                    {

                            CustomerLogin customerLogin=new CustomerLogin(UserID,Password,new Response.Listener<String>(){
                                @Override

                                public void onResponse(String response) {
                                    Log.i("Response", response);

                                    try
                                    {
                                        if (new JSONObject(response).getBoolean("success"))
                                        {

                                            Intent intent=new Intent(getApplicationContext(), Customer_Home_Page.class);
                                            startActivity(intent);
                                            //Toast.makeText(Admin_Login.this, "Account Successfully Created", Toast.LENGTH_SHORT).show();
                                            //finish();
                                        }
                                        else
                                            Toast.makeText(getApplicationContext(), "Invalid password or user id ", Toast.LENGTH_SHORT).show();
                                    }
                                    catch (JSONException e)
                                    {
                                        e.printStackTrace();
                                    }
                                }


                            });

                        requestQueue.add(customerLogin);


                    }

//
//                    Intent intent=new Intent(getApplicationContext(),Customer_Home_Page.class);
//                    startActivity(intent);
                }

            }
        });






    }

//    public void CustomerLogin_Submit(View view) {
//
//        startActivity(new Intent(this,Customer_Home_Page.class));
//    }

    public class CustomerLogin extends StringRequest {

        private static final String REGISTER_URL = "https://veiled-heat.000webhostapp.com/MLM/Customer/customer_reg.php";
        private Map<String, String> parameters;

        public CustomerLogin(String Id,String  password, Response.Listener<String> listener) {
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
