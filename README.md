BottomNav
=====

[![Download](https://api.bintray.com/packages/mordag/android/bottomnav/images/download.svg) ](https://bintray.com/mordag/android/bottomnav/_latestVersion)

BottomNav is a custom implementation of the Android bottom navigation. The final result of this library will be to copy the bottom navigation behavior and layout of the current Youtube app.

Download
--------
```gradle
repositories {
  jcenter()
}

dependencies {
  implementation 'org.bottomnav:BottomNav:1.0.0-beta1'
}
```

How do I use BottomNav? (Step-by-step introduction for 1.0.0-beta1)
-------------------
Simple example (more coming soon):
```
<org.bottomnav.BottomNavMenu
      style="@style/CustomBottomNavStyle"
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      android:layout_alignParentBottom="true"
      app:color="@color/colorAccent"
      app:textSize="12sp"
      app:setItems="@{activity.items}"
      app:setOnClickItem="@{activity.listener}" />
```

Status
------
Version 1.0.0 is still in development.

Comments/bugs/questions/pull requests are always welcome!

Compatibility
-------------

 * **Minimum Android SDK**: BottomNav requires a minimum API level of 16.
 
TODO
-------------
* Allow Inject of own item layout
* Tweaking default base/item layout
* Documentation (source code)

Author
------
Alexander Eggers - [@mordag][2] on GitHub

License
-------
Apache 2.0. See the [LICENSE][1] file for details.


[1]: https://github.com/Mordag/bottomnav/blob/master/LICENSE
[2]: https://github.com/Mordag
