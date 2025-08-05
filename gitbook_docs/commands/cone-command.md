---
description: Oriented effect
cover: ../.gitbook/assets/2025-02-12_12.13.15.png
coverY: -35
---

# Cone command

## Description

This effect spawns a cone! It starts from one point and spirals around until it makes a cone of a desidered length! The cone can be hollow or filled. You can even invert it, and you can select the number of strands that make up the cone.

<figure><img src="../.gitbook/assets/2025-02-12_11.56.00.png" alt="" width="563"><figcaption></figcaption></figure>

To use this command you will need to type:

> `/pal cone <paramters...>`

### Demo effect

To display the demo effect use:

> `/pal cone demo <particle> <posX> <posY> <posZ> <duration>`

This will spawn a single strand cone of about 5 blocks oriented towards positive Z looking direction

### Command Structure

The basic command structure for this effect is:

> `/pal cone <particle> <originPos> <yaw> <pitch> <count> <particlesPerIteration> <strands> <lengthGrow> <radiusGrow> <angularVelocity> <startingRotation> <solid> <randomStart> <inverted> <duration>`
>
> or
>
> `/pal animatedcircle <particle> <originPos> <count> <particlesPerIteration> <strands> <lengthGrow> <radiusGrow> <angularVelocity> <startingRotation> <solid> <randomStart> <inverted> <duration>`

* `particle` , `originPos`, `yaw`, `pitch` and `duration`have already been explained in the [General Information page](general-information.md).
* `count` is the total number of particles that will make up the cone, similar to AnimatedBall's count.
* `particlesPerIteration` is again, like the one in the AnimatedBall effect. This is the number of particles that will be displayed each iteration
* `strands` is the number of strands of the cone. A strand is one of the spirals that make up the cone. (Be careful! It is somewhat influenced by the angularVelocity and number of particles too!
* `lengthGrow` how much the length will grow each iteration, in blocks.  It is advised to keep this values at or below `0.1`, since bigger increments will make a VERY big effect, which won't be easily visible. (Also depends on all your other parameters)
* `radiusGrow` defines how much the radius of the cone will grow each iteration, in blocks. It is advised to keep this values at or below `0.1`, since bigger increments will make a VERY big effect, which won't be easily visible. (But again, also depends on all your other parameters)
* `angularVelocity` defines how fast the strands spiral around the center. It is expressed in radians (pi = 3.14 = 180°). Negative values will make the strands spins counter clockwise. A velocity will just create a number of lines equal to the number of strands.
* `startingRotation` in radians, is used to predetermine the intial rotation of the spirals, before applying any velocity.&#x20;
* `solid` is a boolean value, and if set to true, it will spawn particles from the starnds towards the center as well, thus making a solid cone.
* `randomStart` make the cone have a random starting rotation, overriding any values specified in startingRotation before
* `inverted` inverts the direction of the cone. If true, instead of having the starting point of the cone at the originPos you have specified, you will have the end of the cone.



{% hint style="info" %}
Especially in this effect, pretty much every paramter has influence on the other ones, so play around with them to make sure you get the desidered effect!
{% endhint %}

<table data-view="cards"><thead><tr><th></th><th></th><th data-hidden data-card-cover data-type="files"></th></tr></thead><tbody><tr><td>The standard circle</td><td><code>/pal animatedcircle minecraft:soul_fire_flame ~ ~ ~ 50 2 0 3.14 true false false 0.0 0.0 0.0 0.0 0.0 0.0 5</code></td><td><a href="../.gitbook/assets/2025-02-10_18.41.32.png">2025-02-10_18.41.32.png</a></td></tr><tr><td>A semi circle</td><td><code>/pal animatedcircle minecraft:soul_fire_flame ~ ~ ~ 50 2 0 3.14 true true false 0.0 0.0 0.0 0.0 0.0 0.0 5</code></td><td><a href="../.gitbook/assets/2025-02-10_18.04.29.png">2025-02-10_18.04.29.png</a></td></tr><tr><td>Adding some angular velocity</td><td><code>/pal animatedcircle minecraft:soul_fire_flame ~ ~ ~ 50 2 0 3.14 false false true 2.0 0.0 0.0 0.0 0.0 0.0 5</code></td><td><a href="../.gitbook/assets/AnimatedCircle example 2.gif">AnimatedCircle example 2.gif</a></td></tr><tr><td>Example of messing around with parameters (angularVelocity x=2.0)</td><td><code>/pal animatedcircle minecraft:electric_spark ~ ~ ~ 50 2 0 7 true false true 0.0 2.0 0.0 0.0 0.0 0.0 3</code></td><td><a href="../.gitbook/assets/Cool animation.gif">Cool animation.gif</a></td></tr></tbody></table>
