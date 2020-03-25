package by.popkov.homework2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {
//    private Paint paint;

    public CustomView(Context context) {
        super(context);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GREEN);
        super.onDraw(canvas);
    }

//    private void initPaint() {
//        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setColor(Color.BLUE);
//        paint.setAntiAlias(true);
//        paint.setStyle(Paint.Style.FILL);
//    }
}
