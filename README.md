# Android Ripple Background

A beautiful ripple animation for your app. You can easily change its color, speed of wave, one ripple or multiple ripples. See demo below.

![Simple Ripple](previews/rippleSimple.gif)　　　　　　![Multiple ripples](previews/rippleFoundDevice.gif)

##Usage

###Step 1

####Install with Gradle

```groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```


```groovy
dependencies {
        implementation 'com.github.jeffreyliu8:android-ripple-background:-SNAPSHOT'
}
```
###Step 2
####RippleBackground

Add `RippleBackground` to your layout with content you want, like an ImageView. Configure the view customization elements using styleable attributes. NOTE: RippleBackground extends FrameLayout, not RelativeLayout 
 
```xml
    <com.askjeffreyliu.library.RippleBackground
        android:id="@+id/rippleBackground"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:rb_color="#0099CC"
        app:rb_duration="3000"
        app:rb_radius="32dp"
        app:rb_rippleAmount="6"
        app:rb_scale="6"
        app:rb_icon="@drawable/ic_launcher"> <!-- NEW -->

        <ImageView
            android:id="@+id/centerImage"
            android:layout_width="64dp"
            android:layout_height="64dp"
            android:layout_gravity="center"
            android:src="@drawable/phone1" />

    </com.askjeffreyliu.library.RippleBackground>
```
Start animation:

```java
    final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);
    ImageView imageView=(ImageView)findViewById(R.id.centerImage);
    imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            rippleBackground.startRippleAnimation();
        }
    });
```
Stop animation:

```java
    rippleBackground.stopRippleAnimation();
```

##Theming
* app:rb_color [color def:@android:color/holo_blue_dark] --> Color of the ripple
* app:rb_radius [dimension def:64dp ] --> Radius of the ripple
* app:rb_duration [integer def:3000 ] --> Duration of one ripple animation (millisecond) 
* app:rb_rippleAmount [integer def:6] --> Max amount of ripples at one screen
* app:rb_scale [interger def:6] --> Scale of ripple at the end of one animation cycle
* app:rb_type [enum (fillRipple, strokeRipple) def:fillRipple] --> Filled circle or ring
* app:rb_strokeWidth [dimension def:2dp] --> Stroke width of the ripple, ONLY work when rb_type="strokeRipple"
* app:rb_icon [reference def:@drawable/ic_launcher] --> NEW!!! The drawable from your resources, THIS OVERRIDE THE CIRCLE RIPPLE, leave blank if you want the old circle

