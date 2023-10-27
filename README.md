# AndroidDevSettings

Simple application for enabling and disabling a complete set of developer settings with a single click on the quicktile.

Note that this app requires you to **manually grant permission** to allow the app to alter the developer settings. To do this, issue the following `adb` command:

`adb shell pm grant de.mchllngr.devsettings[.debug] android.permission.WRITE_SECURE_SETTINGS`

Inspired by: https://github.com/nickbutcher/AnimatorDurationTile

## Building

### Release

To build the release variant you need to create a `keystore.properties` file in the root project directory defining the following variables:

```
storeFile=/path/to/keystore.jks
storePassword=STORE_PASSWORD
keyAlias=KEY_ALIAS
keyPassword=KEY_PASSWORD
```

## App Icons

This project used https://icon.kitchen/ to generate its icons.

# License

```
Copyright 2016, 2023 Michael Langer (mchllngr)

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
