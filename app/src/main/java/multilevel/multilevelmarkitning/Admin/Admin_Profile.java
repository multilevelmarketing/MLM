package multilevel.multilevelmarkitning.Admin;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import multilevel.multilevelmarkitning.R;

import static android.content.Context.MODE_PRIVATE;

public class Admin_Profile extends Fragment {

     TextView id,username;

     @Nullable
    @Override
    public View onCreateView( @Nullable LayoutInflater inflater,  @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
       View mview=inflater.inflate(R.layout.admin_profile, container, false);


        SharedPreferences pref =this.getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        String Id=pref.getString("RegId","");
        Boolean login=pref.getBoolean("IsLogin",false);
        id=(TextView)mview.findViewById(R.id.Admin_Id);
        id.setText(Id);

        return mview;



    }

}
