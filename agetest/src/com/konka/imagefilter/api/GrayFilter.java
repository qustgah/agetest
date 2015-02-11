package com.konka.imagefilter.api;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class GrayFilter {
	/**
	 * �ڰ�Ч������
	 * @param bitmap
	 * @return
	 */
	public static Bitmap changeToGray(Bitmap bitmap)
			{
		int width ,height;
		width=bitmap.getWidth();
		height=bitmap.getHeight();
		Bitmap grayBitmap=Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
		Canvas canvas =new Canvas(grayBitmap);
		Paint paint=new Paint();
		paint.setAntiAlias(true); //���ÿ����
//		float[] array={1,0,0,0,100,
//				0,1,0,0,100,
//				0,0,1,0,0,
//				0,0,0,1,0
//				};
		ColorMatrix colorMatrix=new ColorMatrix();
		colorMatrix.setSaturation(0);  //���û�ɫ 
		ColorMatrixColorFilter colorFilter=new ColorMatrixColorFilter(colorMatrix);
		paint.setColorFilter(colorFilter);
		canvas.drawBitmap(bitmap, 0, 0, paint);
		return grayBitmap;
		
		
		
	}
	

}
