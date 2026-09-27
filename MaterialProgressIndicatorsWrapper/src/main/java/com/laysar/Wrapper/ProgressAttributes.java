package com.laysar.Wrapper;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

final class ProgressAttributes {
    static final int INDICATOR_TYPE_LOADING = 0;
    static final int INDICATOR_TYPE_CIRCULAR = 1;
    static final int INDICATOR_TYPE_LINEAR = 2;
    static final int VARIANT_DEFAULT = 0;
    static final int VARIANT_CONTAINED = 1;
    static final int VARIANT_LEGACY = 2;
    static final int VARIANT_WAVY = 3;
    static final int VARIANT_MEDIUM = 4;
    static final int VARIANT_SMALL = 5;
    static final int VARIANT_EXTRA_SMALL = 6;

    @IntDef({
            INDICATOR_TYPE_LOADING,
            INDICATOR_TYPE_CIRCULAR,
            INDICATOR_TYPE_LINEAR
    })
    @Retention(RetentionPolicy.SOURCE)
    private @interface IndicatorType {
    }

    @IntDef({
            VARIANT_DEFAULT,
            VARIANT_CONTAINED,
            VARIANT_LEGACY,
            VARIANT_WAVY,
            VARIANT_MEDIUM,
            VARIANT_SMALL,
            VARIANT_EXTRA_SMALL
    })
    @Retention(RetentionPolicy.SOURCE)
    private @interface IndicatorVariant {
    }

    static final class Configuration {
        final int IndicatorType;
        final int Variant;
        final int[] Colors;
        final Integer Size;
        final Integer Inset;
        final @MaterialProgressIndicators.Direction.Mode int Direction;
        final Integer Animation;
        final Integer StopSize;
        final Integer StopPadding;
        final ProgressUnitResolver.DimensionOrPercentResult InnerCorners;
        final Integer ContainerColor;
        final Integer ContainerHeight;
        final Integer ContainerWidth;
        final int ShowDelay;
        final int MinHideDelay;
        final Boolean Indeterminate;
        final Integer Progress;
        final Integer Max;
        final Integer TrackColor;
        final Integer TrackThickness;
        final ProgressUnitResolver.DimensionOrPercentResult TrackCorners;
        final Integer TrackGap;
        final Float Speed;
        final Float SpringStiffness;
        final Float SpringDamping;
        final Integer ShowAnimation;
        final Integer HideAnimation;
        final Integer HideVisibility;
        final Integer WaveLengthDeterminate;
        final Integer WaveLengthIndeterminate;
        final Integer WaveAmplitude;
        final Integer WaveSpeed;
        final Float WaveRangeMin;
        final Float WaveRangeMax;
        final Boolean AutoHide;

        private Configuration(
                int IndicatorType,
                int Variant,
                int[] Colors,
                Integer Size,
                Integer Inset,
                @MaterialProgressIndicators.Direction.Mode int Direction,
                Integer Animation,
                Integer StopSize,
                Integer StopPadding,
                ProgressUnitResolver.DimensionOrPercentResult InnerCorners,
                Integer ContainerColor,
                Integer ContainerHeight,
                Integer ContainerWidth,
                int ShowDelay,
                int MinHideDelay,
                Boolean Indeterminate,
                Integer Progress,
                Integer Max,
                Integer TrackColor,
                Integer TrackThickness,
                ProgressUnitResolver.DimensionOrPercentResult TrackCorners,
                Integer TrackGap,
                Float Speed,
                Float SpringStiffness,
                Float SpringDamping,
                Integer ShowAnimation,
                Integer HideAnimation,
                Integer HideVisibility,
                Integer WaveLengthDeterminate,
                Integer WaveLengthIndeterminate,
                Integer WaveAmplitude,
                Integer WaveSpeed,
                Float WaveRangeMin,
                Float WaveRangeMax,
                Boolean AutoHide
        ) {
            this.IndicatorType = IndicatorType;
            this.Variant = Variant;
            this.Colors = Colors;
            this.Size = Size;
            this.Inset = Inset;
            this.Direction = Direction;
            this.Animation = Animation;
            this.StopSize = StopSize;
            this.StopPadding = StopPadding;
            this.InnerCorners = InnerCorners;
            this.ContainerColor = ContainerColor;
            this.ContainerHeight = ContainerHeight;
            this.ContainerWidth = ContainerWidth;
            this.ShowDelay = ShowDelay;
            this.MinHideDelay = MinHideDelay;
            this.Indeterminate = Indeterminate;
            this.Progress = Progress;
            this.Max = Max;
            this.TrackColor = TrackColor;
            this.TrackThickness = TrackThickness;
            this.TrackCorners = TrackCorners;
            this.TrackGap = TrackGap;
            this.Speed = Speed;
            this.SpringStiffness = SpringStiffness;
            this.SpringDamping = SpringDamping;
            this.ShowAnimation = ShowAnimation;
            this.HideAnimation = HideAnimation;
            this.HideVisibility = HideVisibility;
            this.WaveLengthDeterminate = WaveLengthDeterminate;
            this.WaveLengthIndeterminate = WaveLengthIndeterminate;
            this.WaveAmplitude = WaveAmplitude;
            this.WaveSpeed = WaveSpeed;
            this.WaveRangeMin = WaveRangeMin;
            this.WaveRangeMax = WaveRangeMax;
            this.AutoHide = AutoHide;
        }
    }

