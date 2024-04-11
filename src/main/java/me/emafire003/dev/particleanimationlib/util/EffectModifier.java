package me.emafire003.dev.particleanimationlib.util;

import me.emafire003.dev.particleanimationlib.Effect;

public interface EffectModifier {
    void modifyEffect(Effect effect, int current_tick);
}
