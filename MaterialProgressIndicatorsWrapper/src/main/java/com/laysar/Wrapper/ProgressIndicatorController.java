package com.laysar.Wrapper;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringForce;

import com.google.android.material.progressindicator.BaseProgressIndicator;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.DeterminateDrawable;
import com.google.android.material.progressindicator.LinearProgressIndicator;

final class ProgressIndicatorController extends IndicatorController {
    private final BaseProgressIndicator<?> Indicator;
    private final boolean Circular;
    private boolean ControllerHideDispatching;
    private LinearCornerState LinearTrackCornersState;
    private LinearCornerState LinearInnerCornersState;
    private Float CustomSpringStiffness;
    private Float CustomSpringDamping;
    private int LogicalProgress;
    private boolean ProgressCompleteState;
    private long ProgressStateGeneration;
    private Integer PendingProgressAnimationTarget;
    private Integer DeferredProgressTarget;
    private boolean DeferredProgressAnimated;
    private boolean ControllerProgressDispatching;
    private boolean ControllerIndeterminateDispatching;
    private boolean ControllerInitializationComplete;
    private boolean ControllerWantsIndicatorVisible = true;
    private int ActiveHideVisibility = View.INVISIBLE;
    private final DynamicAnimation.OnAnimationEndListener ProgressSpringEndListener =
            (Animation, Canceled, Value, Velocity) -> HandleProgressSpringEnd(Canceled);

    ProgressIndicatorController(
            Context AppContext,
            View CallbackHost,
            ProgressAttributes.Configuration Configuration
    ) {
        super(CallbackHost, Configuration.ShowDelay, Configuration.MinHideDelay);

        Circular = Configuration.IndicatorType == ProgressAttributes.INDICATOR_TYPE_CIRCULAR;
        Indicator = CreateProgressIndicator(
                AppContext,
                Configuration.IndicatorType,
                Configuration.Variant
        );
        RequireProgressDrawable().addSpringAnimationEndListener(ProgressSpringEndListener);

        if (!Circular) {
            CaptureStyledLinearCornerStates();
            ValidateLinearConfiguration(Configuration);
        }

        if (Configuration.Colors != null) {
            if (Circular) {
                setColor(Configuration.Colors);
            } else {
                Indicator.setIndicatorColor(Configuration.Colors);
            }
        }

        if (Configuration.TrackColor != null) {
            Indicator.setTrackColor(Configuration.TrackColor);
        }

        if (Configuration.TrackThickness != null) {
            ApplyTrackThickness(Configuration.TrackThickness);
        }

        if (Configuration.TrackCorners != null) {
            ApplyTrackCorners(Configuration.TrackCorners);
        }

        if (Configuration.TrackGap != null) {
            Indicator.setIndicatorTrackGapSize(Configuration.TrackGap);
        }

        if (Circular) {
            if (Configuration.Size != null) {
                ApplyCircularSize(Configuration.Size);
            }

            if (Configuration.Inset != null) {
                RequireCircularIndicator("Inset").setIndicatorInset(Configuration.Inset);
            }

            if (Configuration.Animation != null) {
                RequireCircularIndicator("Animation").setIndeterminateAnimationType(
                        ResolveCircularAnimation(Configuration.Animation)
                );
            }
        } else {
            LinearProgressIndicator LinearIndicator = RequireLinearIndicator("Configuration");

            if (Configuration.InnerCorners != null) {
                ApplyLinearInnerCorners(Configuration.InnerCorners);
            }

            if (Configuration.StopSize != null) {
                LinearIndicator.setTrackStopIndicatorSize(Configuration.StopSize);
            }

            if (Configuration.StopPadding != null) {
                LinearIndicator.setTrackStopIndicatorPadding(Configuration.StopPadding);
            }

            if (Configuration.Animation != null) {
                LinearIndicator.setIndeterminateAnimationType(
                        ResolveLinearAnimation(Configuration.Animation)
                );
            }
        }

        if (Configuration.WaveLengthDeterminate != null) {
            Indicator.setWavelengthDeterminate(Configuration.WaveLengthDeterminate);
        }

        if (Configuration.WaveLengthIndeterminate != null) {
            Indicator.setWavelengthIndeterminate(Configuration.WaveLengthIndeterminate);
        }

        if (Configuration.WaveAmplitude != null) {
            Indicator.setWaveAmplitude(Configuration.WaveAmplitude);
        }

        if (Configuration.WaveSpeed != null) {
            Indicator.setWaveSpeed(Configuration.WaveSpeed);
        }

        if (Configuration.WaveRangeMin != null) {
            Indicator.setWaveAmplitudeRampProgressMin(Configuration.WaveRangeMin);
        }

        if (Configuration.WaveRangeMax != null) {
            Indicator.setWaveAmplitudeRampProgressMax(Configuration.WaveRangeMax);
        }

        if (Configuration.Speed != null) {
            Indicator.setIndeterminateAnimatorDurationScale(Configuration.Speed);
        }

        if (Configuration.SpringStiffness != null && Configuration.SpringDamping != null) {
            setSpring(Configuration.SpringStiffness, Configuration.SpringDamping);
        } else if (Configuration.SpringStiffness != null) {
            setSpringStiffness(Configuration.SpringStiffness);
        } else if (Configuration.SpringDamping != null) {
            setSpringDamping(Configuration.SpringDamping);
        }

        if (Configuration.ShowAnimation != null) {
            Indicator.setShowAnimationBehavior(ResolveShowAnimation(Configuration.ShowAnimation));
        }

        if (Configuration.HideAnimation != null) {
            Indicator.setHideAnimationBehavior(ResolveHideAnimation(Configuration.HideAnimation));
        }

        if (Configuration.HideVisibility != null) {
            ApplyHideVisibility(ResolveHideVisibility(Configuration.HideVisibility));
        }

        if (Configuration.AutoHide != null) {
            Indicator.setHideAfterMaxProgress(Configuration.AutoHide);
        }

        if (Configuration.Max != null) {
            Indicator.setMax(Configuration.Max);
        }

        if (Configuration.Progress != null) {
            Indicator.setProgressCompat(Configuration.Progress, false);
        }

        if (Configuration.Indeterminate != null) {
            Indicator.setIndeterminate(Configuration.Indeterminate);
        }

        LogicalProgress = Indicator.getProgress();
        ProgressCompleteState = LogicalProgress == Indicator.getMax();
        PendingProgressAnimationTarget = null;
        DeferredProgressTarget = null;
        DeferredProgressAnimated = false;
        ControllerProgressDispatching = false;
        ControllerIndeterminateDispatching = false;
        ControllerInitializationComplete = true;
    }

    void setProgress(int Progress) {
        ApplyProgress(Progress, false);
    }

    void setProgress(int Progress, boolean Animated) {
        ApplyProgress(Progress, Animated);
    }

    void setMax(int Max) {
        int PreviousMax = Indicator.getMax();
        Integer PreviousPendingProgressAnimationTarget = PendingProgressAnimationTarget;
        boolean RequestedMaxDiffers = Max != PreviousMax;

        if (RequestedMaxDiffers) {
            PendingProgressAnimationTarget = null;
        }

        try {
            Indicator.setMax(Max);
        } catch (RuntimeException | Error Cause) {
            PendingProgressAnimationTarget = PreviousPendingProgressAnimationTarget;
            throw Cause;
        }

        int NewMax = Indicator.getMax();

        if (NewMax == PreviousMax) {
            if (RequestedMaxDiffers) {
                PendingProgressAnimationTarget = PreviousPendingProgressAnimationTarget;
            }

            return;
        }

        LogicalProgress = ResolveProgressTarget(LogicalProgress);
        ProgressCompleteState = LogicalProgress == NewMax;
        ProgressStateGeneration++;

        if (DeferredProgressTarget != null) {
            DeferredProgressTarget = ResolveProgressTarget(DeferredProgressTarget);
        }
    }

