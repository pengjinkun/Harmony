package com.example.aircraft_play_demo.slice;

import com.example.aircraft_play_demo.DrawAbility;
import com.example.aircraft_play_demo.MainAbility;
import com.example.aircraft_play_demo.MyComponent.DrawBoard;
import com.example.aircraft_play_demo.MyComponent.DrawBoard.paintData;
import com.example.aircraft_play_demo.MyComponent.DrawContainer;
import com.example.aircraft_play_demo.ResourceTable;
import com.example.aircraft_play_demo.devices.SelectDeviceDialog;
import com.example.aircraft_play_demo.utils.DBUtils;
import com.example.aircraft_play_demo.utils.DeviceUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.IAbilityContinuation;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.app.Context;
import ohos.data.distributed.common.*;
import ohos.data.distributed.user.SingleKvStore;
import ohos.distributedschedule.interwork.DeviceInfo;
import ohos.distributedschedule.interwork.DeviceManager;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DrawAbilitySlice extends AbilitySlice implements IAbilityContinuation {
    private List<DeviceInfo> devices = new ArrayList<>();
    String otherDeviceId;

    @Override
    public void onStart(Intent intent) {
        requestPermissionsFromUser(new String[]{"ohos.permission.DISTRIBUTED_DATASYNC"}, 0);
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_draw);
        Button button = (Button) findComponentById(ResourceTable.Id_btn_connect);
        // 点击迁移
        button.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                continueAbility(getDeviceId());
            }
        });
    }

    private String getDeviceId(){
        String deviceId = "";
        List<String> outerDevices = DeviceUtils.getAvailableDeviceId();
        if (outerDevices == null || outerDevices.size() == 0){
        }else {
            for (String item : outerDevices) {
            }
            deviceId = outerDevices.get(0);
        }
        return deviceId;
    };
    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }


    private void getDevices() {
        if (devices.size() > 0) {
            devices.clear();
        }
        List<DeviceInfo> deviceInfos =
                DeviceManager.getDeviceList(ohos.distributedschedule.interwork.DeviceInfo.FLAG_GET_ONLINE_DEVICE);
        devices.addAll(deviceInfos);
        showDevicesDialog();
    }

    private void showDevicesDialog() {
        new SelectDeviceDialog(this, devices, deviceInfo -> {
            startLocalFa(deviceInfo.getDeviceId());
            startRemoteFa(deviceInfo.getDeviceId());
        }).show();
    }

    private void startLocalFa(String deviceId) {
        Intent intent = new Intent();
        intent.setParam("remoteDeviceId", deviceId);
        intent.setParam("isLocal", true);
        Operation operation = new Intent.OperationBuilder().withBundleName(getBundleName())
                .withAbilityName(MainAbility.class.getName())
                .withAction("action.system.draw")
                .build();
        intent.setOperation(operation);
        startAbility(intent);
    }

    private void startRemoteFa(String deviceId) {
        String localDeviceId =
                KvManagerFactory.getInstance().createKvManager(new KvManagerConfig(this)).getLocalDeviceInfo().getId();
        Intent intent = new Intent();
        otherDeviceId = localDeviceId;
        intent.setParam("remoteDeviceId", localDeviceId);
        intent.setParam("isLocal", false);
        Operation operation = new Intent.OperationBuilder().withDeviceId(deviceId)
                .withBundleName(getBundleName())
                .withAbilityName(MainAbility.class.getName())
                .withAction("action.system.guess")
                .withFlags(Intent.FLAG_ABILITYSLICE_MULTI_DEVICE)
                .build();
        intent.setOperation(operation);
        startAbility(intent);
    }

    @Override
    public boolean onStartContinuation() {
        return false;
    }

    @Override
    public boolean onSaveData(IntentParams intentParams) {
        return false;
    }

    @Override
    public boolean onRestoreData(IntentParams intentParams) {
        return false;
    }

    @Override
    public void onCompleteContinuation(int i) {

    }
}
