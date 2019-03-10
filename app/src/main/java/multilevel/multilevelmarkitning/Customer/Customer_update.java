package multilevel.multilevelmarkitning.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

 class Customer_update extends AppCompatActivity {

    Intent intent;
    RequestQueue requestQueue;
    EditText editTextid,editTextname,editTextprofession,editTextparentid,editTextemail,editTextmobile,editTextaddress,editTextpassword;
    Spinner spinnergender;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_customer_update);
        setContentView(R.layout.activity_customer_update);
        intent=getIntent();

        final String id = intent.getStringExtra("id");
        editTextid = (EditText) findViewById(R.id.cusupdate_id);
        editTextname = (EditText) findViewById(R.id.cusupdate_username);
        editTextprofession = (EditText) findViewById(R.id.cusupdate_prof);
        editTextparentid = (EditText) findViewById(R.id.cusupdate_parent);
        editTextemail = (EditText) findViewById(R.id.cusupdate_emailid);
        editTextmobile = (EditText) findViewById(R.id.cusupdate_usermobile);
        editTextaddress = (EditText) findViewById(R.id.cusupdate_addres);
        editTextpassword = (EditText) findViewById(R.id.cusupdate_pass);
        spinnergender = (Spinner) findViewById(R.id.cusupdate_gender);
        button = (Button) findViewById(R.id.cus_updatebtn);
        requestQueue = Volley.newRequestQueue(Customer_update.this);
        editTextid.setEnabled(false);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Customer_update.this,R.array.spinnergender,R.layout.support_simple_spinner_dropdown_item);
        spinnergender.setAdapter(adapter);


        CustomerView customerView=new CustomerView(id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

                Log.i("Response",response);

                try
                {

                    if (new JSONObject(response).getString("success").equals("true"))
                    {
                        editTextid.setText(new JSONObject(response).getString("RegId"));
                        editTextname.setText(new JSONObject(response).getString("RegName"));
                        editTextprofession.setText(new JSONObject(response).getString("RegProfession"));
                        editTextpassword.setText(new JSONObject(response).getString("RegPassword"));
                     //   .setText(new JSONObject(response).getString("RegLevel"));
                        editTextparentid.setText(new JSONObject(response).getString("RegParent"));
                        editTextemail.setText(new JSONObject(response).getString("RegEmail"));
                        editTextmobile.setText(new JSONObject(response).getString("RegMobile"));
                        spinnergender.setSelection(adapter.getPosition(new JSONObject(response).getString("RegGender")));
                      //  tvgender.setText(new JSONObject(response).getString("RegGender"));
                        editTextaddress.setText(new JSONObject(response).getString("RegAddress"));


                    }

                    else
                    {

                        Toast.makeText(getApplicationContext(),"id not found",Toast.LENGTH_LONG).show();
                    }


                }

                catch (JSONException e)
                {

                    e.printStackTrace();
                }

            }
        });



        requestQueue.add(customerView);





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=editTextname.getText().toString().trim();
                String profession=editTextprofession.getText().toString().trim();
                String parent=editTextparentid.getText().toString().trim();
                String mobile=editTextmobile.getText().toString().trim();
                String address=editTextaddress.getText().toString().trim();
                String gender=spinnergender.getSelectedItem().toString().trim();
                String password=editTextpassword.getText().toString().trim();
                String email=editTextemail.getText().toString().trim();


                CustomerUpdate customerUpdate=new CustomerUpdate(id,name,profession,parent,password,email,mobile,gender,address,new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response", response);

                        try
                        {
                            if (new JSONObject(response).getString("status").equals("true"))
                            {

                                Toast.makeText(Customer_update.this, "Account updated successfully", Toast.LENGTH_LONG).show();

                                startActivity(new Intent(Customer_update.this, Customer_show.class));

                                //finish();
                            }
                            else
                                Toast.makeText(getApplicationContext(), "Something Has Happened. Please Try Again!", Toast.LENGTH_SHORT).show();
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }

                });
                requestQueue.add(customerUpdate);





            }
        });



    }

    public class CustomerView extends StringRequest
    {
        private static final String url="https://veiled-heat.000webhostapp.com/MLM/Customer/customer_profile.php";
        private Map<String,String> parameters;



        public CustomerView(String userid, Response.Listener<String> listener)
        {
            super(Method.POST,url,listener,null);
            parameters=new HashMap<>();
            parameters.put("cusid",userid);
        }
        protected Map<String,String> getParams() throws AuthFailureError
        {
            return parameters;
        }

    }

    public class CustomerUpdate  extends StringRequest
    {
        private static final String url="https://veiled-heat.000webhostapp.com/MLM/Customer/cus_update.php";
        private Map<String,String> parameters;



        public CustomerUpdate(String userid,String name,String profession,String parentid,String password,String email,String mobile,String gender,String address ,Response.Listener<String> listener)
        {
            super(Method.POST,url,listener,null);
            parameters=new HashMap<>();
            parameters.put("cusid",userid);
            parameters.put("name",name);
            parameters.put("profession",profession);
            parameters.put("parentid",parentid);
            parameters.put("password",password);
            parameters.put("mobile",mobile);
            parameters.put("gender",gender);
            parameters.put("address",address);
            parameters.put("email",email);
        }
        protected Map<String,String> getParams() throws AuthFailureError
        {
            return parameters;
        }

    }
}
