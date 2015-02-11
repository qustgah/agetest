package custom;
import com.android.volley.Response;
import com.android.volley.VolleyError;
public abstract class PstRequstListener implements Response.Listener<String>, Response.ErrorListener{

	@Override
	public void onErrorResponse(VolleyError error) {
		
	}

	@Override
	public void onResponse(String response) {
		
	}
	 public abstract void onSaved(int affectedRows);

}
