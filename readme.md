# EnderBag
## A plugin for easier ender chest access.
---
### Features
- A configurable custom item which provides access to the ender chest when right clicked
- An `/enderchest` command which opens the ender chest directly
- Both features can be disabled entirely if you only want one or the other
- Resource pack support, the item can be skinned to look like a native item.
- Economy integration. Accessing the chest can be made to take currency from the player.

### Configuration Options
- **Custom recipe** - Define a custom recipe for the item. Want it to be incredibly difficult to obtain? Make it cost a few wither stars.
- **Custom name and lore** - Don't like the name "Ender Bag"? You can name and describe the item however you want in the config.
- **Access Restrictions** - Limit the number of uses, or the time between usage of the item.
- **Durability settings** - Customize the total durability of the item (or remove it completely), and define the amount of durability taken per use. You can also define what happens when durability runs out. Should the item break, or become unusuable like elytra?

### Permissions
`enderbag.item.use` - Allows the player to use the ender bag item  
`enderbag.command.use` - Allows the player to use the ender bag command  
`enderbag.give` - Allows the player to give an ender bag to themselves or other players

### Resource pack
This plugin applies `custom_model_data` to the new item. This allows a resource pack to apply a unique skin, and even change the skin based on certain conditions. The custom model data is described below, along with a standard resource pack. 

**NOTE:** All model data will end with a unique identifier specified in the server config. This is to prevent conflicts with other model data on the same item. This identifier can be left blank if there are no possibly conflicting resource packs. If you alter this value, you will need to update the predicates in your resource pack as well.

The first character represents the durability  
`0` - Normal  
`1` - Damaged  
`2` - Broken

The second character represents the cooldown status  
`0` - Off cooldown  
`1` - On cooldown

Examples - Assuming the `uid` is set to `1732`  
`001732` - Normal ender bag off cooldown  
`111732` - Damaged ender bag on cooldown  
`201732` - Broken ender bag off cooldown

This is my placeholder programmer art pack <sub><sup>*I color shifted the bundle ;)*</sup></sub> It is enough to give the ender bag a unique look. If anybody creates a better
resource pack and doesn't mind sharing, I would love to replace replace the "official pack" or start listing them here.

[EnderBag Resource Pack](http://mries.org/PUBLIC/EnderBag_Resource_Pack.zip) - Compatible pack. Feel free to extract assets and combine with your servers existing resource pack.

From `1.18`, you can force clients to use your resource pack in `server.properties`. Point it to a hosted compatible resource pack and clients will download it when they join.