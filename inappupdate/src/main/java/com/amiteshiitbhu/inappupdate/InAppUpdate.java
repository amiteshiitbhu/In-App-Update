package com.amiteshiitbhu.inappupdate;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;

public class InAppUpdate {

    private static InAppUpdate INSTANCE = new InAppUpdate();
    private AppUpdateManager appUpdateManager;
    private InAppUpdateListener inAppUpdateListener;

    public static InAppUpdate getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        return new InAppUpdate();
    }

    public void requestInAppUpdate(int myRequestCode, int updateType, Context context, InAppUpdateListener inAppUpdateListener) {
        appUpdateManager = AppUpdateManagerFactory.create(context);
        this.inAppUpdateListener = inAppUpdateListener;

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateManager.registerListener(installStateUpdatedListener);


        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {
                requestAppUpdate(myRequestCode, updateType, context, appUpdateInfo);
            }
        });
    }


    // Checks that the update is not stalled during 'onResume()'.
    // However, you should execute this check at all app entry points.
    public void requestInAppUpdateFromOnResume(int myRequestCode, int updateType, Context context, InAppUpdateListener inAppUpdateListener) {
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(appUpdateInfo -> {
                    if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                        inAppUpdateListener.showSnakBar(appUpdateManager);
                    } else if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                        try {
                            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, updateType, (Activity) context, myRequestCode);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    private void requestAppUpdate(int myRequestCode, int updateType, Context context, AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, updateType, (Activity) context, myRequestCode);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    private InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(InstallState installState) {
            inAppUpdateListener.onStateUpdate(appUpdateManager, installState);
        }
    };

}
