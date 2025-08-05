---
description: Info on how to use the commands in general
---

# General Information

{% hint style="info" %}
All of the commands present in this wiki are only available by installing the companion mod [ParticleAnimationLibCommands](https://modrinth.com/mod/particleanimationlibcommands). They are not available in the core library, in order to make maintenance easier and having a lighter library to download if the commands aren't required in other mods.
{% endhint %}

{% hint style="warning" %}
A lot of the effects don't currently have the related images. Sorry! But I don't have enough time at the moment to put all of them into place! But you can help by making those images/videos with your own tinkering, and send them to me along with the commands you have used! You can do so either on discord or on PAL commands issues!
{% endhint %}

### The commands

All of the commands start with `/pal` or `/particleanimationlib`, either alias will work. In this guide the /pal alias will be used in the examples.&#x20;

&#x20;Another thing that many of them share is their very high number of parameters, and there is not much I can do about it withouth losing some customizability of the effects (which is already a bit limited compared to using the API through code due to technical restrictions). For these reason, you will want to check out form this wiki a full description of all of the parameters of an effect to get a clearer picture of what you are working with.

Some commands have parameters like rotations, angular velocity and factors. These parameters are made up three values, like coordinates. One problem linked to them is that minecraft ALWAYS expects a `double` type value (aka, a number with a floating point), so you WILL need to specify the floating point number part. For example, if you do this:&#x20;



{% hint style="danger" %}
`/pal myeffect stuff 0 1 0`
{% endhint %}

The effect won't display correctly, and you will have to do this instead:

{% hint style="success" %}
`/pal myeffect stuff .0 1.0 .0`
{% endhint %}

### Common aspects between commands

Most of the commands share a few parameters of the same kind between them. These parameters are:

* `particle`:  Often found among the first paramters of a command, it refers to the type of particle that will be used for the effect. Both vanilla and modded particles are supported. Some examples may include _"minecraft:dragon\_breath", "minecraft:composter"_. Dust-type particles are also valid, but keep in mind that you should also add the parameters that will determine their color and such. For example, if you want to display a blue dust particle of size 1, instead of inputting "_minecraft:dust_" you will need to put "minecraft:_dust{color:\[0.0,0.0,1.0],scale:1}" (for versions below 1.21 it would be: "dust 0 0 1 1")_
* `origin`: Also refereed to as `originPos, pos`, etc. This represents the coordinates at wich the effect will be spawned. It usually means it's the center but could also be the start of a line or an angle of a cube. It works just like any other coordinate parameter, so you will need to supply it with an X, Y, Z set of coordinates.
* `duration`: It represent the duration of effect. It's in seconds, so if you put "5" the effect will run for 5 seconds before stopping. Negative durations aren't valid values.&#x20;

#### Oriented Effects

Some effects are **"oriented"** effects, meaning they can be oriented to face a particular direction, using the paramters `yaw` and `pitch`. Generally, there will be two command variants, one where you will be able to selected them by hand, and another that will use the player's current yaw an pitch to orientate the effect towards them. They are exapressend in <kbd>**degrees**</kbd>!

#### Targeted Effects

Another type of effects are the **"targeted"** ones, where there will be a paramter of a `target`. This target could either be a player, or a usually another set of coordinates. It represent the point on which the effect ends, for example the Arc and Line effects require both a start and a finish point, and target is the finish point. (It works a bit like the /fill command positions!)

### Demo effects

All the commands have a `demo` parameter which will allow you to see a very simple example of the effect without needing to play around with all of the other parameters too much. Generally, they will look like this:

`/pal myeffect demo <particle> <posx> <posy> <posz> <duration>`

### Limitations

There are some things that aren't possible with the commands (for one reason or another) but are possible with the API. Notably, you can't chain effects togther using commands, unless you use some command block magic. You also can't modify running effects.&#x20;

Another thing of note, is that there is no particle limiter in place! Aka you could spawn way more particles that what the server can handle, and crash or freeze the game so be careful!

In future releases there may be a way to store commands created with commands and modify some things on those.

{% hint style="info" %}
In general, the best way to understand how the effects work and what they do is to play around with them and the parameters, so play with them!
{% endhint %}
