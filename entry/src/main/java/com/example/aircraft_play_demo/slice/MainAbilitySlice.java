package com.example.aircraft_play_demo.slice;

import com.example.aircraft_play_demo.MainAbility;
import com.example.aircraft_play_demo.ResourceTable;
import com.example.aircraft_play_demo.devices.SelectDeviceDialog;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.IAbilityConnection;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Component;
import ohos.bundle.ElementName;
import ohos.data.distributed.common.KvManagerConfig;
import ohos.data.distributed.common.KvManagerFactory;
import ohos.distributedschedule.interwork.DeviceInfo;
import ohos.distributedschedule.interwork.DeviceManager;
import ohos.rpc.*;

import java.util.ArrayList;
import java.util.List;

public class MainAbilitySlice extends AbilitySlice {

    private List<DeviceInfo> devices = new ArrayList<>();

    @Override
    public void onStart(Intent intent) {
        requestPermissionsFromUser(new String[]{"ohos.permission.DISTRIBUTED_DATASYNC"}, 0);
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        initView();
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    // 为按钮分配响应事件
    private void initView() {
        findComponentById(ResourceTable.Id_btn_1).setClickedListener(new ButtonClick());
        findComponentById(ResourceTable.Id_btn_2).setClickedListener(new ButtonClick());
//        findComponentById(ResourceTable.Id_draw_guess).setClickedListener(new ButtonClick());
    }

    private void open_draw() {
        Intent draw_Intent = new Intent();
        Operation operation_Draw = new Intent.OperationBuilder().withBundleName(getBundleName())
                .withAbilityName(MainAbility.class.getName())
                .withAction("action.system.draw")
                .build();
        draw_Intent.setOperation(operation_Draw);
        startAbility(draw_Intent);
    }

    private void open_guess() {
        Intent guess_Intent = new Intent();
        Operation operationGuess = new Intent.OperationBuilder().withBundleName(getBundleName())
                .withAbilityName(MainAbility.class.getName())
                .withAction("action.system.guess")
                .build();
        guess_Intent.setOperation(operationGuess);
        startAbility(guess_Intent);
    }

    private class ButtonClick implements Component.ClickedListener {
        @Override
        public void onClick(Component component) {
            int btnId = component.getId();
            switch (btnId) {
                case ResourceTable.Id_btn_1:
                    getDevices(btnId);
                    break;
                case ResourceTable.Id_btn_2:
                    getDevices(btnId);
                    break;
                default:
                    break;
            }
        }
    }


    private void getDevices(int game_type) {
        if (devices.size() > 0) {
            devices.clear();
        }
        List<DeviceInfo> deviceInfos =
                DeviceManager.getDeviceList(ohos.distributedschedule.interwork.DeviceInfo.FLAG_GET_ONLINE_DEVICE);
        devices.addAll(deviceInfos);
        showDevicesDialog(game_type);
    }

    private void showDevicesDialog(int game_type) {
        new SelectDeviceDialog(this, devices, deviceInfo -> {
            startLocalFa(deviceInfo.getDeviceId(), game_type);
            startRemoteFa(deviceInfo.getDeviceId(), game_type);
        }).show();
    }

    private void startLocalFa(String deviceId, int game_type) {
        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder().withBundleName(getBundleName())
                .withAbilityName(MainAbility.class.getName())
                .withAction((game_type == ResourceTable.Id_btn_1) ? "action.system.draw" : "action.system.guess")
                .build();
        intent.setOperation(operation);
        startAbility(intent);
    }

    private void startRemoteFa(String deviceId, int game_type) {
//        String localDeviceId =
//                KvManagerFactory.getInstance().createKvManager(new KvManagerConfig(this)).getLocalDeviceInfo().getId();
        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder().withDeviceId(deviceId)
                .withBundleName(getBundleName())
                .withAbilityName(MainAbility.class.getName())
                .withAction((game_type == ResourceTable.Id_btn_1) ? "action.system.guess" : "action.system.draw")
                .withFlags(Intent.FLAG_ABILITYSLICE_MULTI_DEVICE)
                .build();
        intent.setOperation(operation);
        startAbility(intent);
    }



}
