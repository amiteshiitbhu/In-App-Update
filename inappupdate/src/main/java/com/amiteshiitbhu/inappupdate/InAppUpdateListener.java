package com.amiteshiitbhu.inappupdate;

import android.content.Intent;

import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.install.InstallState;

public interface InAppUpdateListener {

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onStateUpdate(AppUpdateManager appUpdateManager, InstallState installState);

    void showSnakBar(AppUpdateManager appUpdateManager);
}
