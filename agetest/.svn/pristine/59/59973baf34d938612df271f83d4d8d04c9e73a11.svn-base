package com.doepiccoding.facedetection;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Face;
import android.hardware.Camera.FaceDetectionListener;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.doepiccoding.facedetection.FaceDetect.DetectCallback;
import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;
import com.ijiaban.agetestcamera.R;
import com.jpardogo.android.googleprogressbar.library.FoldingCirclesDrawable;

public class MainActivity extends Activity {

	private int midScreenWidth;
	private int midScreenHeight;
	private Camera mCamera;
	private SurfaceView cameraSurface;
	private SurfaceHolder cameraSurfaceHolder;
	private CustomView myCustomView;
	private int orientionOfCamera;
	private ImageButton photoImgBtn;
	private ImageButton switchbutton;
	private int cameraPosition = 0;// 0代表前置摄像头，1代表后置摄像头
	private boolean hasstartnext=false;

	int currentVolume;
	AudioManager audio;
	private boolean isVolumeChanged = false;
	private Bitmap mBitmap = null;

	DrawingView drawingView;
	Face[] detectedFaces = {};
	private ProgressBar bar;

	/**
	 * 测试数据
	 */
	private TextView sextext;
	private TextView agetext;
	private TextView skintext;
	private TextView smiletext;

	private File file;

