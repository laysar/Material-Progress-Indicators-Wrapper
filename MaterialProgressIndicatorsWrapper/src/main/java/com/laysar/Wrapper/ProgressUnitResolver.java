package com.laysar.Wrapper;

import android.content.Context;

import com.laysar.UnitParser;

final class ProgressUnitResolver {
    private static final UnitParser.UnitPolicy DIMENSION_UNIT_POLICY = UnitParser.fromAllowedUnits(
            UnitParser.UnitType.PIXEL,
            UnitParser.UnitType.DENSITY_INDEPENDENT_PIXEL,
            UnitParser.UnitType.SCALE_INDEPENDENT_PIXEL,
            UnitParser.UnitType.POINT,
            UnitParser.UnitType.PICA,
            UnitParser.UnitType.INCH,
            UnitParser.UnitType.CENTIMETER,
            UnitParser.UnitType.MILLIMETER,
            UnitParser.UnitType.QUARTER_MILLIMETER
    );
    private static final UnitParser.UnitPolicy DIMENSION_OR_PERCENT_UNIT_POLICY = UnitParser.fromAllowedUnits(
            UnitParser.UnitType.PIXEL,
            UnitParser.UnitType.DENSITY_INDEPENDENT_PIXEL,
            UnitParser.UnitType.SCALE_INDEPENDENT_PIXEL,
            UnitParser.UnitType.POINT,
            UnitParser.UnitType.PICA,
            UnitParser.UnitType.INCH,
            UnitParser.UnitType.CENTIMETER,
            UnitParser.UnitType.MILLIMETER,
            UnitParser.UnitType.QUARTER_MILLIMETER,
            UnitParser.UnitType.PERCENT
    );

    static final class DimensionOrPercentResult {
        final boolean Percent;
        final int Pixels;
        final float Fraction;

        DimensionOrPercentResult(boolean Percent, int Pixels, float Fraction) {
            this.Percent = Percent;
            this.Pixels = Pixels;
            this.Fraction = Fraction;
        }
    }

    private ProgressUnitResolver() {
    }

    static int ResolveDimension(Context AppContext, String DimensionValue, String ParameterName) {
        if (DimensionValue == null) {
            throw new IllegalArgumentException(
                    ParameterName + " must not be null. Supplied value: null"
            );
        }

        UnitParser.UnitResult ParsedResult = UnitParser.toPXResult(
                AppContext,
                DimensionValue,
                DIMENSION_UNIT_POLICY
        );

        if (!ParsedResult.isValid()) {
            throw new IllegalArgumentException(
                    ParameterName + " has an invalid dimension value: " + DimensionValue
            );
        }

        float ResolvedPixels = ParsedResult.getValue();

        if (!isFinite(ResolvedPixels)) {
            throw new IllegalArgumentException(
                    ParameterName + " must resolve to a finite pixel value. Supplied value: " + DimensionValue
            );
        }

        if (ResolvedPixels < 0.0f) {
            throw new IllegalArgumentException(
                    ParameterName + " must resolve to a non-negative pixel value. Supplied value: " + DimensionValue
            );
        }

        return Math.round(ResolvedPixels);
    }

    static DimensionOrPercentResult ResolveDimensionOrPercent(
            Context AppContext,
            String InputValue,
            String ParameterName
    ) {
        if (InputValue == null) {
            throw new IllegalArgumentException(
                    ParameterName + " must not be null. Supplied value: null"
            );
        }

        UnitParser.UnitResult ParsedResult = UnitParser.toPXResult(
                AppContext,
                InputValue,
                DIMENSION_OR_PERCENT_UNIT_POLICY
        );

        if (!ParsedResult.isValid()) {
            throw new IllegalArgumentException(
                    ParameterName + " has an invalid dimension or percent value: " + InputValue
            );
        }

        float ResolvedValue = ParsedResult.getValue();

        if (!isFinite(ResolvedValue)) {
            throw new IllegalArgumentException(
                    ParameterName + " must resolve to a finite value. Supplied value: " + InputValue
            );
        }

        if (ResolvedValue < 0.0f) {
            throw new IllegalArgumentException(
                    ParameterName + " must resolve to a non-negative value. Supplied value: " + InputValue
            );
        }

        if (ParsedResult.getUnitType() == UnitParser.UnitType.PERCENT) {
            return new DimensionOrPercentResult(true, 0, ResolvedValue);
        }

        return new DimensionOrPercentResult(false, Math.round(ResolvedValue), 0.0f);
    }

    private static boolean isFinite(float FloatValue) {
        return !Float.isNaN(FloatValue) && !Float.isInfinite(FloatValue);
    }
}
