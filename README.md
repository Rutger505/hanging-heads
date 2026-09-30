<p align="center"><img src="https://i.imgur.com/UdgyS8c.png" alt="Title" /></p>

<p align="center">A Paper plugin that adds hanging mob heads.</p>

<p align="center"><img src="https://i.imgur.com/QuZo9G5.png" alt="Showcase" width="600" /></p>

This is a Paper port of [Hanging Heads](https://github.com/ZLT9/hanging-heads) by ZLT ([Modrinth](https://modrinth.com/mod/hanging-heads), [Ko-fi](https://ko-fi.com/zlt09)).

## For players

Right-click the underside of a block with any mob head to hang it from the ceiling. Punch it to take it down again.

No client mod is needed: the heads are made of vanilla display entities, so they work on unmodded clients.

## For server owners

Drop the jar into `plugins/` on a Paper 26.2 server.

- Placing and breaking fire `BlockPlaceEvent` and `BlockBreakEvent`, so protection plugins apply.
- Hanging heads block fluids and block placement, and pop off when a piston pushes into them or a falling block lands on them.
- Dragon and piglin heads don't animate, and hanging wither skeleton skulls don't count towards summoning a Wither.

## Building

```sh
./gradlew build      # jar in build/libs
./gradlew runServer  # local Paper test server
```
