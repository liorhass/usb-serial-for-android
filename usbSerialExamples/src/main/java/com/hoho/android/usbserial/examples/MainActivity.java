// ADB connect to a device over WiFi:   https://developer.android.com/studio/command-line/adb#wireless
//   Connect the device to the host computer with a USB cable.
//        adb tcpip 5555
//   Find the IP address of the device: Settings -> System -> About tablet -> Status
//        adb connect <device_ip_address>
//        adb devices   (to verify)
//   If the adb connection is ever lost:
//     Reconnect by executing the    adb connect <device_ip_address>    step again.
//     If that doesn't work, reset your adb host:
//        adb kill-server
//     Then start over from the beginning.
//
package com.hoho.android.usbserial.examples;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, new DevicesFragment(), "devices").commit();
        else
            onBackStackChanged();
    }

    @Override
    public void onBackStackChanged() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(getSupportFragmentManager().getBackStackEntryCount()>0);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if(intent.getAction().equals("android.hardware.usb.action.USB_DEVICE_ATTACHED")) {
            TerminalFragment terminal = (TerminalFragment)getSupportFragmentManager().findFragmentByTag("terminal");
            if (terminal != null)
                terminal.status("USB device detected");
        }
        super.onNewIntent(intent);
    }

}
