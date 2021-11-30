package com.example.aircraft_play_demo;

import com.example.aircraft_play_demo.slice.DrawAbilitySlice;
import com.example.aircraft_play_demo.slice.Guess_AbilitySlice;
import com.example.aircraft_play_demo.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());
        addActionRoute("action.system.draw", DrawAbilitySlice.class.getName());
        addActionRoute("action.system.guess", Guess_AbilitySlice.class.getName());
    }


}
