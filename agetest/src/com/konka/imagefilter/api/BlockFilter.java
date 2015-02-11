package com.konka.imagefilter.api;

import android.graphics.Bitmap;
import android.graphics.Color;

public class BlockFilter {
	/**
	 * °æ»­Ð§¹ûº¯Êý
	 */
public static Bitmap changeToBlock(Bitmap bitmap){
	int width=bitmap.getWidth();
	int height=bitmap.getHeight();
	int dst[]=new int[width*height];
	bitmap.getPixels(dst, 0, width, 0, 0, width, height);
	int iPiexl=0;
	int i,j,color,pos;
	for(j=0;j<height;j++){
		for(i=0;i<width;i++){
			pos=j*width+i;
			color=dst[pos];
			int avg=(Color.red(color)+Color.green(color)+Color.blue(color));
			if(avg>=100)
				iPiexl=255;
				else iPiexl=0;
				dst[pos]=Color.rgb(iPiexl, iPiexl, iPiexl);
		}
	}
	Bitmap bmpReturn=Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
	bmpReturn.setPixels(dst, 0, width, 0, 0, width, height);
	return bmpReturn;
	
}
}
