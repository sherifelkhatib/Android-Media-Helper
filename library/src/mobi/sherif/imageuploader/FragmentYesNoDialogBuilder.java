package mobi.sherif.imageuploader;

import android.content.Context;
import android.os.Bundle;

class FragmentYesNoDialogBuilder {
	Context mContext;
	int id;
	String title;
	String message;
	String yes;
	String no;
	String cancel;
	int icon = -1;
	static final String EXTRA_ID = FragmentYesNoDialogBuilder.class.getName() + "d";
	static final String EXTRA_ICON = FragmentYesNoDialogBuilder.class.getName() + "i";
	static final String EXTRA_MESSAGE = FragmentYesNoDialogBuilder.class.getName() + "m";
	static final String EXTRA_CANCEL = FragmentYesNoDialogBuilder.class.getName() + "c";
	static final String EXTRA_NO = FragmentYesNoDialogBuilder.class.getName() + "n";
	static final String EXTRA_YES = FragmentYesNoDialogBuilder.class.getName() + "y";
	static final String EXTRA_TITLE = FragmentYesNoDialogBuilder.class.getName() + "t";

	public FragmentYesNoDialogBuilder(Context context, int id) {
		this.mContext = context;
		this.id = id;
	}

	public FragmentYesNoDialogBuilder setTitle(int title) {
		this.title = mContext.getString(title);
		return this;
	}

	public FragmentYesNoDialogBuilder setTitle(String title) {
		this.title = title;
		return this;
	}

	public FragmentYesNoDialogBuilder setYes(int yes) {
		this.yes = mContext.getString(yes);
		return this;
	}

	public FragmentYesNoDialogBuilder setYes(String yes) {
		this.yes = yes;
		return this;
	}

	public FragmentYesNoDialogBuilder setNo(int no) {
		this.no = mContext.getString(no);
		return this;
	}

	public FragmentYesNoDialogBuilder setNo(String no) {
		this.no = no;
		return this;
	}

	public FragmentYesNoDialogBuilder setCancel(int cancel) {
		this.cancel = mContext.getString(cancel);
		return this;
	}

	public FragmentYesNoDialogBuilder setCancel(String cancel) {
		this.cancel = cancel;
		return this;
	}

	public FragmentYesNoDialogBuilder setMessage(int message) {
		this.message = mContext.getString(message);
		return this;
	}

	public FragmentYesNoDialogBuilder setMessage(String message) {
		this.message = message;
		return this;
	}

	public FragmentYesNoDialogBuilder setIcon(int icon) {
		this.icon = icon;
		return this;
	}

	public FragmentYesNoDialog build( ) {
		return newInstance();
	}

	public FragmentSupportYesNoDialog buildSupport( ) {
		return newSupportInstance();
	}

	protected FragmentSupportYesNoDialog newSupportInstance( ) {
		FragmentSupportYesNoDialog frag = new FragmentSupportYesNoDialog();
		frag.setArguments(prepareBundle(new Bundle()));
		return frag;
	}

	protected FragmentYesNoDialog newInstance( ) {
		FragmentYesNoDialog frag = new FragmentYesNoDialog();
		frag.setArguments(prepareBundle(new Bundle()));
		return frag;
	}

	protected Bundle prepareBundle(Bundle args) {
		args.putString(EXTRA_TITLE, title);
		args.putString(EXTRA_YES, yes);
		args.putString(EXTRA_NO, no);
		args.putString(EXTRA_CANCEL, cancel);
		args.putString(EXTRA_MESSAGE, message);
		args.putInt(EXTRA_ICON, icon);
		args.putInt(EXTRA_ID, id);
		return args;
	}
}