    void setIndeterminate(boolean Indeterminate) {
        boolean WasIndeterminate = Indicator.isIndeterminate();

        if (Indeterminate) {
            if (!WasIndeterminate) {
                Integer PreviousPendingProgressAnimationTarget = PendingProgressAnimationTarget;
                Integer PreviousDeferredProgressTarget = DeferredProgressTarget;
                boolean PreviousDeferredProgressAnimated = DeferredProgressAnimated;
                PendingProgressAnimationTarget = null;
                ClearDeferredProgressTransition();

                try {
                    ApplyIndeterminateToMaterial(true);
                } catch (RuntimeException | Error Cause) {
                    PendingProgressAnimationTarget = PreviousPendingProgressAnimationTarget;
                    DeferredProgressTarget = PreviousDeferredProgressTarget;
                    DeferredProgressAnimated = PreviousDeferredProgressAnimated;
                    throw Cause;
                }

                return;
            }

            ApplyIndeterminateToMaterial(true);
            return;
        }

        if (WasIndeterminate && DeferredProgressTarget != null) {
            ApplyIndeterminateToMaterial(false);

            if (DeferredProgressTarget != null) {
                int ManualProgress = DeferredProgressTarget;
                boolean ManualAnimated = DeferredProgressAnimated;
                ApplyProgressToMaterial(ManualProgress, ManualAnimated);
                CompleteDeferredProgressReplay(ManualProgress, ManualAnimated);
            }

            return;
        }

        ApplyIndeterminateToMaterial(false);
    }

    void setTrackColor(int Color) {
        Indicator.setTrackColor(Color);
    }

    void setTrackThickness(String Thickness) {
        int ThicknessPixels = ProgressUnitResolver.ResolveDimension(
                getContext(),
                Thickness,
                "Thickness"
        );

        if (!Circular) {
            LinearProgressIndicator LinearIndicator = RequireLinearIndicator("setTrackThickness");
            ValidateLinearContiguousState(
                    LinearIndicator.getIndeterminateAnimationType(),
                    LinearIndicator.getIndicatorColor().length,
                    ThicknessPixels,
                    LinearTrackCornersState,
                    LinearIndicator.getIndicatorTrackGapSize(),
                    LinearInnerCornersState
            );
        }

        ApplyTrackThickness(ThicknessPixels);
    }

    void setTrackCorners(String Corners) {
        ProgressUnitResolver.DimensionOrPercentResult ResolvedCorners =
                ProgressUnitResolver.ResolveDimensionOrPercent(
                        getContext(),
                        Corners,
                        "Corners"
                );
        ValidateTrackCorners(ResolvedCorners, "Corners");

        if (!Circular) {
            LinearProgressIndicator LinearIndicator = RequireLinearIndicator("setTrackCorners");
            LinearCornerState CandidateCorners = NormalizeLinearTrackCorners(
                    ResolvedCorners,
                    LinearIndicator.getTrackThickness()
            );
            ValidateLinearContiguousState(
                    LinearIndicator.getIndeterminateAnimationType(),
                    LinearIndicator.getIndicatorColor().length,
                    LinearIndicator.getTrackThickness(),
                    CandidateCorners,
                    LinearIndicator.getIndicatorTrackGapSize(),
                    LinearInnerCornersState
            );
        }

        ApplyTrackCorners(ResolvedCorners);
    }

    void setTrackGap(String Gap) {
        int GapPixels = ProgressUnitResolver.ResolveDimension(getContext(), Gap, "Gap");

        if (!Circular) {
            LinearProgressIndicator LinearIndicator = RequireLinearIndicator("setTrackGap");
            ValidateLinearContiguousState(
                    LinearIndicator.getIndeterminateAnimationType(),
                    LinearIndicator.getIndicatorColor().length,
                    LinearIndicator.getTrackThickness(),
                    LinearTrackCornersState,
                    GapPixels,
                    LinearInnerCornersState
            );
        }

        Indicator.setIndicatorTrackGapSize(GapPixels);
    }

    void setSize(String Size) {
        if (!Circular) {
            throw new UnsupportedOperationException(
                    "setSize is not applicable to Linear progress indicators."
            );
        }

        int SizePixels = ProgressUnitResolver.ResolveDimension(getContext(), Size, "Size");
        ApplyCircularSize(SizePixels);
    }

    void setInset(String Inset) {
        CircularProgressIndicator CircularIndicator = RequireCircularIndicator("setInset");
        int InsetPixels = ProgressUnitResolver.ResolveDimension(getContext(), Inset, "Inset");
        CircularIndicator.setIndicatorInset(InsetPixels);
    }

    void setAnimation(@MaterialProgressIndicators.Animation.Mode int Animation) {
        if (Circular) {
            CircularProgressIndicator CircularIndicator = RequireCircularIndicator("setAnimation");
            int ResolvedAnimation = ResolveCircularAnimation(Animation);
            int PreviousAnimation = CircularIndicator.getIndeterminateAnimationType();
            CircularIndicator.setIndeterminateAnimationType(ResolvedAnimation);

            if (PreviousAnimation != CircularIndicator.getIndeterminateAnimationType()
                    && DeferredProgressTarget != null) {
                ResumeDeferredProgressTransition();
            }

            return;
        }

        LinearProgressIndicator LinearIndicator = RequireLinearIndicator("setAnimation");
        int ResolvedAnimation = ResolveLinearAnimation(Animation);

        if (DeferredProgressTarget != null
                && ResolvedAnimation
                == LinearProgressIndicator.INDETERMINATE_ANIMATION_TYPE_CONTIGUOUS) {
            throw new IllegalStateException(
                    "Cannot switch LinearProgressIndicator to Contiguous animation while a deferred determinate progress transition is pending."
            );
        }

        if (ResolvedAnimation == LinearProgressIndicator.INDETERMINATE_ANIMATION_TYPE_CONTIGUOUS) {
            ValidateLinearContiguousState(
                    ResolvedAnimation,
                    LinearIndicator.getIndicatorColor().length,
                    LinearIndicator.getTrackThickness(),
                    LinearTrackCornersState,
                    LinearIndicator.getIndicatorTrackGapSize(),
                    LinearInnerCornersState
            );
        }

        int PreviousAnimation = LinearIndicator.getIndeterminateAnimationType();
        LinearIndicator.setIndeterminateAnimationType(ResolvedAnimation);

        if (PreviousAnimation != LinearIndicator.getIndeterminateAnimationType()
                && DeferredProgressTarget != null) {
            ResumeDeferredProgressTransition();
        }
    }

    void setStopSize(String Size) {
        LinearProgressIndicator LinearIndicator = RequireLinearIndicator("setStopSize");
        int StopSizePixels = ProgressUnitResolver.ResolveDimension(getContext(), Size, "StopSize");
        LinearIndicator.setTrackStopIndicatorSize(StopSizePixels);
    }

    void setStopPadding(String Padding) {
        LinearProgressIndicator LinearIndicator = RequireLinearIndicator("setStopPadding");
        int StopPaddingPixels = ProgressUnitResolver.ResolveDimension(
                getContext(),
                Padding,
                "StopPadding"
        );
        LinearIndicator.setTrackStopIndicatorPadding(StopPaddingPixels);
    }

    void setInnerCorners(String Corners) {
        LinearProgressIndicator LinearIndicator = RequireLinearIndicator("setInnerCorners");
        ProgressUnitResolver.DimensionOrPercentResult ResolvedCorners =
                ProgressUnitResolver.ResolveDimensionOrPercent(
                        getContext(),
                        Corners,
                        "InnerCorners"
                );
        ValidateTrackCorners(ResolvedCorners, "InnerCorners");
        LinearCornerState CandidateCorners = NormalizeLinearInnerCorners(
                ResolvedCorners,
                LinearIndicator.getTrackThickness()
        );
        ValidateLinearContiguousState(
                LinearIndicator.getIndeterminateAnimationType(),
                LinearIndicator.getIndicatorColor().length,
                LinearIndicator.getTrackThickness(),
                LinearTrackCornersState,
                LinearIndicator.getIndicatorTrackGapSize(),
                CandidateCorners
        );
        ApplyLinearInnerCorners(ResolvedCorners);
    }

    void setSpeed(float Speed) {
        ValidateSpeed(Speed);
        Indicator.setIndeterminateAnimatorDurationScale(Speed);
    }

