package mobi.sherif.imageuploader;

import mobi.sherif.imageuploader.MediaEngine.MediaChooseCallback;
import android.content.Context;
import android.content.Intent;

interface ActivityManager {
	void startActivityForResult(Intent intent, int requestCode);

	void show(FragmentYesNoDialogBuilder builder, String tag);

	MediaChooseCallback getCallback( );

	Context getContext( );

	public static class StaticBuilder {
		private StaticBuilder( ) {
		}

		public static ActivityManager build(final android.app.Activity activity) {
			return new ActivityManager() {
				@Override
				public void startActivityForResult(Intent intent, int requestCode) {
					activity.startActivityForResult(intent, requestCode);
				}

				@Override
				public void show(FragmentYesNoDialogBuilder builder, String tag) {
					builder.build().show(activity.getFragmentManager(), tag);
				}

				@Override
				public Context getContext( ) {
					return activity;
				}

				@Override
				public MediaChooseCallback getCallback( ) {
					return (MediaChooseCallback) activity;
				}
			};
		}

		public static ActivityManager build(final android.support.v4.app.FragmentActivity activity) {
			return new ActivityManager() {
				@Override
				public void startActivityForResult(Intent intent, int requestCode) {
					activity.startActivityForResult(intent, requestCode);
				}

				@Override
				public void show(FragmentYesNoDialogBuilder builder, String tag) {
					builder.buildSupport().show(activity.getSupportFragmentManager(), tag);
				}

				@Override
				public Context getContext( ) {
					return activity;
				}

				@Override
				public MediaChooseCallback getCallback( ) {
					return (MediaChooseCallback) activity;
				}
			};
		}

		public static ActivityManager build(final android.app.Fragment fragment) {
			return new ActivityManager() {
				@Override
				public void startActivityForResult(Intent intent, int requestCode) {
					fragment.startActivityForResult(intent, requestCode);
				}

				@Override
				public void show(FragmentYesNoDialogBuilder builder, String tag) {
					builder.build().show(fragment.getChildFragmentManager(), tag);
				}

				@Override
				public Context getContext( ) {
					return fragment.getActivity();
				}

				@Override
				public MediaChooseCallback getCallback( ) {
					return (MediaChooseCallback) fragment;
				}
			};
		}

		public static ActivityManager build(final android.support.v4.app.Fragment fragment) {
			return new ActivityManager() {
				@Override
				public void startActivityForResult(Intent intent, int requestCode) {
					fragment.startActivityForResult(intent, requestCode);
				}

				@Override
				public void show(FragmentYesNoDialogBuilder builder, String tag) {
					builder.buildSupport().show(fragment.getChildFragmentManager(), tag);
				}

				@Override
				public Context getContext( ) {
					return fragment.getActivity();
				}

				@Override
				public MediaChooseCallback getCallback( ) {
					return (MediaChooseCallback) fragment;
				}
			};
		}
	}
}