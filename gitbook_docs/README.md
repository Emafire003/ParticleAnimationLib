---
icon: hand-wave
cover: https://gitbookio.github.io/onboarding-template-images/header.png
coverY: 0
---

# Welcome

![](https://github.com/Emafire003/ParticleAnimationLib/assets/29462910/3b1316c8-96c3-4450-abf8-241ce05eaf2e)

Do you want to create cool particle effects such as cones, cubes, spheres, and vortices but don't know how? Well, then this mod is for you! It is mainly a developer utility, but as a player you can play around with the commands, especially to try out the effects before implementing them with code. It is also fully serverside, meaning you won't need it on the client. It is inspirerd by the [EffectLib](https://github.com/elBukkit/EffectLib) plugin as you may have guessed.

Currently available effects:

* \[3D] Animated Sphere, Sphere, Cuboid, Vortex, Cone, Donut
* \[2D] Arc, Line, Animated Circle, Colored and BW Images, Text

**At present time this mod is in beta, meaning that stuff could still break or change a bit. And some effects are still missing** A forge/neoforge version may come in the future, but I first want to get the fabric/quilt all done before starting the port. Sorry! (It most likely will work with Syntra connector for the time being)

### How to use this mod

This mod on its own, is only intended for developers. But don't worry, if you are a player and still want to try out all of the amazing effects (albeit with some limitations currently) you can do so by install [ParticleAnimationLibCommands](https://modrinth.com/mod/particleanimationlibcommands).

Yes, I know, the commands are a bit long and confusing, that's why I've also started working on a wiki, that you can find [right here](https://emafire003.gitbook.io/particleanimationlibwiki/commands)!

### How to create the effects?

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

**Note:** when using the builder you will need to always supply the world particle effect and origin pos to the method, beacuse every animation effect uses them and it is assumed they are not null. You can change them later if you need to. **TLDR;** you are forced to supply them in order to make the effect work instead of crashing!

<div align="center"><img src="https://github.com/Emafire003/ParticleAnimationLib/assets/29462910/f3614984-c6c8-4fd1-ac5b-0ed9adef732a" alt="Demo of some of the effects from version 0.0.1"></div>

_Demo of some of the effects from version 0.0.1_

### How to manipulate the animation effect?

Every effect has its own getters and setters, so you can use those to change the settings before calling the run method. You can also provide a lamda that will execute each tickt that the effect is active to the run/runFor method. For more information about what each setting does please refer to the javadoc of the constructor for that effect.

### How do I use the commands?

First of all, you must install the companion mod [ParticleAnimationLibCommands](https://modrinth.com/mod/particleanimationlibcommands). You can use either `pal` or `particleanimationlib` and then use tab-completition to create your effects. You can even have a preview of each effect by using `/pal <effect> demo <particle> <pos>`!

Please refer to the [wiki](https://emafire003.gitbook.io/particleanimationlibwiki/commands) for more information!

#### For developers:

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

### Credit

This mod is based on the bukkit plugin [EffectLib](https://github.com/elBukkit/EffectLib) by elBukkit team, check it out too! The effects are taken from there and tweaked to be compatible with modding, occasionally adding extra functionality.

### License

This library is available under MIT license
