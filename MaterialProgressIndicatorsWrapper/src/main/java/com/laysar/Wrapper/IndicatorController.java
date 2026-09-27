package com.laysar.Wrapper;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;

abstract class IndicatorController {
    private static final int MAX_MIN_HIDE_DELAY_MILLISECONDS = 1000;
    private static final long NO_EFFECTIVE_SHOW_UPTIME = -1L;

    private final View CallbackHost;
    private MaterialProgressIndicators.ProgressListener CurrentProgressListener;
    private final int ShowDelayMilliseconds;
    private final int MinHideDelayMilliseconds;
    private long EffectiveShowStartUptime;
    private boolean IndicatorEffectivelyShown;
    private final Runnable DelayedShowAction = this::ExecuteDelayedShow;
    private final Runnable DelayedHideAction = this::ExecuteDelayedHide;
    private boolean ShowPending;
    private boolean HidePending;

    IndicatorController(View CallbackHost, int ShowDelayMilliseconds, int MinHideDelayMilliseconds) {
        this.CallbackHost = CallbackHost;
        this.ShowDelayMilliseconds = Math.max(0, ShowDelayMilliseconds);
        this.MinHideDelayMilliseconds = Math.min(
                MAX_MIN_HIDE_DELAY_MILLISECONDS,
                Math.max(0, MinHideDelayMilliseconds)
        );
        EffectiveShowStartUptime = NO_EFFECTIVE_SHOW_UPTIME;
        IndicatorEffectivelyShown = true;
    }

    final View getIndicator() {
        return getManagedIndicator();
    }

    final Context getContext() {
        return CallbackHost.getContext();
    }

    final void Show() {
        CancelPendingHide();
        CancelPendingShow();

        if (ShowDelayMilliseconds > 0) {
            ShowPending = true;
            CallbackHost.postDelayed(DelayedShowAction, ShowDelayMilliseconds);
            return;
        }

        DispatchShow();
    }

    final void Hide() {
        boolean HadPendingShow = ShowPending;
        CancelPendingShow();

        if (HadPendingShow && !IndicatorEffectivelyShown) {
            CancelPendingHide();
            return;
        }

        CancelPendingHide();

        if (!IndicatorEffectivelyShown) {
            return;
        }

        long RemainingDelay = ResolveRemainingHideDelay();

        if (RemainingDelay <= 0L) {
            DispatchHide();
            return;
        }

        HidePending = true;
        CallbackHost.postDelayed(DelayedHideAction, RemainingDelay);
    }

    final void setColor(int... Colors) {
        if (Colors == null) {
            throw new IllegalArgumentException("Colors must not be null. Supplied value: null");
        }

        ApplyColors(Colors);
    }

    final void setProgressListener(MaterialProgressIndicators.ProgressListener Listener) {
        CurrentProgressListener = Listener;
    }

    protected final void DispatchProgressChange(int Progress) {
        MaterialProgressIndicators.ProgressListener Listener = CurrentProgressListener;

        if (Listener == null) {
            return;
        }

        Listener.onProgressChange(Progress);
    }

    protected final void DispatchProgressComplete() {
        MaterialProgressIndicators.ProgressListener Listener = CurrentProgressListener;

        if (Listener == null) {
            return;
        }

        Listener.onProgressComplete();
    }

    protected final void DispatchProgressAnimationEnd() {
        MaterialProgressIndicators.ProgressListener Listener = CurrentProgressListener;

        if (Listener == null) {
            return;
        }

        Listener.onProgressAnimationEnd();
    }

    final void setDirection(
            @MaterialProgressIndicators.Direction.Mode int Direction,
            int LayoutDirection
    ) {
        if (LayoutDirection != View.LAYOUT_DIRECTION_LTR
                && LayoutDirection != View.LAYOUT_DIRECTION_RTL) {
            throw new IllegalArgumentException(
                    "Unsupported LayoutDirection value: " + LayoutDirection
            );
        }

        boolean LayoutIsRTL = LayoutDirection == View.LAYOUT_DIRECTION_RTL;
        boolean Reversed;

        if (Direction == MaterialProgressIndicators.Direction.AUTO
                || Direction == MaterialProgressIndicators.Direction.START_TO_END) {
            Reversed = LayoutIsRTL;
        } else if (Direction == MaterialProgressIndicators.Direction.NATURAL) {
            Reversed = isNaturalDirectionReversed() ? !LayoutIsRTL : LayoutIsRTL;
        } else if (Direction == MaterialProgressIndicators.Direction.END_TO_START) {
            Reversed = !LayoutIsRTL;
        } else if (Direction == MaterialProgressIndicators.Direction.LEFT_TO_RIGHT) {
            Reversed = false;
        } else if (Direction == MaterialProgressIndicators.Direction.RIGHT_TO_LEFT) {
            Reversed = true;
        } else {
            throw new IllegalArgumentException("Unsupported Direction value: " + Direction);
        }

        ApplyResolvedDirection(Reversed);
    }

    protected abstract View getManagedIndicator();

    protected abstract void PerformMaterialShow();

    protected abstract void PerformMaterialHide();

    protected abstract void ApplyColors(int... Colors);

    protected abstract boolean isNaturalDirectionReversed();

    protected abstract void ApplyResolvedDirection(boolean Reversed);

    private void ExecuteDelayedShow() {
        if (!ShowPending) {
            return;
        }

        ShowPending = false;
        DispatchShow();
    }

    private void ExecuteDelayedHide() {
        if (!HidePending) {
            return;
        }

        HidePending = false;
        DispatchHide();
    }

    private void DispatchShow() {
        CancelPendingHide();
        IndicatorEffectivelyShown = true;

        if (MinHideDelayMilliseconds > 0) {
            EffectiveShowStartUptime = SystemClock.uptimeMillis();
        } else {
            EffectiveShowStartUptime = NO_EFFECTIVE_SHOW_UPTIME;
        }

        PerformMaterialShow();

        MaterialProgressIndicators.ProgressListener Listener = CurrentProgressListener;

        if (Listener != null) {
            Listener.onShow();
        }
    }

    private void DispatchHide() {
        if (!IndicatorEffectivelyShown) {
            return;
        }

        CancelPendingShow();
        PerformMaterialHide();
        IndicatorEffectivelyShown = false;
        EffectiveShowStartUptime = NO_EFFECTIVE_SHOW_UPTIME;

        MaterialProgressIndicators.ProgressListener Listener = CurrentProgressListener;

        if (Listener != null) {
            Listener.onHide();
        }
    }

    private long ResolveRemainingHideDelay() {
        if (MinHideDelayMilliseconds <= 0
                || EffectiveShowStartUptime == NO_EFFECTIVE_SHOW_UPTIME) {
            return 0L;
        }

        long ElapsedTime = SystemClock.uptimeMillis() - EffectiveShowStartUptime;
        long RemainingDelay = (long) MinHideDelayMilliseconds - ElapsedTime;
        return Math.max(0L, RemainingDelay);
    }

    private void CancelPendingShow() {
        if (!ShowPending) {
            return;
        }

        CallbackHost.removeCallbacks(DelayedShowAction);
        ShowPending = false;
    }

    private void CancelPendingHide() {
        if (!HidePending) {
            return;
        }

        CallbackHost.removeCallbacks(DelayedHideAction);
        HidePending = false;
    }
}
