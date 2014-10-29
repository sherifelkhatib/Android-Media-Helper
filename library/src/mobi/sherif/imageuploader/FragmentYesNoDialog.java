package mobi.sherif.imageuploader;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

class FragmentYesNoDialog extends android.app.DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String title = getArguments().getString(FragmentYesNoDialogBuilder.EXTRA_TITLE);
		String yes = getArguments().getString(FragmentYesNoDialogBuilder.EXTRA_YES);
		String no = getArguments().getString(FragmentYesNoDialogBuilder.EXTRA_NO);
		String cancel = getArguments().getString(FragmentYesNoDialogBuilder.EXTRA_CANCEL);
		String message = getArguments().getString(FragmentYesNoDialogBuilder.EXTRA_MESSAGE);
		int icon = getArguments().getInt(FragmentYesNoDialogBuilder.EXTRA_ICON);
		AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
		if (icon != -1) {
			b.setIcon(icon);
		}
		if (title != null) {
			b.setTitle(title);
		}
		if (message != null) {
			b.setMessage(message);
		}
		if (yes != null) {
			b.setPositiveButton(yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					onYes();
				}
			});
		}
		if (no != null) {
			b.setNegativeButton(no, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					onNo();
				}
			});
		}
		if (cancel != null) {
			b.setNeutralButton(cancel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					onNeutral();
				}
			});
		}
		return b.create();
	}

	protected void onNeutral( ) {
		( MediaEngine.get(getArguments().getInt(FragmentYesNoDialogBuilder.EXTRA_ID)) ).performCancel();
	}

	protected void onNo( ) {
		( MediaEngine.get(getArguments().getInt(FragmentYesNoDialogBuilder.EXTRA_ID)) ).performMediaChoose();
	}

	protected void onYes( ) {
		( MediaEngine.get(getArguments().getInt(FragmentYesNoDialogBuilder.EXTRA_ID)) ).performMediaTake();
	}
}