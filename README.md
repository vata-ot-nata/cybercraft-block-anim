# Cybercraft Block Animations
[Download](https://github.com/vata-ot-nata/cybercraft-block-anim/releases)

BlockAnim allows server administrators to create dynamic block animations using a sequence of static animation frames placed next to each other in the world with a fixed offset along the X-axis.
Each frame is a pre-built block structure that remains static in its original location. BlockAnim sequentially copies these frames and pastes them into a designated target location,
creating the illusion of an animated screen, a waving flag, smoke rising from a chimney, or other animated effects.

The plugin uses WorldEdit's clipboard system to efficiently copy regions and paste them at target locations. Animations run asynchronously in a loop, with configurable number of frames and delays.

## Visual Demonstration
(Clickable)  
[![Visual demonstration](https://i.ytimg.com/vi/jylJQEwPslw/hq720.jpg?sqp=-oaymwEnCNAFEJQDSFryq4qpAxkIARUAAIhCGAHYAQHiAQoIGBACGAY4AUAB&rs=AOn4CLCCteejp0ihEh8C9JHN2lJjnowUaQ)](https://youtu.be/jylJQEwPslw?si=5nmgVfPokrqMkd95)  

## Requirements

- Minecraft server (Spigot/Paper)
- WorldEdit plugin (Tested with WorldEdit v7.3.10)

## Installation

1. Install [WorldEdit](https://modrinth.com/plugin/worldedit/versions?l=paper) on your server
2. [Download the latest BlockAnim.jar](https://github.com/vata-ot-nata/cybercraft-block-anim/releases) and place it in your `plugins` folder
3. Restart the server
4. Use `/animcreate` to set up your first animation

## Commands

| Command | Arguments | Description |
|---------|-----------|-------------|
| `/animcreate` | `<name> <frames> <delay> <x1> <y1> <z1> <x2> <y2> <z2> <x3> <y3> <z3> <distance>` | Creates a new animation |
| `/animdelete` | `<name>` | Deletes an animation |
| `/addframe` | `<name>` | Increases number of frames by 1 |
| `/removeframe` | `<name>` | Decreases number of frames by 1 |
| `/setdelay` | `<name> <delay>` | Sets the delay between frames (in ticks) |
| `/animreload` | — | Reloads the plugin |

### Command Arguments

- **name** — Unique identifier for the animation
- **frames** — Total number of frames in the animation loop
- **delay** — Time between frames in ticks (20 ticks = 1 second)
- **x1 y1 z1** — First corner of the source region
- **x2 y2 z2** — Second corner of the source region
- **x3 y3 z3** — Target paste location
- **distance** — Block offset between frames along the X-axis


## Storage

Animations are stored in `config.yml` in `C:\Users\user\Desktop\paper-server\plugins\BlockAnim`
```yaml
anim:
  example:
    name: example
    frames: 10
    delay: 5
    pos1: 100 64 100
    pos2: 110 70 110
    pos3: 200 64 200
    long: 2
```
