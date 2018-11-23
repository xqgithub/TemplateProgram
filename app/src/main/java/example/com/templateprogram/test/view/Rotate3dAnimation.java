package example.com.templateprogram.test.view;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import example.com.templateprogram.base.MyApp;

/**
 * Created by beijixiong on 2018/11/20.
 * 3D 翻转
 * <p>
 * 安卓自定义View进阶-Matrix Camera:http://www.gcssloop.com/customview/matrix-3d-camera
 */

public class Rotate3dAnimation extends Animation {

    private final float mFromDegrees;
    private final float mToDegrees;
    private final float mCenterX;
    private final float mCenterY;
    private final float mDepthZ;
    private final boolean mReverse;
    private Camera mCamera;
    private float scale = 1;    // <------- 像素密度

    /**
     * 创建一个绕y轴旋转的3D动画效果，旋转过程中具有深度调节，可以指定旋转中心。
     *
     * @param fromDegrees 起始时角度
     * @param toDegrees   结束时角度
     * @param centerX     旋转中心x坐标
     * @param centerY     旋转中心y坐标
     * @param depthZ      最远到达的z轴坐标
     * @param reverse     true 表示由从0到depthZ，false相反
     */
    public Rotate3dAnimation(float fromDegrees, float toDegrees,
                             float centerX, float centerY, float depthZ, boolean reverse) {
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mCenterX = centerX;
        mCenterY = centerY;
        mDepthZ = depthZ;//Z轴移动的距离，这个来影响视觉效果，可以解决flip animation那个给人看似放大的效果
        mReverse = reverse;
        scale = MyApp.getApplication().getResources().getDisplayMetrics().density;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDegrees = mFromDegrees;
        float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;

        final Matrix matrix = t.getMatrix();

        Log.i("interpolatedTime", interpolatedTime + "");
        camera.save();
        if (mReverse) {
            camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
        } else {
            camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
        }
        camera.rotateY(degrees);

        camera.getMatrix(matrix);
        camera.restore();

        // 修正失真，主要修改 MPERSP_0 和 MPERSP_1
        float[] mValues = new float[9];
        matrix.getValues(mValues);//获取数值
        mValues[6] = mValues[6] / scale;//数值修正
        mValues[7] = mValues[7] / scale;//数值修正
        matrix.setValues(mValues);//重新赋值

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
