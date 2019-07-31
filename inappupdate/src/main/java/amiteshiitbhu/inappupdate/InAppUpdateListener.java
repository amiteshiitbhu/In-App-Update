package amiteshiitbhu.inappupdate;

import android.content.Intent;

import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;

public interface InAppUpdateListener {

    public void onActivityResult(int requestCode, int resultCode, Intent data);


    void onStateUpdate(InstallState installState);
}
