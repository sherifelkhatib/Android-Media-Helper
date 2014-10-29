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

import mobi.sherif.imageuploader.MediaEngine;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * @author Sherif elKhatib (sherif.elkhatib[at]gmail[dot]com)
 */
public class ImageUploaderFragmentSupportVideo extends Fragment implements MediaEngine.MediaChooseCallback, MediaEngine.LoadingListener {
	MediaEngine uploadEngine;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uploadEngine = new MediaEngine.Builder(this, savedInstanceState).setLoadingListener(this).build();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		uploadEngine.onSaveInstanceState(outState);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (uploadEngine.onActivityResult(requestCode, resultCode, data)) return;
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onResult(MediaEngine engine, MediaEngine.Result result) {
		if (result.isError()) {
			if (result.isCanceled()) {
				onCanceled(engine);
			} else {
				onError(engine, result.getException());
			}
		} else {
			onChosen(engine, result.getPath(), result.isNewfile());
		}
	}

	public void onCanceled(MediaEngine engine) {
		mTextView.setText("Canceled");
	}

	public void onChosen(MediaEngine engine, String path, boolean newPicture) {
		mTextView.setText( ( newPicture ? "New" : "Existing" ) + ": " + path);
		mVideoView.setVideoPath(path);
	}

	public void onError(MediaEngine engine, Exception ex) {
		mTextView.setText("Error: " + ex.getMessage());
	}

	@Override
	public void onLoadingStarted( ) {
		mProgress.setVisibility(View.VISIBLE);
	}

	@Override
	public void onLoadingDone( ) {
		mProgress.setVisibility(View.GONE);
	}

	VideoView mVideoView;
	TextView mTextView;
	View mProgress;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.choosevideo, container, false);
		mProgress = v.findViewById(R.id.progress);
		mVideoView = (VideoView) v.findViewById(R.id.video);
		mTextView = (TextView) v.findViewById(R.id.textvideo);
		v.findViewById(R.id.button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				uploadEngine.performVideoAsk(R.string.app_name, R.string.newvideo, R.string.oldvideo, R.string.choosevideo, R.drawable.ic_launcher);
			}
		});
		( (VideoView) v.findViewById(R.id.video) ).setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				mp.start();
			}
		});
		return v;
	}
}