	@Override
	protected void onResume() {
		super.onResume();
		cleartext();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		Window myWindow = this.getWindow();
		myWindow.setFlags(flag, flag);

		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		init();
		bar.setIndeterminateDrawable(new FoldingCirclesDrawable.Builder(this).build());

		// Screen sizes...
		Display display = getWindowManager().getDefaultDisplay();
		midScreenHeight = display.getHeight() / 2;
		midScreenWidth = display.getWidth() / 2;
		/**
		 * 切换相机
		 */
		switchbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				init();
				cleartext();
				drawingView.setHaveFace(false);
				// mCamera.stopFaceDetection();
				mCamera.stopPreview();// 停掉原来摄像头的预览
				mCamera.release();// 释放资源
				mCamera = null;// 取消原来摄像头
				int cameraCount = 0;
				CameraInfo info = new CameraInfo();
				cameraCount = Camera.getNumberOfCameras();
				for (int i = 0; i < cameraCount; i++) {
					Camera.getCameraInfo(i, info);
					if (cameraPosition == 1) {
						if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
						}
						mCamera = Camera.open(cameraPosition);
						Parameters params = mCamera.getParameters();
						Log.i("i", params.flatten());
						WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
						Display display = manager.getDefaultDisplay();

						params.setJpegQuality(80); // 设置照片的质量
						Camera.Size bestSize = null;

						List<Camera.Size> sizeList = mCamera.getParameters()
								.getSupportedPreviewSizes();
						bestSize = sizeList.get(0);

						for (int j = 0; j < sizeList.size(); j++) {
							if ((sizeList.get(j).width * sizeList.get(j).height) > (bestSize.width * bestSize.height)) {
								bestSize = sizeList.get(j);
							}
						}
//						params.setPictureSize(bestSize.width, bestSize.height);
						params.setPreviewSize(bestSize.width, bestSize.height);
						params.setPreviewFrameRate(5); // 预览帧率
						mCamera.setParameters(params);
						setCameraDisplayOrientation(cameraPosition, mCamera);
						try {
							mCamera.setPreviewDisplay(cameraSurfaceHolder);
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						mCamera.startPreview();
						if(mCamera.getParameters().getMaxNumDetectedFaces()>0){
						mCamera.setFaceDetectionListener(faceDetectionListener);
						mCamera.startFaceDetection();
						}
						cameraPosition = 0;
						break;
					} else {
						if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {

							// mCamera = Camera.open(i);//打开当前选中的摄像头
							mCamera = Camera.open(cameraPosition);
							Parameters params = mCamera.getParameters();
							// Log.i("i", params.flatten());
							// params.setJpegQuality(80); // 设置照片的质量
							// params.setPictureSize(1280, 720);
							// params.setPreviewSize(1280, 720);
							Camera.Size bestSize2 = null;
							List<Camera.Size> sizeList = mCamera
									.getParameters().getSupportedPreviewSizes();
							bestSize2 = sizeList.get(0);

							for (int j = 0; j < sizeList.size(); j++) {
								if ((sizeList.get(j).width * sizeList.get(j).height) > (bestSize2.width * bestSize2.height)) {
									bestSize2 = sizeList.get(j);
								}
							}
//							params.setPictureSize(bestSize2.width,
//									bestSize2.height);
							params.setPreviewSize(bestSize2.width,
									bestSize2.height);
							params.setPreviewFrameRate(5); // 预览帧率
							mCamera.setParameters(params);
							setCameraDisplayOrientation(cameraPosition, mCamera);
							try {
								mCamera.setPreviewDisplay(cameraSurfaceHolder);
							} catch (IOException e) {
								e.printStackTrace();
							}
							mCamera.startPreview();
							if(mCamera.getParameters().getMaxNumDetectedFaces()>0){
							mCamera.setFaceDetectionListener(faceDetectionListener);
							mCamera.startFaceDetection();
							}
							cameraPosition = 1;
							break;
						}
					}

				}
			}
		});
		/**
		 * 拍照
		 */
		photoImgBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(mCamera.getParameters().getMaxNumDetectedFaces()>0){
				if (mCamera != null && drawingView.getHaveFace() && hasTested()) {

					mCamera.autoFocus(new AutoFocusCallback() {
						@Override
						public void onAutoFocus(boolean success, Camera camera) {
							hasstartnext=true;
							photoImgBtn.setEnabled(false);
							mCamera.takePicture(myShutterCallback,
									myRawCallback, myJpegCallback);
							new Handler().postDelayed(new Runnable() {

								@Override
								public void run() {
									photoImgBtn.setEnabled(true);
									Intent intent = new Intent();
									intent.putExtra("age", agetext.getText());
									intent.putExtra(
											"skin",
											skintext.getText().equals(getResources().getString(R.string.asian)) ? getResources().getString(R.string.asian)
													: skintext.getText()
															.equals(getResources().getString(R.string.white)) ? getResources().getString(R.string.white)
															: getResources().getString(R.string.dark));
									intent.putExtra("smile",
											smiletext.getText());
									intent.putExtra("sex", sextext.getText().equals("欧巴") ? "Male"
											: sextext.getText().equals(R.string.nanying) ? "Male"
													: sextext.getText().equals(getResources().getString(R.string.shaonan)) ?"Male"
															: sextext.getText().equals(getResources().getString(R.string.growman)) ? "Male"
																	: sextext.getText().equals(getResources().getString(R.string.matureman)) ? "Male"
																			: sextext.getText().equals(
																					getResources().getString(R.string.strangeuncle)) ?"Male"
																					: sextext.getText()
																							.equals(getResources().getString(R.string.bobo)) ? "Male"
																							: sextext
																									.getText()
																									.equals(getResources().getString(R.string.grandfather)) ? "Male"
																									:  "Female");
									intent.putExtra("url",
											file.getAbsolutePath());
									intent.putExtra("nihao", "zhichi");
									intent.setClass(getApplicationContext(),
											PictureActivity.class);
									startActivity(intent);

								}
							}, 3000);
						}
					});
				} else {
					Toast.makeText(MainActivity.this, R.string.toast1
							, 1000)
							.show();
				}
				}
				else{
					
					mCamera.autoFocus(new AutoFocusCallback() {
						@Override
						public void onAutoFocus(boolean success, Camera camera) {
//							photoImgBtn.setEnabled(false);
							mCamera.takePicture(myShutterCallback,
									myRawCallback, myJpegCallback);
							bar.setVisibility(View.VISIBLE);
							Toast toast = Toast.makeText(getApplicationContext(),getResources().getString(R.string.detecting), Toast.LENGTH_LONG);
							// Set the Gravity to Top and Left
							toast.setGravity(Gravity.CENTER, 100, 200);
							toast.show();
							Toast.makeText(getApplicationContext(), getResources().getString(R.string.detecting), 15000).show();
							photoImgBtn.setEnabled(false);
							 switchbutton.setEnabled(false);
						
						}
					});
				}

			}
		});
		drawingView = new DrawingView(this);
		LayoutParams layoutParamsDrawing = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.addContentView(drawingView, layoutParamsDrawing);
	}

	private void init() {
		photoImgBtn = (ImageButton) findViewById(R.id.photoImgBtn);
		bar=(ProgressBar) findViewById(R.id.google_progress);
		switchbutton = (ImageButton) findViewById(R.id.cameraSwtBtn);
		myCustomView = (CustomView) findViewById(R.id.myCustomView);
		cameraSurface = (SurfaceView) findViewById(R.id.cameraSurface);
		cameraSurfaceHolder = cameraSurface.getHolder();
		cameraSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		cameraSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
		cameraSurfaceHolder.addCallback(cameraSurfaceHolderCallbacks);
		agetext = (TextView) findViewById(R.id.agedetail);
		sextext = (TextView) findViewById(R.id.sexdetail);
		skintext = (TextView) findViewById(R.id.skindetail);
		smiletext = (TextView) findViewById(R.id.smiledetail);
	}

	public boolean hasTested() {
		if (!agetext.getText().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	private SurfaceHolder.Callback cameraSurfaceHolderCallbacks = new SurfaceHolder.Callback() {

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			if (mCamera == null)
				return;
			// mCamera.stopFaceDetection();
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {

			try {
				// Try to open front camera if exist...
				Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
				int cameraId = 0;
				int camerasCount = Camera.getNumberOfCameras();
				for (int camIndex = 0; camIndex < camerasCount; camIndex++) {
					Camera.getCameraInfo(camIndex, cameraInfo);
					if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
						cameraId = camIndex;
						break;
					}
				}
				// Parameters params = camera.getParameters();
				// Log.i("i", params.flatten());
				// params.setJpegQuality(80); // 设置照片的质量
				// params.setPictureSize(1024, 768);
				// params.setPreviewFrameRate(5); // 预览帧率
				// camera.setParameters(params); // 将参数设置给相机

				mCamera = Camera.open(cameraId);
				Parameters params = mCamera.getParameters();
				int m=		params.getMaxNumDetectedFaces();
				Log.i("i", params.flatten());
				params.setJpegQuality(80); // 设置照片的质量
				// params.setPictureSize(1280, 720);
				// params.setPreviewSize(1280, 720);
				Camera.Size bestSize2 = null;
				List<Camera.Size> sizeList = mCamera.getParameters()
						.getSupportedPreviewSizes();
				bestSize2 = sizeList.get(0);

				for (int j = 0; j < sizeList.size(); j++) {
					if ((sizeList.get(j).width * sizeList.get(j).height) > (bestSize2.width * bestSize2.height)) {
						bestSize2 = sizeList.get(j);
					}
				}
//				params.setPictureSize(bestSize2.width, bestSize2.height);
				params.setPreviewSize(bestSize2.width, bestSize2.height);
				params.setPreviewFrameRate(5); // 预览帧率
				mCamera.setParameters(params);
				// List<Size> size=params.getSupportedPictureSizes();
				// List<Size> size2=params.getSupportedPreviewSizes();
				setCameraDisplayOrientation(cameraId, mCamera);
				mCamera.setPreviewDisplay(holder);
				setContinueShot();
			} catch (Exception exception) {
				android.util.Log.e("TrackingFlow", "Surface Created Exception",
						exception);
				if (mCamera == null)
					return;
				mCamera.release();
				mCamera = null;
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			 Parameters params = mCamera.getParameters();
			// Log.i("i", params.flatten());
			// params.setJpegQuality(80); // 设置照片的质量
			// // params.setPictureSize(width, height);
			// params.setPreviewFrameRate(5); // 预览帧率
			// mCamera.setParameters(params);
			mCamera.startPreview();
			if(params.getMaxNumDetectedFaces()>0){
			mCamera.setFaceDetectionListener(faceDetectionListener);
			mCamera.startFaceDetection();
			}
		}
	};

	private FaceDetectionListener faceDetectionListener = new FaceDetectionListener() {

		@Override
		public void onFaceDetection(Face[] faces, Camera camera) {
			if (faces.length == 0) {
				// prompt.setText(" No Face Detected! ");
				drawingView.setHaveFace(false);
				cleartext();
			} else {
				// prompt.setText(String.valueOf(faces.length) +
				// " Face Detected :) ");
				drawingView.setHaveFace(true);
				detectedFaces = faces;
				if (mCamera != null) {
					mCamera.setOneShotPreviewCallback(new PreviewCallback() {

						@Override
						public void onPreviewFrame(byte[] data, Camera camera) {
							int second = Calendar.getInstance().get(
									Calendar.SECOND);
							if (null != data && second % 8 == 0) {
								// mBitmap = BitmapFactory.decodeByteArray(data,
								// 0,
								// data.length);// data是字节数据，将其解析成位图
								// mCamera.stopPreview();
								
								Camera.Parameters parameters = camera
										.getParameters();
								Size size = parameters.getPreviewSize();
								YuvImage image = new YuvImage(data, parameters
										.getPreviewFormat(), size.width,
										size.height, null);
								File file = new File(Environment
										.getExternalStorageDirectory()
										.getPath()
										+ "/out.jpg");
								// FileOutputStream filecon = null;
								ByteArrayOutputStream out = new ByteArrayOutputStream();
								// try {
								// filecon = new FileOutputStream(file);
								// } catch (FileNotFoundException e) {
								// // TODO Auto-generated catch block
								// e.printStackTrace();
								// }
								// image.compressToJpeg(
								// new Rect(0, 0, image.getWidth(),
								// image.getHeight()), 90,
								// filecon);
								image.compressToJpeg(
										new Rect(0, 0, image.getWidth(), image
												.getHeight()), 90, out);
								byte[] imageBytes = out.toByteArray();
								BitmapFactory.Options opts=new BitmapFactory.Options();
								opts.inTempStorage = new byte[100 * 1024];
								opts.inPreferredConfig = Bitmap.Config.RGB_565;
								opts.inPurgeable = true;
								opts.inSampleSize = 2;
										opts.inInputShareable = true; ;
								Bitmap image2 = BitmapFactory.decodeByteArray(
										imageBytes, 0, imageBytes.length,opts);
								Matrix matrix = new Matrix();
								matrix.postRotate((float) 90.0);

								if (cameraPosition == 0) {

									matrix.postScale(1, -1); // 上下翻转
								}

								Bitmap rotaBitmap = Bitmap.createBitmap(image2,
										0, 0, image2.getWidth(),
										image2.getHeight(), matrix, false);
								if (rotaBitmap == null) {
									rotaBitmap.recycle();
								}
								FaceDetect faceDetect = new FaceDetect(
										new DetectCallback() {

											@Override
											public void detectResult(
													final JSONObject rst) {
												MainActivity.this
														.runOnUiThread(new Runnable() {

															@Override
															public void run() {
																// Toast.makeText(getApplicationCo
																// ntext(),
																// rst.toString(),
																// 1000).show();
																showinfo(rst);
															}

														});

											}
										}, rotaBitmap);
								faceDetect.detect();

							}
							// 再次进入预览
							// mCamera.startPreview();
							// mCamera.startFaceDetection();
							// isPreview = true;

						}
					});
				}
			}
			new Handler().postDelayed(new Runnable() {
				public void run() {
					drawingView.invalidate();
				}
			}, 2000);
		}

	};

	protected void onDestroy() {
		super.onDestroy();
		if (mCamera != null) {
			mCamera.release();
			mCamera = null; // 销毁
		}
	};

	protected void cleartext() {
		sextext.setText("");
		agetext.setText("");
		smiletext.setText("");
		skintext.setText("");
	}

	protected void setContinueShot() {

	}

	/**
	 * 设置相机的角度
	 * 
	 * @param paramInt
	 * @param paramCamera
	 */
	@SuppressLint("NewApi")
	public void setCameraDisplayOrientation(int paramInt, Camera paramCamera) {
		CameraInfo info = new CameraInfo();
		Camera.getCameraInfo(paramInt, info);
		int rotation = ((WindowManager) getSystemService("window"))
				.getDefaultDisplay().getRotation(); // 获得显示器件角度
		int degrees = 0;
		// Log.i(TAG,"getRotation's rotation is " + String.valueOf(rotation));
		switch (rotation) {
		case Surface.ROTATION_0:
			degrees = 0;
			break;
		case Surface.ROTATION_90:
			degrees = 90;
			break;
		case Surface.ROTATION_180:
			degrees = 180;
			break;
		case Surface.ROTATION_270:
			degrees = 270;
			break;
		}

		orientionOfCamera = info.orientation; // 获得摄像头的安装旋转角度

		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360; // compensate the mirror
		} else { // back-facing
			result = (info.orientation - degrees + 360) % 360;
		}
		paramCamera.setDisplayOrientation(result); // 注意前后置的处理，前置是映象画面，该段是SDK文档的标准DEMO
	}

	/* 为了实现拍照的快门声音及拍照保存照片需要下面三个回调变量 */
	ShutterCallback myShutterCallback = new ShutterCallback()
	// 快门按下的回调，在这里我们可以设置类似播放“咔嚓”声之类的操作。默认的就是咔嚓。
	{

		@SuppressLint("NewApi")
		public void onShutter() {
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
				mCamera.enableShutterSound(false);

			} else {
				audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
				currentVolume = audio
						.getStreamVolume(AudioManager.STREAM_SYSTEM);
				audio.setStreamVolume(AudioManager.STREAM_SYSTEM, 0,
						AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
				MediaPlayer media = MediaPlayer.create(MainActivity.this,
						R.raw.pull_event);
				media.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
				isVolumeChanged = true;
			}

		}
	};
	PictureCallback myJpegCallback = new PictureCallback()
	// 对jpeg图像数据的回调,最重要的一个回调
	{

		public void onPictureTaken(byte[] data, Camera camera) {
			if (null != data) {
				mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// data是字节数据，将其解析成位图
				mCamera.stopPreview();
				// isPreview = false;
			}
			// 设置FOCUS_MODE_CONTINUOUS_VIDEO)之后，myParam.set("rotation",
			// 90)失效。图片竟然不能旋转了，故这里要旋转下
			Matrix matrix = new Matrix();
			matrix.postRotate((float) 90.0);

			if (cameraPosition == 0) {

				matrix.postScale(1, -1); // 上下翻转
			}

			Bitmap rotaBitmap = Bitmap.createBitmap(mBitmap, 0, 0,
					mBitmap.getWidth(), mBitmap.getHeight(), matrix, false);

			/**
			 * bitmap 转byte //
			 */
			if(mCamera.getParameters().getMaxNumDetectedFaces()<=0){
			 ByteArrayOutputStream stream = new ByteArrayOutputStream();
			 // float scale = Math.min(
			 // 1,
			 // Math.min(600f / img.getWidth(),
			 // 600f / img.getHeight()));
			 // Matrix matrix = new Matrix();
			 // matrix.postScale(scale, scale);
			
			 Bitmap imgSmall = Bitmap.createBitmap(rotaBitmap, 0, 0,
			 rotaBitmap.getWidth(), rotaBitmap.getHeight(), null, false);
			 // Log.v(TAG, "imgSmall size : " + imgSmall.getWidth() + " "
			 // + imgSmall.getHeight());
			
			 imgSmall.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			 final byte[] array = stream.toByteArray();
			 new Thread(new Runnable() {
			
			 @Override
			 public void run() {
			 HttpRequests httpRequests = new HttpRequests(
			 "4480afa9b8b364e30ba03819f3e9eff5",
			 "Pz9VFT8AP3g_Pz8_dz84cRY_bz8_Pz8M", true, false);
			 try {
			 JSONObject result = httpRequests
			 .detectionDetect(new PostParameters()
			 .setImg(array));
			 } catch (FaceppParseException e) {
			 e.printStackTrace();
			 }
			 }
			 }).start();
			 FaceDetect faceDetect=new FaceDetect(new DetectCallback() {
			
			 @Override
			 public void detectResult(final JSONObject rst) {
			 MainActivity.this.runOnUiThread(new Runnable() {
			
			 @Override
			 public void run() {
//			 Toast.makeText(getApplicationContext(), rst.toString(),
//			 1000).show();
			 showinfo(rst);
			 }
			
			
			
			 });
			
			 }
			 },rotaBitmap);
			 faceDetect.detect();
			}
			

			/**
			 * 
			 */
			// 保存图片到sdcard
			if (null != rotaBitmap) {
				saveJpeg(rotaBitmap);
			}

			// 再次进入预览
			// mCamera.startPreview();
			// mCamera.startFaceDetection();
			// isPreview = true;
		}

	};

	private void showinfo(JSONObject rst) {
		try {
			String m = null;
			m = rst.getJSONArray("face").getJSONObject(0)
					.getJSONObject("attribute").getJSONObject("age")
					.getString("value");
			final String age = m;
			final String smile = rst.getJSONArray("face").getJSONObject(0)
					.getJSONObject("attribute").getJSONObject("smiling")
					.getString("value");
			final String sex = rst.getJSONArray("face").getJSONObject(0)
					.getJSONObject("attribute").getJSONObject("gender")
					.getString("value");
			final String skin = rst.getJSONArray("face").getJSONObject(0)
					.getJSONObject("attribute").getJSONObject("race")
					.getString("value");
			if(mCamera!=null&&mCamera.getParameters().getMaxNumDetectedFaces()>0){
			MainActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					agetext.setText(age);
					smiletext.setText(smile);
					skintext.setText(skin.equals("Asian")?R.string.asian:skin.equals("White")?R.string.white:R.string.dark);
					if (Integer.valueOf(age) <= 8) {
						sextext.setText(sex.equals("Female") ? R.string.nvying : R.string.nanying);
					} else if (Integer.valueOf(age) > 8
							&& Integer.valueOf(age) <= 22) {
						sextext.setText(sex.equals("Female") ? getResources().getString(R.string.shaonv) : getResources().getString(R.string.shaonan));
					} else if (Integer.valueOf(age) > 22
							&& Integer.valueOf(age) <= 28) {
						sextext.setText(sex.equals("Female") ? getResources().getString(R.string.guniang) : getResources().getString(R.string.growman));
					} else if (Integer.valueOf(age) > 28
							&& Integer.valueOf(age) <= 35) {
						sextext.setText(sex.equals("Female") ? getResources().getString(R.string.shengnv) : getResources().getString(R.string.matureman));
					} else if (Integer.valueOf(age) > 35
							&& Integer.valueOf(age) <= 50) {
						sextext.setText(sex.equals("Female") ? getResources().getString(R.string.ayi) : getResources().getString(R.string.strangeuncle));
					} else if (Integer.valueOf(age) > 50
							&& Integer.valueOf(age) <= 65) {
						sextext.setText(sex.equals("Female") ? getResources().getString(R.string.daniang) : getResources().getString(R.string.bobo));
					} else if (Integer.valueOf(age) > 65) {
						sextext.setText(sex.equals("Female") ? getResources().getString(R.string.nainai) : getResources().getString(R.string.grandfather));
					}
					setColor();

				}
			});
			}
			else{
				if(!hasstartnext&&mCamera.getParameters().getMaxNumDetectedFaces()<=0){
					bar.setVisibility(View.GONE);
				Intent intent=new Intent();
				intent.putExtra("age", age);
				intent.putExtra("smile", smile);
				intent.putExtra("url", file.getAbsolutePath());
				intent.putExtra("skin", skin.equals("Asian")?getResources().getString(R.string.asian):skin.equals("White")?getResources().getString(R.string.white):getResources().getString(R.string.dark));
				if (Integer.valueOf(age) <= 8){
					intent.putExtra("sex", sex.equals("Female") ? getResources().getString(R.string.nvying) :getResources().getString(R.string.nanying));
				}else if (Integer.valueOf(age) > 8
						&& Integer.valueOf(age) <= 22){
					intent.putExtra("sex", sex.equals("Female") ? getResources().getString(R.string.shaonv) : getResources().getString(R.string.shaonan));
				}else if (Integer.valueOf(age) > 22
						&& Integer.valueOf(age) <= 28){
					intent.putExtra("sex", sex.equals("Female") ? getResources().getString(R.string.guniang) : getResources().getString(R.string.growman));
				}
				else if (Integer.valueOf(age) > 28
						&& Integer.valueOf(age) <= 35){
					intent.putExtra("sex", sex.equals("Female") ? getResources().getString(R.string.shengnv) : getResources().getString(R.string.matureman));
				}
				else if (Integer.valueOf(age) > 35
						&& Integer.valueOf(age) <= 50){
					intent.putExtra("sex", sex.equals("Female") ? getResources().getString(R.string.ayi) : getResources().getString(R.string.strangeuncle));
				}else if (Integer.valueOf(age) > 50
						&& Integer.valueOf(age) <= 65){
					intent.putExtra("sex", sex.equals("Female") ? getResources().getString(R.string.daniang) : getResources().getString(R.string.bobo));
				}else if (Integer.valueOf(age) > 65){
					intent.putExtra("sex", sex.equals("Female") ? getResources().getString(R.string.nainai) : getResources().getString(R.string.grandfather));
				}
				switchbutton.setEnabled(true);
				photoImgBtn.setEnabled(true);
				intent.putExtra("nihao", "buzhichi");
				intent.setClass(MainActivity.this, PictureActivity.class);
				startActivity(intent);
				}
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setColor() {
		// sextext.setTextColor(palette.getDarkMutedColor(Color.RED));
		// agetext.setTextColor(palette.getDarkVibrantColor(Color.RED));
		// smiletext.setTextColor(palette.getLightMutedColor(Color.RED));
		// skintext.setTextColor(palette.getLightVibrantColor(Color.RED));
		sextext.setTextColor(sextext.getText().equals("欧巴") ? getResources()
				.getColor(R.color.male)
				: sextext.getText().equals(R.string.nanying) ? getResources().getColor(
						R.color.male)
						: sextext.getText().equals(getResources().getString(R.string.shaonan)) ? getResources()
								.getColor(R.color.male)
								: sextext.getText().equals(getResources().getString(R.string.growman)) ? getResources()
										.getColor(R.color.male)
										: sextext.getText().equals(getResources().getString(R.string.matureman)) ? getResources()
												.getColor(R.color.male)
												: sextext.getText().equals(
														getResources().getString(R.string.strangeuncle)) ? getResources()
														.getColor(R.color.male)
														: sextext.getText()
																.equals(getResources().getString(R.string.bobo)) ? getResources()
																.getColor(
																		R.color.male)
																: sextext
																		.getText()
																		.equals(getResources().getString(R.string.grandfather)) ? getResources()
																		.getColor(
																				R.color.male)
																		: getResources()
																				.getColor(
																						R.color.female));
		agetext.setTextColor(sextext.getText().equals("欧巴") ? getResources()
				.getColor(R.color.male)
				: sextext.getText().equals(R.string.nanying) ? getResources().getColor(
						R.color.male)
						: sextext.getText().equals(getResources().getString(R.string.shaonan)) ? getResources()
								.getColor(R.color.male)
								: sextext.getText().equals(getResources().getString(R.string.growman)) ? getResources()
										.getColor(R.color.male)
										: sextext.getText().equals(getResources().getString(R.string.matureman)) ? getResources()
												.getColor(R.color.male)
												: sextext.getText().equals(
														getResources().getString(R.string.strangeuncle)) ? getResources()
														.getColor(R.color.male)
														: sextext.getText()
																.equals(getResources().getString(R.string.bobo)) ? getResources()
																.getColor(
																		R.color.male)
																: sextext
																		.getText()
																		.equals(getResources().getString(R.string.grandfather)) ? getResources()
																		.getColor(
																				R.color.male)
																		: getResources()
																				.getColor(
																						R.color.female));
		smiletext
				.setTextColor(sextext.getText().equals("欧巴") ? getResources()
						.getColor(R.color.male)
						: sextext.getText().equals(R.string.nanying) ? getResources()
								.getColor(R.color.male)
								: sextext.getText().equals(getResources().getString(R.string.shaonan)) ? getResources()
										.getColor(R.color.male)
										: sextext.getText().equals(getResources().getString(R.string.growman)) ? getResources()
												.getColor(R.color.male)
												: sextext.getText().equals(
														getResources().getString(R.string.matureman)) ? getResources()
														.getColor(R.color.male)
														: sextext.getText()
																.equals(getResources().getString(R.string.strangeuncle)) ? getResources()
																.getColor(
																		R.color.male)
																: sextext
																		.getText()
																		.equals(getResources().getString(R.string.bobo)) ? getResources()
																		.getColor(
																				R.color.male)
																		: sextext
																				.getText()
																				.equals(getResources().getString(R.string.grandfather)) ? getResources()
																				.getColor(
																						R.color.male)
																				: getResources()
																						.getColor(
																								R.color.female));
		skintext.setTextColor(sextext.getText().equals("欧巴") ? getResources()
				.getColor(R.color.male)
				: sextext.getText().equals(R.string.nanying) ? getResources().getColor(
						R.color.male)
						: sextext.getText().equals(getResources().getString(R.string.shaonan)) ? getResources()
								.getColor(R.color.male)
								: sextext.getText().equals(getResources().getString(R.string.growman)) ? getResources()
										.getColor(R.color.male)
										: sextext.getText().equals(getResources().getString(R.string.matureman)) ? getResources()
												.getColor(R.color.male)
												: sextext.getText().equals(
														getResources().getString(R.string.strangeuncle)) ? getResources()
														.getColor(R.color.male)
														: sextext.getText()
																.equals(getResources().getString(R.string.bobo)) ? getResources()
																.getColor(
																		R.color.male)
																: sextext
																		.getText()
																		.equals(getResources().getString(R.string.grandfather)) ? getResources()
																		.getColor(
																				R.color.male)
																		: getResources()
																				.getColor(
																						R.color.female));

	}

	PictureCallback myRawCallback = new PictureCallback()
	// 拍摄的未压缩原数据的回调,可以为null

	{

		public void onPictureTaken(byte[] data, Camera camera) {
			// if (isVolumeChanged) {
			// audio.setStreamVolume(AudioManager.STREAM_SYSTEM,
			// currentVolume,
			// AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
			// }
		}
	};

	private void saveJpeg(Bitmap rotaBitmap) {
		file = new File(Environment.getExternalStorageDirectory(),
				System.currentTimeMillis() + ".jpg");
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fout);

			rotaBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
			Log.i("存储", "saveJpeg：存储完毕！");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.i("存储", "saveJpeg：存储失败！");
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("存储", "saveJpeg：存储失败！");
		}

	}

	public class DrawingView extends View {
		boolean haveFace;
		Paint drawingPaint;

		public DrawingView(Context context) {
			super(context);
			haveFace = false;
			drawingPaint = new Paint();
			drawingPaint.setColor(Color.GREEN);
			drawingPaint.setStyle(Paint.Style.STROKE);
			drawingPaint.setStrokeWidth(2);
		}

		public void setHaveFace(boolean h) {
			haveFace = h;
		}

		public boolean getHaveFace() {
			return haveFace;
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			if (haveFace) {

				// Camera driver coordinates range from (-1000, -1000) to (1000,
				// 1000).
				// UI coordinates range from (0, 0) to (width, height).

				int vWidth = getWidth();
				int vHeight = getHeight();

				for (int i = 0; i < detectedFaces.length; i++) {

					int l = detectedFaces[i].rect.left;
					int t = detectedFaces[i].rect.top;
					int r = detectedFaces[i].rect.right;
					int b = detectedFaces[i].rect.bottom;
					if (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE == getRequestedOrientation()) {
						int left = (l + 1000) * vWidth / 2000;
						int top = (t + 1000) * vHeight / 2000;
						int right = (r + 1000) * vWidth / 2000;
						int bottom = (b + 1000) * vHeight / 2000;
						Toast.makeText(MainActivity.this, R.string.drawsuccess, 1000).show();
						canvas.drawRect(left, top, right, bottom, drawingPaint);
					} else if (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT == getRequestedOrientation()) {
						int left = (l + 1000) * vHeight / 2000;
						int top = (t + 1000) * vWidth / 2000;
						int right = (r + 1000) * vHeight / 2000;
						int bottom = (b + 1000) * vWidth / 2000;
						// Face face = detectedFaces[i];
						// PointF myMidPoint = new PointF();
						// face.
						// myEyesDistance = face.eyesDistance();
						// //得到人脸中心点和眼间距离参数，并对每个人脸进行画框
						// canvas.drawRect( //矩形框的位置参数
						// (int)(myMidPoint.x - myEyesDistance),
						// (int)(myMidPoint.y - myEyesDistance),
						// (int)(myMidPoint.x + myEyesDistance),
						// (int)(myMidPoint.y + myEyesDistance),
						// myPaint);
						// canvas.drawRect(l, t, r, b, drawingPaint);
						// Log.i("facedetect",
						// "l:++"+l+"t:+++"+t+"r:++"+r+"b+++"+b+"left+++"+left+"top+++"+top
						// +"right"+right+"bottom"+bottom);
						if (cameraPosition == 1) {
							canvas.drawRect(vWidth - bottom, left,
									vWidth - top, right, drawingPaint);
						} else {
							// 中心对称
							canvas.drawRect(vWidth - bottom, vHeight - right,
									vWidth - top, vHeight - left, drawingPaint);
						}
					}

				}
			} else {
				canvas.drawColor(Color.TRANSPARENT);
			}
		}
	}

}
