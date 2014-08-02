package com.worthed.sectorview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SectorView extends ImageView {

	private Bitmap bitmap;

	private BitmapShader bitmapShader;
	private Paint paint;
	
	public SectorView(Context context) {
		super(context);
		init();
	}

	public SectorView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public SectorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		paint = new Paint();
		paint.setAntiAlias(true);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		if (bitmap == null) {
			super.onDraw(canvas);
			return;
		}

		// Nothing to draw (Empty bounds)
		if (bitmap.getHeight() == 0 || bitmap.getWidth() == 0)
			return;

		bitmapShader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
		paint.setShader(bitmapShader);
		
		float radius = Math.min(bitmap.getWidth(), bitmap.getHeight()) / 2;
		
		// canvas.drawBitmap(bitmap, 0, 0, null);
		canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, radius, paint);
		
	}

	@Override
	public void invalidate() {
		// TODO Auto-generated method stub
		super.invalidate();
		bitmap = drawableToBitmap(getDrawable());
	}

	/**
	 * Convert a drawable object into a Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	private Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable == null) { // Don't do anything without a proper drawable
			return null;
		} else if (drawable instanceof BitmapDrawable) { // Use the getBitmap()
															// method instead if
															// BitmapDrawable
			return ((BitmapDrawable) drawable).getBitmap();
		}

		// Create Bitmap object out of the drawable
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}

}
