package shawn.c4q.nyc.testcolorwheel;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.HashMap;

/**
 */

public class DrawingCanvas extends LinearLayout {

    private int paintColor;
    private Paint drawPaint, canvasPaint;
    private Path drawPath;
    Canvas drawCanvas;

    Bitmap canvasBitmap;

    private HashMap<Path, Paint> colorPathList = new HashMap<>();



    public DrawingCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DrawingCanvas);
        final int arraySize = array.getIndexCount();
        for (int i = 0; i < arraySize; i++) {
            int xmlAttribute = array.getIndex(i);
            switch (xmlAttribute) {
                case R.styleable.DrawingCanvas_BrushColor:
                    paintColor = array.getColor(xmlAttribute, Color.BLACK);
                    break;
                //ADD LOGIC INSIDE SWITCH FOR ANY ADDITIONAL XML CODED VALUES;
            }
        }
        array.recycle();
        setBackgroundColor(Color.TRANSPARENT);
        drawPaint = createPaint(paintColor);
        drawPath = createPath(new Path());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        for(Path p: colorPathList.keySet()) {
//                canvas.drawPath(p, colorPathList.get(p));
//        }

        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);


        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        Path tempPath;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
//                tempPath = new Path();
//                tempPath.lineTo(touchX, touchY);
//                colorPathList.put(tempPath, createPaint(paintColor));
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
        }
        invalidate();
        return true;
    }

    private Paint createPaint(int paintColor) {
        Paint myNewPaint = new Paint();

        myNewPaint.setColor(paintColor);
        myNewPaint.setAntiAlias(true);
        myNewPaint.setStyle(Paint.Style.STROKE);
        myNewPaint.setStrokeJoin(Paint.Join.ROUND);
        myNewPaint.setStrokeCap(Paint.Cap.ROUND);
        myNewPaint.setStrokeWidth(20);
        canvasPaint = new Paint(Paint.DITHER_FLAG);


        return myNewPaint;
    }

    private Path createPath(Path myNewPath){
        return myNewPath;
    }


    @Override
    public void invalidate() {
        super.invalidate();
        paintColor = getColorPickerColor();
        drawPaint.setColor(paintColor);
    }

    private int getColorPickerColor(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return preferences.getInt("newColor", 0);
    }

    public void updateBackground(ViewGroup viewgroup){
        viewgroup.setDrawingCacheEnabled(true);
        viewgroup.buildDrawingCache(true);
        Bitmap tempBitmap = Bitmap.createBitmap(viewgroup.getDrawingCache());
        Drawable tempDrawable = new BitmapDrawable(getResources(), tempBitmap);
        viewgroup.setBackground(tempDrawable);
    }
}