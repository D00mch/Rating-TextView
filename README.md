# RatingTextView
Creating customizable rating TextView with progress.

## using from xml

![example](https://github.com/Liverm0r/Rating-TextView/blob/master/example.png)

```xml
<com.livermor.ratingview.RatingTextView
    android:id="@+id/circularTextView3"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_margin="10dp"

    android:textColor="@android:color/black"
    android:textSize="22sp"

    app:arcWidth="15dp"
    app:diameter="100dp"
    app:fillColor="@android:color/white"
    app:rate="2.3"
    app:useRatingAsText="false"
    app:backStrokeColor="@color/common_green_light"
    tools:layout_editor_absoluteX="58dp"
    tools:layout_editor_absoluteY="0dp"/>
```

## Using it from code:

```kotlin
val rv = RatingTextView(this).apply {
    diameter = resources.getDimensionPixelSize(R.dimen.diameter)
    arcWidth = resources.getDimensionPixelSize(R.dimen.arcWidth)
    fillColor = ContextCompat.getColor(this@MainActivity, R.color.some_color)
    backStrokeColor = ContextCompat.getColor(this@MainActivity, R.color.some_color2)
    shouldOverrideText = true
    rating = 2.5f
    setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.textSize))
}
llRoot.addView(rv)
```
