package com.example.aircraft_play_demo.utils;

import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.distributedschedule.interwork.DeviceInfo;
import ohos.distributedschedule.interwork.DeviceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeviceUtils {
    private static  final String bundleName="com.example.aircraft_play_demo";

    //获取当前网络设备Id、设备名称
    public static List getDevice(){
        List deviceDataList = new ArrayList();
        List<DeviceInfo> deviceList= DeviceManager.getDeviceList(DeviceInfo.FLAG_GET_ONLINE_DEVICE);
        deviceList.forEach(device->{
            HashMap<String, String> deviceInfo=new HashMap<String, String>();
            deviceInfo.put("deviceId",device.getDeviceId());
            deviceInfo.put("deviceName",device.getDeviceName());
            deviceDataList.add(deviceInfo);
        });
        return deviceDataList;
    }

    public static String getDeviceId(){
        //第一步 获取设备列表
        // 调用DeviceManager的getDeviceList接口，通过FLAG_GET_ONLINE_DEVICE标记获得在线设备列表
        List<DeviceInfo> onlineDevices = DeviceManager.getDeviceList(DeviceInfo.FLAG_GET_ONLINE_DEVICE);
        // 判断组网设备是否为空
        if (onlineDevices.isEmpty()) {
            return null;
        }
        int numDevices = onlineDevices.size();
        ArrayList<String> deviceIds = new ArrayList<>(numDevices);
        ArrayList<String> deviceNames = new ArrayList<>(numDevices);
        onlineDevices.forEach((device) -> {
            deviceIds.add(device.getDeviceId());
            deviceNames.add(device.getDeviceName());
        });
        // 以选择首个设备作为目标设备为例
        // 开发者也可按照具体场景，通过别的方式进行设备选择
        String selectDeviceId = deviceIds.get(0);
        return selectDeviceId;
    }

    //远程启动FA或PA
    public static Intent getAbilityIntent(String deviceId, String abilityName){
        Intent intent=new Intent();
        Operation operation=new Intent.OperationBuilder()
                .withDeviceId(deviceId)
                .withBundleName(bundleName)
                .withAbilityName(abilityName)
                .withFlags(Intent.FLAG_ABILITYSLICE_MULTI_DEVICE)
                .build();
        intent.setOperation(operation);

        return intent;
    }

}