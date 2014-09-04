/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.telecomm.testapps;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

/**
 * This activity exists in order to add an icon to the launcher. This activity has no UI of its own
 * and instead starts the notification for {@link TestConnectionService} via
 * {@link CallServiceNotifier}. After triggering a notification update, this activity immediately
 * finishes.
 *
 * To directly trigger a new incoming call, use the following adb command:
 *
 * adb shell am start -a android.telecomm.testapps.ACTION_START_INCOMING_CALL -d "tel:123456789"
 */
public class TestCallActivity extends Activity {

    public static final String ACTION_NEW_INCOMING_CALL =
            "android.telecomm.testapps.ACTION_START_INCOMING_CALL";

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        final Intent intent = getIntent();
        if (intent != null && intent.getData() != null) {
            startIncomingCallBroadcast(intent.getData());
        }
        CallServiceNotifier.getInstance().updateNotification(this);
        finish();
    }

    /**
     * Bypass the notification and start the test incoming call directly.
     */
    private void startIncomingCallBroadcast(Uri handle) {
        CallNotificationReceiver.sendIncomingCallIntent(this, handle, false);
    }
}
