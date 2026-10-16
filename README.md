# Cybercraft Block Animations
[Download](https://github.com/vata-ot-nata/cybercraft-block-anim/releases)

BlockAnim allows server administrators to create dynamic block animations by defining a source region that gets copied and pasted repeatedly with an offset.
Each frame shifts the copied blocks along the X-axis, creating the illusion of movement, scrolling text, or other animated effects.

The plugin uses WorldEdit's clipboard system to efficiently copy regions and paste them at target locations. Animations run asynchronously on a timer, with configurable frame counts and delays.

## Visual Demonstration
(Clicable)
[![Visual demonstration](https://i.ytimg.com/vi/jylJQEwPslw/hq720.jpg?sqp=-oaymwEnCNAFEJQDSFryq4qpAxkIARUAAIhCGAHYAQHiAQoIGBACGAY4AUAB&rs=AOn4CLCCteejp0ihEh8C9JHN2lJjnowUaQ)](https://youtu.be/jylJQEwPslw?si=5nmgVfPokrqMkd95)

## Requirements

- Minecraft server (Spigot/Paper)
- WorldEdit plugin

## Installation

1. Install WorldEdit on your server
2. Download BlockAnim.jar and place it in your `plugins` folder
3. Restart the server
4. Use `/animcreate` to set up your first animation

## Commands

| Command | Arguments | Description |
|---------|-----------|-------------|
| `/animcreate` | `<name> <frames> <delay> <x1> <y1> <z1> <x2> <y2> <z2> <x3> <y3> <z3> <distance>` | Creates a new animation |
| `/animdelete` | `<name>` | Deletes an animation |
| `/addframe` | `<name>` | Increases frame count by 1 |
| `/removeframe` | `<name>` | Decreases frame count by 1 |
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
