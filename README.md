# ![Logo](https://github.com/sherifelkhatib/Android-Image-Helper/raw/master/sample/res/drawable-mdpi/ic_launcher.png) Image Helper for Android

This project aims to provide an easy-to-user instrument for android developers to use when they need the user to select an image on their android phone and upload it.
If the process has any errors on specific phones, these should be handled inside the library to provide the same user experience on all phones.

![Screenshot](https://github.com/sherifelkhatib/Android-Image-Helper/raw/master/screenshot1.png)

## Features
 * Allow user to choose Photo from Gallery or Take new Photo.
 
Android 2.2+ support

## Downloads
 * **[android-image-helper-1.0.0.jar](https://github.com/sherifelkhatib/Android-Image-Helper/raw/master/downloads/android-image-helper-1.0.0.jar)** (library; contains *.class files)
 * **[android-image-helper-1.0.0-with-sources.jar](https://github.com/sherifelkhatib/Android-Image-Helper/raw/master/downloads/android-image-helper-1.0.0-withsources.jar)** (library with sources inside; contains *.class and *.java files)<br />_Prefer to use this JAR so you can see Java docs in Eclipse tooltips._
 * **[android-image-helper-sample-1.0.0.apk](https://github.com/sherifelkhatib/Android-Image-Helper/raw/master/downloads/android-image-helper-sample-1.0.0.apk)** (sample application)

Latest snapshot of the library - **[here](https://github.com/sherifelkhatib/Android-Image-Helper/tree/master/sample/libs)**

### [Changelog](https://github.com/sherifelkhatib/Android-Image-Helper/blob/master/CHANGELOG.md)

### User Support
 1. Look into **[How to Use](https://github.com/sherifelkhatib/Android-Image-Helper#how-to-use)**

**Bugs** and **feature requests** put **[here](https://github.com/sherifelkhatib/Android-Image-Helper/issues/new)**.<br />

## How to Use

#### 1. Include library

**Manual:**
 * [Download JAR](https://github.com/sherifelkhatib/Android-Image-Helper/raw/master/downloads/android-image-helper-1.0.0-withsources.jar)
 * Put the JAR in the **libs** subfolder of your Android project

#### 2. Usage
Here is the minimal setup needed. This could be used in an Activity, SupportActivity, Fragment, or SupportFragment.
``` java
public class ImageUploaderActivity extends Activity implements ImageChooseCallback {
	ImageUploadEngine uploadEngine;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uploadEngine = new ImageUploadEngine.Builder(this, savedInstanceState).build();
		//...
		someButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				uploadEngine.performImageAsk(R.string.app_name, R.string.newphoto, R.string.oldphoto, R.string.choose, R.drawable.ic_launcher);
			}
		});
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		uploadEngine.onSaveInstanceState(outState);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(uploadEngine.onActivityResult(requestCode, resultCode, data)) return;
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onCanceled(ImageUploadEngine engine) {
		//notifying you that the user has canceled the request
	}

	@Override
	public void onChosen(ImageUploadEngine engine, String path, boolean newPicture) {
		//notifying you that the user has selected an image
	}

	@Override
	public void onError(ImageUploadEngine engine, Exception ex) {
		//notifying you that an error has occurred
	}
}
```

## License

You are allowed to use this project.

    Copyright 2013 Sherif elKhatib

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.