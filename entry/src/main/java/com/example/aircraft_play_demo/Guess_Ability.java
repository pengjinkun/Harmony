package com.example.aircraft_play_demo;

import com.example.aircraft_play_demo.slice.Guess_AbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class Guess_Ability extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(Guess_AbilitySlice.class.getName());
    }
}
