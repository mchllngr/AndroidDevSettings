# AndroidDevSettings
Simple application for enabling and disabling a complete set of developer settings with a single click within the app or on the quicktile (Android N only).

Note that this app requires you to **manually grant permission** to allow the app to alter the developer settings. To do this, issue the following `adb` command:

`adb shell pm grant de.mchllngr.devsettings[.debug] android.permission.WRITE_SECURE_SETTINGS`

Inspired by: https://github.com/nickbutcher/AnimatorDurationTile

# Using you own developer settings
If you want to use your own set of developer settings just update <a href="https://github.com/mchllngr/AndroidDevSettings/blob/master/app/src/main/java/de/mchllngr/devsettings/util/DevSettingsUtil.java#L57:L79">this</a> and <a href="https://github.com/mchllngr/AndroidDevSettings/blob/master/app/src/main/java/de/mchllngr/devsettings/util/DevSettingsUtil.java#L81:L92">this</a> method to match your needs.

# TODO
- add widget/notification for devices with older android versions

# License

```
Copyright 2016 Michael Langer (mchllngr)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
