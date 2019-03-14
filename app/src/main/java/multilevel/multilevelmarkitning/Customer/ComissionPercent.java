package multilevel.multilevelmarkitning.Customer;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ComissionPercent extends StringRequest {

    private static final String REGISTER_URL = "https://veiled-heat.000webhostapp.com/MLM/Customer/commisionpercent.php";
    private Map<String, String> parameters;

    public ComissionPercent(String Id, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("cus_id",Id);


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        return parameters;
    }
}
