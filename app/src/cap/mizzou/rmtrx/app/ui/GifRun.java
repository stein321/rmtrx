package cap.mizzou.rmtrx.app.ui;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;


public class GifRun implements Runnable, Callback {

	
	public Bitmap bmb;
	public GIFDecode decode;
	public int ind;
	public int gifCount;
	public SurfaceHolder mSurfaceHolder ;
	boolean surfaceExists;
	
	public void LoadGif(SurfaceView v, android.content.Context theTHIS, int R_drawable)
	{		
	       mSurfaceHolder = v.getHolder();
	       mSurfaceHolder.addCallback(this);
	       decode = new GIFDecode();
	       decode.read(theTHIS.getResources().openRawResource(R_drawable));
	       ind = 0;
			gifCount = decode.getFrameCount();
			bmb = decode.getFrame(0);
			surfaceExists=true;
			Thread t = new Thread(this);
			t.start();
	}

	public void run() 
	{
		while (surfaceExists) {
			try {
				
					Canvas rCanvas = mSurfaceHolder.lockCanvas();
					rCanvas.drawBitmap(bmb, 0, 0, new Paint());
                    Bitmap.createScaledBitmap(bmb, 400, 400, false);
					
					mSurfaceHolder.unlockCanvasAndPost(rCanvas);
					bmb = decode.next();
					
				Thread.sleep(100);
			} catch (Exception ex) {

			}
		}
		
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) 
	{
		
		
		
	}

	public void surfaceCreated(SurfaceHolder holder) 
	{
		
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		
		surfaceExists=false;
	}
	
}
