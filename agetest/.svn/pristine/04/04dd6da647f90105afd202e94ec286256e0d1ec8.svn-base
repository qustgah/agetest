package custom;

import java.util.logging.Logger;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PstRequst extends StringRequest{
	 private static final String HUABAN_API =
	            "https://huaban.com/favorite/data_presentation/?limit=" + 50;

	    private final Gson mGson;
//	    private final DatabaseHelper mDatabaseHelper;
	    private final PstRequstListener mListener;
	    private int mAffectedRows = 0;
	    public PstRequst(long max, long since, PstRequstListener listener) {
	        super(getApiUrl(max, since), listener, listener);

	        mGson = new Gson();
//	        mDatabaseHelper = Huaban.getInstance().getDatabaseHelper();
	        mListener = listener;
	    }
		private static  String getApiUrl(long max, long since) {
			 String tail = "";
		        if (since > 0) {
		            tail += "&since=" + since;
		        }

		        if (max > 0) {
		            tail += "&max=" + max;
		        }

		        String api = HUABAN_API + tail + "&t=" + System.currentTimeMillis();
		        Log.i("Request JSON data from " , api);
		        return api;
		}
		@Override
		protected Response<String> parseNetworkResponse(NetworkResponse response) {
			  String result = "";
		        String encoding = response.headers.get("Content-Encoding");
		        if (encoding != null && encoding.equals("gzip")) {
//		            result = StringHelper.unzip(response.data);
		        	 
		        	result = new String(response.data);
		        } else {
		            Response<String> stringResponse = super.parseNetworkResponse(response);
		            result = stringResponse.result;
		        }
		        getPinsFromResponse(result);
			return super.parseNetworkResponse(response);
		}
		private int getPinsFromResponse(String result) {
			return mAffectedRows;
//			JsonObject jsonObject = (JsonObject) new JsonParser().parse(response);
//	        JsonArray jsonArrayPins = jsonObject.getAsJsonArray("pins");
//
//	        mAffectedRows = 0;
//	        for (int i = 0; i < jsonArrayPins.size(); i++) {
//	            JsonObject tmp = (JsonObject) jsonArrayPins.get(i);
//	            Pin pin = mGson.fromJson(tmp, Pin.class);
//	            Pin.File file = mGson.fromJson(tmp.getAsJsonObject("file"), Pin.File.class);
//	            pin.setKey(file.key);
//	            pin.setWidth(file.width);
//	            pin.setHeight(file.height);
//
//	            // ignore gif and some 'bad' images.
//	            if (file.frames == 1 && (file.width / (float) file.height < .38f)) {
//	                try {
//	                    Dao.CreateOrUpdateStatus status =
//	                            mDatabaseHelper.getPinsDAO().createOrUpdate(pin);
//
//	                    mAffectedRows += status.getNumLinesChanged();
//	                } catch (SQLException e) {
//	                    Logger.e(e.getMessage());
//	                }
//	            }
//	        }
//
//	        mListener.onSaved(mAffectedRows);
//	        return mAffectedRows;
			
		}
		
		
		
}
