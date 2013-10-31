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

import mobi.sherif.imageuploader.ImageUploadEngine;
import mobi.sherif.imageuploader.ImageUploadEngine.ImageChooseCallback;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * @author Sherif elKhatib (sherif.elkhatib[at]gmail[dot]com)
 */
public class ImageUploaderFragmentSupport extends Fragment implements ImageChooseCallback {
	ImageUploadEngine uploadEngine;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uploadEngine = new ImageUploadEngine.Builder(this, savedInstanceState).build();
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
		mTextView.setText("Canceled");
	}

	@Override
	public void onChosen(ImageUploadEngine engine, String path, boolean newPicture) {
		mTextView.setText((newPicture?"New":"Existing") + ": " + path);
	}

	@Override
	public void onError(ImageUploadEngine engine, Exception ex) {
		mTextView.setText("Error: " + ex.getMessage());
	}
	
	TextView mTextView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.choose, null);
		mTextView = (TextView) v.findViewById(R.id.textimage);
		v.findViewById(R.id.button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				uploadEngine.performImageAsk(R.string.app_name, R.string.newphoto, R.string.oldphoto, R.string.choose, R.drawable.ic_launcher);
			}
		});
		return v;
	}
	
}