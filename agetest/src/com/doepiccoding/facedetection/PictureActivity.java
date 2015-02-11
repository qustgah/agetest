package com.doepiccoding.facedetection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.ijiaban.agetestcamera.R;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify.IconValue;
import com.jpardogo.android.googleprogressbar.library.FoldingCirclesDrawable;
import com.konka.imagefilter.api.BitmapFilter;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.picasso.Picasso;

import android.R.menu;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.support.v7.graphics.Palette;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PictureActivity extends Activity {
	private ImageView myimage;
	private String sex;
	private String skin;
	private String age;
	private String smile;
	private String url;
	private TextView sextext;
	private TextView skintext;
	private TextView agetext;
	private TextView smiletext;
	private TextView sextext1;
	private TextView skintext1;
	private TextView agetext1;
	private TextView smiletext1;
	private Bitmap originailbitmap;
	private Bitmap newbitmap;
	private int style=BitmapFilter.GRAY_STYLE;
	private String name;
	private String filepath;
	private String zhichi="123" ;
	private boolean female=true;
	private ProgressBar bar;
	 private AdView mAdView;

	private SystemBarTintManager mTintManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.piclayout);
		setOverFlowButtonAlways();
		 LinearLayout layout = (LinearLayout) findViewById(R.id.adlayout);
	        mAdView = new AdView(this);
			mAdView.setAdUnitId(getResources().getString(R.string.ad_unit_id));
			mAdView.setAdSize(AdSize.BANNER);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT);
			layout.addView(mAdView, params);
			mAdView.loadAd(new AdRequest.Builder().build());
		mTintManager = new SystemBarTintManager(this);
		mTintManager.setStatusBarTintEnabled(true);
		mTintManager.setNavigationBarTintEnabled(true);
		bar=(ProgressBar) findViewById(R.id.mmmm);
		bar.setIndeterminateDrawable(new FoldingCirclesDrawable.Builder(this).build());
		bar.setVisibility(View.GONE);
		int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		if (actionBarTitleId > 0) {
		    TextView title = (TextView) findViewById(actionBarTitleId);
		    if (title != null) {
		        title.setTextColor(Color.RED);
		    }
		}
		getActionBar().setDisplayShowHomeEnabled(false);
		myimage = (ImageView) findViewById(R.id.myimage);
		sextext = (TextView) findViewById(R.id.sexdetail2);
		skintext = (TextView) findViewById(R.id.skindetail2);
		agetext = (TextView) findViewById(R.id.agedetail2);
		smiletext = (TextView) findViewById(R.id.smiledetail2);
		sextext1 = (TextView) findViewById(R.id.sex);
		skintext1 = (TextView) findViewById(R.id.skin);
		agetext1= (TextView) findViewById(R.id.age);
		smiletext1 = (TextView) findViewById(R.id.smile);
		Intent intent = getIntent();
		sex = intent.getStringExtra("sex");
		url = intent.getStringExtra("url");
		age = intent.getStringExtra("age");
		smile = intent.getStringExtra("smile");
		skin = intent.getStringExtra("skin");
		zhichi=intent.getStringExtra("nihao");
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					originailbitmap = Picasso.with(getApplicationContext())
							.load("file:///" + url).get();
					PictureActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							myimage.setImageBitmap(originailbitmap);
							final Palette palette=Palette.generate(originailbitmap);
							getActionBar().setBackgroundDrawable(new ColorDrawable(palette.getDarkMutedColor(Color.RED)));
							setColor(new ColorDrawable(palette.getLightVibrantColor(Color.RED)),palette);
						mTintManager.setTintColor(palette.getDarkMutedColor(Color.RED));
							
							
							
						}

					
					});

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}).start();

