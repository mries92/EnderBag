# EnderBag
![A plugin for easy ender chest access](web/banner.png)
---
## Features
- A configurable custom item which provides access to the ender chest when right clicked.
- Resource pack support, the item can be skinned to look like a native item.

## Configuration Options
- **Custom recipe** - Define a custom recipe for the item. Want it to be incredibly difficult to obtain? Make it cost a few wither stars.
- **Custom name and description** - Don't like the name "Ender Bag"? You can name and describe the item however you want in the config.

## Permissions
`enderbag.use` - Allows the player to use the ender bag item  
`enderbag.command` - Allows you to open the ender bag with a slash command /enderbag
`enderbag.give.self` - Allows you to give yourself an ender bag
`enderbag.give.others` - Allows you to give ender bags to others

## Commands
/enderbag - Open the ender bag
/enderbag give <player> - Give an ender bag to a player. If player is ommited, gives to yourself.

## Resource pack
This plugin applies `custom_model_data` to the new item. This allows a resource pack to apply a unique skin, and even change the skin based on certain conditions. The custom model data is described below, along with a standard resource pack. 

**NOTE:** All model data will end with a unique identifier specified in the server config. This is to prevent conflicts with other model data on the same item. This identifier can be left blank if there are no possibly conflicting resource packs. If you alter this value, you will need to update the predicates in the resource pack as well. The default identifier is `831`.

This is my placeholder programmer art pack <sub><sup>*I color shifted the bundle ;)*</sup></sub> It is enough to give the ender bag a unique look. If anybody creates a better
resource pack and doesn't mind sharing, I would love to replace replace the "official pack" or start listing them here.

[EnderBag Resource Pack](https://drive.google.com/file/d/1vIv1Z1259-t373P2_PKatjWAa9zHaqwh/view?usp=sharing) - Compatible pack. Feel free to extract assets and combine with your servers existing resource pack.

From `1.18`, you can force clients to use your resource pack in `server.properties`. Point it to a hosted compatible resource pack and clients will download it when they join.