    void setSpring(float Stiffness, float Damping) {
        ValidateSpringStiffness(Stiffness);
        ValidateSpringDamping(Damping);
        SpringForce ProgressSpring = RequireProgressDrawable().getSpringForce();
        ProgressSpring.setStiffness(Stiffness);
        ProgressSpring.setDampingRatio(Damping);
        CustomSpringStiffness = Stiffness;
        CustomSpringDamping = Damping;
    }

    void setSpringStiffness(float Stiffness) {
        ValidateSpringStiffness(Stiffness);
        SpringForce ProgressSpring = RequireProgressDrawable().getSpringForce();
        ProgressSpring.setStiffness(Stiffness);
        CustomSpringStiffness = Stiffness;
    }

    void setSpringDamping(float Damping) {
        ValidateSpringDamping(Damping);
        SpringForce ProgressSpring = RequireProgressDrawable().getSpringForce();
        ProgressSpring.setDampingRatio(Damping);
        CustomSpringDamping = Damping;
    }

    void setShowAnimation(@MaterialProgressIndicators.ShowAnimation.Mode int Animation) {
        Indicator.setShowAnimationBehavior(ResolveShowAnimation(Animation));
    }

    void setHideAnimation(@MaterialProgressIndicators.HideAnimation.Mode int Animation) {
        Indicator.setHideAnimationBehavior(ResolveHideAnimation(Animation));
    }

    void setHideVisibility(@MaterialProgressIndicators.HideVisibility.Mode int Visibility) {
        int ResolvedVisibility = ResolveHideVisibility(Visibility);
        ApplyHideVisibility(ResolvedVisibility);
    }

    void setWaveLength(String Length) {
        int LengthPixels = ProgressUnitResolver.ResolveDimension(getContext(), Length, "Length");
        Indicator.setWavelength(LengthPixels);
    }

    void setWaveLength(String Determinate, String Indeterminate) {
        int DeterminatePixels = ProgressUnitResolver.ResolveDimension(
                getContext(),
                Determinate,
                "Determinate"
        );
        int IndeterminatePixels = ProgressUnitResolver.ResolveDimension(
                getContext(),
                Indeterminate,
                "Indeterminate"
        );
        Indicator.setWavelengthDeterminate(DeterminatePixels);
        Indicator.setWavelengthIndeterminate(IndeterminatePixels);
    }

    void setWaveAmplitude(String Amplitude) {
        int AmplitudePixels = ProgressUnitResolver.ResolveDimension(
                getContext(),
                Amplitude,
                "Amplitude"
        );
        Indicator.setWaveAmplitude(AmplitudePixels);
    }

    void setWaveSpeed(String Speed) {
        int SpeedPixels = ProgressUnitResolver.ResolveDimension(getContext(), Speed, "Speed");
        Indicator.setWaveSpeed(SpeedPixels);
    }

    void setWaveRange(float Min, float Max) {
        ValidateWaveRangeValue(Min, "Min");
        ValidateWaveRangeValue(Max, "Max");

        if (Min > Max) {
            throw new IllegalArgumentException(
                    "Min must be less than or equal to Max. Supplied values: " + Min + ", " + Max
            );
        }

        Indicator.setWaveAmplitudeRampProgressMin(Min);
        Indicator.setWaveAmplitudeRampProgressMax(Max);
    }

    void setAutoHide(boolean Enabled) {
        Indicator.setHideAfterMaxProgress(Enabled);
    }

    private void ApplyProgress(int Progress, boolean Animated) {
        if (!Circular) {
            LinearProgressIndicator LinearIndicator = RequireLinearIndicator("setProgress");

            if (LinearIndicator.isIndeterminate()
                    && LinearIndicator.getIndeterminateAnimationType()
                    == LinearProgressIndicator.INDETERMINATE_ANIMATION_TYPE_CONTIGUOUS) {
                return;
            }
        }

        boolean WasIndeterminate = Indicator.isIndeterminate();
        int ResolvedProgress = ResolveProgressTarget(Progress);
        int PreviousLogicalProgress = LogicalProgress;
        boolean PreviousCompleteState = ProgressCompleteState;
        Integer PreviousPendingProgressAnimationTarget = PendingProgressAnimationTarget;
        Integer PreviousDeferredProgressTarget = DeferredProgressTarget;
        boolean PreviousDeferredProgressAnimated = DeferredProgressAnimated;
        boolean ProgressChanged = ResolvedProgress != PreviousLogicalProgress;

        if (!Animated) {
            PendingProgressAnimationTarget = null;
        } else if (ProgressChanged) {
            PendingProgressAnimationTarget = ResolvedProgress;
        }

        if (WasIndeterminate) {
            DeferredProgressTarget = ResolvedProgress;
            DeferredProgressAnimated = Animated;
        } else {
            ClearDeferredProgressTransition();
        }

        try {
            ApplyProgressToMaterial(ResolvedProgress, Animated);
        } catch (RuntimeException | Error Cause) {
            PendingProgressAnimationTarget = PreviousPendingProgressAnimationTarget;
            DeferredProgressTarget = PreviousDeferredProgressTarget;
            DeferredProgressAnimated = PreviousDeferredProgressAnimated;
            throw Cause;
        }

        if (!ProgressChanged) {
            return;
        }

        LogicalProgress = ResolvedProgress;
        ProgressCompleteState = LogicalProgress == Indicator.getMax();
        ProgressStateGeneration++;
        long CurrentGeneration = ProgressStateGeneration;
        boolean EnteredCompleteState = !PreviousCompleteState && ProgressCompleteState;

        DispatchProgressChange(ResolvedProgress);

        if (EnteredCompleteState
                && ProgressStateGeneration == CurrentGeneration
                && LogicalProgress == ResolvedProgress
                && ProgressCompleteState
                && LogicalProgress == Indicator.getMax()) {
            DispatchProgressComplete();
        }
    }

    private void ClearDeferredProgressTransition() {
        DeferredProgressTarget = null;
        DeferredProgressAnimated = false;
    }

    private void CompleteDeferredProgressReplay(int Progress, boolean Animated) {
        if (DeferredProgressTarget != null
                && DeferredProgressTarget == Progress
                && DeferredProgressAnimated == Animated) {
            ClearDeferredProgressTransition();
        }
    }

    private void ResumeDeferredProgressTransition() {
        if (!ControllerInitializationComplete || DeferredProgressTarget == null) {
            return;
        }

        int Progress = DeferredProgressTarget;
        boolean Animated = DeferredProgressAnimated;

        if (!Circular) {
            LinearProgressIndicator LinearIndicator = RequireLinearIndicator(
                    "Deferred progress transition"
            );

            if (LinearIndicator.isIndeterminate()
                    && LinearIndicator.getIndeterminateAnimationType()
                    == LinearProgressIndicator.INDETERMINATE_ANIMATION_TYPE_CONTIGUOUS) {
                throw new IllegalStateException(
                        "Cannot resume a deferred determinate progress transition while LinearProgressIndicator uses Contiguous indeterminate animation."
                );
            }
        }

        ApplyProgressToMaterial(Progress, Animated);

        if (!Indicator.isIndeterminate()) {
            CompleteDeferredProgressReplay(Progress, Animated);
        }
    }

    private void ApplyProgressToMaterial(int Progress, boolean Animated) {
        ControllerProgressDispatching = true;

        try {
            Indicator.setProgressCompat(Progress, Animated);
        } finally {
            ControllerProgressDispatching = false;
        }
    }

    private boolean ConsumeControllerProgressDispatch() {
        if (!ControllerProgressDispatching) {
            return false;
        }

        ControllerProgressDispatching = false;
        return true;
    }

    private void ApplyIndeterminateToMaterial(boolean Indeterminate) {
        ControllerIndeterminateDispatching = true;

        try {
            Indicator.setIndeterminate(Indeterminate);
        } finally {
            ControllerIndeterminateDispatching = false;
        }
    }

    private boolean ConsumeControllerIndeterminateDispatch() {
        if (!ControllerIndeterminateDispatching) {
            return false;
        }

        ControllerIndeterminateDispatching = false;
        return true;
    }