//		 Picasso.with(getApplicationContext()).load("file:///"+url).into(myimage);
		if(zhichi.equals("buzhichi")){
			sextext.setText(sex);
		}
		else{
			if(age!=null){
		if(Integer.valueOf(age)<=8){
			sextext.setText(sex.equals("Female")?R.string.nvying:R.string.nanying);
		}else if(Integer.valueOf(age)>8&&Integer.valueOf(age)<=22){
			sextext.setText(sex.equals("Female")?R.string.shaonv:R.string.shaonan);
		}else if(Integer.valueOf(age)>22&&Integer.valueOf(age)<=28){
			sextext.setText(sex.equals("Female")?R.string.guniang:R.string.growman);
		}else if(Integer.valueOf(age)>28&&Integer.valueOf(age)<=35){
			sextext.setText(sex.equals("Female")?R.string.shengnv:R.string.matureman);
		}
		else if(Integer.valueOf(age)>35&&Integer.valueOf(age)<=50){
			sextext.setText(sex.equals("Female")?R.string.ayi:R.string.strangeuncle);
		}
		else if(Integer.valueOf(age)>50&&Integer.valueOf(age)<=65){
			sextext.setText(sex.equals("Female")?R.string.daniang:R.string.bobo);
		}
		else if(Integer.valueOf(age)>65){
			sextext.setText(sex.equals("Female")?R.string.nainai:R.string.grandfather);
		}
			}
		}
	
		agetext.setText(age+getResources().getString(R.string.agenum));
		smiletext.setText(smile);
		skintext.setText(skin.equals(getResources().getString(R.string.asian))?R.string.asian:skin.equals(getResources().getString(R.string.white))?R.string.white:R.string.dark);

	}
	private void setColor(ColorDrawable colorDrawable, Palette palette) {
//		sextext.setTextColor(palette.getDarkMutedColor(Color.RED));
//		agetext.setTextColor(palette.getDarkVibrantColor(Color.RED));
//		smiletext.setTextColor(palette.getLightMutedColor(Color.RED));
//		skintext.setTextColor(palette.getLightVibrantColor(Color.RED));
		sextext.setTextColor(sex.equals("Male")?getResources().getColor(R.color.male):getResources().getColor(R.color.female));
		agetext.setTextColor(sex.equals("Male")?getResources().getColor(R.color.male):getResources().getColor(R.color.female));
		smiletext.setTextColor(sex.equals("Male")?getResources().getColor(R.color.male):getResources().getColor(R.color.female));
		skintext.setTextColor(sex.equals("Male")?getResources().getColor(R.color.male):getResources().getColor(R.color.female));
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.filter_menu, menu);
		menu.findItem(R.id.SHARE_IT).setIcon(new IconDrawable(this, IconValue.fa_send).colorRes(R.color.female).actionBarSize());
		menu.findItem(R.id.GRAY_STYLE).setIcon(new IconDrawable(this,IconValue.fa_align_center).colorRes(R.color.material_grey).actionBarSize());
		menu.findItem(R.id.BLOCK_STYLE).setIcon(new IconDrawable(this, IconValue.fa_pagelines).colorRes(R.color.material_green).actionBarSize());
		menu.findItem(R.id.OLD_STYLE).setIcon(new IconDrawable(this, IconValue.fa_heart_o).colorRes(R.color.material_red).actionBarSize());
		menu.findItem(R.id.CARTON_STYLE).setIcon(new IconDrawable(this, IconValue.fa_reddit_square).colorRes(R.color.material_pink).actionBarSize());
		menu.findItem(R.id.MOLTEN_STYLE).setIcon(new IconDrawable(this, IconValue.fa_shield).colorRes(R.color.material_purple).actionBarSize());
		menu.findItem(R.id.SOFTNESS_STYLE).setIcon(new IconDrawable(this, IconValue.fa_umbrella).colorRes(R.color.material_deeppurple).actionBarSize());
		menu.findItem(R.id.ECLOSION_STYLE).setIcon(new IconDrawable(this, IconValue.fa_tachometer).colorRes(R.color.material_indigo).actionBarSize());
		menu.findItem(R.id.RELIEF_STYLE).setIcon(new IconDrawable(this, IconValue.fa_yelp).colorRes(R.color.material_cyan).actionBarSize());
		menu.findItem(R.id.OIL_STYLE).setIcon(new IconDrawable(this, IconValue.fa_flag_checkered).colorRes(R.color.material_teal).actionBarSize());
		menu.findItem(R.id.INVERT_STYLE).setIcon(new IconDrawable(this, IconValue.fa_eye).colorRes(R.color.material_lime).actionBarSize());
		menu.findItem(R.id.LIGHT_STYLE).setIcon(new IconDrawable(this, IconValue.fa_sun_o).colorRes(R.color.material_yellow).actionBarSize());
		menu.findItem(R.id.LOMO_STYLE).setIcon(new IconDrawable(this, IconValue.fa_pied_piper_alt).colorRes(R.color.material_orange).actionBarSize());
		menu.findItem(R.id.HAHA_STYLE).setIcon(new IconDrawable(this, IconValue.fa_tree).colorRes(R.color.material_amber).actionBarSize());
		menu.findItem(R.id.ICE_STYLE).setIcon(new IconDrawable(this, IconValue.fa_frown_o).colorRes(R.color.material_ice).actionBarSize());
		menu.findItem(R.id.NO_STYLE).setIcon(new IconDrawable(this, IconValue.fa_smile_o).colorRes(R.color.material_w).actionBarSize());
		return true;
	}
	/**
	 * 所有滤镜效果的id
	
	public static final int GRAY_STYLE = 1; 	// 黑白效果
	public static final int BLOCK_STYLE = 2; 	// 版画
	public static final int OLD_STYLE = 3; 		// 怀旧效果
	public static final int ICE_STYLE = 4;		//冰冻效果
	public static final int CARTON_STYLE = 5;	//连环画效果
	public static final int MOLTEN_STYLE = 6;	//铸融效果
	public static final int SOFTNESS_STYLE = 7; //柔化效果
	public static final int ECLOSION_STYLE = 8; //羽化效果
	public static final int RELIEF_STYLE = 9; 	// 浮雕效果
	public static final int OIL_STYLE = 10; 	// 油画效果
	public static final int INVERT_STYLE = 11; 	// 反色效果
	public static final int LIGHT_STYLE = 12; 	// 光照效果
	public static final int LOMO_STYLE = 13;	//LOMO效果
	public static final int HAHA_STYLE = 14;	//哈哈镜效果
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()){
		case R.id.GRAY_STYLE:
			style=BitmapFilter.GRAY_STYLE;
			break;
		case R.id.BLOCK_STYLE:
			style=BitmapFilter.BLOCK_STYLE;
			break;
		case R.id.OLD_STYLE:
			style=BitmapFilter.OLD_STYLE;
			break;
		case R.id.ICE_STYLE:
			style=BitmapFilter.ICE_STYLE;
			break;
		case R.id.CARTON_STYLE:
			style=BitmapFilter.CARTON_STYLE;
			break;
		case R.id.MOLTEN_STYLE:
			style=BitmapFilter.MOLTEN_STYLE;
			break;
		case R.id.SOFTNESS_STYLE:
			style=BitmapFilter.SOFTNESS_STYLE;
			break;
		case R.id.ECLOSION_STYLE:
			style=BitmapFilter.ECLOSION_STYLE;
			break;
		case R.id.RELIEF_STYLE:
			style=BitmapFilter.RELIEF_STYLE;
			break;
		case R.id.OIL_STYLE:
			style=BitmapFilter.OIL_STYLE;
			break;
		case R.id.INVERT_STYLE:
			style=BitmapFilter.INVERT_STYLE;
			break;
		case R.id.LIGHT_STYLE:
			style=BitmapFilter.LIGHT_STYLE;
			break;
		case R.id.LOMO_STYLE:
			style=BitmapFilter.LOMO_STYLE;
			break;
		case R.id.HAHA_STYLE:
			style=BitmapFilter.HAHA_STYLE;
			break;
		case R.id.NO_STYLE:
			Picasso.with(getApplicationContext()).load("file:///"+url).into(myimage);
			break;
		case R.id.SHARE_IT:
		       GetandSaveCurrentImage();
		       initShareIntent(getResources().getString(R.string.sharecontent)+age+getResources().getString(R.string.sharecontent1)+getPackageName());
			
			break;
			
			
		}
		if(!(item.getItemId()==R.id.NO_STYLE)&&!(item.getItemId()==R.id.SHARE_IT)){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					originailbitmap = Picasso.with(getApplicationContext())
							.load("file:///" + url).get();
					newbitmap = BitmapFilter.changeStyle(originailbitmap,
							style);
					PictureActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							myimage.setImageBitmap(newbitmap);
							Palette palette=Palette.generate(newbitmap);
							getActionBar().setBackgroundDrawable(new ColorDrawable(palette.getDarkMutedColor(Color.RED)));
							mTintManager.setTintColor(palette.getDarkMutedColor(Color.RED));
							setColor(new ColorDrawable(palette.getLightVibrantColor(Color.RED)), palette);
						}
					});

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		}
		return super.onOptionsItemSelected(item);
	}
	/**
	 * 获取和保存当前屏幕的截图
	 */
	private void GetandSaveCurrentImage(){
		WindowManager windowManager=getWindowManager();
		Display display=windowManager.getDefaultDisplay();
		int w=display.getWidth();
		int h=display.getHeight();
		Bitmap Bmp=Bitmap.createBitmap(w, h, Config.ARGB_8888);
		View decorview=getWindow().getDecorView();
		decorview.setDrawingCacheEnabled(true);
		Bmp=decorview.getDrawingCache();
		String SavePath =getSdCardPath()+"/AgeTest/";
		
		File path=new File(SavePath);
		name=System.currentTimeMillis()+".png";
		 filepath=SavePath+name;
		File file=new File(filepath);
		if(!path.exists()){
			path.mkdir();
		}
		if(!file.exists()){
			try {
				file.createNewFile();
				FileOutputStream fos=null;
				fos=new FileOutputStream(file);
				if(null!=fos){
					Bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
					fos.flush();
					fos.close();
					Toast.makeText(getApplicationContext(), R.string.savesuccess, 2000).show();
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	}

	private String getSdCardPath() {
		File sdcardDir=null;
		boolean sdcardExist=Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
		if(sdcardExist){
			sdcardDir=Environment.getExternalStorageDirectory();
		}
		return sdcardDir.toString();
	}
	private void initShareIntent(String _text)
	{
	    Intent shareIntent = new Intent();
	    shareIntent.setAction(Intent.ACTION_SEND);
	    shareIntent.putExtra(Intent.EXTRA_TEXT, _text);
	    shareIntent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(new File(filepath))); 
	    //optional//use this when you want to send an image
	    shareIntent.setType("image/jpeg");
	    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
	    startActivity(Intent.createChooser(shareIntent, "send"));
	}
	private void setOverFlowButtonAlways(){
		try {
			ViewConfiguration config=ViewConfiguration.get(this);
			Field menukey=ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			menukey.setAccessible(true);
			menukey.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if(featureId==Window.FEATURE_ACTION_BAR&&menu!=null){
			if(menu.getClass().getSimpleName().equals("MenuBuilder")){
				try {
					Method m=menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}
	

}
