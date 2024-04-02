package me.emafire003.dev.animatedparticleslib;

public enum EffectType {

    /**
     * Effect is once played instantly.
     */
    INSTANT,
    /**
     * Effect is several times played instantly. Set the interval with {@link Effect#period}.
     */
    REPEATING,
    /**
     * Effect is once delayed played. Set delay with {@link Effect#delay}.
     */
    DELAYED

}
