package me.emafire003.dev.particleanimationlib;

public enum EffectType {

    /**
     * Effect is once played instantly.
     * Currently unused v 0.0.1
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
     * Currently unused v 0.0.1
     */
    DELAYED

}
