package com.example.aircraft_play_demo;

import com.example.aircraft_play_demo.slice.DrawAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class DrawAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(DrawAbilitySlice.class.getName());
    }
}
