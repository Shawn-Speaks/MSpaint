package shawn.c4q.nyc.testcolorwheel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        insertFragment();
    }

    public void insertFragment(){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.replaceMe, new ColorPaletteFragment(), "Fragment_ID")
                .commit();
    }
}
