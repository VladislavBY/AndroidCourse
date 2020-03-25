package by.popkov.homework2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class CustomView extends View {
    private Paint paintLeftBottom;
    private Paint paintLeftTop;
    private Paint paintRightTop;
    private Paint paintRightBottom;
    private Paint paintCenter;
    private int bigCircleRadius = 400;
    private int smallCircleRadius = 150;
    Context myContext;
    Canvas myCanvas;


    int centerX;
    int centerY;

    public CustomView(Context context) {
        super(context);
        myContext = context;
        initPaintRightBottom();
        initPaintLeftBottom();
        initPaintLeftTop();
        initPaintRightTop();
        initPaintCenter();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        myContext = context;
        initPaintRightBottom();
        initPaintLeftBottom();
        initPaintLeftTop();
        initPaintRightTop();
        initPaintCenter();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        myContext = context;
        initPaintRightBottom();
        initPaintLeftBottom();
        initPaintLeftTop();
        initPaintRightTop();
        initPaintCenter();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        myContext = context;
        initPaintRightBottom();
        initPaintLeftBottom();
        initPaintLeftTop();
        initPaintRightTop();
        initPaintCenter();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        invalidate();
        float eventX = event.getX();
        float eventY = event.getY();
        colorChange(eventX, eventY);
        String pos = String.format("Нажаты координаты %f:%f", eventX, eventY);
        Toast.makeText(myContext, String.valueOf(getRandomColor()), Toast.LENGTH_SHORT).show();
        paintLeftTop.setColor(getRandomColor());
        myCanvas.drawArc(centerX - bigCircleRadius, centerY - bigCircleRadius,
                centerX + bigCircleRadius, centerY + bigCircleRadius,
                180, 90, true, paintLeftTop);
        return super.onTouchEvent(event);
    }

    private void colorChange(float eventX, float eventY) {
        if (eventX > centerX - bigCircleRadius && eventX < centerX) {
            if (eventY > centerY - bigCircleRadius && eventY < centerY) {
                paintLeftTop.setColor(getRandomColor());
            }
//            }else if (eventY < centerY + bigCircleRadius && eventY > centerY){}
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
        canvas.drawArc(centerX - bigCircleRadius, centerY - bigCircleRadius,
                centerX + bigCircleRadius, centerY + bigCircleRadius,
                0, 90, true, paintRightBottom);

        canvas.drawArc(centerX - bigCircleRadius, centerY - bigCircleRadius,
                centerX + bigCircleRadius, centerY + bigCircleRadius,
                90, 90, true, paintLeftBottom);

        canvas.drawArc(centerX - bigCircleRadius, centerY - bigCircleRadius,
                centerX + bigCircleRadius, centerY + bigCircleRadius,
                180, 90, true, paintLeftTop);

        canvas.drawArc(centerX - bigCircleRadius, centerY - bigCircleRadius,
                centerX + bigCircleRadius, centerY + bigCircleRadius,
                270, 90, true, paintRightTop);

        canvas.drawCircle(centerX, centerY, smallCircleRadius, paintCenter);

        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        centerX = w / 2;
        centerY = h / 2;
        super.onSizeChanged(w, h, oldw, oldh);
    }


    private void initPaintRightBottom() {
        paintRightBottom = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintRightBottom.setColor(Color.BLUE);
        paintRightBottom.setAntiAlias(true);
        paintRightBottom.setStyle(Paint.Style.FILL);
    }

    private void initPaintLeftBottom() {
        paintLeftBottom = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintLeftBottom.setColor(Color.RED);
        paintLeftBottom.setAntiAlias(true);
        paintLeftBottom.setStyle(Paint.Style.FILL);
    }

    private void initPaintLeftTop() {
        paintLeftTop = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintLeftTop.setColor(Color.YELLOW);
        paintLeftTop.setAntiAlias(true);
        paintLeftTop.setStyle(Paint.Style.FILL);
    }

    private void initPaintRightTop() {
        paintRightTop = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintRightTop.setColor(Color.GREEN);
        paintRightTop.setAntiAlias(true);
        paintRightTop.setStyle(Paint.Style.FILL);
    }

    private void initPaintCenter() {
        paintCenter = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCenter.setColor(Color.GRAY);
        paintCenter.setAntiAlias(true);
        paintCenter.setStyle(Paint.Style.FILL);
    }
}
