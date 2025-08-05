---
description: Targeted effect
cover: ../.gitbook/assets/2025-02-10_18.11.16.png
coverY: 78
---

# Arc command

## Description

This effect spawns an arc between two points. You can configure the height of the arc and its orientation

<figure><img src="../.gitbook/assets/Untitled Project.gif" alt=""><figcaption></figcaption></figure>

To use this command you will need to type:

> `/pal arc <paramters...>`

### Demo effect

To display the demo effect use:

> `/pal arc demo <particle> <posX> <posY> <posZ> <duration>`

This will spawn an arc

### Command Structure

The basic command structure for this effect is:

> `/pal arc <particle> <originPos> <targetPos> <count> <height> <duration>`

* `particle` , `originPos`, `targetPos` and `duration`have already been explained in the [General Information page](general-information.md).
* `count` is the total number of particles that will make up the arc. The more, the more defined the arc will be.
* `height` is the height at the middle point of the arc. Aka the top height the arc will reach between the two points.

<table data-view="cards"><thead><tr><th></th><th></th><th data-hidden data-card-cover data-type="files"></th></tr></thead><tbody><tr><td>Example using count = 5 and perIteration = 100</td><td><code>/pal animatedball minecraft:electric_spark ~ ~ ~ 5 100 1 3</code></td><td><a href="../.gitbook/assets/2025-02-09_12.29.25.png">2025-02-09_12.29.25.png</a></td></tr><tr><td>Example using count = 500 and perIteration = 100</td><td><code>/pal animatedball minecraft:electric_spark ~ ~ ~ 500 100 1 3</code></td><td><a href="../.gitbook/assets/2025-02-09_12.33.26.png">2025-02-09_12.33.26.png</a></td></tr><tr><td>Example using factors of 1 2 1, creating an oval</td><td><code>/pal animatedball minecraft:electric_spark ~ ~ ~ 500 100 1 1 2 1 0.0 0.0 0.0 3</code></td><td><a href="../.gitbook/assets/2025-02-09_12.32.01_2.png">2025-02-09_12.32.01_2.png</a></td></tr><tr><td>Example using factors of 1 2 1, creating an oval, and rotating it</td><td><code>/pal animatedball minecraft:electric_spark ~ ~ ~ 500 100 1 1 2 1 2.0 0.0 0.0 3</code></td><td><a href="../.gitbook/assets/2025-02-09_12.32.49_2.png">2025-02-09_12.32.49_2.png</a></td></tr><tr><td></td><td></td><td></td></tr></tbody></table>
