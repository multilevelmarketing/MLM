package multilevel.multilevelmarkitning.Customer;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import multilevel.multilevelmarkitning.Admin.Admin_Profile;
import multilevel.multilevelmarkitning.Admin.Admin_Update;
import multilevel.multilevelmarkitning.R;

public class Customer_show extends AppCompatActivity {

    TextView tvid,tvname,tvprofession,tvmobile,tvaddress,tvemail,tvparent,tvlevel,tvgender;
    Button update;
    RequestQueue requestQueue;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
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
        String id=pref.getString("customerid",null);
        tvid.setText(pref.getString("customerid",null));




        CustomerProfile customerProfile=new CustomerProfile(id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

                Log.i("Response",response);

                try
                {

                    if (new JSONObject(response).getString("success").equals("true"))
                    {
                        id.setText(new JSONObject(response).getString("RegId"));
                        adminname.setText(new JSONObject(response).getString("RegName"));
                        Companyname.setText(new JSONObject(response).getString("RegCmp"));
                        Password.setText(new JSONObject(response).getString("RegPass"));
                        Email.setText(new JSONObject(response).getString("RegEmail"));
                        Pn.setText(new JSONObject(response).getString("RegMobile"));
                        Gender.setText(new JSONObject(response).getString("RegGender"));
                        Address.setText(new JSONObject(response).getString("RegAddress"));


                    }

                    else
                    {

                        Toast.makeText(getContext(),"Something Has Happened. Please Try Again",Toast.LENGTH_LONG).show();
                    }


                }

                catch (JSONException e)
                {

                    e.printStackTrace();
                }

            }
        });

        requestQueue.add(adminProfile);





    }
}
