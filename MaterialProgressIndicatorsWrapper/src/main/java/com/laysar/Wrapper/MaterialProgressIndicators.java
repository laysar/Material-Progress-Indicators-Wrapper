package com.laysar.Wrapper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MaterialProgressIndicators extends FrameLayout {
    public interface ProgressListener {
        public default void onShow() {
        }

        public default void onHide() {
        }

        public default void onProgressChange(int Progress) {
        }

        public default void onProgressComplete() {
        }

        public default void onProgressAnimationEnd() {
        }
    }

    public static final class ShowAnimation {
        public static final int NONE = 0;
        public static final int OUTWARD = 1;
        public static final int INWARD = 2;

        @IntDef({
                NONE,
                OUTWARD,
                INWARD
        })
        @Retention(RetentionPolicy.SOURCE)
        public @interface Mode {
        }

        private ShowAnimation() {
        }
    }

    public static final class HideAnimation {
        public static final int NONE = 0;
        public static final int OUTWARD = 1;
        public static final int INWARD = 2;
        public static final int ESCAPE = 3;

        @IntDef({
                NONE,
                OUTWARD,
                INWARD,
                ESCAPE
        })
        @Retention(RetentionPolicy.SOURCE)
        public @interface Mode {
        }

        private HideAnimation() {
        }
    }

    public static final class HideVisibility {
        public static final int VISIBLE = View.VISIBLE;
        public static final int INVISIBLE = View.INVISIBLE;
        public static final int GONE = View.GONE;

        @IntDef({
                VISIBLE,
                INVISIBLE,
                GONE
        })
        @Retention(RetentionPolicy.SOURCE)
        public @interface Mode {
        }

        private HideVisibility() {
        }
    }

    public static final class Direction {
        public static final int AUTO = 0;
        public static final int NATURAL = 1;
        public static final int START_TO_END = 2;
        public static final int END_TO_START = 3;
        public static final int LEFT_TO_RIGHT = 4;
        public static final int RIGHT_TO_LEFT = 5;

        @IntDef({
                AUTO,
                NATURAL,
                START_TO_END,
                END_TO_START,
                LEFT_TO_RIGHT,
                RIGHT_TO_LEFT
        })
        @Retention(RetentionPolicy.SOURCE)
        public @interface Mode {
        }

        private Direction() {
        }
    }

    public static final class Animation {
        public static final int SMOOTH = 0;
        public static final int TRASH = 1;
        public static final int DISJOINT = 2;
        public static final int CONTIGUOUS = 3;

        @IntDef({
                SMOOTH,
                TRASH,
                DISJOINT,
                CONTIGUOUS
        })
        @Retention(RetentionPolicy.SOURCE)
        public @interface Mode {
        }

        private Animation() {
        }
    }

    private IndicatorController ActiveController;
    private int ActiveIndicatorType;
    private @Direction.Mode int ActiveDirection = Direction.NATURAL;

    public MaterialProgressIndicators(Context AppContext) {
        this(AppContext, null);
    }

    public MaterialProgressIndicators(Context AppContext, AttributeSet Attributes) {
        this(AppContext, Attributes, 0);
    }

    public MaterialProgressIndicators(Context AppContext, AttributeSet Attributes, int DefaultStyleAttribute) {
        this(AppContext, Attributes, DefaultStyleAttribute, 0);
    }

    public MaterialProgressIndicators(
            Context AppContext,
            AttributeSet Attributes,
            int DefaultStyleAttribute,
            int DefaultStyleResource
    ) {
        super(AppContext, Attributes, DefaultStyleAttribute, DefaultStyleResource);
        Initialize(AppContext, Attributes, DefaultStyleAttribute, DefaultStyleResource);
    }

    public void Show() {
        ActiveController.Show();
    }

    public void Hide() {
        ActiveController.Hide();
    }

    public void setColor(int... Colors) {
        ActiveController.setColor(Colors);
    }

    public void setSize(String Size) {
        if (ActiveController instanceof LoadingIndicatorController) {
            ((LoadingIndicatorController) ActiveController).setSize(Size);
            return;
        }

        ((ProgressIndicatorController) ActiveController).setSize(Size);
    }

    public void setInset(String Inset) {
        RequireCircularController("setInset").setInset(Inset);
    }

    public void setDirection(@Direction.Mode int Direction) {
        ActiveController.setDirection(Direction, getLayoutDirection());
        ActiveDirection = Direction;
    }

    public void setAnimation(@Animation.Mode int Animation) {
        RequireProgressController("setAnimation").setAnimation(Animation);
    }

    public void setStopSize(String Size) {
        RequireLinearController("setStopSize").setStopSize(Size);
    }

    public void setStopPadding(String Padding) {
        RequireLinearController("setStopPadding").setStopPadding(Padding);
    }

    public void setInnerCorners(String Corners) {
        RequireLinearController("setInnerCorners").setInnerCorners(Corners);
    }

    public void setContainerColor(int Color) {
        RequireLoadingController("setContainerColor").setContainerColor(Color);
    }

    public void setContainerSize(String Size) {
        RequireLoadingController("setContainerSize").setContainerSize(Size);
    }

    public void setContainerSize(String Height, String Width) {
        RequireLoadingController("setContainerSize").setContainerSize(Height, Width);
    }

    public void setProgressListener(ProgressListener Listener) {
        ActiveController.setProgressListener(Listener);
    }

    public void setProgress(int Progress) {
        RequireProgressController("setProgress").setProgress(Progress);
    }

    public void setProgress(int Progress, boolean Animated) {
        RequireProgressController("setProgress").setProgress(Progress, Animated);
    }

    public void setMax(int Max) {
        RequireProgressController("setMax").setMax(Max);
    }

    public void setIndeterminate(boolean Indeterminate) {
        RequireProgressController("setIndeterminate").setIndeterminate(Indeterminate);
    }

    public void setTrackColor(int Color) {
        RequireProgressController("setTrackColor").setTrackColor(Color);
    }

    public void setTrackThickness(String Thickness) {
        RequireProgressController("setTrackThickness").setTrackThickness(Thickness);
    }

    public void setTrackCorners(String Corners) {
        RequireProgressController("setTrackCorners").setTrackCorners(Corners);
    }

    public void setTrackGap(String Gap) {
        RequireProgressController("setTrackGap").setTrackGap(Gap);
    }

    public void setSpeed(float Speed) {
        RequireProgressController("setSpeed").setSpeed(Speed);
    }

    public void setSpring(float Stiffness, float Damping) {
        RequireProgressController("setSpring").setSpring(Stiffness, Damping);
    }

    public void setSpringStiffness(float Stiffness) {
        RequireProgressController("setSpringStiffness").setSpringStiffness(Stiffness);
    }

    public void setSpringDamping(float Damping) {
        RequireProgressController("setSpringDamping").setSpringDamping(Damping);
    }

    public void setShowAnimation(@ShowAnimation.Mode int Animation) {
        RequireProgressController("setShowAnimation").setShowAnimation(Animation);
    }

    public void setHideAnimation(@HideAnimation.Mode int Animation) {
        RequireProgressController("setHideAnimation").setHideAnimation(Animation);
    }

    public void setHideVisibility(@HideVisibility.Mode int Visibility) {
        RequireProgressController("setHideVisibility").setHideVisibility(Visibility);
    }

    public void setWaveLength(String Length) {
        RequireProgressController("setWaveLength").setWaveLength(Length);
    }

    public void setWaveLength(String Determinate, String Indeterminate) {
        RequireProgressController("setWaveLength").setWaveLength(Determinate, Indeterminate);
    }

    public void setWaveAmplitude(String Amplitude) {
        RequireProgressController("setWaveAmplitude").setWaveAmplitude(Amplitude);
    }

    public void setWaveSpeed(String Speed) {
        RequireProgressController("setWaveSpeed").setWaveSpeed(Speed);
    }

    public void setWaveRange(float Min, float Max) {
        RequireProgressController("setWaveRange").setWaveRange(Min, Max);
    }

    public void setAutoHide(boolean Enabled) {
        RequireProgressController("setAutoHide").setAutoHide(Enabled);
    }

    @Override
    public void onRtlPropertiesChanged(int LayoutDirection) {
        super.onRtlPropertiesChanged(LayoutDirection);

        if (ActiveController == null) {
            return;
        }

        ActiveController.setDirection(ActiveDirection, LayoutDirection);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (ActiveController != null) {
            InstallManagedIndicator();
        }
    }

    private void Initialize(
            Context AppContext,
            AttributeSet Attributes,
            int DefaultStyleAttribute,
            int DefaultStyleResource
    ) {
        ProgressAttributes.Configuration Configuration = ProgressAttributes.Parse(
                AppContext,
                Attributes,
                DefaultStyleAttribute,
                DefaultStyleResource
        );
        IndicatorController CreatedController = CreateController(
                AppContext,
                Configuration
        );

        ActiveIndicatorType = Configuration.IndicatorType;
        ActiveDirection = Configuration.Direction;
        ActiveController = CreatedController;
        InstallManagedIndicator();
        ActiveController.setDirection(ActiveDirection, getLayoutDirection());
        setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_NO);
    }

    private IndicatorController CreateController(
            Context AppContext,
            ProgressAttributes.Configuration Configuration
    ) {
        if (Configuration.IndicatorType == ProgressAttributes.INDICATOR_TYPE_LOADING) {
            return new LoadingIndicatorController(AppContext, this, Configuration);
        }

        return new ProgressIndicatorController(AppContext, this, Configuration);
    }

    private void InstallManagedIndicator() {
        View ManagedIndicator = ActiveController.getIndicator();
        FrameLayout.LayoutParams ChildLayoutParameters = CreateChildLayoutParameters();

        removeAllViews();
        addView(ManagedIndicator, ChildLayoutParameters);
    }

    private FrameLayout.LayoutParams CreateChildLayoutParameters() {
        if (ActiveIndicatorType == ProgressAttributes.INDICATOR_TYPE_LINEAR) {
            return new FrameLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER_VERTICAL
            );
        }

        return new FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
        );
    }

    private LoadingIndicatorController RequireLoadingController(String MethodName) {
        if (ActiveController instanceof LoadingIndicatorController) {
            return (LoadingIndicatorController) ActiveController;
        }

        throw new UnsupportedOperationException(
                MethodName + " is applicable only to LoadingIndicator."
        );
    }

    private ProgressIndicatorController RequireProgressController(String MethodName) {
        if (ActiveController instanceof ProgressIndicatorController) {
            return (ProgressIndicatorController) ActiveController;
        }

        throw new UnsupportedOperationException(
                MethodName + " is applicable only to Circular and Linear progress indicators."
        );
    }

    private ProgressIndicatorController RequireCircularController(String MethodName) {
        if (ActiveIndicatorType == ProgressAttributes.INDICATOR_TYPE_CIRCULAR
                && ActiveController instanceof ProgressIndicatorController) {
            return (ProgressIndicatorController) ActiveController;
        }

        throw new UnsupportedOperationException(
                MethodName + " is applicable only to CircularProgressIndicator."
        );
    }

    private ProgressIndicatorController RequireLinearController(String MethodName) {
        if (ActiveIndicatorType == ProgressAttributes.INDICATOR_TYPE_LINEAR
                && ActiveController instanceof ProgressIndicatorController) {
            return (ProgressIndicatorController) ActiveController;
        }

        throw new UnsupportedOperationException(
                MethodName + " is applicable only to LinearProgressIndicator."
        );
    }
}
