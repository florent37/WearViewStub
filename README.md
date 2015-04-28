WearViewStub
=======

[![Build Status](https://travis-ci.org/florent37/WearViewStub.svg?branch=master)](https://travis-ci.org/florent37/WearViewStub)
[![Android Weekly](https://img.shields.io/badge/android--weekly-149-blue.svg)](http://androidweekly.net/issues/issue-149)

![alt poster](https://raw.githubusercontent.com/florent37/WearViewStub/master/wear/src/main/res/drawable/background_small.jpg)

WearViewStub allow your Android Wear application to use different layouts if running on square, round or moto360 device.

WearViewStub is an alternative implementation of Android Wear [WatchViewStub][watch_view_stub].
Instead of Google WatchViewStub, this implementation allways works, even in Fragments of GridViewPager.

Download
--------

In your root build.gradle add
```groovy
allprojects {
    repositories {
        maven {
            url  "http://dl.bintray.com/florent37/maven"
        }
    }
}
```

In your wear module [ ![Download](https://api.bintray.com/packages/florent37/maven/WearViewStub/images/download.svg) ](https://bintray.com/florent37/maven/WearViewStub/_latestVersion)
```groovy
compile 'com.github.florent37:wearviewstub:1.0.0@aar'
```

Usage
--------

In your layout, use WearViewStub with wearRectLayout, wearRoundLayout and wearRoundMotoLayout attributes

```xml
<com.github.florent37.wearviewstub.WearViewStub
        android:id="@+id/wearViewStub"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:wearRectLayout="@layout/fragment_square"
        app:wearRoundLayout="@layout/fragment_round"
        app:wearRoundMotoLayout="@layout/fragment_round"
        />
```

And in your code, wait until WearViewStub is inflated to access child views

```java
((WearViewStub)view.findViewById(R.id.wearViewStub)).setOnLayoutInflatedListener(new WearViewStub.OnLayoutInflatedListener() {
                    @Override
                    public void onLayoutInflated(WearViewStub wearViewStub) {
                        //do operations on inflated view
                    }
                });
```

Dependencies
--------

Based on ShapeWear (by tajchert) - [https://github.com/tajchert/ShapeWear][shape_wear].

Community
--------

Looking for contributors, feel free to fork !

Wear
--------

If you want to learn wear development : [http://tutos-android-france.com/developper-une-application-pour-les-montres-android-wear/][tuto_wear].

Credits
-------

Author: Florent Champigny

<a href="https://plus.google.com/+florentchampigny">
  <img alt="Follow me on Google+"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/gplus.png" />
</a>
<a href="https://twitter.com/florent_champ">
  <img alt="Follow me on Twitter"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/twitter.png" />
</a>
<a href="https://www.linkedin.com/profile/view?id=297860624">
  <img alt="Follow me on LinkedIn"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/linkedin.png" />
</a>

Pictures by Logan Bourgouin

<a href="https://plus.google.com/+LoganBOURGOIN">
  <img alt="Follow me on Google+"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/gplus.png" />
</a>

License
--------

    Copyright 2015 florent37, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


[snap]: https://oss.sonatype.org/content/repositories/snapshots/
[android_doc]: https://developer.android.com/training/wearables/data-layer/assets.html
[tuto_wear]: http://tutos-android-france.com/developper-une-application-pour-les-montres-android-wear/
[shape_wear]: https://github.com/tajchert/ShapeWear
[watch_view_stub]: https://developer.android.com/samples/WatchViewStub/index.html
