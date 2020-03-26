package by.popkov.homework2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;


public class CustomView extends View {
    private Paint paintLeftBottom;
    private Paint paintLeftTop;
    private Paint paintRightTop;
    private Paint paintRightBottom;
    private Paint paintCenter;

    private int bigRadius;
    private int smallRadius;

    int centerX;
    int centerY;

    int leftSideBig;
    int rightSideBig;
    int topSideBig;
    int bottomSideBig;

    interface OnTouchEvent {
        void onTouchEvent(float eventX, float eventY);
    }

    private OnTouchEvent listener;

    public void setListener(OnTouchEvent listener) {
        this.listener = listener;
    }

    public CustomView(Context context) {
        super(context);
        initPaints();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaints();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaints();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaints();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        colorChange(eventX, eventY);
        if (listener != null) {
            listener.onTouchEvent(eventX, eventY);
        }
        invalidate();
        return super.onTouchEvent(event);
    }

    private void colorChange(float eventX, float eventY) {
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
        canvas.drawArc(leftSideBig, topSideBig, rightSideBig, bottomSideBig,
                0, 90, true, paintRightBottom);

        canvas.drawArc(leftSideBig, topSideBig, rightSideBig, bottomSideBig,
                90, 90, true, paintLeftBottom);

        canvas.drawArc(leftSideBig, topSideBig, rightSideBig, bottomSideBig,
                180, 90, true, paintLeftTop);

        canvas.drawArc(leftSideBig, topSideBig, rightSideBig, bottomSideBig,
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
        leftSideBig = centerX - bigRadius;
        rightSideBig = centerX + bigRadius;
        topSideBig = centerY - bigRadius;
        bottomSideBig = centerY + bigRadius;
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
