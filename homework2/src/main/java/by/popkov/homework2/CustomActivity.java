package by.popkov.homework2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Locale;

public class CustomActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        CustomView customView = findViewById(R.id.colorCircle);
        customView.setListener(new CustomView.OnTouchEvent() {
            @Override
            public void onTouchEvent(float eventX, float eventY) {
                String pos = String.format(Locale.ENGLISH, "Нажаты координаты %.0f:%.0f", eventX, eventY);
                Toast.makeText(CustomActivity.this, pos, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