    private int ResolveProgressTarget(int Progress) {
        int Minimum = Indicator.getMin();
        int Maximum = Indicator.getMax();
        return Math.max(Minimum, Math.min(Progress, Maximum));
    }

    private DeterminateDrawable<?> RequireProgressDrawable() {
        DeterminateDrawable<?> ProgressDrawable = Indicator.getProgressDrawable();

        if (ProgressDrawable == null) {
            throw new IllegalStateException("Progress indicator has no determinate progress drawable.");
        }

        return ProgressDrawable;
    }

    private void ReapplySpringConfiguration() {
        if (CustomSpringStiffness == null && CustomSpringDamping == null) {
            return;
        }

        SpringForce ProgressSpring = RequireProgressDrawable().getSpringForce();

        if (CustomSpringStiffness != null) {
            ProgressSpring.setStiffness(CustomSpringStiffness);
        }

        if (CustomSpringDamping != null) {
            ProgressSpring.setDampingRatio(CustomSpringDamping);
        }
    }

    private void HandleProgressSpringEnd(boolean Canceled) {
        if (Canceled) {
            PendingProgressAnimationTarget = null;
            return;
        }

        if (PendingProgressAnimationTarget == null) {
            return;
        }

        int PendingTarget = PendingProgressAnimationTarget;

        if (LogicalProgress != PendingTarget) {
            PendingProgressAnimationTarget = null;
            return;
        }

        PendingProgressAnimationTarget = null;
        DispatchProgressAnimationEnd();
    }

    @Override
    protected View getManagedIndicator() {
        return Indicator;
    }

    @Override
    protected void PerformMaterialShow() {
        boolean PreviousControllerWantsIndicatorVisible = ControllerWantsIndicatorVisible;
        ControllerWantsIndicatorVisible = true;

        try {
            Indicator.show();

            if (Indicator.getVisibility() == View.VISIBLE) {
                ApplyCurrentMaterialVisibility(true);
            }
        } catch (RuntimeException | Error Cause) {
            ControllerWantsIndicatorVisible = PreviousControllerWantsIndicatorVisible;
            throw Cause;
        }
    }

    @Override
    protected void PerformMaterialHide() {
        boolean PreviousControllerWantsIndicatorVisible = ControllerWantsIndicatorVisible;
        ControllerWantsIndicatorVisible = false;
        ControllerHideDispatching = true;

        try {
            Indicator.hide();
        } catch (RuntimeException | Error Cause) {
            ControllerWantsIndicatorVisible = PreviousControllerWantsIndicatorVisible;
            throw Cause;
        } finally {
            ControllerHideDispatching = false;
        }
    }

    private void ApplyHideVisibility(int Visibility) {
        Indicator.setVisibilityAfterHide(Visibility);
        ActiveHideVisibility = Visibility;
    }

    private void HideMaterialDrawablesImmediately() {
        if (Indicator.getProgressDrawable() != null) {
            Indicator.getProgressDrawable().hideNow();
        }

        if (Indicator.getIndeterminateDrawable() != null) {
            Indicator.getIndeterminateDrawable().hideNow();
        }
    }

    private void ApplyCurrentMaterialVisibility(boolean Animate) {
        if (Circular) {
            ((ObservedCircularProgressIndicator) Indicator).ApplyControllerVisibility(Animate);
            return;
        }

        ((ObservedLinearProgressIndicator) Indicator).ApplyControllerVisibility(Animate);
    }

    private void ReconcileControllerVisibility() {
        if (!ControllerInitializationComplete) {
            return;
        }

        if (ControllerWantsIndicatorVisible) {
            if (Indicator.getVisibility() != View.VISIBLE) {
                Indicator.setVisibility(View.VISIBLE);
                return;
            }

            ApplyCurrentMaterialVisibility(true);
            return;
        }

        if (Indicator.getVisibility() != ActiveHideVisibility) {
            Indicator.setVisibility(ActiveHideVisibility);
        }

        HideMaterialDrawablesImmediately();
    }

    @Override
    protected void ApplyColors(int... Colors) {
        if (!Circular) {
            LinearProgressIndicator LinearIndicator = RequireLinearIndicator("setColor");
            ValidateLinearContiguousState(
                    LinearIndicator.getIndeterminateAnimationType(),
                    ResolveEffectiveColorCount(Colors),
                    LinearIndicator.getTrackThickness(),
                    LinearTrackCornersState,
                    LinearIndicator.getIndicatorTrackGapSize(),
                    LinearInnerCornersState
            );
        }

        Indicator.setIndicatorColor(Colors);
    }

    @Override
    protected boolean isNaturalDirectionReversed() {
        return Circular;
    }

    @Override
    protected void ApplyResolvedDirection(boolean Reversed) {
        if (Circular) {
            RequireCircularIndicator("setDirection").setIndicatorDirection(
                    Reversed
                            ? CircularProgressIndicator.INDICATOR_DIRECTION_COUNTERCLOCKWISE
                            : CircularProgressIndicator.INDICATOR_DIRECTION_CLOCKWISE
            );
            return;
        }

        RequireLinearIndicator("setDirection").setIndicatorDirection(
                Reversed
                        ? LinearProgressIndicator.INDICATOR_DIRECTION_RIGHT_TO_LEFT
                        : LinearProgressIndicator.INDICATOR_DIRECTION_LEFT_TO_RIGHT
        );
    }

    private BaseProgressIndicator<?> CreateProgressIndicator(
            Context AppContext,
            int IndicatorTypeValue,
            int VariantValue
    ) {
        if (IndicatorTypeValue == ProgressAttributes.INDICATOR_TYPE_CIRCULAR) {
            Context IndicatorContext = CreateCircularContext(AppContext, VariantValue);
            return new ObservedCircularProgressIndicator(IndicatorContext, this);
        }

        if (IndicatorTypeValue == ProgressAttributes.INDICATOR_TYPE_LINEAR) {
            Context IndicatorContext = CreateLinearContext(AppContext, VariantValue);
            return new ObservedLinearProgressIndicator(IndicatorContext, this);
        }

        throw new IllegalArgumentException(
                "ProgressIndicatorController requires Circular or Linear IndicatorType."
        );
    }

    private Context CreateCircularContext(Context AppContext, int VariantValue) {
        if (VariantValue == ProgressAttributes.VARIANT_DEFAULT) {
            return new ContextThemeWrapper(
                    AppContext,
                    R.style.MaterialProgressIndicatorsCircularDefaultChildTheme
            );
        }

        if (VariantValue == ProgressAttributes.VARIANT_LEGACY) {
            return new ContextThemeWrapper(
                    AppContext,
                    R.style.MaterialProgressIndicatorsCircularLegacyChildTheme
            );
        }

        if (VariantValue == ProgressAttributes.VARIANT_WAVY) {
            return new ContextThemeWrapper(
                    AppContext,
                    R.style.MaterialProgressIndicatorsCircularWavyChildTheme
            );
        }

        if (VariantValue == ProgressAttributes.VARIANT_MEDIUM) {
            return new ContextThemeWrapper(
                    AppContext,
                    R.style.MaterialProgressIndicatorsCircularMediumChildTheme
            );
        }

        if (VariantValue == ProgressAttributes.VARIANT_SMALL) {
            return new ContextThemeWrapper(
                    AppContext,
                    R.style.MaterialProgressIndicatorsCircularSmallChildTheme
            );
        }

        if (VariantValue == ProgressAttributes.VARIANT_EXTRA_SMALL) {
            return new ContextThemeWrapper(
                    AppContext,
                    R.style.MaterialProgressIndicatorsCircularExtraSmallChildTheme
            );
        }

        throw new IllegalArgumentException("Unsupported Circular Variant: " + VariantValue);
    }

