  package com.doepiccoding.facedetection;

import java.io.ByteArrayOutputStream;

import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;

public class FaceDetect {
	DetectCallback callback = null;
	Bitmap image = null;

	public void setDetectCallback(DetectCallback detectCallback) {
		callback = detectCallback;
	}

	public FaceDetect(DetectCallback callback, Bitmap image) {
		super();
		this.callback = callback;
		this.image = image;
	}

	public void detect() {

		new Thread(new Runnable() {

			public void run() {
				HttpRequests httpRequests = new HttpRequests(
						"4480afa9b8b364e30ba03819f3e9eff5",
						"Pz9VFT8AP3g_Pz8_dz84cRY_bz8_Pz8M", true, false);
				// Log.v(TAG, "image size : " + img.getWidth() + " " +
				// img.getHeight());

				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				// float scale = Math.min(
				// 1,
				// Math.min(600f / img.getWidth(),
				// 600f / img.getHeight()));
				// Matrix matrix = new Matrix();
				// matrix.postScale(scale, scale);

				Bitmap imgSmall = Bitmap.createBitmap(image, 0, 0,
						image.getWidth(), image.getHeight(), null, false);
				// Log.v(TAG, "imgSmall size : " + imgSmall.getWidth() + " "
				// + imgSmall.getHeight());

				imgSmall.compress(Bitmap.CompressFormat.JPEG, 70, stream);
				byte[] array = stream.toByteArray();

				try {
					// detect
					JSONObject result = httpRequests
							.detectionDetect(new PostParameters().setImg(array));
					// finished , then call the callback function
					if (callback != null) {
						callback.detectResult(result);
					}
				} catch (FaceppParseException e) {
					e.printStackTrace();
					// MainActivity.this.runOnUiThread(new Runnable() {
					// public void run() {
					// textView.setText("Network error.");
					// }
					// });
				}

			}
		}).start();
	}

	interface DetectCallback {
		void detectResult(JSONObject rst);
	}
}
