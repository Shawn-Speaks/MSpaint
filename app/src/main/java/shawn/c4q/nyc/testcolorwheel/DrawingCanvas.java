package shawn.c4q.nyc.testcolorwheel;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;

/**
 * Created by shawnspeaks on 1/18/17.
 */

public class DrawingCanvas extends View {

    public static int paintColor;
    private Paint drawPaint;
    private Path drawPath;
    private Context context;
    private HashMap<Path, Integer> colorPathList = new HashMap<>();



    public DrawingCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt("newColor", 0);
    }

    public static int getPaintColor() {
        return paintColor;
    }
}