    private Context CreateLinearContext(Context AppContext, int VariantValue) {
        if (VariantValue == ProgressAttributes.VARIANT_DEFAULT) {
            return new ContextThemeWrapper(
                    AppContext,
                    R.style.MaterialProgressIndicatorsLinearDefaultChildTheme
            );
        }

        if (VariantValue == ProgressAttributes.VARIANT_LEGACY) {
            return new ContextThemeWrapper(
                    AppContext,
                    R.style.MaterialProgressIndicatorsLinearLegacyChildTheme
            );
        }

        if (VariantValue == ProgressAttributes.VARIANT_WAVY) {
            return new ContextThemeWrapper(
                    AppContext,
                    R.style.MaterialProgressIndicatorsLinearWavyChildTheme
            );
        }

        throw new IllegalArgumentException("Unsupported Linear Variant: " + VariantValue);
    }

    private void ApplyTrackThickness(int ThicknessPixels) {
        if (Circular) {
            CircularProgressIndicator CircularIndicator = (CircularProgressIndicator) Indicator;
            CircularIndicator.setTrackThickness(ThicknessPixels);
            int CurrentIndicatorSize = CircularIndicator.getIndicatorSize();
            CircularIndicator.setIndicatorSize(CurrentIndicatorSize);
            return;
        }

        LinearProgressIndicator LinearIndicator = RequireLinearIndicator("TrackThickness");
        EnsureLinearInnerZeroBeforeOuterGrowth(
                LinearIndicator,
                LinearTrackCornersState,
                ThicknessPixels
        );
        LinearIndicator.setTrackThickness(ThicknessPixels);
    }

    private void ApplyCircularSize(int SizePixels) {
        if (!Circular) {
            throw new UnsupportedOperationException(
                    "Size is not applicable to Linear progress indicators."
            );
        }

        ((CircularProgressIndicator) Indicator).setIndicatorSize(SizePixels);
    }

    private CircularProgressIndicator RequireCircularIndicator(String MethodName) {
        if (!Circular) {
            throw new UnsupportedOperationException(
                    MethodName + " is applicable only to CircularProgressIndicator."
            );
        }

        return (CircularProgressIndicator) Indicator;
    }

    private LinearProgressIndicator RequireLinearIndicator(String MethodName) {
        if (Circular) {
            throw new UnsupportedOperationException(
                    MethodName + " is applicable only to LinearProgressIndicator."
            );
        }

        return (LinearProgressIndicator) Indicator;
    }

    private void ApplyTrackCorners(ProgressUnitResolver.DimensionOrPercentResult Corners) {
        if (Circular) {
            if (Corners.Percent) {
                Indicator.setTrackCornerRadiusFraction(Corners.Fraction);
                return;
            }

            Indicator.setTrackCornerRadius(Corners.Pixels);
            return;
        }

        LinearProgressIndicator LinearIndicator = RequireLinearIndicator("TrackCorners");
        LinearCornerState CandidateCorners = NormalizeLinearTrackCorners(
                Corners,
                LinearIndicator.getTrackThickness()
        );
        EnsureLinearInnerZeroBeforeOuterGrowth(
                LinearIndicator,
                CandidateCorners,
                LinearIndicator.getTrackThickness()
        );
        ApplyLinearTrackCornerState(LinearIndicator, Corners);
    }

    private void ApplyLinearInnerCorners(ProgressUnitResolver.DimensionOrPercentResult Corners) {
        LinearProgressIndicator LinearIndicator = RequireLinearIndicator("InnerCorners");
        ApplyLinearInnerCornerState(LinearIndicator, Corners);
    }

    private void ValidateTrackCorners(
            ProgressUnitResolver.DimensionOrPercentResult Corners,
            String ParameterName
    ) {
        if (Corners.Percent && Corners.Fraction > 0.5f) {
            throw new IllegalArgumentException(
                    ParameterName + " percent must be between 0% and 50% inclusive. Supplied fraction: "
                            + Corners.Fraction
            );
        }
    }

    private void ValidateSpeed(float Speed) {
        if (!isFinite(Speed) || Speed < 0.1f || Speed > 10.0f) {
            throw new IllegalArgumentException(
                    "Speed must be finite and between 0.1 and 10.0 inclusive. Supplied value: " + Speed
            );
        }
    }

    private void ValidateSpringStiffness(float Stiffness) {
        if (!isFinite(Stiffness) || Stiffness <= 0.0f) {
            throw new IllegalArgumentException(
                    "Stiffness must be finite and greater than 0.0. Supplied value: " + Stiffness
            );
        }
    }

    private void ValidateSpringDamping(float Damping) {
        if (!isFinite(Damping) || Damping <= 0.0f) {
            throw new IllegalArgumentException(
                    "Damping must be finite and greater than 0.0. Supplied value: " + Damping
            );
        }
    }

    private int ResolveCircularAnimation(int Animation) {
        if (Animation == MaterialProgressIndicators.Animation.SMOOTH) {
            return CircularProgressIndicator.INDETERMINATE_ANIMATION_TYPE_ADVANCE;
        }

        if (Animation == MaterialProgressIndicators.Animation.TRASH) {
            return CircularProgressIndicator.INDETERMINATE_ANIMATION_TYPE_RETREAT;
        }

        throw new IllegalArgumentException(
                "CircularProgressIndicator supports only Smooth or Trash Animation."
        );
    }

    private int ResolveLinearAnimation(int Animation) {
        if (Animation == MaterialProgressIndicators.Animation.DISJOINT) {
            return LinearProgressIndicator.INDETERMINATE_ANIMATION_TYPE_DISJOINT;
        }

        if (Animation == MaterialProgressIndicators.Animation.CONTIGUOUS) {
            return LinearProgressIndicator.INDETERMINATE_ANIMATION_TYPE_CONTIGUOUS;
        }

        throw new IllegalArgumentException(
                "LinearProgressIndicator supports only Disjoint or Contiguous Animation."
        );
    }

    private void CaptureStyledLinearCornerStates() {
        LinearProgressIndicator LinearIndicator = RequireLinearIndicator("Linear style state");
        Context IndicatorContext = LinearIndicator.getContext();
        int[] CornerAttributes = new int[]{
                com.google.android.material.R.attr.trackCornerRadius,
                com.google.android.material.R.attr.trackInnerCornerRadius
        };
        TypedArray StyledCorners = IndicatorContext.obtainStyledAttributes(
                null,
                CornerAttributes,
                com.google.android.material.R.attr.linearProgressIndicatorStyle,
                LinearProgressIndicator.DEF_STYLE_RES
        );

        try {
            TypedValue OuterValue = StyledCorners.peekValue(0);

            if (OuterValue == null) {
                LinearTrackCornersState = new LinearCornerState(
                        false,
                        LinearIndicator.getTrackCornerRadius(),
                        LinearIndicator.getTrackCornerRadiusFraction(),
                        true
                );
            } else if (OuterValue.type == TypedValue.TYPE_FRACTION) {
                LinearTrackCornersState = new LinearCornerState(
                        true,
                        LinearIndicator.getTrackCornerRadius(),
                        LinearIndicator.getTrackCornerRadiusFraction(),
                        true
                );
            } else if (OuterValue.type == TypedValue.TYPE_DIMENSION) {
                LinearTrackCornersState = new LinearCornerState(
                        false,
                        LinearIndicator.getTrackCornerRadius(),
                        LinearIndicator.getTrackCornerRadiusFraction(),
                        true
                );
            } else {
                throw new IllegalStateException(
                        "Linear trackCornerRadius resolved to an unsupported styled value type: "
                                + OuterValue.type
                );
            }

            TypedValue InnerValue = StyledCorners.peekValue(1);

            if (InnerValue == null) {
                LinearInnerCornersState = null;
            } else if (InnerValue.type == TypedValue.TYPE_FRACTION) {
                float Fraction = Math.min(StyledCorners.getFraction(1, 1, 1, 0.0f), 0.5f);
                LinearInnerCornersState = new LinearCornerState(
                        true,
                        LinearIndicator.getTrackInnerCornerRadius(),
                        Fraction,
                        true
                );
            } else if (InnerValue.type == TypedValue.TYPE_DIMENSION) {
                LinearInnerCornersState = new LinearCornerState(
                        false,
                        LinearIndicator.getTrackInnerCornerRadius(),
                        0.0f,
                        true
                );
            } else {
                throw new IllegalStateException(
                        "Linear trackInnerCornerRadius resolved to an unsupported styled value type: "
                                + InnerValue.type
                );
            }
        } finally {
            StyledCorners.recycle();
        }
    }

