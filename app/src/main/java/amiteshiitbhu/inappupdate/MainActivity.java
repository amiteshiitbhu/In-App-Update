package amiteshiitbhu.inappupdate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.amiteshiitbhu.inappupdate.InAppUpdate;
import com.amiteshiitbhu.inappupdate.InAppUpdateListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;


public class MainActivity extends AppCompatActivity implements InAppUpdateListener {
    private static int MY_REQUEST_CODE = 8888;
    private InAppUpdate instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = InAppUpdate.getInstance();
        instance.requestInAppUpdate(MY_REQUEST_CODE, AppUpdateType.FLEXIBLE, this, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_REQUEST_CODE) {
            System.out.println("App Update = " + resultCode);
            if (resultCode != RESULT_OK) {
                System.out.println("Update flow failed! Result code: " + resultCode);
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }
        }
    }

    @Override
    public void onStateUpdate(AppUpdateManager appUpdateManager, InstallState installState) {
        Log.d("installState", installState.toString());
        if (installState.installStatus() == InstallStatus.DOWNLOADED) {
            popupSnackbarForCompleteUpdate(appUpdateManager);
        }
    }

    @Override
    public void showSnakBar(AppUpdateManager appUpdateManager) {
        popupSnackbarForCompleteUpdate(appUpdateManager);
    }


    private void popupSnackbarForCompleteUpdate(AppUpdateManager appUpdateManager) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                "An update has just been downloaded.", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("RESTART", view -> appUpdateManager.completeUpdate());
        snackbar.setActionTextColor(this.getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        instance.requestInAppUpdateFromOnResume(MY_REQUEST_CODE,AppUpdateType.FLEXIBLE, this, this);
    }
}
