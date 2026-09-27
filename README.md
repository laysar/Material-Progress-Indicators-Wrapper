# MaterialProgressIndicators

A unified Android wrapper that brings together the loading indicator and circular and linear progress from (Material 3 Expressive) within a single consistent interface for use in Java and XML

## Dependency

```
implementation("io.github.laysar:MaterialProgressIndicatorsWrapper:0.9.0")
```

## 3-in-1 Hybrid Indicator

> In `v0.9.0`, use `MaterialProgressIndicators` from XML. Do not instantiate it with `new MaterialProgressIndicators(context)`. Programmatic creation will be revised in `v0.9.5` or `v1.0.0`.

Available indicator types:

```text
Loading
Circular
Linear
```

Use the same view for all three types and select the one you want with `IndicatorType`.

```xml
<com.laysar.Wrapper.MaterialProgressIndicators
    android:id="@+id/Progress"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:IndicatorType="Circular" />
```

Then control it from Java:

```java
MaterialProgressIndicators Progress = findViewById(R.id.Progress);

Progress.Show();
Progress.setColor(Color.RED);
Progress.setTrackThickness("6dp");
```

# API Reference

> Java methods and XML attributes use the same naming scheme wherever an equivalent XML option exists. Some features are intentionally available only in Java or XML.

```java
COMMON - Loading / Circular / Linear

Show()
Hide()

setColor(int... Colors)
setDirection(@Direction.Mode int Direction)
setProgressListener(ProgressListener Listener)


LOADING

setSize(String Size)

setContainerColor(int Color)

setContainerSize(String Size)
setContainerSize(String Height, String Width)


PROGRESS - Circular / Linear

setProgress(int Progress)
setProgress(int Progress, boolean Animated)

setMax(int Max)
setIndeterminate(boolean Indeterminate)

setTrackColor(int Color)
setTrackThickness(String Thickness)
setTrackCorners(String Corners)
setTrackGap(String Gap)

setSpeed(float Speed)

setShowAnimation(@ShowAnimation.Mode int Animation)
setHideAnimation(@HideAnimation.Mode int Animation)
setHideVisibility(@HideVisibility.Mode int Visibility)

setWaveLength(String Length)
setWaveLength(String Determinate, String Indeterminate)
setWaveAmplitude(String Amplitude)
setWaveSpeed(String Speed)
setWaveRange(float Min, float Max)

setAutoHide(boolean Enabled)

setSpring(float Stiffness, float Damping)
setSpringStiffness(float Stiffness)
setSpringDamping(float Damping)


CIRCULAR

setSize(String Size)
setInset(String Inset)

setAnimation(@Animation.Mode int Animation)


LINEAR

setAnimation(@Animation.Mode int Animation)

setStopSize(String Size)
setStopPadding(String Padding)
setInnerCorners(String Corners)
```

# Component Compatibility

> `✓` means the API is supported by that indicator type. `-` means the API does not apply to that type.

| API | Loading | Circular | Linear |
|---|:---:|:---:|:---:|
| `Show()` / `Hide()` | ✓ | ✓ | ✓ |
| `setColor(...)` | ✓ | ✓ | ✓ |
| `setDirection(...)` | ✓ | ✓ | ✓ |
| `setProgressListener(...)` | ✓ | ✓ | ✓ |
| `setSize(...)` | ✓ | ✓ | - |
| `setContainerColor(...)` | ✓ | - | - |
| `setContainerSize(...)` | ✓ | - | - |
| `setProgress(...)` | - | ✓ | ✓ |
| `setMax(...)` | - | ✓ | ✓ |
| `setIndeterminate(...)` | - | ✓ | ✓ |
| `setTrackColor(...)` | - | ✓ | ✓ |
| `setTrackThickness(...)` | - | ✓ | ✓ |
| `setTrackCorners(...)` | - | ✓ | ✓ |
| `setTrackGap(...)` | - | ✓ | ✓ |
| `setSpeed(...)` | - | ✓ | ✓ |
| `setShowAnimation(...)` | - | ✓ | ✓ |
| `setHideAnimation(...)` | - | ✓ | ✓ |
| `setHideVisibility(...)` | - | ✓ | ✓ |
| `setWaveLength(...)` | - | ✓ | ✓ |
| `setWaveAmplitude(...)` | - | ✓ | ✓ |
| `setWaveSpeed(...)` | - | ✓ | ✓ |
| `setWaveRange(...)` | - | ✓ | ✓ |
| `setAutoHide(...)` | - | ✓ | ✓ |
| `setSpring(...)` | - | ✓ | ✓ |
| `setSpringStiffness(...)` | - | ✓ | ✓ |
| `setSpringDamping(...)` | - | ✓ | ✓ |
| `setAnimation(...)` | - | ✓ | ✓ |
| `setInset(...)` | - | ✓ | - |
| `setStopSize(...)` | - | - | ✓ |
| `setStopPadding(...)` | - | - | ✓ |
| `setInnerCorners(...)` | - | - | ✓ |

