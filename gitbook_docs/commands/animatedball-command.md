---
description: Normal effect
cover: ../.gitbook/assets/Untitled Project(1).gif
coverY: -27.02933333333333
---

# AnimatedBall command

## Description

This effect spawns an animated ball, an effect that makes particles spiral around into tracing the surface of sphere. You can also make ovals.

<figure><img src="../.gitbook/assets/Untitled Project(1).gif" alt="" width="563"><figcaption></figcaption></figure>

To use this command you will need to type:

> `/pal animateball <paramters...>`

### Demo effect

To display the demo effect use:

> `/pal animatedball demo <particle> <posX> <posY> <posZ> <duration>`

This will create a 1x1 ball at the selected coordinates, using the selected particles, running for the selected duration of time

### Command Structure

The basic command structure for this effect is:

> `/pal animatedball <particle> <pos> <count> <perIteration> <size> <factors> <rotation> <duration>`
>
> or
>
> `/pal animatedball <particle> <pos> <count> <perIteration> <size> <duration>`

* `particle` , `duration` and `pos` have already been explained in the [General Information page](general-information.md).
* `count` is the total number of particles that will be displayed in the spiral that makes up the sphere, and the more there are the more the curves will be evident, and the slower the animation will go
* `perIteration` is the number of particles that are drawn each time the spiral is drawn. They can be more or less than the total count above, and will produce interesting effects. Best way to understand what these two numbers do is trying out different combinations!&#x20;
* `size` defines how big the sphere is. It acts like a multiplier, so the higher the bigger the sphere will be. If it's between 0 and 1, the sphere will be smaller.&#x20;
* `factors` are a series of 3 values defining how big in each direction the sphere should be. This can be used to create some ovals for example. (Remember that they are all double values so you will need to addtì the floating point!). If the factors are 0, the sphere won't be visible. This parameter is optional, and defaults to (1.0,1.0,1.0)
* `rotation` is another set of three double values like before, but handles the rotation of the ball. They are expressed in radians, so PI (3.14) = 180°. Keep in mind that if the sphere is uniform (aka a true sphere, with all the same factors) you won't see the rotation. This parameter is optional, and by default doesn't rotate anything.&#x20;

<table data-view="cards"><thead><tr><th></th><th></th><th data-hidden data-card-cover data-type="files"></th></tr></thead><tbody><tr><td>Example using count = 5 and perIteration = 100</td><td><code>/pal animatedball minecraft:electric_spark ~ ~ ~ 5 100 1 3</code></td><td><a href="../.gitbook/assets/2025-02-09_12.29.25.png">2025-02-09_12.29.25.png</a></td></tr><tr><td>Example using count = 500 and perIteration = 100</td><td><code>/pal animatedball minecraft:electric_spark ~ ~ ~ 500 100 1 3</code></td><td><a href="../.gitbook/assets/2025-02-09_12.33.26.png">2025-02-09_12.33.26.png</a></td></tr><tr><td>Example using factors of 1 2 1, creating an oval</td><td><code>/pal animatedball minecraft:electric_spark ~ ~ ~ 500 100 1 1 2 1 0.0 0.0 0.0 3</code></td><td><a href="../.gitbook/assets/2025-02-09_12.32.01_2.png">2025-02-09_12.32.01_2.png</a></td></tr><tr><td>Example using factors of 1 2 1, creating an oval, and rotating it</td><td><code>/pal animatedball minecraft:electric_spark ~ ~ ~ 500 100 1 1 2 1 2.0 0.0 0.0 3</code></td><td><a href="../.gitbook/assets/2025-02-09_12.32.49_2.png">2025-02-09_12.32.49_2.png</a></td></tr></tbody></table>
