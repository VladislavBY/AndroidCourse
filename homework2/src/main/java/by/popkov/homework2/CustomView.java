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

    private int centerX;
    private int centerY;

    private int leftSideBig;
    private int rightSideBig;
    private int topSideBig;
    private int bottomSideBig;

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
                (eventY > centerY - smallRadius && eventY < centerY + smallRadius)) {
            paintLeftBottom.setColor(getRandomColor());
            paintLeftTop.setColor(getRandomColor());
            paintRightTop.setColor(getRandomColor());
            paintRightBottom.setColor(getRandomColor());
            paintCenter.setColor(getRandomColor());
        } else if (eventX > leftSideBig && eventX < centerX) {
            if (eventY > topSideBig && eventY < centerY) {
                paintLeftTop.setColor(getRandomColor());
            } else if (eventY < bottomSideBig && eventY > centerY) {
                paintLeftBottom.setColor(getRandomColor());
            }
        } else if (eventX > centerX && eventX < rightSideBig) {
            if (eventY > topSideBig && eventY < centerY) {
                paintRightTop.setColor(getRandomColor());
            } else if ((eventY < bottomSideBig && eventY > centerY)) {
                paintRightBottom.setColor(getRandomColor());
            }
        }
    }

    private int getRandomColor() {
        return Color.argb(
                255,
                (int) (Math.random() * 256),
                (int) (Math.random() * 256),
                (int) (Math.random() * 256)
        );
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
