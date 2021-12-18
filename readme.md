# EnderBag
---
### This plugin gives the players a few options for easier ender chest access.
### Features
- A configurable custom item which provides access to the ender chest when right clicked
- An `/enderchest` command which opens the ender chest directly
- Both features can be disabled entirely if you only want one or the other

### Various configuration options allow you to tweak the plugin to suit your servers players and economy
- **Custom recipe** - Define a custom recipe for the item. Want it to be incredibly difficult to obtain? Make it cost a few wither stars.
- **Custom name and lore** - Don't like the name "Ender Bag"? You can name and describe the item however you want in the config.
- **Access Restrictions** - Limit the number of uses, or the time between usage of the item.
- **Durability settings** - Customize the total durability of the item (or remove it completely), and define the amount of durability taken per use. You can also define what happens when durability runs out. Should the item break, or become unusuable like elytra?

### Resource pack
This plugin applies `custom_model_data` to the affected items. This allows a resource pack to apply a unique skin to these new items.
The custom model data is described below.

`10` - Normal ender bag  
`11` - Damaged ender bag  
`12` - Broken ender bag

I have a placeholder resource pack with a color shifted bundle. It is enough to give the ender bag a unique look. If anybody creates a better
resource pack and doesn't mind sharing, I would love to have a custom texture.

[EnderBag Resource Pack](http://mries.org/PUBLIC/EnderBag_Resource_Pack.zip)

### Permissions
`enderbag.item.use` - Allows the player to use the ender bag item  
`enderbag.command.use` - Allows the player to use the ender bag command  
`enderbag.give` - Allows the player to give an ender bag to themselves or other players