    private ProgressAttributes() {
    }

    static Configuration Parse(
            Context AppContext,
            AttributeSet Attributes,
            int DefaultStyleAttribute,
            int DefaultStyleResource
    ) {
        @IndicatorType int ResolvedIndicatorType;
        @IndicatorVariant int ResolvedVariant;
        int[] ResolvedColors = null;
        Integer ResolvedSize = null;
        Integer ResolvedInset = null;
        @MaterialProgressIndicators.Direction.Mode int ResolvedDirection = MaterialProgressIndicators.Direction.NATURAL;
        Integer ResolvedAnimation = null;
        Integer ResolvedStopSize = null;
        Integer ResolvedStopPadding = null;
        ProgressUnitResolver.DimensionOrPercentResult ResolvedInnerCorners = null;
        Integer ResolvedContainerColor = null;
        Integer ResolvedContainerHeight = null;
        Integer ResolvedContainerWidth = null;
        int ResolvedShowDelay = 0;
        int ResolvedMinHideDelay = 0;
        Boolean ResolvedIndeterminate = null;
        Integer ResolvedProgress = null;
        Integer ResolvedMax = null;
        Integer ResolvedTrackColor = null;
        Integer ResolvedTrackThickness = null;
        ProgressUnitResolver.DimensionOrPercentResult ResolvedTrackCorners = null;
        Integer ResolvedTrackGap = null;
        Float ResolvedSpeed = null;
        Float ResolvedSpringStiffness = null;
        Float ResolvedSpringDamping = null;
        Integer ResolvedShowAnimation = null;
        Integer ResolvedHideAnimation = null;
        Integer ResolvedHideVisibility = null;
        Integer ResolvedWaveLengthDeterminate = null;
        Integer ResolvedWaveLengthIndeterminate = null;
        Integer ResolvedWaveAmplitude = null;
        Integer ResolvedWaveSpeed = null;
        Float ResolvedWaveRangeMin = null;
        Float ResolvedWaveRangeMax = null;
        Boolean ResolvedAutoHide = null;

        TypedArray CustomAttributes = AppContext.obtainStyledAttributes(
                Attributes,
                R.styleable.MaterialProgressIndicators,
                DefaultStyleAttribute,
                DefaultStyleResource
        );

        try {
            if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_IndicatorType)) {
                ResolvedIndicatorType = CustomAttributes.getInt(
                        R.styleable.MaterialProgressIndicators_IndicatorType,
                        -1
                );
                ValidateIndicatorType(ResolvedIndicatorType);
            } else {
                ResolvedIndicatorType = INDICATOR_TYPE_LOADING;
            }

            ResolvedVariant = CustomAttributes.getInt(
                    R.styleable.MaterialProgressIndicators_Variant,
                    VARIANT_DEFAULT
            );
            ValidateVariant(ResolvedIndicatorType, ResolvedVariant);
            ValidateApplicability(CustomAttributes, ResolvedIndicatorType);