# Java & XML Availability

> Java is used for runtime control, while XML configures the indicator when the view is created. Some Java methods combine values that are separate attributes in XML.

| Feature | Java | XML |
|---|:---:|:---:|
| `IndicatorType` | - | ✓ |
| `Variant` | - | ✓ |
| `Color` | ✓ | ✓ |
| `Size` | ✓ | ✓ |
| `Direction` | ✓ | ✓ |
| `ContainerColor` | ✓ | ✓ |
| `ContainerSize` | ✓ | ✓ |
| `ContainerHeight` | via `setContainerSize(Height, Width)` | ✓ |
| `ContainerWidth` | via `setContainerSize(Height, Width)` | ✓ |
| `ShowDelay` | - | ✓ |
| `MinHideDelay` | - | ✓ |
| `Indeterminate` | ✓ | ✓ |
| `Progress` | ✓ | ✓ |
| `Max` | ✓ | ✓ |
| `TrackColor` | ✓ | ✓ |
| `TrackThickness` | ✓ | ✓ |
| `TrackCorners` | ✓ | ✓ |
| `TrackGap` | ✓ | ✓ |
| `Speed` | ✓ | ✓ |
| `ShowAnimation` | ✓ | ✓ |
| `HideAnimation` | ✓ | ✓ |
| `HideVisibility` | ✓ | ✓ |
| `WaveLength` | ✓ | ✓ |
| `WaveLengthDeterminate` | via `setWaveLength(Determinate, Indeterminate)` | ✓ |
| `WaveLengthIndeterminate` | via `setWaveLength(Determinate, Indeterminate)` | ✓ |
| `WaveAmplitude` | ✓ | ✓ |
| `WaveSpeed` | ✓ | ✓ |
| `WaveRangeMin` | via `setWaveRange(Min, Max)` | ✓ |
| `WaveRangeMax` | via `setWaveRange(Min, Max)` | ✓ |
| `AutoHide` | ✓ | ✓ |
| `SpringStiffness` | ✓ | ✓ |
| `SpringDamping` | ✓ | ✓ |
| `Spring` combined setter | ✓ | - |
| `Inset` | ✓ | ✓ |
| `Animation` | ✓ | ✓ |
| `StopSize` | ✓ | ✓ |
| `StopPadding` | ✓ | ✓ |
| `InnerCorners` | ✓ | ✓ |
| `ProgressListener` | ✓ | - |

# Values & Units

> Dimension-based Java APIs accept values as strings with units. Conversion is handled internally, so you do not need to convert `dp`, `sp`, or other supported units to pixels yourself.

Standard dimension values support:

```text
px
dp
sp
pt
pc
in
cm
mm
qmm
```

Examples:

```java
Progress.setSize("48dp");
Progress.setTrackThickness("6dp");
Progress.setInset("8mm");
Progress.setContainerSize("2.5cm");
Progress.setWaveAmplitude("4dp");
```

For two-dimensional container sizing, the parameter order is always:

```text
Height, Width
```

Example:

```java
Progress.setContainerSize("48dp", "64dp");
```

Percentage values are supported by `TrackCorners` and `InnerCorners`.

```java
Progress.setTrackCorners("50%");
Progress.setInnerCorners("25%");
```

XML also supports regular Android dimension resources where applicable.

```xml
app:TrackThickness="@dimen/progress_track_thickness"
```

# Available Values

