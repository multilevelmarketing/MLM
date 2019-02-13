package multilevel.multilevelmarkitning.Admin;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

class RegisterRequest extends StringRequest {

    private static final String REGISTER_URL = "https://veiled-heat.000webhostapp.com/MLM/Admin/Admin_register.php";
    private Map<String, String> parameters;

    public RegisterRequest(String Id,String name, String company, String pass,String email,  String mobile,String Gender,String Address, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_URL, listener,null);
        parameters = new HashMap<>();
        parameters.put("adminid",Id);
        parameters.put("name", name);
        parameters.put("companyname", company);
        parameters.put("password", pass);
        parameters.put("email", email);
        parameters.put("mobile", mobile);
        parameters.put("gender",Gender);
        parameters.put("address",Address);


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
