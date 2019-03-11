package multilevel.multilevelmarkitning.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.util.UUID;

import multilevel.multilevelmarkitning.R;
import multilevel.multilevelmarkitning.User.User_Login;

public class Admin_Update extends AppCompatActivity
{
    EditText company,user_name,email,password,mobile,user_id,user_address,gender;
    RequestQueue requestQueue;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__update);
        user_id=(EditText)findViewById(R.id.adminup_id);
        user_name=(EditText)findViewById(R.id.adminup_username);
        company=(EditText)findViewById(R.id.adminup_cname);
        email=(EditText)findViewById(R.id.adminup_email);
        password=(EditText)findViewById(R.id.adminup_pass);
        mobile=(EditText)findViewById(R.id.adminup_usermobile);
        user_address=(EditText)findViewById(R.id.adminup_addres);
        gender=(EditText)findViewById(R.id.adminup_gender);


        SharedPreferences pref =this.getSharedPreferences("Login", MODE_PRIVATE);
        userId=pref.getString("RegId",null);
        Boolean login=pref.getBoolean("IsLogin",false);


        requestQueue= Volley.newRequestQueue(Admin_Update.this);

        AdminUpdate adminUpdate=new AdminUpdate(userId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("Response",response);

                try
                {
                    if(new JSONObject(response).getString("success").equals("true"))
                    {
                        user_id.setText(new JSONObject(response).getString("RegId"));
                        user_name.setText(new JSONObject(response).getString("RegName"));
                        company.setText(new JSONObject(response).getString("RegCompany"));
                        password.setText(new JSONObject(response).getString("RegPass"));
                        email.setText(new JSONObject(response).getString("RegEmail"));
                        mobile.setText(new JSONObject(response).getString("RegMobile"));
                        gender.setText(new JSONObject(response).getString("RegGender"));
                        user_address.setText(new JSONObject(response).getString("RegAddress"));

                    }

                    else
                    {

                        Toast.makeText(Admin_Update.this, "Something Has Happened. Please Try Again!", Toast.LENGTH_SHORT).show();
                    }

                }

                catch (JSONException e)
                {
                    e.printStackTrace();
                }


            }

        });
        requestQueue.add(adminUpdate);
    }



    public void adminupdate(View view)
    {

           String updatename=user_name.getText().toString().trim();
           String updateemail=email.getText().toString().trim();
           String updatepass=password.getText().toString().trim();
           String updatemobile=mobile.getText().toString().trim();
           String updateaddress=user_address.getText().toString();

         UpdateValue updateValue=new UpdateValue(updatename,updateemail,updatepass,updatemobile,updateaddress, new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
                 Log.i("Response", response);

                 try
                 {
                     if (new JSONObject(response).getString("status").equals("update"))
                     {

                          Toast.makeText(Admin_Update.this, "Account Successfully Created", Toast.LENGTH_LONG).show();

                          startActivity(new Intent(Admin_Update.this,Admin_Profile.class));

                         //finish();
                     }
                     else
                         Toast.makeText(Admin_Update.this, "Something Has Happened. Please Try Again!", Toast.LENGTH_SHORT).show();
                 }
                 catch (JSONException e)
                 {
                     e.printStackTrace();
                 }
             }



         });



         requestQueue.add(updateValue);







    }


    public class AdminUpdate extends StringRequest
    {
        private static final String URL="https://veiled-heat.000webhostapp.com/MLM/Admin/profile.php";
        private Map<String,String> paremeters;

        public AdminUpdate(String userid, Response.Listener<String> listener) {
            super(Method.POST, URL, listener, null);
            paremeters = new HashMap<>();
            paremeters.put("adminid",userid);
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError
        {
            return paremeters;
        }
    }

    public class UpdateValue extends StringRequest
    {
         private static final String URL="https://veiled-heat.000webhostapp.com/MLM/Admin/Admin_Update.php";
          Map<String,String>response;


        public UpdateValue(String name,String email,String pass,String mobile,String address,Response.Listener<String> listener) {
            super(Method.POST, URL, listener, null);
            String id=userId;

            response=new HashMap<String,String>();
            response.put("adminid",id);
            response.put("name",name);
            response.put("email",email);
            response.put("mobile",mobile);
            response.put("address",address);
            response.put("password",pass);
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return response;
        }

    }


}
