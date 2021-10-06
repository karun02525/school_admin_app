package org.dps.admin.utils;

import android.content.Context;
import android.util.Log;

import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadServiceBroadcastReceiver;

public class SingleUploadBroadcastReceiver extends UploadServiceBroadcastReceiver{
    private String mUploadID;
    private Delegate mDelegate;

    public void setUploadID(String uploadID) {
        mUploadID = uploadID;
    }

    public void setDelegate(Delegate delegate) {
        mDelegate = delegate;
    }

    @Override
    public void onProgress(Context context, UploadInfo uploadInfo) {
        if (uploadInfo.getUploadId().equals(mUploadID) && mDelegate != null) {
            mDelegate.onProgress(uploadInfo.getProgressPercent());
        }
    }

    @Override
    public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
        if (uploadInfo.getUploadId().equals(mUploadID) && mDelegate != null) {
            mDelegate.onError(exception);
        }

    }

    @Override
    public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
        Log.e("sid2321", "completed");
        if (uploadInfo.getUploadId().equals(mUploadID) && mDelegate != null) {

            mDelegate.onCompleted(serverResponse.getHttpCode(), serverResponse.getBody());
        }
    }

    @Override
    public void onCancelled(Context context, UploadInfo uploadInfo) {
        if (uploadInfo.getUploadId().equals(mUploadID) && mDelegate != null) {
            mDelegate.onCancelled();
        }
    }

public interface Delegate {
    void onProgress(int progress);

    void onProgress(long uploadedBytes, long totalBytes);

    void onError(Exception exception);

    void onCompleted(int serverResponseCode, byte[] serverResponseBody);

    void onCancelled();
}


}
