package me.emafire003.dev.particleanimationlib;

import net.minecraft.util.StringIdentifiable;

public enum EffectType implements StringIdentifiable {

    //TODO maybe add a sort of completable effect
    /**
     * Effect is once played instantly.
     * Currently unused v 0.0.1
     */
    INSTANT("instant"),
    //{@link Effect#period}.
    /**
     * Effect is several times played instantly. Set the interval with
     */
    REPEATING("repeating"),
    //{@link Effect#delay}.
    /**
     * Effect is once delayed played. Set delay with
     * Currently unused v 0.0.1
     */
    DELAYED("delayed");

    private final String name;


    private EffectType(final String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
