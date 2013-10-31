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

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * @author Sherif elKhatib (sherif.elkhatib[at]gmail[dot]com)
 */
public class ActivityForFragment extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
		if(savedInstanceState == null) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.add(R.id.container, new ImageUploaderFragment());
			ft.commit();
		}
	}
}