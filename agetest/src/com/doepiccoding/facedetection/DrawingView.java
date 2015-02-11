package com.doepiccoding.facedetection;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera.Face;
import android.view.View;

public class DrawingView extends View{
	boolean haveFace;
	  Paint drawingPaint;
	  Face[] detectedFaces;
	public DrawingView(Context context,Face[] detectedFaces) {
		super(context);
	haveFace=false;
	this.detectedFaces=detectedFaces;
	 drawingPaint = new Paint();
	   drawingPaint.setColor(Color.GREEN);
	   drawingPaint.setStyle(Paint.Style.STROKE); 
	   drawingPaint.setStrokeWidth(2);
	}
	public void setHaveFace(boolean h){
		   haveFace = h;
		  }
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		   if(haveFace){

			    // Camera driver coordinates range from (-1000, -1000) to (1000, 1000).
			     // UI coordinates range from (0, 0) to (width, height).
			     
			     int vWidth = getWidth();
			     int vHeight = getHeight();
			    
			    for(int i=0; i<detectedFaces.length; i++){
			     
			     int l = detectedFaces[i].rect.left;
			     int t = detectedFaces[i].rect.top;
			     int r = detectedFaces[i].rect.right;
			     int b = detectedFaces[i].rect.bottom;
			     int left = (l+1000) * vWidth/2000;
			     int top  = (t+1000) * vHeight/2000;
			     int right = (r+1000) * vWidth/2000;
			     int bottom = (b+1000) * vHeight/2000;
//			     canvas.drawRect(
//			       left, top, right, bottom,  
//			       drawingPaint);
			     canvas.drawRect(l, t, r, b, drawingPaint);
			    }
			   }else{
			    canvas.drawColor(Color.TRANSPARENT);
			   }
			  }
	}
	


