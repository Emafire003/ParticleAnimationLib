![particle_animation_library_header](https://github.com/Emafire003/ParticleAnimationLib/assets/29462910/3b1316c8-96c3-4450-abf8-241ce05eaf2e)


Do you want to create cool particle effects such as cones cubues spheres and vortices but don't know how? Well then this mod is for you! 
It is mainly a developer utility, but as a player you can play around with the commands, especially to try out the effects before implementing them with code. It is also fully serverside, meaning you won't need it on the client. It is inspirerd by the [EffectLib](https://github.com/elBukkit/EffectLib) plugin as you may have guessed.

Currently available effects:
- [3D] Animated Sphere, Sphere, Cuboid, Vortex, Cone
- [2D] Arc, Line, Animated Circle, Colored and BW Images


**At present time this mod is in alpha stage meaning that stuff could change or break between version, so be aware of that (I will try to avoid making such changes tho). Moreover, only a small part of effects planned are available, and the documentation will be relegated mostly to the javadocs for the time being, although I hope to make a wiki in the future!**
A forge/neoforge version may come in the future, but I first want to get the fabric/quilt one to a beta stage before starting the porting!

[![bisecthosting](https://github.com/Emafire003/ColoredGlowLib/assets/29462910/973c0c1a-062c-4c4a-aa04-f02e184fd5d7)](https://www.bisecthosting.com/LightDev)

## How to create the effects?
All of the effects are in the `effects` package and you can create a new effect simply by creating a new effect object or you can use their builder method, kinda like the Block properties.

Then you can use your preferred flavour of `#run()` method to activate the effect:
```java
//Constructor pseudocode with runFor example
Effect effect = new Effect(ServerWorld world, ParticleEffect effect, Vec3d originPos, int particles, Other stuff...)
effect.runFor(5);

//Builder pseudocode with run and setIterations example (20 ticks = 1 second)
Effect effect = Effect.builder(ServerWorld world, ParticleEffect effect, Vec3d originPos).particles(10).anotherOption(true).build;
effect.setIterations(5*20);
effect.run();
```

**Note:** when using the builder you will need to alwasy supply the world particle effect and origin pos to the method, beacuse every animation effect uses them and it is assumed they are not null. You can change them later if you need to. 
**TLDR;** you are forced to supply them in order to make the effect work instead of crashing!

<center>
  
<p align="center">
  <img src="https://github.com/Emafire003/ParticleAnimationLib/assets/29462910/f3614984-c6c8-4fd1-ac5b-0ed9adef732a" alt="Demo of some of the effects from version 0.0.1" />
</p>

</center>

*Demo of some of the effects from version 0.0.1*

## How to manipulate the animation effect?
Every effect has its own getters and setters, so you can use those to change the settings before calling the run method. You can also provide a lamda that will execute each tickt that the effect is active to the run/runFor method. For more information about what each setting does please refer to the javadoc of the constructor for that effect.

## How do I use the commands?
You can use either `pal` or `particleanimationlib` and then use tab-completition to create your effects. They kinda lack documentation except from the names of the arguments, so I suggest to look in the corresponding classes of the effect and see from the javadoc what each thing does. Sorry :P

*Note*: The commands that take in stuff like rotations and such, change the integer values to "x.5", since the game thinks they are positions. You can get around that by inputting a double instead of an integer like so "x.0" (for example 2.0 or 0.0)

### For developers:
Add this library into your `build.gradle` as a dependency
```gradle
repositories {
    maven {
        name = "Modrinth"
        url = "https://api.modrinth.com/maven"
        content {
            includeGroup "maven.modrinth"
        }
    }
}

dependencies {
    modImplementation "maven.modrinth:particleanimationlib:<version>"
}
```
You can get the version number from the modrinth versions page.

## Credit
This mod is based on the bukkit plugin [EffectLib](https://github.com/elBukkit/EffectLib) by elBukkit team, check it out too! The effects are taken from there and tweaked to be made compatible with modding, occasionally adding extra functionality.

## License
This library is available under MIT license
