package com.example.aircraft_play_demo;

import com.example.aircraft_play_demo.slice.DrawAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.IAbilityContinuation;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;

public class DrawAbility extends Ability implements IAbilityContinuation {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(DrawAbilitySlice.class.getName());
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
