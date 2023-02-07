# EnderBag
![A plugin for easy ender chest access](web/banner.png)
---
## Features
- A configurable custom item which provides access to the ender chest when right clicked.
- An `/enderbag` command which opens the ender chest directly, includes aliases.
- Both features can be disabled entirely if you only want one or the other.
- Resource pack support, the item can be skinned to look like a native item.

## Configuration Options
- **Custom recipe** - Define a custom recipe for the item. Want it to be incredibly difficult to obtain? Make it cost a few wither stars.
- **Custom name and lore** - Don't like the name "Ender Bag"? You can name and describe the item however you want in the config.

## Permissions
`enderbag.item.use` - Allows the player to use the ender bag item  
`enderbag.command.use` - Allows the player to use the ender bag command  
`enderbag.give` - Allows the player to give an ender bag to themselves or other players

## Commands
**NOTE:** `/enderbag` is the base command, but it can be subsituted with `/enderchest`, `/eb`, or `/ec`  

`/ec help` - Get information about the mod and list commands  
`/ec` - Open the ender chest  
`/ec give` - Give the player an ender chest  

## Resource pack
This plugin applies `custom_model_data` to the new item. This allows a resource pack to apply a unique skin, and even change the skin based on certain conditions. The custom model data is described below, along with a standard resource pack. 

**NOTE:** All model data will end with a unique identifier specified in the server config. This is to prevent conflicts with other model data on the same item. This identifier can be left blank if there are no possibly conflicting resource packs. If you alter this value, you will need to update the predicates in the resource pack as well. The default identifier is `831`.

This is my placeholder programmer art pack <sub><sup>*I color shifted the bundle ;)*</sup></sub> It is enough to give the ender bag a unique look. If anybody creates a better
resource pack and doesn't mind sharing, I would love to replace replace the "official pack" or start listing them here.

[EnderBag Resource Pack](http://mries.org/PUBLIC/EnderBag_Resource_Pack.zip) - Compatible pack. Feel free to extract assets and combine with your servers existing resource pack.

From `1.18`, you can force clients to use your resource pack in `server.properties`. Point it to a hosted compatible resource pack and clients will download it when they join.