    private void ValidateLinearConfiguration(ProgressAttributes.Configuration Configuration) {
        LinearProgressIndicator LinearIndicator = RequireLinearIndicator("Linear configuration");
        int CandidateTrackThickness = Configuration.TrackThickness != null
                ? Configuration.TrackThickness
                : LinearIndicator.getTrackThickness();
        LinearCornerState CandidateTrackCorners = Configuration.TrackCorners != null
                ? NormalizeLinearTrackCorners(Configuration.TrackCorners, CandidateTrackThickness)
                : LinearTrackCornersState;
        LinearCornerState CandidateInnerCorners = Configuration.InnerCorners != null
                ? NormalizeLinearInnerCorners(Configuration.InnerCorners, CandidateTrackThickness)
                : LinearInnerCornersState;
        int CandidateTrackGap = Configuration.TrackGap != null
                ? Configuration.TrackGap
                : LinearIndicator.getIndicatorTrackGapSize();
        int CandidateColorCount = Configuration.Colors != null
                ? ResolveEffectiveColorCount(Configuration.Colors)
                : LinearIndicator.getIndicatorColor().length;
        int CandidateAnimation = Configuration.Animation != null
                ? ResolveLinearAnimation(Configuration.Animation)
                : LinearIndicator.getIndeterminateAnimationType();

        ValidateLinearContiguousState(
                CandidateAnimation,
                CandidateColorCount,
                CandidateTrackThickness,
                CandidateTrackCorners,
                CandidateTrackGap,
                CandidateInnerCorners
        );
    }

    private LinearCornerState NormalizeLinearTrackCorners(
            ProgressUnitResolver.DimensionOrPercentResult Corners,
            int TrackThickness
    ) {
        if (Corners.Percent) {
            return new LinearCornerState(true, 0, Corners.Fraction, true);
        }

        return new LinearCornerState(
                false,
                Math.min(Corners.Pixels, TrackThickness / 2),
                0.0f,
                true
        );
    }

    private LinearCornerState NormalizeLinearInnerCorners(
            ProgressUnitResolver.DimensionOrPercentResult Corners,
            int TrackThickness
    ) {
        if (Corners.Percent) {
            return new LinearCornerState(true, 0, Corners.Fraction, true);
        }

        int NormalizedPixels = Math.round(
                Math.min((float) Corners.Pixels, TrackThickness / 2.0f)
        );
        return new LinearCornerState(false, NormalizedPixels, 0.0f, true);
    }

    private void ApplyLinearTrackCornerState(
            LinearProgressIndicator LinearIndicator,
            ProgressUnitResolver.DimensionOrPercentResult Corners
    ) {
        LinearCornerState CurrentCorners = LinearTrackCornersState;

        if (Corners.Percent) {
            float RequestedFraction = Corners.Fraction;

            if (RequestedFraction == 0.0f) {
                if (CurrentCorners.Percent) {
                    float ActualFraction = CurrentCorners.Fraction;

                    if (ActualFraction != 0.0f) {
                        LinearIndicator.setTrackCornerRadiusFraction(0.0f);
                        ActualFraction = 0.0f;
                    }

                    LinearTrackCornersState = new LinearCornerState(
                            true,
                            CurrentCorners.Pixels,
                            ActualFraction,
                            true
                    );
                } else {
                    int ActualPixels = CurrentCorners.Pixels;

                    if (ActualPixels != 0) {
                        LinearIndicator.setTrackCornerRadius(0);
                        ActualPixels = 0;
                    }

                    LinearTrackCornersState = new LinearCornerState(
                            false,
                            ActualPixels,
                            CurrentCorners.Fraction,
                            true
                    );
                }

                return;
            }

            if (CurrentCorners.Percent) {
                if (CurrentCorners.Fraction != RequestedFraction) {
                    LinearIndicator.setTrackCornerRadiusFraction(RequestedFraction);
                }
            } else if (CurrentCorners.Fraction != RequestedFraction) {
                LinearIndicator.setTrackCornerRadiusFraction(RequestedFraction);
            } else {
                LinearIndicator.setTrackCornerRadiusFraction(0.0f);
                LinearIndicator.setTrackCornerRadiusFraction(RequestedFraction);
            }

            LinearTrackCornersState = new LinearCornerState(
                    true,
                    CurrentCorners.Pixels,
                    RequestedFraction,
                    true
            );
            return;
        }

        int RequestedPixels = Corners.Pixels;

        if (RequestedPixels == 0) {
            if (CurrentCorners.Percent) {
                float ActualFraction = CurrentCorners.Fraction;

                if (ActualFraction != 0.0f) {
                    LinearIndicator.setTrackCornerRadiusFraction(0.0f);
                    ActualFraction = 0.0f;
                }

                LinearTrackCornersState = new LinearCornerState(
                        true,
                        CurrentCorners.Pixels,
                        ActualFraction,
                        true
                );
            } else {
                int ActualPixels = CurrentCorners.Pixels;

                if (ActualPixels != 0) {
                    LinearIndicator.setTrackCornerRadius(0);
                    ActualPixels = 0;
                }

                LinearTrackCornersState = new LinearCornerState(
                        false,
                        ActualPixels,
                        CurrentCorners.Fraction,
                        true
                );
            }

            return;
        }

        int ActualPixels;

        if (CurrentCorners.Percent) {
            if (CurrentCorners.Pixels != RequestedPixels) {
                LinearIndicator.setTrackCornerRadius(RequestedPixels);
            } else {
                LinearIndicator.setTrackCornerRadius(0);
                LinearIndicator.setTrackCornerRadius(RequestedPixels);
            }

            ActualPixels = Math.min(RequestedPixels, LinearIndicator.getTrackThickness() / 2);
        } else if (CurrentCorners.Pixels != RequestedPixels) {
            LinearIndicator.setTrackCornerRadius(RequestedPixels);
            ActualPixels = Math.min(RequestedPixels, LinearIndicator.getTrackThickness() / 2);
        } else {
            ActualPixels = CurrentCorners.Pixels;
        }

        LinearTrackCornersState = new LinearCornerState(
                false,
                ActualPixels,
                CurrentCorners.Fraction,
                true
        );
    }

