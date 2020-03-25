package by.popkov.homework2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Locale;


public class CustomView extends View {
    private Paint paintLeftBottom;
    private Paint paintLeftTop;
    private Paint paintRightTop;
    private Paint paintRightBottom;
    private Paint paintCenter;
    private int bigRadius = 400;
    private int smallRadius = 150;
    Context myContext;
    Canvas myCanvas;
    int centerX;
    int centerY;

    interface OnTouchEvent {
        void onTouchEvent(float eventX, float eventY);
    }

    private OnTouchEvent listener;

    public void setListener(OnTouchEvent listener) {
        this.listener = listener;
    }

    public CustomView(Context context) {
        super(context);
        myContext = context;
        initPaints();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        myContext = context;
        initPaints();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        myContext = context;
        initPaints();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        myContext = context;
        initPaints();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        colorChange(eventX, eventY);
        listener.onTouchEvent(eventX, eventY);
        invalidate();
        return super.onTouchEvent(event);
    }

    private void colorChange(float eventX, float eventY) {
        int leftSideBig = centerX - bigRadius;
        int rightSideBig = centerX + bigRadius;
        int topSideBig = centerY - bigRadius;
        int bottomSideBig = centerY + bigRadius;
        if ((eventX > centerX - smallRadius && eventX < centerX + smallRadius) &&
                (eventY > centerY - smallRadius && eventY < centerY + smallRadius)) { //Center
            paintLeftBottom.setColor(getRandomColor());
            paintLeftTop.setColor(getRandomColor());
            paintRightTop.setColor(getRandomColor());
            paintRightBottom.setColor(getRandomColor());
            paintCenter.setColor(getRandomColor());
        } else if (eventX > leftSideBig && eventX < centerX) {  //Left
            if (eventY > topSideBig && eventY < centerY) { //LeftTop
                paintLeftTop.setColor(getRandomColor());
            } else if (eventY < bottomSideBig && eventY > centerY) { //LeftBottom
                paintLeftBottom.setColor(getRandomColor());
            }
        } else if (eventX > centerX && eventX < rightSideBig) { //Right
            if (eventY > topSideBig && eventY < centerY) { // RightTop
                paintRightTop.setColor(getRandomColor());
            } else if ((eventY < bottomSideBig && eventY > centerY)) { //RightBottom
                paintRightBottom.setColor(getRandomColor());
            }
        }
    }

    private int getRandomColor() {
        switch ((int) (Math.random() * 10)) {
            case 0:
                return Color.BLACK;
            case 1:
                return Color.DKGRAY;
            case 2:
                return Color.GRAY;
            case 3:
                return Color.LTGRAY;
            case 4:
                return Color.RED;
            case 5:
                return Color.GREEN;
            case 6:
                return Color.BLUE;
            case 7:
                return Color.YELLOW;
            case 8:
                return Color.CYAN;
            case 9:
                return Color.MAGENTA;
            default:
                return Color.WHITE;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        myCanvas = canvas;
        canvas.drawArc(centerX - bigRadius, centerY - bigRadius,
                centerX + bigRadius, centerY + bigRadius,
                0, 90, true, paintRightBottom);

        canvas.drawArc(centerX - bigRadius, centerY - bigRadius,
                centerX + bigRadius, centerY + bigRadius,
                90, 90, true, paintLeftBottom);

        canvas.drawArc(centerX - bigRadius, centerY - bigRadius,
                centerX + bigRadius, centerY + bigRadius,
                180, 90, true, paintLeftTop);

        canvas.drawArc(centerX - bigRadius, centerY - bigRadius,
                centerX + bigRadius, centerY + bigRadius,
                270, 90, true, paintRightTop);

        canvas.drawCircle(centerX, centerY, smallRadius, paintCenter);

        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int narrowSide = Math.min(w, h);
        bigRadius = narrowSide / 2;
        smallRadius = narrowSide / 6;
        centerX = w / 2;
        centerY = h / 2;
        super.onSizeChanged(w, h, oldw, oldh);
    }


    private void initPaints() {
        paintLeftBottom = initPaintWithRandColor();
        paintLeftTop = initPaintWithRandColor();
        paintRightTop = initPaintWithRandColor();
        paintRightBottom = initPaintWithRandColor();
        paintCenter = initPaintWithRandColor();
    }

    private Paint initPaintWithRandColor() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getRandomColor());
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }
}
