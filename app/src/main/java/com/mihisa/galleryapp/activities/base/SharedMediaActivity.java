package com.mihisa.galleryapp.activities.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import com.mihisa.galleryapp.R;
import com.mihisa.galleryapp.data.StorageHelper;
import com.mihisa.galleryapp.util.AlertDialogHelper;

import org.horaapps.liz.ThemedActivity;

/**
 * Created by insight on 14.03.18.
 */

public class SharedMediaActivity extends ThemedActivity {

    private int REQUEST_CODE_SD_CARD_PERMISSION = 42;

    public void requestSdCardPermissions() {

        AlertDialog textDialog = AlertDialogHelper.getTextDialog(this, R.string.sd_card_write_permission_title, R.string.sd_card_permissions_message);
        textDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.ok_action).toUpperCase(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                startActivityForResult(new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE), REQUEST_CODE_SD_CARD_PERMISSION);
            }
        });
        textDialog.show();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent resultData) {
        if(resultCode == RESULT_OK) {
            if(requestCode == REQUEST_CODE_SD_CARD_PERMISSION) {
                Uri treeUri = resultData.getData();
                StorageHelper.saveSdCardInfo(getApplicationContext(), treeUri);
                getContentResolver().takePersistableUriPermission(treeUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                Toast.makeText(this, R.string.got_permission_wr_sdcard, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