    private void ApplyLinearInnerCornerState(
            LinearProgressIndicator LinearIndicator,
            ProgressUnitResolver.DimensionOrPercentResult Corners
    ) {
        LinearCornerState CurrentCorners = LinearInnerCornersState;
        boolean RequestedZero = Corners.Percent
                ? Corners.Fraction == 0.0f
                : Corners.Pixels == 0;

        if (RequestedZero && (CurrentCorners == null || !CurrentCorners.Materialized)) {
            if (canMaterializeLinearInnerZero(LinearIndicator)) {
                LinearInnerCornersState = MaterializeLinearInnerZero(LinearIndicator);
            } else {
                int FallbackRadius = ResolveLinearCornerRadius(
                        LinearTrackCornersState,
                        LinearIndicator.getTrackThickness()
                );

                if (FallbackRadius != 0) {
                    throw new IllegalStateException(
                            "Virtual explicit InnerCorners zero requires a zero Material fallback radius."
                    );
                }

                LinearInnerCornersState = new LinearCornerState(
                        false,
                        0,
                        0.0f,
                        false
                );
            }

            return;
        }

        if (CurrentCorners == null || !CurrentCorners.Materialized) {
            if (Corners.Percent) {
                LinearIndicator.setTrackInnerCornerRadiusFraction(Corners.Fraction);
                LinearInnerCornersState = new LinearCornerState(
                        true,
                        0,
                        Corners.Fraction,
                        true
                );
            } else {
                int ActualPixels = Math.round(
                        Math.min(
                                (float) Corners.Pixels,
                                LinearIndicator.getTrackThickness() / 2.0f
                        )
                );
                LinearIndicator.setTrackInnerCornerRadius(Corners.Pixels);
                LinearInnerCornersState = new LinearCornerState(
                        false,
                        ActualPixels,
                        0.0f,
                        true
                );
            }

            return;
        }

        if (Corners.Percent) {
            float RequestedFraction = Corners.Fraction;

            if (RequestedFraction == 0.0f) {
                if (CurrentCorners.Percent) {
                    float ActualFraction = CurrentCorners.Fraction;

                    if (ActualFraction != 0.0f) {
                        LinearIndicator.setTrackInnerCornerRadiusFraction(0.0f);
                        ActualFraction = 0.0f;
                    }

                    LinearInnerCornersState = new LinearCornerState(
                            true,
                            CurrentCorners.Pixels,
                            ActualFraction,
                            true
                    );
                } else {
                    int ActualPixels = CurrentCorners.Pixels;

                    if (ActualPixels != 0) {
                        LinearIndicator.setTrackInnerCornerRadius(0);
                        ActualPixels = 0;
                    }

                    LinearInnerCornersState = new LinearCornerState(
                            false,
                            ActualPixels,
                            CurrentCorners.Fraction,
                            true
                    );
                }

                return;
            }

            if (CurrentCorners.Percent) {
                if (CurrentCorners.Fraction != RequestedFraction) {
                    LinearIndicator.setTrackInnerCornerRadiusFraction(RequestedFraction);
                }
            } else if (CurrentCorners.Fraction != RequestedFraction) {
                LinearIndicator.setTrackInnerCornerRadiusFraction(RequestedFraction);
            } else {
                LinearIndicator.setTrackInnerCornerRadiusFraction(0.0f);
                LinearIndicator.setTrackInnerCornerRadiusFraction(RequestedFraction);
            }

            LinearInnerCornersState = new LinearCornerState(
                    true,
                    CurrentCorners.Pixels,
                    RequestedFraction,
                    true
            );
            return;
        }

        int RequestedPixels = Corners.Pixels;

        if (RequestedPixels == 0) {
            if (CurrentCorners.Percent) {
                float ActualFraction = CurrentCorners.Fraction;

                if (ActualFraction != 0.0f) {
                    LinearIndicator.setTrackInnerCornerRadiusFraction(0.0f);
                    ActualFraction = 0.0f;
                }

                LinearInnerCornersState = new LinearCornerState(
                        true,
                        CurrentCorners.Pixels,
                        ActualFraction,
                        true
                );
            } else {
                int ActualPixels = CurrentCorners.Pixels;

                if (ActualPixels != 0) {
                    LinearIndicator.setTrackInnerCornerRadius(0);
                    ActualPixels = 0;
                }

                LinearInnerCornersState = new LinearCornerState(
                        false,
                        ActualPixels,
                        CurrentCorners.Fraction,
                        true
                );
            }

            return;
        }

        int ActualPixels;

        if (CurrentCorners.Percent) {
            if (CurrentCorners.Pixels != RequestedPixels) {
                LinearIndicator.setTrackInnerCornerRadius(RequestedPixels);
            } else {
                LinearIndicator.setTrackInnerCornerRadius(0);
                LinearIndicator.setTrackInnerCornerRadius(RequestedPixels);
            }

            ActualPixels = Math.round(
                    Math.min(
                            (float) RequestedPixels,
                            LinearIndicator.getTrackThickness() / 2.0f
                    )
            );
        } else if (CurrentCorners.Pixels != RequestedPixels) {
            LinearIndicator.setTrackInnerCornerRadius(RequestedPixels);
            ActualPixels = Math.round(
                    Math.min(
                            (float) RequestedPixels,
                            LinearIndicator.getTrackThickness() / 2.0f
                    )
            );
        } else {
            ActualPixels = CurrentCorners.Pixels;
        }

        LinearInnerCornersState = new LinearCornerState(
                false,
                ActualPixels,
                CurrentCorners.Fraction,
                true
        );
    }

    private boolean canMaterializeLinearInnerZero(LinearProgressIndicator LinearIndicator) {
        return LinearIndicator.getIndeterminateAnimationType()
                != LinearProgressIndicator.INDETERMINATE_ANIMATION_TYPE_CONTIGUOUS
                || LinearIndicator.getIndicatorTrackGapSize() > 0
                || LinearIndicator.getTrackThickness() == 0;
    }

    private LinearCornerState MaterializeLinearInnerZero(
            LinearProgressIndicator LinearIndicator
    ) {
        if (!canMaterializeLinearInnerZero(LinearIndicator)) {
            throw new IllegalStateException(
                    "Explicit InnerCorners zero cannot be materialized safely in the current Linear state."
            );
        }

        LinearIndicator.setTrackInnerCornerRadius(1);
        int StoredPixels = LinearIndicator.getTrackInnerCornerRadius();

        if (StoredPixels != 0) {
            LinearIndicator.setTrackInnerCornerRadius(0);
        }

        return new LinearCornerState(false, 0, 0.0f, true);
    }

    private void EnsureLinearInnerZeroBeforeOuterGrowth(
            LinearProgressIndicator LinearIndicator,
            LinearCornerState CandidateTrackCorners,
            int CandidateTrackThickness
    ) {
        if (LinearInnerCornersState == null || LinearInnerCornersState.Materialized) {
            return;
        }

        int CandidateOuterRadius = ResolveLinearCornerRadius(
                CandidateTrackCorners,
                CandidateTrackThickness
        );

        if (CandidateOuterRadius <= 0) {
            return;
        }

        if (!canMaterializeLinearInnerZero(LinearIndicator)) {
            throw new IllegalStateException(
                    "Virtual explicit InnerCorners zero cannot be synchronized before outer corner growth."
            );
        }

        LinearInnerCornersState = MaterializeLinearInnerZero(LinearIndicator);
    }

    private int ResolveLinearCornerRadius(LinearCornerState Corners, int TrackThickness) {
        if (Corners.Percent) {
            return (int) (TrackThickness * Corners.Fraction);
        }

        return Corners.Pixels;
    }

    private int ResolveEffectiveColorCount(int[] Colors) {
        return Colors.length == 0 ? 1 : Colors.length;
    }

    private void ValidateLinearContiguousState(
            int Animation,
            int ColorCount,
            int TrackThickness,
            LinearCornerState TrackCorners,
            int TrackGap,
            LinearCornerState InnerCorners
    ) {
        if (Animation != LinearProgressIndicator.INDETERMINATE_ANIMATION_TYPE_CONTIGUOUS) {
            return;
        }

        if (ColorCount < 3) {
            throw new IllegalArgumentException(
                    "Contiguous animation requires 3 or more indicator colors."
            );
        }

        int OuterRadius = ResolveLinearCornerRadius(TrackCorners, TrackThickness);
        LinearCornerState EffectiveInnerCorners = InnerCorners != null ? InnerCorners : TrackCorners;
        int InnerRadius = ResolveLinearCornerRadius(EffectiveInnerCorners, TrackThickness);

        if (TrackGap == 0 && (OuterRadius > 0 || InnerRadius > 0)) {
            throw new IllegalArgumentException(
                    "Rounded corners require a positive TrackGap in Contiguous animation."
            );
        }
    }

    private int ResolveShowAnimation(int Animation) {
        if (Animation == MaterialProgressIndicators.ShowAnimation.NONE) {
            return BaseProgressIndicator.SHOW_NONE;
        }

        if (Animation == MaterialProgressIndicators.ShowAnimation.OUTWARD) {
            return BaseProgressIndicator.SHOW_OUTWARD;
        }

        if (Animation == MaterialProgressIndicators.ShowAnimation.INWARD) {
            return BaseProgressIndicator.SHOW_INWARD;
        }

        throw new IllegalArgumentException("Unsupported ShowAnimation value: " + Animation);
    }

    private int ResolveHideAnimation(int Animation) {
        if (Animation == MaterialProgressIndicators.HideAnimation.NONE) {
            return BaseProgressIndicator.HIDE_NONE;
        }

        if (Animation == MaterialProgressIndicators.HideAnimation.OUTWARD) {
            return BaseProgressIndicator.HIDE_OUTWARD;
        }

        if (Animation == MaterialProgressIndicators.HideAnimation.INWARD) {
            return BaseProgressIndicator.HIDE_INWARD;
        }

        if (Animation == MaterialProgressIndicators.HideAnimation.ESCAPE) {
            return BaseProgressIndicator.HIDE_ESCAPE;
        }

        throw new IllegalArgumentException("Unsupported HideAnimation value: " + Animation);
    }

