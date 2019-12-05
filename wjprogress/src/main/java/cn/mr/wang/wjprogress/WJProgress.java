package cn.mr.wang.wjprogress;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import cn.shur.kidz.wjprogress.R;

/**
 * @ author LUCKY
 * @ create date 2019/9/19 13:27
 * 圆形/条形进度条
 */
public class WJProgress extends View {
    private static final String TAG = "WJProgress";
    private float maxProgress = 100f;
    private float minProgress = 0f;
    //直线进度条
    public static final int STRAIGHT_LINE = 0;
    //圆形进度条
    public static final int CIRCULAR = 1;
    //直形进度条提示文字的位置选项start
    //进度条左边
    public static final int LEFT = 10;
    //进度条中间，如果进度为0，在进度条正中间，进度条不为0，在进度的中间
    public static final int CENTER = 11;
    //进度条中间，如果进度为0，在进度条右边，进度条不为0，在进度的右边
    public static final int RIGHT = 12;
    //在进度条上方显示，如果选top，设置layout_height的时候需要把文字的高度预留出来，否则提示文字会显示不全
    public static final int TOP = 13;
    //直形进度条提示文字的位置选项end
    //直形进度条提示文字的位置，默认在左边
    private int textGravity = LEFT;
    //宽
    private int width;
    //高
    private int height;
    //进度条进度,
    private float progress = 0;
    //当前进度
    private float currentProgressNumber = 0;
    //进度宽度
    private int progressWidth;
    //进度条外边距
    private int progressMargin = 20;
    //线条形状
    //圆头
    private Paint.Cap defaultCap = Paint.Cap.ROUND;
    //平头
    private Paint.Cap defaultCapButt = Paint.Cap.BUTT;
    //方头
    private Paint.Cap defaultCapSquare = Paint.Cap.SQUARE;
    //进度条背景颜色
    private int progressBackgroundColor = Color.parseColor("#ffffff");
    //进度条颜色
    private int progressColor = Color.parseColor("#f5d66e");
    //进度条字体颜色
    private int progressTipColor = Color.parseColor("#000000");
    //字体颜色
    private int textColor = Color.BLACK;
    //画笔
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //进度
    private float progressNumber = 0;
    //进度字体size
    private int textSize = 66;
    //动画时间
    private long animtionDuration = 5000;
    //是否显示顶部小圆点
    private boolean isShowPoint = false;
    //是否显示进度提示
    private boolean isShowTip = false;
    //进度条提示字体大小
    private int tipTextSize = dip2px(14);
    //前置提示文字
    private String beforeTipText = "";
    //后置提示文字
    private String afterTipText = "";
    //是否显示%
    private boolean showPercent = true;
    //进度条的类型,默认圆形
    private int progressType = CIRCULAR;
    //直线进度条，画笔形状为圆形，需要将宽度进行缩减，宽度的缩减率
    private float indentation = 0.6f;
    //画笔的形状
    //圆头
    private static final int ROUND = 20;
    //平头
    private static final int BUTT = 21;
    //方头
    private static final int SQUARE = 22;
    //画笔默认形状圆头
    private int defaultPaintCap = ROUND;


