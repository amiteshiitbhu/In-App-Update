package amiteshiitbhu.inappupdate;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;

public class DemoClass extends AppCompatActivity implements InAppUpdateListener {

    private InAppUpdate instance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = InAppUpdate.getInstance();
        instance.requestInAppUpdate(AppUpdateType.FLEXIBLE, this, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onStateUpdate(InstallState installState) {
        if (installState.installStatus() == InstallStatus.DOWNLOADED) {
            instance.showSnakBar(this, getCurrentFocus());
        }
    }


}