    private int ResolveHideVisibility(int Visibility) {
        if (Visibility == MaterialProgressIndicators.HideVisibility.VISIBLE) {
            return View.VISIBLE;
        }

        if (Visibility == MaterialProgressIndicators.HideVisibility.INVISIBLE) {
            return View.INVISIBLE;
        }

        if (Visibility == MaterialProgressIndicators.HideVisibility.GONE) {
            return View.GONE;
        }

        throw new IllegalArgumentException("Unsupported HideVisibility value: " + Visibility);
    }

    private void ValidateWaveRangeValue(float RangeValue, String ParameterName) {
        if (!isFinite(RangeValue) || RangeValue < 0.0f || RangeValue > 1.0f) {
            throw new IllegalArgumentException(
                    ParameterName + " must be finite and between 0.0 and 1.0 inclusive. Supplied value: "
                            + RangeValue
            );
        }
    }

    private boolean isFinite(float FloatValue) {
        return !Float.isNaN(FloatValue) && !Float.isInfinite(FloatValue);
    }

    private static final class LinearCornerState {
        final boolean Percent;
        final int Pixels;
        final float Fraction;
        final boolean Materialized;

        LinearCornerState(boolean Percent, int Pixels, float Fraction, boolean Materialized) {
            this.Percent = Percent;
            this.Pixels = Pixels;
            this.Fraction = Fraction;
            this.Materialized = Materialized;
        }
    }

    private static final class ObservedCircularProgressIndicator extends CircularProgressIndicator {
        private final ProgressIndicatorController Controller;

        ObservedCircularProgressIndicator(
                Context AppContext,
                ProgressIndicatorController Controller
        ) {
            super(AppContext);
            this.Controller = Controller;
        }

        @Override
        public void hide() {
            if (Controller == null || Controller.ControllerHideDispatching) {
                super.hide();
                return;
            }

            Controller.Hide();
        }

        private void ApplyControllerVisibility(boolean Animate) {
            applyNewVisibility(Animate);
        }

        @Override
        protected void onVisibilityChanged(View ChangedView, int Visibility) {
            super.onVisibilityChanged(ChangedView, Visibility);

            if (Controller == null) {
                return;
            }

            Controller.ReapplySpringConfiguration();
            Controller.ReconcileControllerVisibility();
        }

        @Override
        protected void onWindowVisibilityChanged(int Visibility) {
            super.onWindowVisibilityChanged(Visibility);

            if (Controller == null) {
                return;
            }

            Controller.ReapplySpringConfiguration();
            Controller.ReconcileControllerVisibility();
        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();

            if (Controller == null) {
                return;
            }

            Controller.ReapplySpringConfiguration();
            Controller.ResumeDeferredProgressTransition();
            Controller.ReconcileControllerVisibility();
        }

        @Override
        public synchronized void setIndeterminate(boolean Indeterminate) {
            if (Controller == null) {
                super.setIndeterminate(Indeterminate);
                return;
            }

            if (!Controller.ControllerInitializationComplete) {
                super.setIndeterminate(Indeterminate);
                Controller.ReapplySpringConfiguration();
                return;
            }

            if (Controller.ConsumeControllerIndeterminateDispatch()) {
                super.setIndeterminate(Indeterminate);
                Controller.ReapplySpringConfiguration();
                Controller.ReconcileControllerVisibility();
                return;
            }

            if (!Indeterminate) {
                if (Controller.DeferredProgressTarget == null) {
                    return;
                }

                super.setIndeterminate(false);
                Controller.ReapplySpringConfiguration();
                Controller.ReconcileControllerVisibility();
                return;
            }

            super.setIndeterminate(true);
            Controller.ReapplySpringConfiguration();
            Controller.ReconcileControllerVisibility();
        }

        @Override
        public void setProgressCompat(int Progress, boolean Animated) {
            if (Controller == null) {
                super.setProgressCompat(Progress, Animated);
                return;
            }

            Controller.ReapplySpringConfiguration();

            if (!Controller.ControllerInitializationComplete) {
                super.setProgressCompat(Progress, Animated);
                return;
            }

            if (Controller.ConsumeControllerProgressDispatch()) {
                super.setProgressCompat(Progress, Animated);
                return;
            }

            if (Controller.DeferredProgressTarget == null) {
                return;
            }

            int ReplayProgress = Controller.DeferredProgressTarget;
            boolean ReplayAnimated = Controller.DeferredProgressAnimated;
            super.setProgressCompat(ReplayProgress, ReplayAnimated);
            Controller.CompleteDeferredProgressReplay(ReplayProgress, ReplayAnimated);
        }
    }

    private static final class ObservedLinearProgressIndicator extends LinearProgressIndicator {
        private final ProgressIndicatorController Controller;

        ObservedLinearProgressIndicator(
                Context AppContext,
                ProgressIndicatorController Controller
        ) {
            super(AppContext);
            this.Controller = Controller;
        }

        @Override
        public void hide() {
            if (Controller == null || Controller.ControllerHideDispatching) {
                super.hide();
                return;
            }

            Controller.Hide();
        }

        private void ApplyControllerVisibility(boolean Animate) {
            applyNewVisibility(Animate);
        }

        @Override
        protected void onVisibilityChanged(View ChangedView, int Visibility) {
            super.onVisibilityChanged(ChangedView, Visibility);

            if (Controller == null) {
                return;
            }

            Controller.ReapplySpringConfiguration();
            Controller.ReconcileControllerVisibility();
        }

        @Override
        protected void onWindowVisibilityChanged(int Visibility) {
            super.onWindowVisibilityChanged(Visibility);

            if (Controller == null) {
                return;
            }

            Controller.ReapplySpringConfiguration();
            Controller.ReconcileControllerVisibility();
        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();

            if (Controller == null) {
                return;
            }

            Controller.ReapplySpringConfiguration();
            Controller.ResumeDeferredProgressTransition();
            Controller.ReconcileControllerVisibility();
        }

        @Override
        public synchronized void setIndeterminate(boolean Indeterminate) {
            if (Controller == null) {
                super.setIndeterminate(Indeterminate);
                return;
            }

            if (!Controller.ControllerInitializationComplete) {
                super.setIndeterminate(Indeterminate);
                Controller.ReapplySpringConfiguration();
                return;
            }

            if (Controller.ConsumeControllerIndeterminateDispatch()) {
                super.setIndeterminate(Indeterminate);
                Controller.ReapplySpringConfiguration();
                Controller.ReconcileControllerVisibility();
                return;
            }

            if (!Indeterminate) {
                if (Controller.DeferredProgressTarget == null) {
                    return;
                }

                super.setIndeterminate(false);
                Controller.ReapplySpringConfiguration();
                Controller.ReconcileControllerVisibility();
                return;
            }

            super.setIndeterminate(true);
            Controller.ReapplySpringConfiguration();
            Controller.ReconcileControllerVisibility();
        }

        @Override
        public void setProgressCompat(int Progress, boolean Animated) {
            if (Controller == null) {
                super.setProgressCompat(Progress, Animated);
                return;
            }

            Controller.ReapplySpringConfiguration();

            if (!Controller.ControllerInitializationComplete) {
                super.setProgressCompat(Progress, Animated);
                return;
            }

            if (Controller.ConsumeControllerProgressDispatch()) {
                super.setProgressCompat(Progress, Animated);
                return;
            }

            if (Controller.DeferredProgressTarget == null) {
                return;
            }

            int ReplayProgress = Controller.DeferredProgressTarget;
            boolean ReplayAnimated = Controller.DeferredProgressAnimated;
            super.setProgressCompat(ReplayProgress, ReplayAnimated);
            Controller.CompleteDeferredProgressReplay(ReplayProgress, ReplayAnimated);
        }
    }
}
