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
    private Paint drawPaint;
    private Path drawPath;
    private HashMap<Path, Paint> colorPathList = new HashMap<>();


    DrawingCanvas thisCanvas;

    public DrawingCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        thisCanvas = this;
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
        createPaint(paintColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(drawPath, drawPaint);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    private void createPaint(int paintColor) {
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        drawPaint.setStrokeWidth(20);
        drawPath = new Path();
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