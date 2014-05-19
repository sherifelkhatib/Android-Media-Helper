/*******************************************************************************
 * Copyright 2011-2013 Sherif elKhatib
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package mobi.sherif.example.imageuploader;

import java.io.File;

import mobi.sherif.imageuploader.ImageUploadEngine;
import mobi.sherif.imageuploader.ImageUploadEngine.ImageChooseCallback;
import mobi.sherif.imageuploader.LoadingListener;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Sherif elKhatib (sherif.elkhatib[at]gmail[dot]com)
 */
public class ImageUploaderActivity extends Activity implements ImageChooseCallback, LoadingListener {
	ImageUploadEngine uploadEngine;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose);
		uploadEngine = new ImageUploadEngine.Builder(this, savedInstanceState).setLoadingListener(this).build();
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
	
	public void onChoose(View v) {
		uploadEngine.performImageAsk(R.string.app_name, R.string.newphoto, R.string.oldphoto, R.string.choose, R.drawable.ic_launcher);
	}

	@Override
	public void onCanceled(ImageUploadEngine engine) {
		((TextView)findViewById(R.id.textimage)).setText("Canceled");
	}

	@Override
	public void onChosen(ImageUploadEngine engine, String path, boolean newPicture) {
		((TextView)findViewById(R.id.textimage)).setText((newPicture?"New":"Existing") + ": " + path);
		((ImageView)findViewById(R.id.image)).setImageURI(Uri.fromFile(new File(path)));
	}

	@Override
	public void onError(ImageUploadEngine engine, Exception ex) {
		((TextView)findViewById(R.id.textimage)).setText("Error: " + ex.getMessage());
	}

	@Override
	public void onLoadingStarted() {
		findViewById(R.id.progress).setVisibility(View.VISIBLE);
	}

	@Override
	public void onLoadingDone() {
		findViewById(R.id.progress).setVisibility(View.GONE);
	}
}