package me.emafire003.dev.particleanimationlib.util;

import me.emafire003.dev.particleanimationlib.EffectV3;

public interface EffectModifier {
    void modifyEffect(EffectV3 effect, int current_tick);
}