    public WJProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    /**
     * 初始化
     */
    @SuppressLint("Recycle")
    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WJProgress);
        if (typedArray != null) {
            defaultPaintCap = typedArray.getInteger(R.styleable.WJProgress_wj_paint_cap, ROUND);
            float pWidth = typedArray.getFloat(R.styleable.WJProgress_wj_width, dip2px(10));
            progressWidth = (int) pWidth;
            progressTipColor = typedArray.getColor(R.styleable.WJProgress_wj_progressTextColor, Color.parseColor("#000000"));
            progressBackgroundColor = typedArray.getColor(R.styleable.WJProgress_wj_backgroundColor, Color.parseColor("#ffffff"));
            progressColor = typedArray.getColor(R.styleable.WJProgress_wj_color, Color.parseColor("#f5d66e"));
            isShowPoint = typedArray.getBoolean(R.styleable.WJProgress_wj_showPoint, false);
            isShowTip = typedArray.getBoolean(R.styleable.WJProgress_wj_showProgressText, false);
            int textSize = typedArray.getInt(R.styleable.WJProgress_wj_progressTextSize, 14);
            tipTextSize = dip2px(textSize);
            beforeTipText = typedArray.getString(R.styleable.WJProgress_wj_beforeTipText);
            if (beforeTipText == null) {
                beforeTipText = "";
            }
            afterTipText = typedArray.getString(R.styleable.WJProgress_wj_afterTipText);
            if (afterTipText == null) {
                afterTipText = "";
            }
            showPercent = typedArray.getBoolean(R.styleable.WJProgress_wj_showPercent, true);
            progressType = typedArray.getInteger(R.styleable.WJProgress_wj_progressType, CIRCULAR);
            textGravity = typedArray.getInteger(R.styleable.WJProgress_wj_textGravity, LEFT);
            float p = typedArray.getFloat(R.styleable.WJProgress_wj_progressNumber, 0);
            p = p >= maxProgress ? maxProgress : p < minProgress ? minProgress : p;
            progress = 360 * p / 100;
            progressNumber = p;
        }
    }

    /**
     * 获取画笔的形状，默认是圆头
     *
     * @return Paint.Cap
     */
    private Paint.Cap getDefaultCap() {
        Paint.Cap cap;
        if (defaultPaintCap == BUTT) {
            cap = defaultCapButt;
        } else if (defaultPaintCap == SQUARE) {
            cap = defaultCapSquare;
        } else {
            cap = defaultCap;
        }
        return cap;
    }

    /**
     * 设置进度
     *
     * @param p 进度
     */
    public WJProgress setProgress(float p) {
        p = p >= maxProgress ? maxProgress : p < minProgress ? minProgress : p;
        progressNumber = p;
        progress = 360 * p / 100;
        invalidate();
        return this;
    }

    /**
     * 设置动画
     */
    public void startAnimation() {
        Keyframe keyframe = Keyframe.ofFloat(0, getCurrentProgressNumber());
        Keyframe keyframe1 = Keyframe.ofFloat(0.5f, getProgress() + 1);
        Keyframe keyframe2 = Keyframe.ofFloat(1, getProgress());
        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofKeyframe("progress", keyframe, keyframe1, keyframe2);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(this, propertyValuesHolder);
        objectAnimator.setDuration(1500);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                currentProgressNumber = getProgress();
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.start();
    }


    /**
     * 获取进度
     *
     * @return
     */
    public float getProgress() {
        return progressNumber;
    }

    /**
     * 获取当前进度
     *
     * @return
     */
    private float getCurrentProgressNumber() {
        return currentProgressNumber;
    }

    /**
     * 重写
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (progressType == CIRCULAR) {
            //圆形进度条
            drawCircularProgress(canvas);
        } else if (progressType == STRAIGHT_LINE) {
            //直线进度条
            drawStraightLineProgress(canvas);
        }

    }

    /**
     * 画直线进度条
     *
     * @param canvas
     */
    @SuppressLint("NewApi")
    private void drawStraightLineProgress(Canvas canvas) {
        progressNumber = progressNumber >= maxProgress ? maxProgress : progressNumber;
        //获取宽度
        width = getWidth();
        //获取高度
        height = getHeight();
        //设置进度条背景色画笔
        setProgressBackgroundPaint();
        int startY = (height / 2);

        //如果是圆头，宽度进行缩减
        if (defaultPaintCap == ROUND) {
            int progressStartX = (int) (progressWidth * indentation);
            canvas.drawLine(progressStartX, startY, width - progressStartX, startY, paint);
        } else {
            canvas.drawLine(0, startY, width, startY, paint);
        }

        //设置进度条颜色画笔
        setProgressPaint();

        float progressn = progressNumber == 0 ? 1 : progressNumber;
        //画原点
        if (isShowPoint) {
            paint.setColor(progressColor);
            int progressStartX = (int) ((width * progressn / 100) + (progressWidth * indentation) + progressWidth / 4);
            canvas.drawCircle((width * progressn / 100) >= progressWidth ? (width * progressn / 100) >= width ? progressStartX - progressWidth * 2 : progressStartX - progressWidth : progressStartX, (height / 2), progressWidth / 2, paint);
        }

        if (defaultPaintCap == ROUND) {
            int progressStartX = (int) (progressWidth * indentation);
            int progressEndX = (int) (width * progressn / 100 - progressStartX);
            canvas.drawLine(progressStartX, startY, progressEndX <= 0 ? progressStartX : progressEndX, startY, paint);
        } else {
            canvas.drawLine(0, startY, width * progressn / 100, startY, paint);
        }

        //进度提示
        if (isShowTip) {
            setTextPaint();
            canvas.save();
            //文本X轴的位置
            float textX = paint.measureText(getTipText()) / 2;
            //文本Y轴的位置
            float textY = Math.abs(paint.ascent() + paint.descent()) / 2;
            //移动到中间位置
            canvas.translate(getDx(width), getDy(height));
            //设置进度
            canvas.drawText(getTipText(), -textX, textY, paint);
            canvas.restore();
        }
    }

    /**
     * 画圆形进度条
     *
     * @param canvas
     */
    @SuppressLint("NewApi")
    private void drawCircularProgress(Canvas canvas) {
        //获取宽度
        width = getWidth();
        //获取高度
        height = getHeight();
        //设置进度条背景色画笔
        setProgressBackgroundPaint();
        //画进度条背景圆
        canvas.drawCircle((width / 2), (height / 2), ((width - progressWidth) / 2) - (progressWidth / 2), paint);
        //设置进度条颜色画笔
        setProgressPaint();
        float p = progress;
        //画原点
        if (isShowPoint) {
            float radian = (float) Math.toRadians(progress - 90);
            float x = (float) ((width / 2) + Math.cos(radian) * (width - progressWidth * 2) / 2);
            float y = (float) ((height / 2) + Math.sin(radian) * (width - progressWidth * 2) / 2);
            paint.setColor(progressColor);
            canvas.drawCircle(x, y, progressWidth / 2, paint);
        } else {
            if (progress == 0) {
                p = 1;
            }
        }
        //画弧度
        canvas.drawArc(progressWidth, progressWidth,
                (width - progressWidth),
                (height - progressWidth),
                -90, p,
                false, paint);
        //进度提示
        if (isShowTip) {
            setTextPaint();
            canvas.save();
            //文本X轴的位置,measureText(): 它测量的是文字绘制时所占用的宽度
            float textX = paint.measureText(getTipText()) / 2;
            //文本Y轴的位置,普通的字符，上不会高过 ascent ，下不会低过 descent,Math.abs(n),绝对值
            float textY = Math.abs(paint.ascent() + paint.descent()) / 2;
            //移动到中间位置,Canvas.translate(float dx, float dy) 平移
            canvas.translate(width / 2, height / 2);
            //设置进度
            canvas.drawText(getTipText(), -textX, textY, paint);
            canvas.restore();
        }
    }

    /**
     * 直线进度条，提示文字x轴的位置
     *
     * @param w
     * @return
     */
    private float getDx(int w) {
        float dx;
        if (textGravity == CENTER) {
            dx = w / 2;
        } else if (textGravity == RIGHT) {
            dx = w - ((paint.measureText(getTipText()) / 2) + dip2px(5));
        } else {
            dx = ((paint.measureText(getTipText()) / 2) + dip2px(5));
        }
        return dx;
    }

    /**
     * 直线进度条，提示文字y轴的位置
     *
     * @param h
     * @return
     */
    private float getDy(int h) {
        return h - (progressWidth * 2) - dip2px(1);
    }

    /**
     * 设置进度条背景画笔
     */
    private void setProgressBackgroundPaint() {
        //划线
        paint.setStyle(Paint.Style.STROKE);
        //线条的宽度
        paint.setStrokeWidth(progressWidth);
        //线条的形状
        paint.setStrokeCap(getDefaultCap());
        //线条颜色
        paint.setColor(progressBackgroundColor);
        //抗锯齿
        paint.setAntiAlias(true);
    }

    /**
     * 设置进度条画笔
     */
    private void setProgressPaint() {
        //设置画笔颜色
        paint.setColor(progressColor);
    }

    /**
     * 设置文字画笔
     */
    private void setTextPaint() {
        //设置画笔颜色
        paint.setColor(progressTipColor);
        //填充
        paint.setStyle(Paint.Style.FILL);
        //线条的宽度
        paint.setStrokeWidth(10);
        //字体大小
        paint.setTextSize(tipTextSize);

    }

    /**
     * 获取提示文字
     *
     * @return
     */
    private String getTipText() {
        String tt;
        if (showPercent) {
            tt = beforeTipText + (int) progressNumber + "%" + afterTipText;
        } else {
            tt = beforeTipText + (int) progressNumber + afterTipText;
        }
        return tt;
    }

    private int dip2px(float dip) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }
}
