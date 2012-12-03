package com.drovik.utils;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;


public class BitmapUtil {
	
	// 重构图片大小
	public static Bitmap resizeBitmapSize(Bitmap src, int width, int height) {
		return Bitmap.createScaledBitmap(src, width, height, true);
	}
	
	
	public static Bitmap resizeApplicationIcon(Drawable src, int width, int height) {
		Bitmap desc = Bitmap.createBitmap(width, height, src.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(desc);   
		src.setBounds(0,0,width,height);   
		src.draw(canvas);
		return desc;
	}
	
	public static BitmapDrawable createTriangleImage(String txt, int txtSize) {
		Bitmap mbmpTest = Bitmap.createBitmap(txt.length() * txtSize + 4,
		txtSize + 4, Config.ALPHA_8);
		Canvas canvasTemp = new Canvas(mbmpTest);
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setColor(Color.BLACK);
		p.setTextSize(txtSize);
		canvasTemp.drawText(txt, 2, txtSize - 2, p);
        BitmapDrawable bd = new BitmapDrawable(mbmpTest);
		return bd;
	}
	
	public static Bitmap createTxtImage(String txt, int txtSize) {
		Bitmap mbmpTest = Bitmap.createBitmap(txt.length() * txtSize + 8, txtSize*27/10, Config.ALPHA_8);
		Canvas canvasTemp = new Canvas(mbmpTest);
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setColor(Color.BLACK);
		p.setTextSize(txtSize*4/3);
		canvasTemp.drawText(txt, 0, txtSize +4, p);
		return mbmpTest;
	}
	
	public static Bitmap createBookImageWithName(String name, int txtSize) {
		int w = 150;//name.length() * txtSize + 8;
		int h = 60;//txtSize * 4;
		Bitmap mbmpTest = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvasTemp = new Canvas(mbmpTest);
		Paint p = new Paint();
		p.setColor(Color.BLACK);
		p.setTextSize(txtSize);
		canvasTemp.drawText(name, 0, h/3, p);
		//canvasTemp.translate(0, 0);
		//canvasTemp.drawText(name, 0, txtSize +4, p);
		/*TextPaint textPaint = new TextPaint();
		textPaint.setTextSize(20);
		Layout layout = new StaticLayout(name, textPaint, w, Layout.Alignment.ALIGN_NORMAL, 1.0f, 1.0f, false);
		layout.draw(canvasTemp);*/
		return mbmpTest;
	}
	

	public static Bitmap createBottomLineImage() {
		Bitmap mbmpTest = Bitmap.createBitmap(30, 30, Config.ARGB_8888);
		Canvas canvasTemp = new Canvas(mbmpTest);
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setColor(Color.BLACK);
		//canvasTemp.drawCircle(18, 18, 5, p);
		p.setStrokeWidth(1);
		canvasTemp.drawLine(8, 25, 22, 25, p);
		return mbmpTest;
	}
	
	//带倒影的图片
	public static Bitmap createReflectedImage(Bitmap originalImage) {
		// The gap we want between the reflection and the original image
		final int reflectionGap = 0;
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		// This will not scale but will flip on the Y axis
		Matrix matrix = new Matrix();
		
		matrix.preScale(1, -1);
		// Create a Bitmap with the flip matrix applied to it.
		// We only want the bottom half of the image
		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,

		height / 2, width, height / 2, matrix, false);
		// Create a new bitmap with same width but taller to fit reflection
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
		(height + height / 2), Config.ARGB_8888);
		// Create a new Canvas with the bitmap that's big enough for
		// the image plus gap plus reflection
		Canvas canvas = new Canvas(bitmapWithReflection);
		// Draw in the original image
		canvas.drawBitmap(originalImage, 0, 0, null);
		// Draw in the gap
		Paint defaultPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
		// Draw in the reflection
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
		// Create a shader that is a linear gradient that covers the reflection
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0,
				originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
						+ reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
		// Set the paint to use this shader (linear gradient)
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);
		return bitmapWithReflection;
	}

	// 圆角图片
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,float roundPx){  
        
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap  
                .getHeight(), Config.ARGB_8888);  
        Canvas canvas = new Canvas(output);  
   
        final int color = 0xff424242;  
        final Paint paint = new Paint();  
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());  
        final RectF rectF = new RectF(rect);  
   
        paint.setAntiAlias(true);  
        canvas.drawARGB(0, 0, 0, 0);  
        paint.setColor(color);  
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);  
   
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
        canvas.drawBitmap(bitmap, rect, rect, paint);  
   
        return output;  
    }  

	
	public static Bitmap drawBookName(Bitmap src, String textPath) {
		int bitmapWidth =  src.getWidth();
		int x = 20;
		int y = 35;
		Canvas canvas = new Canvas(src);
		TextPaint textPaint = new TextPaint();
		textPaint.setTextSize(20);
		canvas.translate(x, y);
		Layout layout = new StaticLayout(textPath, textPaint, bitmapWidth-30, Layout.Alignment.ALIGN_CENTER, 1.3f, 1.0f, false);
		layout.draw(canvas);
		return src;
	}
	
	public static String getBooKName(String textPath) {
		if(textPath == null || "".equalsIgnoreCase(textPath) || textPath.startsWith(".")) {
			return "";
		}
		return textPath.substring(textPath.lastIndexOf(File.separator)+1);
	}
	
	
	private static final int OPTIONS_SCALE_UP = 0x1;

	public static final int OPTIONS_RECYCLE_INPUT = 0x2;

	private static final int OPTIONS_NONE = 0x0;

	public static Bitmap extractThumbnail(Bitmap source, int width, int height) {
		return extractThumbnail(source, width, height, OPTIONS_NONE);
	}

	private static Bitmap extractThumbnail(Bitmap source, int width, int height, int options) {
		if (source == null) {
			return null;
		}
		float scale;
		if (source.getWidth() < source.getHeight()) {
			scale = width / (float) source.getWidth();
		} else {
			scale = height / (float) source.getHeight();
		}
		Matrix matrix = new Matrix();
		matrix.setScale(scale, scale);
		Bitmap thumbnail = transform(matrix, source, width, height,	OPTIONS_SCALE_UP | options);
		return thumbnail;
	}

	private static Bitmap transform(Matrix scaler, Bitmap source, int targetWidth, int targetHeight, int options) {
	       boolean scaleUp = (options & OPTIONS_SCALE_UP) != 0;
	       boolean recycle = (options & OPTIONS_RECYCLE_INPUT) != 0;
	       int deltaX = source.getWidth() - targetWidth;
	       int deltaY = source.getHeight() - targetHeight;
	       if (!scaleUp && (deltaX < 0 || deltaY < 0)) {
	           Bitmap b2 = Bitmap.createBitmap(targetWidth, targetHeight,
	           Bitmap.Config.ARGB_8888);
	           Canvas c = new Canvas(b2);
	           int deltaXHalf = Math.max(0, deltaX / 2);
	           int deltaYHalf = Math.max(0, deltaY / 2);
	           Rect src = new Rect(deltaXHalf, deltaYHalf, deltaXHalf + Math.min(targetWidth, source.getWidth()), deltaYHalf + Math.min(targetHeight, source.getHeight()));
	           int dstX = (targetWidth  - src.width())  / 2;
	           int dstY = (targetHeight - src.height()) / 2;
	           Rect dst = new Rect(dstX, dstY, targetWidth - dstX, targetHeight - dstY);
	           c.drawBitmap(source, src, dst, null);
	           if (recycle) {
	               source.recycle();
	           }
	           return b2;
	       }
	       float bitmapWidthF = source.getWidth();
	       float bitmapHeightF = source.getHeight();
	       float bitmapAspect = bitmapWidthF / bitmapHeightF;
	       float viewAspect   = (float) targetWidth / targetHeight;
	       if (bitmapAspect > viewAspect) {
	           float scale = targetHeight / bitmapHeightF;
	           if (scale < .9F || scale > 1F) {
	               scaler.setScale(scale, scale);
	           } else {
	               scaler = null;
	           }
	       } else {
	           float scale = targetWidth / bitmapWidthF;
	           if (scale < .9F || scale > 1F) {
	               scaler.setScale(scale, scale);
	           } else {
	               scaler = null;
	           }
	       }
	       Bitmap b1;
	       if (scaler != null) {
	           b1 = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), scaler, true);
	       } else {
	           b1 = source;
	       }
	       if (recycle && b1 != source) {
	           source.recycle();
	       }
	       int dx1 = Math.max(0, b1.getWidth() - targetWidth);
	       int dy1 = Math.max(0, b1.getHeight() - targetHeight);
	       Bitmap b2 = Bitmap.createBitmap(b1, dx1 / 2, dy1 / 2, targetWidth, targetHeight);
	       if (b2 != b1) {
	           if (recycle || b1 != source) {
	               b1.recycle();
	           }
	       }
	       return b2;
	   }

}