            if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_Direction)) {
                ResolvedDirection = CustomAttributes.getInt(
                        R.styleable.MaterialProgressIndicators_Direction,
                        -1
                );
                ValidateDirection(ResolvedDirection);
            }

            if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_Color)) {
                ResolvedColors = ResolveColorArray(
                        AppContext,
                        CustomAttributes,
                        R.styleable.MaterialProgressIndicators_Color,
                        "Color"
                );
            }

            if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_Size)) {
                ResolvedSize = ResolveXMLDimension(
                        AppContext,
                        CustomAttributes,
                        R.styleable.MaterialProgressIndicators_Size,
                        "Size"
                );
            }

            if (ResolvedIndicatorType == INDICATOR_TYPE_CIRCULAR) {
                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_Inset)) {
                    ResolvedInset = ResolveXMLDimension(
                            AppContext,
                            CustomAttributes,
                            R.styleable.MaterialProgressIndicators_Inset,
                            "Inset"
                    );
                }
            }

            if (ResolvedIndicatorType == INDICATOR_TYPE_LOADING) {
                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_ContainerColor)) {
                    ResolvedContainerColor = ResolveSingleColor(
                            AppContext,
                            CustomAttributes,
                            R.styleable.MaterialProgressIndicators_ContainerColor,
                            "ContainerColor"
                    );
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_ContainerSize)) {
                    int ContainerSizePixels = ResolveXMLDimension(
                            AppContext,
                            CustomAttributes,
                            R.styleable.MaterialProgressIndicators_ContainerSize,
                            "ContainerSize"
                    );
                    ResolvedContainerHeight = ContainerSizePixels;
                    ResolvedContainerWidth = ContainerSizePixels;
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_ContainerHeight)) {
                    ResolvedContainerHeight = ResolveXMLDimension(
                            AppContext,
                            CustomAttributes,
                            R.styleable.MaterialProgressIndicators_ContainerHeight,
                            "ContainerHeight"
                    );
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_ContainerWidth)) {
                    ResolvedContainerWidth = ResolveXMLDimension(
                            AppContext,
                            CustomAttributes,
                            R.styleable.MaterialProgressIndicators_ContainerWidth,
                            "ContainerWidth"
                    );
                }
            } else {
                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_Animation)) {
                    ResolvedAnimation = CustomAttributes.getInt(
                            R.styleable.MaterialProgressIndicators_Animation,
                            -1
                    );
                    ValidateAnimation(ResolvedIndicatorType, ResolvedAnimation);
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_Indeterminate)) {
                    ResolvedIndeterminate = ResolveBooleanAttribute(
                            CustomAttributes,
                            R.styleable.MaterialProgressIndicators_Indeterminate,
                            "Indeterminate"
                    );
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_Progress)) {
                    ResolvedProgress = ResolveIntegerAttribute(
                            AppContext,
                            CustomAttributes,
                            R.styleable.MaterialProgressIndicators_Progress,
                            "Progress"
                    );
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_Max)) {
                    ResolvedMax = ResolveIntegerAttribute(
                            AppContext,
                            CustomAttributes,
                            R.styleable.MaterialProgressIndicators_Max,
                            "Max"
                    );
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_TrackColor)) {
                    ResolvedTrackColor = ResolveSingleColor(
                            AppContext,
                            CustomAttributes,
                            R.styleable.MaterialProgressIndicators_TrackColor,
                            "TrackColor"
                    );
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_TrackThickness)) {
                    ResolvedTrackThickness = ResolveXMLDimension(
                            AppContext,
                            CustomAttributes,
                            R.styleable.MaterialProgressIndicators_TrackThickness,
                            "TrackThickness"
                    );
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_TrackCorners)) {
                    ResolvedTrackCorners = ResolveXMLTrackCorners(
                            AppContext,
                            CustomAttributes,
                            R.styleable.MaterialProgressIndicators_TrackCorners,
                            "TrackCorners"
                    );
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_TrackGap)) {
                    ResolvedTrackGap = ResolveXMLDimension(
                            AppContext,
                            CustomAttributes,
                            R.styleable.MaterialProgressIndicators_TrackGap,
                            "TrackGap"
                    );
                }

                if (ResolvedIndicatorType == INDICATOR_TYPE_LINEAR) {
                    if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_StopSize)) {
                        ResolvedStopSize = ResolveXMLDimension(
                                AppContext,
                                CustomAttributes,
                                R.styleable.MaterialProgressIndicators_StopSize,
                                "StopSize"
                        );
                    }

                    if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_StopPadding)) {
                        ResolvedStopPadding = ResolveXMLDimension(
                                AppContext,
                                CustomAttributes,
                                R.styleable.MaterialProgressIndicators_StopPadding,
                                "StopPadding"
                        );
                    }

                    if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_InnerCorners)) {
                        ResolvedInnerCorners = ResolveXMLTrackCorners(
                                AppContext,
                                CustomAttributes,
                                R.styleable.MaterialProgressIndicators_InnerCorners,
                                "InnerCorners"
                        );
                    }
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_Speed)) {
                    ResolvedSpeed = ResolveFloatAttribute(
                            CustomAttributes,
                            R.styleable.MaterialProgressIndicators_Speed,
                            "Speed"
                    );
                    ValidateSpeed(ResolvedSpeed, "Speed");
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_SpringStiffness)) {
                    ResolvedSpringStiffness = ResolveFloatAttribute(
                            CustomAttributes,
                            R.styleable.MaterialProgressIndicators_SpringStiffness,
                            "SpringStiffness"
                    );
                    ValidateSpringStiffness(ResolvedSpringStiffness);
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_SpringDamping)) {
                    ResolvedSpringDamping = ResolveFloatAttribute(
                            CustomAttributes,
                            R.styleable.MaterialProgressIndicators_SpringDamping,
                            "SpringDamping"
                    );
                    ValidateSpringDamping(ResolvedSpringDamping);
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_ShowAnimation)) {
                    ResolvedShowAnimation = CustomAttributes.getInt(
                            R.styleable.MaterialProgressIndicators_ShowAnimation,
                            -1
                    );
                    ValidateShowAnimation(ResolvedShowAnimation);
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_HideAnimation)) {
                    ResolvedHideAnimation = CustomAttributes.getInt(
                            R.styleable.MaterialProgressIndicators_HideAnimation,
                            -1
                    );
                    ValidateHideAnimation(ResolvedHideAnimation);
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_HideVisibility)) {
                    ResolvedHideVisibility = CustomAttributes.getInt(
                            R.styleable.MaterialProgressIndicators_HideVisibility,
                            -1
                    );
                    ValidateHideVisibility(ResolvedHideVisibility);
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_WaveLength)) {
                    int WaveLengthPixels = ResolveXMLDimension(
                            AppContext,
                            CustomAttributes,
                            R.styleable.MaterialProgressIndicators_WaveLength,
                            "WaveLength"
                    );
                    ResolvedWaveLengthDeterminate = WaveLengthPixels;
                    ResolvedWaveLengthIndeterminate = WaveLengthPixels;
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_WaveLengthDeterminate)) {
                    ResolvedWaveLengthDeterminate = ResolveXMLDimension(
                            AppContext,
                            CustomAttributes,
                            R.styleable.MaterialProgressIndicators_WaveLengthDeterminate,
                            "WaveLengthDeterminate"
                    );
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_WaveLengthIndeterminate)) {
                    ResolvedWaveLengthIndeterminate = ResolveXMLDimension(
                            AppContext,
                            CustomAttributes,
                            R.styleable.MaterialProgressIndicators_WaveLengthIndeterminate,
                            "WaveLengthIndeterminate"
                    );
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_WaveAmplitude)) {
                    ResolvedWaveAmplitude = ResolveXMLDimension(
                            AppContext,
                            CustomAttributes,
                            R.styleable.MaterialProgressIndicators_WaveAmplitude,
                            "WaveAmplitude"
                    );
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_WaveSpeed)) {
                    ResolvedWaveSpeed = ResolveXMLDimension(
                            AppContext,
                            CustomAttributes,
                            R.styleable.MaterialProgressIndicators_WaveSpeed,
                            "WaveSpeed"
                    );
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_WaveRangeMin)) {
                    ResolvedWaveRangeMin = ResolveFloatAttribute(
                            CustomAttributes,
                            R.styleable.MaterialProgressIndicators_WaveRangeMin,
                            "WaveRangeMin"
                    );
                    ValidateWaveRangeValue(ResolvedWaveRangeMin, "WaveRangeMin");
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_WaveRangeMax)) {
                    ResolvedWaveRangeMax = ResolveFloatAttribute(
                            CustomAttributes,
                            R.styleable.MaterialProgressIndicators_WaveRangeMax,
                            "WaveRangeMax"
                    );
                    ValidateWaveRangeValue(ResolvedWaveRangeMax, "WaveRangeMax");
                }

                if (ResolvedWaveRangeMin != null
                        && ResolvedWaveRangeMax != null
                        && ResolvedWaveRangeMin > ResolvedWaveRangeMax) {
                    throw new IllegalArgumentException(
                            "WaveRangeMin must be less than or equal to WaveRangeMax. Supplied values: "
                                    + ResolvedWaveRangeMin + ", " + ResolvedWaveRangeMax
                    );
                }

                if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_AutoHide)) {
                    ResolvedAutoHide = ResolveBooleanAttribute(
                            CustomAttributes,
                            R.styleable.MaterialProgressIndicators_AutoHide,
                            "AutoHide"
                    );
                }
            }

            if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_ShowDelay)) {
                ResolvedShowDelay = ResolveIntegerAttribute(
                        AppContext,
                        CustomAttributes,
                        R.styleable.MaterialProgressIndicators_ShowDelay,
                        "ShowDelay"
                );
            }

            if (CustomAttributes.hasValue(R.styleable.MaterialProgressIndicators_MinHideDelay)) {
                ResolvedMinHideDelay = ResolveIntegerAttribute(
                        AppContext,
                        CustomAttributes,
                        R.styleable.MaterialProgressIndicators_MinHideDelay,
                        "MinHideDelay"
                );
            }
        } finally {
            CustomAttributes.recycle();
        }

        return new Configuration(
                ResolvedIndicatorType,
                ResolvedVariant,
                ResolvedColors,
                ResolvedSize,
                ResolvedInset,
                ResolvedDirection,
                ResolvedAnimation,
                ResolvedStopSize,
                ResolvedStopPadding,
                ResolvedInnerCorners,
                ResolvedContainerColor,
                ResolvedContainerHeight,
                ResolvedContainerWidth,
                ResolvedShowDelay,
                ResolvedMinHideDelay,
                ResolvedIndeterminate,
                ResolvedProgress,
                ResolvedMax,
                ResolvedTrackColor,
                ResolvedTrackThickness,
                ResolvedTrackCorners,
                ResolvedTrackGap,
                ResolvedSpeed,
                ResolvedSpringStiffness,
                ResolvedSpringDamping,
                ResolvedShowAnimation,
                ResolvedHideAnimation,
                ResolvedHideVisibility,
                ResolvedWaveLengthDeterminate,
                ResolvedWaveLengthIndeterminate,
                ResolvedWaveAmplitude,
                ResolvedWaveSpeed,
                ResolvedWaveRangeMin,
                ResolvedWaveRangeMax,
                ResolvedAutoHide
        );
    }

    private static void ValidateIndicatorType(@IndicatorType int IndicatorTypeValue) {
        if (IndicatorTypeValue == INDICATOR_TYPE_LOADING
                || IndicatorTypeValue == INDICATOR_TYPE_CIRCULAR
                || IndicatorTypeValue == INDICATOR_TYPE_LINEAR) {
            return;
        }

        throw new IllegalArgumentException(
                "IndicatorType has an unsupported value: " + IndicatorTypeValue
        );
    }

    private static void ValidateVariant(
            @IndicatorType int IndicatorTypeValue,
            @IndicatorVariant int VariantValue
    ) {
        if (IndicatorTypeValue == INDICATOR_TYPE_LOADING) {
            if (VariantValue == VARIANT_DEFAULT || VariantValue == VARIANT_CONTAINED) {
                return;
            }

            throw new IllegalArgumentException(
                    "Loading IndicatorType supports only Default or Contained Variant."
            );
        }

        if (IndicatorTypeValue == INDICATOR_TYPE_CIRCULAR) {
            if (VariantValue == VARIANT_DEFAULT
                    || VariantValue == VARIANT_LEGACY
                    || VariantValue == VARIANT_WAVY
                    || VariantValue == VARIANT_MEDIUM
                    || VariantValue == VARIANT_SMALL
                    || VariantValue == VARIANT_EXTRA_SMALL) {
                return;
            }

            throw new IllegalArgumentException(
                    "Circular IndicatorType supports only Default, Legacy, Wavy, Medium, Small, or ExtraSmall Variant."
            );
        }

        if (IndicatorTypeValue == INDICATOR_TYPE_LINEAR) {
            if (VariantValue == VARIANT_DEFAULT
                    || VariantValue == VARIANT_LEGACY
                    || VariantValue == VARIANT_WAVY) {
                return;
            }

            throw new IllegalArgumentException(
                    "Linear IndicatorType supports only Default, Legacy, or Wavy Variant."
            );
        }

        throw new IllegalArgumentException(
                "IndicatorType has an unsupported value: " + IndicatorTypeValue
        );
    }

    private static void ValidateApplicability(
            TypedArray Attributes,
            @IndicatorType int IndicatorTypeValue
    ) {
        if (IndicatorTypeValue == INDICATOR_TYPE_LOADING) {
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_Indeterminate, "Indeterminate", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_Progress, "Progress", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_Max, "Max", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_TrackColor, "TrackColor", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_TrackThickness, "TrackThickness", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_TrackCorners, "TrackCorners", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_TrackGap, "TrackGap", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_Speed, "Speed", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_SpringStiffness, "SpringStiffness", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_SpringDamping, "SpringDamping", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_ShowAnimation, "ShowAnimation", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_HideAnimation, "HideAnimation", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_HideVisibility, "HideVisibility", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_WaveLength, "WaveLength", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_WaveLengthDeterminate, "WaveLengthDeterminate", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_WaveLengthIndeterminate, "WaveLengthIndeterminate", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_WaveAmplitude, "WaveAmplitude", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_WaveSpeed, "WaveSpeed", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_WaveRangeMin, "WaveRangeMin", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_WaveRangeMax, "WaveRangeMax", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_AutoHide, "AutoHide", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_Inset, "Inset", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_Animation, "Animation", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_StopSize, "StopSize", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_StopPadding, "StopPadding", "Loading");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_InnerCorners, "InnerCorners", "Loading");
            return;
        }

        RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_ContainerColor, "ContainerColor", "progress");
        RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_ContainerSize, "ContainerSize", "progress");
        RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_ContainerHeight, "ContainerHeight", "progress");
        RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_ContainerWidth, "ContainerWidth", "progress");

        if (IndicatorTypeValue == INDICATOR_TYPE_CIRCULAR) {
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_StopSize, "StopSize", "Circular");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_StopPadding, "StopPadding", "Circular");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_InnerCorners, "InnerCorners", "Circular");
        } else if (IndicatorTypeValue == INDICATOR_TYPE_LINEAR) {
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_Size, "Size", "Linear");
            RejectAttribute(Attributes, R.styleable.MaterialProgressIndicators_Inset, "Inset", "Linear");
        }
    }

    private static void RejectAttribute(
            TypedArray Attributes,
            int AttributeIndex,
            String AttributeName,
            String IndicatorName
    ) {
        if (Attributes.hasValue(AttributeIndex)) {
            throw new IllegalArgumentException(
                    AttributeName + " is not applicable to " + IndicatorName + " IndicatorType."
            );
        }
    }

    private static int ResolveXMLDimension(
            Context AppContext,
            TypedArray Attributes,
            int AttributeIndex,
            String AttributeName
    ) {
        int ResourceIdentifier = Attributes.getResourceId(AttributeIndex, 0);

        if (ResourceIdentifier != 0) {
            String ResourceType = ResolveResourceType(AppContext, ResourceIdentifier, AttributeName);

            if ("dimen".equals(ResourceType)) {
                float ResolvedPixels = AppContext.getResources().getDimension(ResourceIdentifier);
                return ValidateResolvedDimension(ResolvedPixels, AttributeName);
            }

            if ("string".equals(ResourceType)) {
                String DimensionValue = AppContext.getResources().getString(ResourceIdentifier);
                return ProgressUnitResolver.ResolveDimension(AppContext, DimensionValue, AttributeName);
            }

            throw new IllegalArgumentException(
                    AttributeName + " resource must be a dimension or string resource."
            );
        }

        TypedValue AttributeValue = Attributes.peekValue(AttributeIndex);

        if (AttributeValue == null) {
            throw new IllegalArgumentException(AttributeName + " has no resolvable value.");
        }

        if (AttributeValue.type == TypedValue.TYPE_DIMENSION) {
            float ResolvedPixels = Attributes.getDimension(AttributeIndex, Float.NaN);
            return ValidateResolvedDimension(ResolvedPixels, AttributeName);
        }

        if (AttributeValue.type == TypedValue.TYPE_STRING) {
            String DimensionValue = Attributes.getString(AttributeIndex);
            return ProgressUnitResolver.ResolveDimension(AppContext, DimensionValue, AttributeName);
        }

        throw new IllegalArgumentException(
                AttributeName + " must be an Android dimension resource/value or a UnitParser-compatible string."
        );
    }

    private static ProgressUnitResolver.DimensionOrPercentResult ResolveXMLTrackCorners(
            Context AppContext,
            TypedArray Attributes,
            int AttributeIndex,
            String AttributeName
    ) {
        int ResourceIdentifier = Attributes.getResourceId(AttributeIndex, 0);

        if (ResourceIdentifier != 0) {
            String ResourceType = ResolveResourceType(AppContext, ResourceIdentifier, AttributeName);

            if ("dimen".equals(ResourceType)) {
                int Pixels = ValidateResolvedDimension(
                        AppContext.getResources().getDimension(ResourceIdentifier),
                        AttributeName
                );
                return new ProgressUnitResolver.DimensionOrPercentResult(false, Pixels, 0.0f);
            }

            if ("fraction".equals(ResourceType)) {
                float Fraction = AppContext.getResources().getFraction(ResourceIdentifier, 1, 1);
                ValidateTrackCornerFraction(Fraction, AttributeName);
                return new ProgressUnitResolver.DimensionOrPercentResult(true, 0, Fraction);
            }

            if ("string".equals(ResourceType)) {
                String CornerValue = AppContext.getResources().getString(ResourceIdentifier);
                ProgressUnitResolver.DimensionOrPercentResult ResolvedCorners =
                        ProgressUnitResolver.ResolveDimensionOrPercent(
                                AppContext,
                                CornerValue,
                                AttributeName
                        );
                ValidateTrackCornerResult(ResolvedCorners, AttributeName);
                return ResolvedCorners;
            }

            throw new IllegalArgumentException(
                    AttributeName + " resource must be a dimension, fraction, or string resource."
            );
        }

        TypedValue AttributeValue = Attributes.peekValue(AttributeIndex);

        if (AttributeValue == null) {
            throw new IllegalArgumentException(AttributeName + " has no resolvable value.");
        }

        if (AttributeValue.type == TypedValue.TYPE_DIMENSION) {
            int Pixels = ValidateResolvedDimension(
                    Attributes.getDimension(AttributeIndex, Float.NaN),
                    AttributeName
            );
            return new ProgressUnitResolver.DimensionOrPercentResult(false, Pixels, 0.0f);
        }

        if (AttributeValue.type == TypedValue.TYPE_FRACTION) {
            float Fraction = Attributes.getFraction(AttributeIndex, 1, 1, Float.NaN);
            ValidateTrackCornerFraction(Fraction, AttributeName);
            return new ProgressUnitResolver.DimensionOrPercentResult(true, 0, Fraction);
        }

        if (AttributeValue.type == TypedValue.TYPE_STRING) {
            String CornerValue = Attributes.getString(AttributeIndex);
            ProgressUnitResolver.DimensionOrPercentResult ResolvedCorners =
                    ProgressUnitResolver.ResolveDimensionOrPercent(
                            AppContext,
                            CornerValue,
                            AttributeName
                    );
            ValidateTrackCornerResult(ResolvedCorners, AttributeName);
            return ResolvedCorners;
        }

        throw new IllegalArgumentException(
                AttributeName + " must be a dimension, fraction, or UnitParser-compatible string."
        );
    }

    private static int ValidateResolvedDimension(float ResolvedPixels, String AttributeName) {
        if (!isFinite(ResolvedPixels)) {
            throw new IllegalArgumentException(
                    AttributeName + " must resolve to a finite dimension."
            );
        }

        if (ResolvedPixels < 0.0f) {
            throw new IllegalArgumentException(
                    AttributeName + " must resolve to a non-negative dimension."
            );
        }

        return Math.round(ResolvedPixels);
    }

    private static void ValidateTrackCornerResult(
            ProgressUnitResolver.DimensionOrPercentResult Corners,
            String AttributeName
    ) {
        if (Corners.Percent) {
            ValidateTrackCornerFraction(Corners.Fraction, AttributeName);
        }
    }

    private static void ValidateTrackCornerFraction(float Fraction, String AttributeName) {
        if (!isFinite(Fraction) || Fraction < 0.0f || Fraction > 0.5f) {
            throw new IllegalArgumentException(
                    AttributeName + " fraction must be between 0.0 and 0.5 inclusive. Supplied value: "
                            + Fraction
            );
        }
    }

    private static int[] ResolveColorArray(
            Context AppContext,
            TypedArray Attributes,
            int AttributeIndex,
            String AttributeName
    ) {
        int ResourceIdentifier = Attributes.getResourceId(AttributeIndex, 0);

        if (ResourceIdentifier != 0) {
            String ResourceType = ResolveResourceType(AppContext, ResourceIdentifier, AttributeName);

            if ("array".equals(ResourceType)) {
                return ResolveColorArrayResource(AppContext, ResourceIdentifier, AttributeName);
            }

            if ("color".equals(ResourceType)) {
                return new int[]{Attributes.getColor(AttributeIndex, 0)};
            }

            throw new IllegalArgumentException(
                    AttributeName + " resource must be a color or color array."
            );
        }

        TypedValue AttributeValue = Attributes.peekValue(AttributeIndex);

        if (AttributeValue == null || !isColorType(AttributeValue.type)) {
            throw new IllegalArgumentException(
                    AttributeName + " must resolve to a valid color or color array."
            );
        }

        return new int[]{Attributes.getColor(AttributeIndex, 0)};
    }

    private static int[] ResolveColorArrayResource(
            Context AppContext,
            int ResourceIdentifier,
            String AttributeName
    ) {
        TypedArray ColorValues = AppContext.getResources().obtainTypedArray(ResourceIdentifier);

        try {
            int[] ResolvedColors = new int[ColorValues.length()];

            for (int color_index = 0; color_index < ColorValues.length(); color_index++) {
                ResolvedColors[color_index] = ResolveColorArrayEntry(
                        AppContext,
                        ColorValues,
                        color_index,
                        AttributeName
                );
            }

            return ResolvedColors;
        } finally {
            ColorValues.recycle();
        }
    }

    private static int ResolveColorArrayEntry(
            Context AppContext,
            TypedArray ColorValues,
            int ColorIndex,
            String AttributeName
    ) {
        int ResourceIdentifier = ColorValues.getResourceId(ColorIndex, 0);

        if (ResourceIdentifier != 0) {
            String ResourceType = ResolveResourceType(AppContext, ResourceIdentifier, AttributeName);

            if (!"color".equals(ResourceType)) {
                throw new IllegalArgumentException(
                        AttributeName + " array entry at index " + ColorIndex + " must resolve to a color."
                );
            }

            return ColorValues.getColor(ColorIndex, 0);
        }

        TypedValue ColorValue = ColorValues.peekValue(ColorIndex);

        if (ColorValue == null || !isColorType(ColorValue.type)) {
            throw new IllegalArgumentException(
                    AttributeName + " array entry at index " + ColorIndex + " must resolve to a color."
            );
        }

        return ColorValues.getColor(ColorIndex, 0);
    }

    private static int ResolveSingleColor(
            Context AppContext,
            TypedArray Attributes,
            int AttributeIndex,
            String AttributeName
    ) {
        int ResourceIdentifier = Attributes.getResourceId(AttributeIndex, 0);

        if (ResourceIdentifier != 0) {
            String ResourceType = ResolveResourceType(AppContext, ResourceIdentifier, AttributeName);

            if (!"color".equals(ResourceType)) {
                throw new IllegalArgumentException(
                        AttributeName + " resource must resolve to a single color."
                );
            }

            return Attributes.getColor(AttributeIndex, 0);
        }

        TypedValue AttributeValue = Attributes.peekValue(AttributeIndex);

        if (AttributeValue == null || !isColorType(AttributeValue.type)) {
            throw new IllegalArgumentException(AttributeName + " must resolve to a valid color.");
        }

        return Attributes.getColor(AttributeIndex, 0);
    }

    private static String ResolveResourceType(
            Context AppContext,
            int ResourceIdentifier,
            String AttributeName
    ) {
        try {
            return AppContext.getResources().getResourceTypeName(ResourceIdentifier);
        } catch (Resources.NotFoundException Cause) {
            throw new IllegalArgumentException(
                    AttributeName + " references an unavailable resource: " + ResourceIdentifier,
                    Cause
            );
        }
    }

    private static int ResolveIntegerAttribute(
            Context AppContext,
            TypedArray Attributes,
            int AttributeIndex,
            String AttributeName
    ) {
        int ResourceIdentifier = Attributes.getResourceId(AttributeIndex, 0);

        if (ResourceIdentifier != 0) {
            String ResourceType = ResolveResourceType(AppContext, ResourceIdentifier, AttributeName);

            if (!"integer".equals(ResourceType)) {
                throw new IllegalArgumentException(
                        AttributeName + " resource must resolve to an integer value."
                );
            }

            return AppContext.getResources().getInteger(ResourceIdentifier);
        }

        TypedValue AttributeValue = Attributes.peekValue(AttributeIndex);

        if (AttributeValue == null
                || (AttributeValue.type != TypedValue.TYPE_INT_DEC
                && AttributeValue.type != TypedValue.TYPE_INT_HEX)) {
            throw new IllegalArgumentException(AttributeName + " must resolve to an integer value.");
        }

        return Attributes.getInt(AttributeIndex, 0);
    }

    private static boolean ResolveBooleanAttribute(
            TypedArray Attributes,
            int AttributeIndex,
            String AttributeName
    ) {
        TypedValue AttributeValue = Attributes.peekValue(AttributeIndex);

        if (AttributeValue == null || AttributeValue.type != TypedValue.TYPE_INT_BOOLEAN) {
            throw new IllegalArgumentException(AttributeName + " must resolve to a boolean value.");
        }

        return Attributes.getBoolean(AttributeIndex, false);
    }

    private static float ResolveFloatAttribute(
            TypedArray Attributes,
            int AttributeIndex,
            String AttributeName
    ) {
        TypedValue AttributeValue = Attributes.peekValue(AttributeIndex);

        if (AttributeValue == null
                || (AttributeValue.type != TypedValue.TYPE_FLOAT
                && AttributeValue.type != TypedValue.TYPE_INT_DEC
                && AttributeValue.type != TypedValue.TYPE_INT_HEX)) {
            throw new IllegalArgumentException(AttributeName + " must resolve to a numeric float value.");
        }

        float ResolvedValue = Attributes.getFloat(AttributeIndex, Float.NaN);

        if (!isFinite(ResolvedValue)) {
            throw new IllegalArgumentException(AttributeName + " must resolve to a finite float value.");
        }

        return ResolvedValue;
    }

    private static void ValidateSpeed(float Speed, String AttributeName) {
        if (!isFinite(Speed) || Speed < 0.1f || Speed > 10.0f) {
            throw new IllegalArgumentException(
                    AttributeName + " must be between 0.1 and 10.0 inclusive. Supplied value: " + Speed
            );
        }
    }

    private static void ValidateSpringStiffness(float Stiffness) {
        if (!isFinite(Stiffness) || Stiffness <= 0.0f) {
            throw new IllegalArgumentException(
                    "SpringStiffness must be finite and greater than 0.0. Supplied value: " + Stiffness
            );
        }
    }

    private static void ValidateSpringDamping(float Damping) {
        if (!isFinite(Damping) || Damping <= 0.0f) {
            throw new IllegalArgumentException(
                    "SpringDamping must be finite and greater than 0.0. Supplied value: " + Damping
            );
        }
    }

    private static void ValidateDirection(int Direction) {
        if (Direction == MaterialProgressIndicators.Direction.AUTO
                || Direction == MaterialProgressIndicators.Direction.NATURAL
                || Direction == MaterialProgressIndicators.Direction.START_TO_END
                || Direction == MaterialProgressIndicators.Direction.END_TO_START
                || Direction == MaterialProgressIndicators.Direction.LEFT_TO_RIGHT
                || Direction == MaterialProgressIndicators.Direction.RIGHT_TO_LEFT) {
            return;
        }

        throw new IllegalArgumentException("Unsupported Direction value: " + Direction);
    }

    private static void ValidateAnimation(int IndicatorTypeValue, int Animation) {
        if (IndicatorTypeValue == INDICATOR_TYPE_CIRCULAR) {
            if (Animation == MaterialProgressIndicators.Animation.SMOOTH
                    || Animation == MaterialProgressIndicators.Animation.TRASH) {
                return;
            }

            throw new IllegalArgumentException(
                    "Circular IndicatorType supports only Smooth or Trash Animation."
            );
        }

        if (IndicatorTypeValue == INDICATOR_TYPE_LINEAR) {
            if (Animation == MaterialProgressIndicators.Animation.DISJOINT
                    || Animation == MaterialProgressIndicators.Animation.CONTIGUOUS) {
                return;
            }

            throw new IllegalArgumentException(
                    "Linear IndicatorType supports only Disjoint or Contiguous Animation."
            );
        }

        throw new IllegalArgumentException(
                "Animation is not applicable to IndicatorType: " + IndicatorTypeValue
        );
    }

    private static void ValidateShowAnimation(int Animation) {
        if (Animation == MaterialProgressIndicators.ShowAnimation.NONE
                || Animation == MaterialProgressIndicators.ShowAnimation.OUTWARD
                || Animation == MaterialProgressIndicators.ShowAnimation.INWARD) {
            return;
        }

        throw new IllegalArgumentException("Unsupported ShowAnimation value: " + Animation);
    }

    private static void ValidateHideAnimation(int Animation) {
        if (Animation == MaterialProgressIndicators.HideAnimation.NONE
                || Animation == MaterialProgressIndicators.HideAnimation.OUTWARD
                || Animation == MaterialProgressIndicators.HideAnimation.INWARD
                || Animation == MaterialProgressIndicators.HideAnimation.ESCAPE) {
            return;
        }

        throw new IllegalArgumentException("Unsupported HideAnimation value: " + Animation);
    }

    private static void ValidateHideVisibility(int Visibility) {
        if (Visibility == MaterialProgressIndicators.HideVisibility.VISIBLE
                || Visibility == MaterialProgressIndicators.HideVisibility.INVISIBLE
                || Visibility == MaterialProgressIndicators.HideVisibility.GONE) {
            return;
        }

        throw new IllegalArgumentException("Unsupported HideVisibility value: " + Visibility);
    }

    private static void ValidateWaveRangeValue(float RangeValue, String AttributeName) {
        if (!isFinite(RangeValue) || RangeValue < 0.0f || RangeValue > 1.0f) {
            throw new IllegalArgumentException(
                    AttributeName + " must be between 0.0 and 1.0 inclusive. Supplied value: " + RangeValue
            );
        }
    }

    private static boolean isColorType(int ValueType) {
        return ValueType >= TypedValue.TYPE_FIRST_COLOR_INT
                && ValueType <= TypedValue.TYPE_LAST_COLOR_INT;
    }

    private static boolean isFinite(float FloatValue) {
        return !Float.isNaN(FloatValue) && !Float.isInfinite(FloatValue);
    }
}
