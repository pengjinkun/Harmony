package com.example.aircraft_play_demo.slice;

import com.example.aircraft_play_demo.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;

public class Guess_AbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        requestPermissionsFromUser(new String[]{"ohos.permission.DISTRIBUTED_DATASYNC"}, 0);
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_guess_);
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
