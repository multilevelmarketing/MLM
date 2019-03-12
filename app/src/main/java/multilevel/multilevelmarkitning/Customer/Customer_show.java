package multilevel.multilevelmarkitning.Customer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class Customer_show extends AppCompatActivity {

    TextView tvid,tvname,tvprofession,tvmobile,tvaddress,tvemail,tvparent,tvlevel,tvgender;
    Button update;
    RequestQueue requestQueue;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ActionBar actionBar;
    String id=new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_show);
        tvid=(TextView)findViewById(R.id.cus_profile_id);
        tvname=(TextView)findViewById(R.id.cus_profile_name);
        tvprofession=(TextView)findViewById(R.id.cus_profile_profession);
        tvmobile=(TextView)findViewById(R.id.cus_profile_mobile);
        tvaddress=(TextView)findViewById(R.id.cus_profile_address);
        tvemail=(TextView)findViewById(R.id.cus_profile_email);
        tvparent=(TextView)findViewById(R.id.cus_profile_parent);
        tvlevel=(TextView)findViewById(R.id.cus_profile_level);
        tvgender=(TextView)findViewById(R.id.cus_profile_gender);
        update=(Button)findViewById(R.id.cus_profile_update);
        requestQueue= Volley.newRequestQueue(this);

        pref=getSharedPreferences("cusLogin",MODE_PRIVATE);
        editor=pref.edit();
        id=pref.getString("customerid",null);
        tvid.setText(pref.getString("customerid",null));

        actionBar=getSupportActionBar();
        actionBar.setTitle("Customer Profile");
        actionBar.setDisplayHomeAsUpEnabled(true);





        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(getApplicationContext(),Customer_update.class);
                it.putExtra("id",id);
                startActivity(it);
            }
        });




        CustomerProfile customerProfile=new CustomerProfile(id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

                Log.i("Response",response);

                try
                {

                    if (new JSONObject(response).getString("success").equals("true"))
                    {
                        tvname.setText(new JSONObject(response).getString("RegName"));
                        tvprofession.setText(new JSONObject(response).getString("RegProfession"));
                        tvlevel.setText(new JSONObject(response).getString("RegLevel"));
                        tvparent.setText(new JSONObject(response).getString("RegParent"));
                        tvemail.setText(new JSONObject(response).getString("RegEmail"));
                        tvmobile.setText(new JSONObject(response).getString("RegMobile"));
                        tvgender.setText(new JSONObject(response).getString("RegGender"));
                        tvaddress.setText(new JSONObject(response).getString("RegAddress"));


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

        requestQueue.add(customerProfile);
    }




    public class CustomerProfile extends StringRequest
    {
        private static final String URL="https://veiled-heat.000webhostapp.com/MLM/Customer/customer_profile.php";
        private Map<String,String> paremeters;

        public CustomerProfile(String userid, Response.Listener<String> listener) {
            super(Method.POST, URL, listener, null);
            paremeters = new HashMap<>();
            paremeters.put("cusid",userid);
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError
        {
            return paremeters;
        }
    }


}
