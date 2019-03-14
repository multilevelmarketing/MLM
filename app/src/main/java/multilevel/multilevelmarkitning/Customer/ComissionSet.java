package multilevel.multilevelmarkitning.Customer;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ComissionSet extends StringRequest {

    private static final String REGISTER_URL = "https://veiled-heat.000webhostapp.com/MLM/comission/set_comission.php";
    private Map<String, String> parameters;

    public ComissionSet(String CusId,String ProdId,String price ,String commission, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("cus_id",CusId);
        parameters.put("pro_id",ProdId);
        parameters.put("price",price);
        parameters.put("com",commission);


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        return parameters;
    }
}