> Closed options use predefined constants in Java and equivalent enum values in XML. `IndicatorType` and `Variant` are XML-only in `v0.9`.

## IndicatorType

```text
Loading
Circular
Linear
```

Example:

```xml
app:IndicatorType="Circular"
```

## Variant

| Indicator | Available Values |
|---|---|
| Loading | `Default`, `Contained` |
| Circular | `Default`, `Legacy`, `Wavy`, `Medium`, `Small`, `ExtraSmall` |
| Linear | `Default`, `Legacy`, `Wavy` |

Example:

```xml
app:Variant="Wavy"
```

## Direction

Available on Loading, Circular, and Linear indicators.

| Java | XML |
|---|---|
| `Direction.AUTO` | `Auto` |
| `Direction.NATURAL` | `Natural` |
| `Direction.START_TO_END` | `StartToEnd` |
| `Direction.END_TO_START` | `EndToStart` |
| `Direction.LEFT_TO_RIGHT` | `LeftToRight` |
| `Direction.RIGHT_TO_LEFT` | `RightToLeft` |

Java:

```java
Progress.setDirection(
    MaterialProgressIndicators.Direction.START_TO_END
);
```

XML:

```xml
app:Direction="StartToEnd"
```

## Animation

Circular:

| Java | XML |
|---|---|
| `Animation.SMOOTH` | `Smooth` |
| `Animation.TRASH` | `Trash` |

Linear:

| Java | XML |
|---|---|
| `Animation.DISJOINT` | `Disjoint` |
| `Animation.CONTIGUOUS` | `Contiguous` |

## ShowAnimation

| Java | XML |
|---|---|
| `ShowAnimation.NONE` | `None` |
| `ShowAnimation.OUTWARD` | `Outward` |
| `ShowAnimation.INWARD` | `Inward` |

## HideAnimation

| Java | XML |
|---|---|
| `HideAnimation.NONE` | `None` |
| `HideAnimation.OUTWARD` | `Outward` |
| `HideAnimation.INWARD` | `Inward` |
| `HideAnimation.ESCAPE` | `Escape` |

## HideVisibility

| Java | XML |
|---|---|
| `HideVisibility.VISIBLE` | `Visible` |
| `HideVisibility.INVISIBLE` | `Invisible` |
| `HideVisibility.GONE` | `Gone` |

# ProgressListener Events

> `setProgressListener(...)` is available for all three indicator types. Progress-specific callbacks apply only to Circular and Linear indicators.

| Event | Loading | Circular | Linear |
|---|:---:|:---:|:---:|
| `onShow()` | ✓ | ✓ | ✓ |
| `onHide()` | ✓ | ✓ | ✓ |
| `onProgressChange(int Progress)` | - | ✓ | ✓ |
| `onProgressComplete()` | - | ✓ | ✓ |
| `onProgressAnimationEnd()` | - | ✓ | ✓ |

Example:

```java
Progress.setProgressListener(
    new MaterialProgressIndicators.ProgressListener() {

        @Override
        public void onShow() {
        }

        @Override
        public void onHide() {
        }

        @Override
        public void onProgressChange(int Progress) {
        }

        @Override
        public void onProgressComplete() {
        }

        @Override
        public void onProgressAnimationEnd() {
        }
    }
);
```

Remove the listener with:

```java
Progress.setProgressListener(null);
```

# Important Constraints

> Invalid values or unsupported combinations can throw an `IllegalArgumentException`, `IllegalStateException`, or `UnsupportedOperationException`.

## Speed

```text
0.1 to 10.0
```

## WaveRange

Both values must be between:

```text
0.0 and 1.0
```

`Min` must be less than or equal to `Max`.

```java
Progress.setWaveRange(0.1f, 0.9f);
```

## TrackCorners and InnerCorners

Percentage values must be between:

```text
0% and 50%
```

## Spring

```text
Stiffness > 0
Damping > 0
```

Example:

```java
Progress.setSpring(500f, 0.7f);
```

## Linear Contiguous Animation

At least 3 indicator colors are required.

```java
Progress.setColor(
    Color.RED,
    Color.GREEN,
    Color.BLUE
);
```

When rounded outer or inner corners are used with `Contiguous`, `TrackGap` must be greater than `0`.
