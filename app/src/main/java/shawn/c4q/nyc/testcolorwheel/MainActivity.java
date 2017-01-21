package shawn.c4q.nyc.testcolorwheel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DrawingCanvas drawingCanvas;
    ColorPicker colorPicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        try {
//            colorPicker = new ColorPicker(this);
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        }
//        drawingCanvas = new DrawingCanvas(this);
//        colorPicker.setListener(drawingCanvas);

        insertFragment();
    }

    public void insertFragment(){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.replaceMe, new ColorPaletteFragment(), "Fragment_ID")
                .commit();
    }
}
