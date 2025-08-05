---
description: Oriented effect
cover: ../.gitbook/assets/AnimatedCircle banner.gif
coverY: 0
---

# AnimatedCircle command

## Description

This effect spawns an animated circle. You can also use it to make 2d spirals if you want. You can make even semi circles, or partial ones. It can be oriented towards a player's looking direction as well. You can also do some crazy stuff by using some rotation, check the cards below!

<figure><img src="../.gitbook/assets/circle main display.gif" alt="" width="563"><figcaption></figcaption></figure>

To use this command you will need to type:

> `/pal animatedcircle <paramters...>`

### Demo effect

To display the demo effect use:

> `/pal animatedcricle demo <particle> <posX> <posY> <posZ> <duration>`

This will a small animated circle of 1 block diamter, like a "loading" cirlce.

### Command Structure

The basic command structure for this effect is:

> `/pal animatedcircle <particle> <originPos> <yaw> <pitch> <count> <radius> <radiusGrow> <maxAngle> <wholeCircle> <resetCircle> <enableRotation> <angularVelocity> <rotations> <subtractFromOrigin> <duration>`
>
> or
>
> `/pal animatedcircle <particle> <originPos> <count> <radius> <radiusGrow> <maxAngle> <wholeCircle> <resetCircle> <enableRotation> <angularVelocity> <rotations> <subtractFromOrigin> <duration>`

* `particle` , `originPos`, `yaw`, `pitch` and `duration`have already been explained in the [General Information page](general-information.md).
* `count` is the total number of particles that will be displayed in the circle affect, aka the total number of particles a circle is going to be made up of. The more, the more defined the circle will be.
* `radius` is the the radius of the circle.
* `radiusGrow` defines how much the radius of a circle will grow each iteration. You can use this parameter to make some spirals! It is advised to keep this values at or below `0.1`, since bigger increments will make a VERY big effect, which won't be easily visible.
* `maxAngle` is used to create fractions of circles. It is represented in radians, and is the max angle the circle will cover, so, for example, if you set it to 3.14 (π, pi) it will create an half-circle!
* `wholeCircle` this parameter allows you to change from a fully displayed circle ("static", setting this to `true`), to a progressivley displayed one ("animated", setting this to `false`).&#x20;
* `resetCircle` makes the circle start at the same origin every step. You can use this along with _maxAngle_ to form persisten semicircles (or subparts of cicles, like quarters). To make this work you will need to set the _wholeCircle_ paramter to `false`, otherwise it will just display a point.
* `enableRotation` enables or disables the rotation of the circle, linked to next paramter:&#x20;
* `angularVelocity` is another set of 3 double values (the floating point ones) which reperest the angular velocity at which the circle rotates in each direction. They are expressed in radians (so π = 180°)&#x20;
* `rotation` is similar to angularVelocity, but is a fixed, static rotation. It also uses the same type o values, so 3 radians one for each direction.

<table data-view="cards"><thead><tr><th></th><th></th><th data-hidden data-card-cover data-type="files"></th></tr></thead><tbody><tr><td>The standard circle</td><td><code>/pal animatedcircle minecraft:soul_fire_flame ~ ~ ~ 50 2 0 3.14 true false false 0.0 0.0 0.0 0.0 0.0 0.0 5</code></td><td><a href="../.gitbook/assets/2025-02-10_18.41.32.png">2025-02-10_18.41.32.png</a></td></tr><tr><td>A semi circle</td><td><code>/pal animatedcircle minecraft:soul_fire_flame ~ ~ ~ 50 2 0 3.14 true true false 0.0 0.0 0.0 0.0 0.0 0.0 5</code></td><td><a href="../.gitbook/assets/2025-02-10_18.04.29.png">2025-02-10_18.04.29.png</a></td></tr><tr><td>Adding some angular velocity</td><td><code>/pal animatedcircle minecraft:soul_fire_flame ~ ~ ~ 50 2 0 3.14 false false true 2.0 0.0 0.0 0.0 0.0 0.0 5</code></td><td><a href="../.gitbook/assets/AnimatedCircle example 2.gif">AnimatedCircle example 2.gif</a></td></tr><tr><td>Example of messing around with parameters (angularVelocity x=2.0)</td><td><code>/pal animatedcircle minecraft:electric_spark ~ ~ ~ 50 2 0 7 true false true 0.0 2.0 0.0 0.0 0.0 0.0 3</code></td><td><a href="../.gitbook/assets/Cool animation.gif">Cool animation.gif</a></td></tr><tr><td>A crazy cool effect, which is a ball btw</td><td><code>/pal animatedcircle minecraft:soul_fire_flame ~ ~ ~ 50 2 0 3.14 true false true 2.0 0.0 0.0 0.0 2.0 1.0 5</code></td><td><a href="../.gitbook/assets/2025-02-11_11.38.04.png">2025-02-11_11.38.04.png</a></td></tr></tbody></table>
