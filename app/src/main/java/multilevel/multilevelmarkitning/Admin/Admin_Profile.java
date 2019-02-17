package multilevel.multilevelmarkitning.Admin;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.android.volley.Response;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarException;

import multilevel.multilevelmarkitning.R;

import static android.content.Context.MODE_PRIVATE;

public class Admin_Profile extends Fragment {

     TextView id,adminname,Companyname,Password,Email,Pn,Gender,Address;
     Button Admin_Update;
     RequestQueue requestQueue;
     String userId;


     @Nullable
     @Override
    public View onCreateView( @Nullable LayoutInflater inflater,  @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
       View mview=inflater.inflate(R.layout.admin_profile, container, false);


        SharedPreferences pref =this.getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        userId=pref.getString("RegId","null");
        Boolean login=pref.getBoolean("IsLogin",false);


        id=(TextView)mview.findViewById(R.id.Admin_Id);
        adminname=(TextView)mview.findViewById(R.id.ad_user_name);
        Companyname=(TextView)mview.findViewById(R.id.ad_cmp_name);
        Password=(TextView)mview.findViewById(R.id.ad_pass);
        Email=(TextView)mview.findViewById(R.id.ad_email);
        Pn=(TextView)mview.findViewById(R.id.ad_ph);
        Gender=(TextView)mview.findViewById(R.id.ad_gender);
        Address=(TextView)mview.findViewById(R.id.ad_address);
        Admin_Update=(Button)mview.findViewById(R.id.admin_update);

        requestQueue = Volley.newRequestQueue(mview.getContext());
        myfunction(mview);


        Admin_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(),Admin_Update.class));
            }
        });

        //id.setText(Id);

        return mview;

    }


    public void myfunction(final View view)
    {


      AdminProfile adminProfile=new AdminProfile(userId, new Response.Listener<String>() {
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

              catch (JSONException  e)
              {

                  e.printStackTrace();
              }

          }
      });

      requestQueue.add(adminProfile);

    }


    public class AdminProfile extends StringRequest
    {
        private static final String URL="https://veiled-heat.000webhostapp.com/MLM/Admin/profile.php";
        private Map<String,String>paremeters;

        public AdminProfile(String userid, Response.Listener<String> listener) {
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

}
