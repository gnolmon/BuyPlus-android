package lc.buyplus.interfaces;

import lc.buyplus.cores.CoreInterface;

public interface OnDialogYesNoListener extends CoreInterface {
	public void onYes();
	public void onNo();
}
