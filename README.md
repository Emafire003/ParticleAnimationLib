# Particle Animation Library
Do you want to create cool particle effects such as cones cubues spheres and vortices but don't know how? Well then this mod is for you! 
It is mainly a developer utility, but as a player you can play around with the commands, especially to try out the effects before implementing them with code.

Currently available effects:
- [3D] Animated Sphere, Sphere, Cuboid, Vortex, Cone
- [2D] Arc, Line, Animated Circle

**At present time this mod is in alpha stage meaning that stuff could change or break between version, so be aware of that. Moreover, only a small part of effects planned are available, and the documentation will be relegated mostly to the javadocs for the time being, although I hope to make a wiki in the future!**
A forge/neoforge version may come in the future, but I first want to get the fabric/quilt one to a beta stage before starting the porting!

[![bisecthosting](https://github.com/Emafire003/ColoredGlowLib/assets/29462910/973c0c1a-062c-4c4a-aa04-f02e184fd5d7)](https://www.bisecthosting.com/LightDev)

## How to create the effects?
All of the effects are in the `effects` package and you can create a new effect simply by creating a new effect object and the use your preferred flavour of `#run()` method to activate the effect:
```java
Effect effect = new Effect(ServerWorld world, ParticleEffect effect, Vec3d pos, ...)
effect.runFor(5);
```

## How to manipulate the effect?
Every effect has its own getters and setters, so you can use those to change the settings before calling the run method. You can also provide a lamda that will execute each tickt that the effect is active to the run/runFor method. For more information about what each setting does please refer to the javadoc of the constructor for that effect.

## How do I use the commands?
You can use either `pal` or `particleanimationlib` and then use tab-completition to create your effects. 

## Credit
This mod is based on the bukkit plugin ![EffectLib](https://github.com/elBukkit/EffectLib) by elBukkit team, check it out too! The effects are taken from there and tweaked to be made compatible with modding!

## License
This library is available under MIT license
