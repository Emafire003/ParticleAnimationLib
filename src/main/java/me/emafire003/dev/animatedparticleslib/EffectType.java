package me.emafire003.dev.animatedparticleslib;

public enum EffectType {

    /**
     * Effect is once played instantly.
     */
    INSTANT,
    //{@link Effect#period}.
    /**
     * Effect is several times played instantly. Set the interval with
     */
    REPEATING,
    //{@link Effect#delay}.
    /**
     * Effect is once delayed played. Set delay with
     */
    DELAYED

}
