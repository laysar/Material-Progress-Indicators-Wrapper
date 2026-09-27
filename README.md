[Arabic](README.md) • [English](README.md)

# MaterialProgressIndicators

غلاف موحد لمؤشرات Material من أنواع التحميل (Loading) والدائري (Circular) والخطي (Linear)، بواجهة متناسقة واحدة في Java وXML

## الاعتماد (Dependency)

تتطلب المكتبة Material Components بالإصدار `1.14.0`

```kotlin
implementation("io.github.laysar:MaterialProgressIndicatorsWrapper:0.9.0")
```

## مؤشر هجين 3 في 1 (3-in-1 Hybrid Indicator)

> في `v0.9` استخدم `MaterialProgressIndicators` من XML  
> لا تنشئ العنصر باستخدام `new MaterialProgressIndicators(context)`، وسيتم تحديث الإنشاء البرمجي في `v0.9.5` أو `v1.0.0`

أنواع المؤشرات المتوفرة:

```text
Loading
Circular
Linear
```

يُستخدم نفس العنصر للأنواع الثلاثة، ويُحدد النوع المطلوب باستخدام `IndicatorType`

```xml
<com.laysar.Wrapper.MaterialProgressIndicators
    android:id="@+id/Progress"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:IndicatorType="Circular" />
```

بعد ذلك يمكن التحكم بالعنصر من Java:

```java
MaterialProgressIndicators Progress = findViewById(R.id.Progress);

Progress.Show();
Progress.setColor(Color.RED);
Progress.setTrackThickness("6dp");
```

# مرجع الواجهة البرمجية (API Reference)

> تستخدم دوال Java وخصائص XML نظام التسمية نفسه عندما يتوفر الخيار في الجهتين، وبعض الميزات متاحة عمدًا في Java أو XML فقط

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

# توافق المكونات (Component Compatibility)

> تعني `✓` أن الواجهة البرمجية مدعومة في نوع المؤشر، وتعني `-` أنها لا تنطبق عليه

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

# التوفر في Java وXML (Java & XML Availability)

> تُستخدم Java للتحكم أثناء التشغيل (Runtime)، بينما تُستخدم XML لإعداد المؤشر عند إنشاء العنصر، وبعض دوال Java تجمع قيمًا توجد كخصائص منفصلة في XML

| الميزة | Java | XML |
|---|:---:|:---:|
| `IndicatorType` | - | ✓ |
| `Variant` | - | ✓ |
| `Color` | ✓ | ✓ |
| `Size` | ✓ | ✓ |
| `Direction` | ✓ | ✓ |
| `ContainerColor` | ✓ | ✓ |
| `ContainerSize` | ✓ | ✓ |
| `ContainerHeight` | عبر `setContainerSize(Height, Width)` | ✓ |
| `ContainerWidth` | عبر `setContainerSize(Height, Width)` | ✓ |
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
| `WaveLengthDeterminate` | عبر `setWaveLength(Determinate, Indeterminate)` | ✓ |
| `WaveLengthIndeterminate` | عبر `setWaveLength(Determinate, Indeterminate)` | ✓ |
| `WaveAmplitude` | ✓ | ✓ |
| `WaveSpeed` | ✓ | ✓ |
| `WaveRangeMin` | عبر `setWaveRange(Min, Max)` | ✓ |
| `WaveRangeMax` | عبر `setWaveRange(Min, Max)` | ✓ |
| `AutoHide` | ✓ | ✓ |
| `SpringStiffness` | ✓ | ✓ |
| `SpringDamping` | ✓ | ✓ |
| دالة `Spring` المدمجة | ✓ | - |
| `Inset` | ✓ | ✓ |
| `Animation` | ✓ | ✓ |
| `StopSize` | ✓ | ✓ |
| `StopPadding` | ✓ | ✓ |
| `InnerCorners` | ✓ | ✓ |
| `ProgressListener` | ✓ | - |

# القيم والوحدات (Values & Units)

> تستقبل دوال Java الخاصة بالأبعاد القيم كنصوص تحتوي على الوحدة، وتتم عملية التحويل داخليًا دون الحاجة لتحويل `dp` أو `sp` أو الوحدات المدعومة الأخرى إلى بكسل يدويًا

الوحدات القياسية المدعومة للأبعاد:

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

أمثلة:

```java
Progress.setSize("48dp");
Progress.setTrackThickness("6dp");
Progress.setInset("8mm");
Progress.setContainerSize("2.5cm");
Progress.setWaveAmplitude("4dp");
```

عند تحديد حجم الحاوية ببعدين، يكون ترتيب القيم دائمًا:

```text
Height, Width
```

مثال:

```java
Progress.setContainerSize("48dp", "64dp");
```

