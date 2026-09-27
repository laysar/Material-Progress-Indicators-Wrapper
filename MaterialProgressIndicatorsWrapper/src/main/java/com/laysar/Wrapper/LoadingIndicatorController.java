package com.laysar.Wrapper;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.View;

import com.google.android.material.loadingindicator.LoadingIndicator;

final class LoadingIndicatorController extends IndicatorController {
    private final LoadingIndicator Indicator;

    LoadingIndicatorController(
            Context AppContext,
            View CallbackHost,
            ProgressAttributes.Configuration Configuration
    ) {
        super(CallbackHost, Configuration.ShowDelay, Configuration.MinHideDelay);

        LoadingIndicator CreatedIndicator = CreateLoadingIndicator(AppContext, Configuration.Variant);

        if (Configuration.Colors != null) {
            CreatedIndicator.setIndicatorColor(Configuration.Colors);
        }

        if (Configuration.Size != null) {
            CreatedIndicator.setIndicatorSize(Configuration.Size);
        }

        if (Configuration.ContainerColor != null) {
            CreatedIndicator.setContainerColor(Configuration.ContainerColor);
        }

        if (Configuration.ContainerHeight != null) {
            CreatedIndicator.setContainerHeight(Configuration.ContainerHeight);
        }

        if (Configuration.ContainerWidth != null) {
            CreatedIndicator.setContainerWidth(Configuration.ContainerWidth);
        }

        Indicator = CreatedIndicator;
    }

    void setSize(String Size) {
        int SizePixels = ProgressUnitResolver.ResolveDimension(getContext(), Size, "Size");
        Indicator.setIndicatorSize(SizePixels);
    }

    void setContainerColor(int Color) {
        Indicator.setContainerColor(Color);
    }

    void setContainerSize(String Size) {
        int SizePixels = ProgressUnitResolver.ResolveDimension(getContext(), Size, "Size");
        Indicator.setContainerHeight(SizePixels);
        Indicator.setContainerWidth(SizePixels);
    }

    void setContainerSize(String Height, String Width) {
        int HeightPixels = ProgressUnitResolver.ResolveDimension(getContext(), Height, "Height");
        int WidthPixels = ProgressUnitResolver.ResolveDimension(getContext(), Width, "Width");
        Indicator.setContainerHeight(HeightPixels);
        Indicator.setContainerWidth(WidthPixels);
    }

    @Override
    protected View getManagedIndicator() {
        return Indicator;
    }

    @Override
    protected void PerformMaterialShow() {
        Indicator.show();
    }

    @Override
    protected void PerformMaterialHide() {
        Indicator.hide();
    }

    @Override
    protected void ApplyColors(int... Colors) {
        Indicator.setIndicatorColor(Colors);
    }

    @Override
    protected boolean isNaturalDirectionReversed() {
        return true;
    }

    @Override
    protected void ApplyResolvedDirection(boolean Reversed) {
        Indicator.setScaleX(Reversed ? -1.0f : 1.0f);
    }

    private LoadingIndicator CreateLoadingIndicator(Context AppContext, int VariantValue) {
        if (VariantValue == ProgressAttributes.VARIANT_CONTAINED) {
            Context ContainedContext = new ContextThemeWrapper(
                    AppContext,
                    R.style.MaterialProgressIndicatorsContainedChildTheme
            );
            return new LoadingIndicator(ContainedContext);
        }

        return new LoadingIndicator(AppContext);
    }
}