تدعم `TrackCorners` و`InnerCorners` القيم المئوية أيضًا:

```java
Progress.setTrackCorners("50%");
Progress.setInnerCorners("25%");
```

وتدعم XML موارد الأبعاد (Dimension Resources) المعتادة في Android حيثما كانت مناسبة:

```xml
app:TrackThickness="@dimen/progress_track_thickness"
```

# القيم المتاحة (Available Values)

> تستخدم الخيارات المغلقة ثوابت محددة مسبقًا في Java وقيمًا مكافئة في XML، بينما `IndicatorType` و`Variant` متاحتان في XML فقط ضمن `v0.9`

## نوع المؤشر (IndicatorType)

```text
Loading
Circular
Linear
```

مثال:

```xml
app:IndicatorType="Circular"
```

## النمط (Variant)

| المؤشر | القيم المتاحة |
|---|---|
| Loading | `Default`, `Contained` |
| Circular | `Default`, `Legacy`, `Wavy`, `Medium`, `Small`, `ExtraSmall` |
| Linear | `Default`, `Legacy`, `Wavy` |

مثال:

```xml
app:Variant="Wavy"
```

## الاتجاه (Direction)

متاح مع مؤشرات Loading وCircular وLinear

| Java | XML |
|---|---|
| `Direction.AUTO` | `Auto` |
| `Direction.NATURAL` | `Natural` |
| `Direction.START_TO_END` | `StartToEnd` |
| `Direction.END_TO_START` | `EndToStart` |
| `Direction.LEFT_TO_RIGHT` | `LeftToRight` |
| `Direction.RIGHT_TO_LEFT` | `RightToLeft` |

في Java:

```java
Progress.setDirection(
    MaterialProgressIndicators.Direction.START_TO_END
);
```

في XML:

```xml
app:Direction="StartToEnd"
```

## الحركة (Animation)

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

## حركة الإظهار (ShowAnimation)

| Java | XML |
|---|---|
| `ShowAnimation.NONE` | `None` |
| `ShowAnimation.OUTWARD` | `Outward` |
| `ShowAnimation.INWARD` | `Inward` |

## حركة الإخفاء (HideAnimation)

| Java | XML |
|---|---|
| `HideAnimation.NONE` | `None` |
| `HideAnimation.OUTWARD` | `Outward` |
| `HideAnimation.INWARD` | `Inward` |
| `HideAnimation.ESCAPE` | `Escape` |

## الرؤية بعد الإخفاء (HideVisibility)

| Java | XML |
|---|---|
| `HideVisibility.VISIBLE` | `Visible` |
| `HideVisibility.INVISIBLE` | `Invisible` |
| `HideVisibility.GONE` | `Gone` |

# أحداث ProgressListener

> تتوفر `setProgressListener(...)` مع أنواع المؤشرات الثلاثة، بينما تنطبق أحداث التقدم (Progress) فقط على Circular وLinear

| الحدث | Loading | Circular | Linear |
|---|:---:|:---:|:---:|
| `onShow()` | ✓ | ✓ | ✓ |
| `onHide()` | ✓ | ✓ | ✓ |
| `onProgressChange(int Progress)` | - | ✓ | ✓ |
| `onProgressComplete()` | - | ✓ | ✓ |
| `onProgressAnimationEnd()` | - | ✓ | ✓ |

مثال:

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

لإزالة المستمع (Listener):

```java
Progress.setProgressListener(null);
```

# القيود المهمة (Important Constraints)

> يمكن أن تؤدي القيم غير الصالحة أو التركيبات غير المدعومة إلى رمي `IllegalArgumentException` أو `IllegalStateException` أو `UnsupportedOperationException`

## السرعة (Speed)

```text
0.1 to 10.0
```

## نطاق الموجة (WaveRange)

يجب أن تكون القيمتان بين:

```text
0.0 and 1.0
```

ويجب أن تكون `Min` أقل من أو تساوي `Max`

```java
Progress.setWaveRange(0.1f, 0.9f);
```

## TrackCorners وInnerCorners

يجب أن تكون النسبة المئوية بين:

```text
0% and 50%
```

## النابض (Spring)

```text
Stiffness > 0
Damping > 0
```

مثال:

```java
Progress.setSpring(500f, 0.7f);
```

## الحركة المتصلة الخطية (Linear Contiguous Animation)

تحتاج إلى 3 ألوان للمؤشر على الأقل:

```java
Progress.setColor(
    Color.RED,
    Color.GREEN,
    Color.BLUE
);
```

وعند استخدام الزوايا الخارجية أو الداخلية المستديرة مع `Contiguous`، يجب أن تكون قيمة `TrackGap` أكبر من `0`
