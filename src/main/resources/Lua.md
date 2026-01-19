__# Custom GUI System Documentation for Datapack Creators

## Table of Contents
1. [Introduction](#introduction)
2. [GUI Types](#gui-types)
3. [File Structure](#file-structure)
4. [Container GUIs](#container-guis)
5. [Custom GUIs](#custom-guis)
6. [GUI Elements](#gui-elements)
7. [Lua Scripting](#lua-scripting)
8. [Context Management](#context-management)
9. [Complete Examples](#complete-examples)

---

## Introduction

This custom GUI system allows datapack creators to build interactive interfaces using JSON definitions and Lua scripting. GUIs can range from simple chest-like containers to complex custom interfaces with buttons, sliders, text boxes, and more.

**Key Features:**
- Two GUI types: Container (chest-like) and Custom (fully customizable)
- Rich element library (buttons, text, images, items, sliders, progress bars, entities, text boxes, dropdowns, switches, checkboxes)
- Lua scripting for dynamic behavior
- Persistent context across button clicks
- Client-server synchronization

---

## GUI Types

### Container GUI
Traditional chest-like inventory interfaces (9-54 slots). Best for:
- Item selection menus
- Shop interfaces
- Simple button grids

### Custom GUI
Pixel-perfect custom interfaces with unlimited flexibility. Best for:
- Complex dashboards
- Settings panels
- Interactive displays
- Mini-games

---

## File Structure

```
data/
└── your_namespace/
    └── triggerapi/
        └── gui/
            ├── my_gui.json          # GUI definition
            └── scripts/
                └── my_script.lua    # Lua scripts
```

**Opening a GUI from Lua:**
```lua
mc.player.openGui("your_namespace:my_gui")
```

---

## Container GUIs

Container GUIs use a traditional chest inventory layout.

### Basic Structure

```json
{
  "type": "container",
  "title": "My Container",
  "size": 27,
  "background": {
    "item": "minecraft:gray_stained_glass_pane",
    "fill_empty": true
  },
  "container_buttons": [
    {
      "slot": 13,
      "item": "minecraft:diamond",
      "name": "§bClick Me!",
      "lore": [
        "§7This is a button",
        "§7Click to execute script"
      ],
      "glow": true,
      "script": "mc.player.sendMessage('Button clicked!')",
      "close_on_click": false
    }
  ]
}
```

### Container Properties

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `type` | String | `"custom"` | Set to `"container"` for chest GUIs |
| `title` | String | `"GUI"` | Title displayed at top of container |
| `size` | Integer | `27` | Number of slots (must be multiple of 9, max 54) |
| `background` | Object | `null` | Background item configuration |
| `container_buttons` | Array | `[]` | List of button definitions |

### Background Object

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `item` | String | `null` | Item ID to use as background |
| `fill_empty` | Boolean | `false` | Fill all empty slots with background item |

### Container Button Definition

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `slot` | Integer | **Required** | Slot index (0-53) |
| `item` | String | **Required** | Item ID to display |
| `name` | String | `null` | Display name (supports § color codes) |
| `lore` | String Array | `[]` | Lore lines (supports § color codes) |
| `glow` | Boolean | `false` | Add enchantment glint effect |
| `script` | String | `null` | Lua script or script file reference |
| `close_on_click` | Boolean | `false` | Close GUI when clicked |

### Script References

Scripts can be **inline** or **file references**:

**Inline:**
```json
"script": "mc.player.sendMessage('Hello!')"
```

**File reference** (ends with `.lua`):
```json
"script": "your_namespace:button_handler.lua"
```

File location: `data/your_namespace/triggerapi/gui/scripts/button_handler.lua`

---

## Custom GUIs

Custom GUIs provide pixel-perfect control over layout and appearance.

### Basic Structure

```json
{
  "type": "custom",
  "title": "My Custom GUI",
  "width": 176,
  "height": 166,
  "background_texture": "your_namespace:textures/gui/my_background.png",
  "elements": [
    {
      "type": "button",
      "id": "my_button",
      "x": 10,
      "y": 20,
      "width": 100,
      "height": 20,
      "texture": "minecraft:textures/gui/widgets.png",
      "texture_x": 0,
      "texture_y": 66,
      "hover_texture_y": 86,
      "script": "mc.player.sendMessage('Custom button clicked!')"
    }
  ]
}
```

### Custom GUI Properties

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `type` | String | `"custom"` | Must be `"custom"` |
| `title` | String | `"GUI"` | Window title |
| `width` | Integer | `176` | GUI width in pixels |
| `height` | Integer | `166` | GUI height in pixels |
| `background_texture` | String | `null` | Background texture path |
| `elements` | Array | `[]` | List of GUI elements |

---

## GUI Elements

### Common Element Properties

All elements share these base properties:

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `type` | String | **Required** | Element type (see below) |
| `id` | String | **Required** | Unique identifier for this element |
| `x` | Integer | **Required** | X position (pixels from left) |
| `y` | Integer | **Required** | Y position (pixels from top) |
| `width` | Integer | **Required** | Element width in pixels |
| `height` | Integer | **Required** | Element height in pixels |
| `tooltip` | String Array | `[]` | Tooltip lines shown on hover |

---

### 1. Button

Interactive clickable button.

```json
{
  "type": "button",
  "id": "my_button",
  "x": 10,
  "y": 20,
  "width": 100,
  "height": 20,
  "texture": "minecraft:textures/gui/widgets.png",
  "texture_x": 0,
  "texture_y": 66,
  "hover_texture_y": 86,
  "script": "mc.player.sendMessage('Clicked!')",
  "tooltip": ["Click me!", "I'm a button"]
}
```

**Additional Properties:**

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `texture` | String | `null` | Texture resource location |
| `texture_x` | Integer | `0` | X offset in texture |
| `texture_y` | Integer | `0` | Y offset in texture (normal state) |
| `hover_texture_y` | Integer | `0` | Y offset in texture (hover state) |
| `image_width` | Integer | `256` | Total texture width |
| `image_height` | Integer | `256` | Total texture height |
| `script` | String | `null` | Script executed on click |

**Script Context Variables:**
- `guiId`: The GUI resource location
- `button`: Mouse button (0=left, 1=right, 2=middle)

---

### 2. Text

Static or dynamic text display.

```json
{
  "type": "text",
  "id": "label",
  "x": 10,
  "y": 10,
  "width": 100,
  "height": 10,
  "text": "§6Hello World!",
  "color": 0x404040,
  "shadow": true,
  "scale": 1.5
}
```

**Additional Properties:**

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `text` | String | **Required** | Text to display (supports § colors) |
| `color` | Integer (Hex) | `0x404040` | Text color (ARGB format) |
| `shadow` | Boolean | `false` | Enable text shadow |
| `scale` | Float | `1.0` | Text scale multiplier |

---

### 3. Image

Static image display.

```json
{
  "type": "image",
  "id": "logo",
  "x": 50,
  "y": 10,
  "width": 64,
  "height": 64,
  "texture": "your_namespace:textures/gui/logo.png",
  "texture_x": 0,
  "texture_y": 0,
  "image_width": 256,
  "image_height": 256
}
```

**Additional Properties:**

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `texture` | String | **Required** | Texture resource location |
| `texture_x` | Integer | `0` | X offset in texture |
| `texture_y` | Integer | `0` | Y offset in texture |
| `image_width` | Integer | `256` | Total texture width |
| `image_height` | Integer | `256` | Total texture height |

---

### 4. Item

Display a Minecraft item.

```json
{
  "type": "item",
  "id": "display_item",
  "x": 80,
  "y": 20,
  "width": 16,
  "height": 16,
  "item": "minecraft:diamond_sword",
  "tooltip": ["§bDiamond Sword", "§7A powerful weapon"]
}
```

**Additional Properties:**

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `item` | String | **Required** | Item resource location |

---

### 5. Progress Bar

Displays progress as a fillable bar (updated via Lua).

```json
{
  "type": "progress_bar",
  "id": "health_bar",
  "x": 10,
  "y": 50,
  "width": 100,
  "height": 10,
  "bar_color": 0xFF00FF00,
  "background_color": 0xFF808080,
  "border_color": 0xFF000000,
  "show_percentage": true,
  "vertical": false,
  "progress_script": "your_namespace:get_health.lua"
}
```

**Additional Properties:**

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `bar_color` | Integer (Hex) | `0xFF00FF00` | Fill color (ARGB) |
| `background_color` | Integer (Hex) | `0xFF808080` | Background color (ARGB) |
| `border_color` | Integer (Hex) | `0xFF000000` | Border color (ARGB) |
| `show_percentage` | Boolean | `false` | Display percentage text |
| `vertical` | Boolean | `false` | Vertical orientation |
| `progress_script` | String | `null` | Script that returns progress value |

**Progress Script Example:**
```lua
-- Must return a value between 0.0 and 1.0
return mc.player.health / mc.player.maxHealth
```

**Note:** Progress bars automatically request updates every 50ms via the `progress_script`.

---

### 6. Slider

Interactive slider for numeric input.

```json
{
  "type": "slider",
  "id": "volume_slider",
  "x": 10,
  "y": 70,
  "width": 100,
  "height": 10,
  "min_value": 0.0,
  "max_value": 100.0,
  "default_value": 50.0,
  "step": 1.0,
  "slider_color": 0xFFFFFFFF,
  "handle_color": 0xFF8B8B8B,
  "show_value": true,
  "on_change_script": "your_namespace:volume_changed.lua"
}
```

**Additional Properties:**

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `min_value` | Float | `0.0` | Minimum value |
| `max_value` | Float | `100.0` | Maximum value |
| `default_value` | Float | `50.0` | Starting value |
| `step` | Float | `1.0` | Value increment (0 = continuous) |
| `slider_color` | Integer (Hex) | `0xFFFFFFFF` | Track color (ARGB) |
| `handle_color` | Integer (Hex) | `0xFF8B8B8B` | Handle color (ARGB) |
| `text_color` | Integer (Hex) | `0xFFFFFFFF` | Value text color |
| `show_value` | Boolean | `true` | Display current value |
| `on_change_script` | String | `null` | Script called when value changes |

**Script Context Variables:**
- `ctx.value`: Current slider value (string)

---

### 7. Entity

Display a 3D rendered entity.

```json
{
  "type": "entity",
  "id": "zombie_display",
  "x": 80,
  "y": 50,
  "width": 50,
  "height": 80,
  "entity_type": "minecraft:zombie",
  "entity_scale": 30.0,
  "rotate_entity": true,
  "entity_nbt": "{CustomName:'{\"text\":\"Bob\"}'}"
}
```

**Additional Properties:**

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `entity_type` | String | **Required** | Entity resource location |
| `entity_scale` | Float | `30.0` | Render scale |
| `rotate_entity` | Boolean | `true` | Auto-rotate (360° every 3.6s) |
| `entity_nbt` | String | `null` | NBT data (JSON format) |

---

### 8. Text Box

Editable text input field.

```json
{
  "type": "text_box",
  "id": "username_input",
  "x": 10,
  "y": 90,
  "width": 150,
  "height": 20,
  "max_length": 32,
  "hint_text": "Enter username...",
  "text_color": 0xFFFFFFFF,
  "hint_color": 0xFF808080,
  "multiline": false,
  "on_text_change_script": "your_namespace:name_changed.lua"
}
```

**Additional Properties:**

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `max_length` | Integer | `32` | Maximum character count |
| `hint_text` | String | `null` | Placeholder text |
| `text_color` | Integer (Hex) | `0xFFFFFFFF` | Text color (ARGB) |
| `hint_color` | Integer (Hex) | `0xFF808080` | Hint text color (ARGB) |
| `multiline` | Boolean | `false` | Allow multiple lines (4x max_length) |
| `on_text_change_script` | String | `null` | Script called when text changes |

**Script Context Variables:**
- `ctx.value`: Current text value (string)

**Reading Text Box Value:**
```lua
-- In any script after text box value has been set
local username = ctx.username_input_value
```

---

### 9. Dropdown

Dropdown selection menu.

```json
{
  "type": "dropdown",
  "id": "difficulty_select",
  "x": 10,
  "y": 110,
  "width": 120,
  "height": 20,
  "options": ["Easy", "Normal", "Hard", "Peaceful"],
  "default_option": 1,
  "dropdown_color": 0xFFFFFFFF,
  "selected_color": 0xFF00FF00,
  "text_color": 0xFF000000,
  "on_select_script": "your_namespace:difficulty_changed.lua"
}
```

**Additional Properties:**

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `options` | String Array | **Required** | List of selectable options |
| `default_option` | Integer | `0` | Default selected index |
| `dropdown_color` | Integer (Hex) | `0xFFFFFFFF` | Dropdown background (ARGB) |
| `selected_color` | Integer (Hex) | `0xFF00FF00` | Selected option highlight (ARGB) |
| `text_color` | Integer (Hex) | `0xFF000000` | Text color (ARGB) |
| `on_select_script` | String | `null` | Script called when selection changes |

**Script Context Variables:**
- `ctx.value`: Selected option index (string)

---

### 10. Switch

Toggle switch (on/off).

```json
{
  "type": "switch",
  "id": "enable_feature",
  "x": 10,
  "y": 130,
  "width": 40,
  "height": 20,
  "default_state": false,
  "on_color": 0xFF00FF00,
  "off_color": 0xFFFF0000,
  "on_toggle_script": "your_namespace:feature_toggled.lua"
}
```

**Additional Properties:**

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `default_state` | Boolean | `false` | Starting state (true=on, false=off) |
| `on_color` | Integer (Hex) | `0xFF00FF00` | Color when enabled (ARGB) |
| `off_color` | Integer (Hex) | `0xFFFF0000` | Color when disabled (ARGB) |
| `on_toggle_script` | String | `null` | Script called when toggled |

**Script Context Variables:**
- `ctx.value`: Current state (string: "true" or "false")

---

### 11. Checkbox

Checkbox with optional label.

```json
{
  "type": "checkbox",
  "id": "accept_terms",
  "x": 10,
  "y": 150,
  "width": 16,
  "height": 16,
  "default_state": false,
  "check_color": 0xFF00FF00,
  "label": "I accept the terms",
  "label_color": 0xFF404040,
  "checked_texture": "your_namespace:textures/gui/checkmark.png",
  "on_toggle_script": "your_namespace:terms_toggled.lua"
}
```

**Additional Properties:**

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `default_state` | Boolean | `false` | Starting state (true=checked) |
| `check_color` | Integer (Hex) | `0xFF00FF00` | Checkmark color (ARGB) |
| `label` | String | `null` | Text label next to checkbox |
| `label_color` | Integer (Hex) | `0xFF404040` | Label text color (ARGB) |
| `checked_texture` | String | `null` | Custom checkmark texture |
| `unchecked_texture` | String | `null` | Custom unchecked texture |
| `on_toggle_script` | String | `null` | Script called when toggled |

**Script Context Variables:**
- `ctx.value`: Current state (string: "true" or "false")

---

## Lua Scripting

### Available Lua Environment

When scripts execute, they have access to:

1. **Standard Lua libraries:** Base, Package, String, Table, Math
2. **`mc` table:** Minecraft game access (see Lua API documentation in Document 11)
3. **`ctx` table:** Persistent context storage (shared across script calls)
4. **`util` table:** Utility functions

### Context Variables

The `ctx` table persists data across button clicks and element interactions within the same GUI session.

**Setting context values:**
```lua
ctx.player_choice = "option_a"
ctx.counter = 5
ctx.has_completed = true
```

**Reading context values:**
```lua
if ctx.counter then
  ctx.counter = ctx.counter + 1
else
  ctx.counter = 1
end
mc.player.sendMessage("Count: " .. ctx.counter)
```

**Pre-populated context variables:**
- `guiId`: Current GUI resource location (string)
- Element-specific variables based on interaction type

### Script Execution Flow

1. **Container buttons:** Execute when slot is clicked
2. **Custom GUI buttons:** Execute when clicked
3. **Sliders:** Execute `on_change_script` when value changes
4. **Progress bars:** Execute `progress_script` every 50ms to get current value
5. **Text boxes:** Execute `on_text_change_script` when text changes
6. **Dropdowns:** Execute `on_select_script` when selection changes
7. **Switches/Checkboxes:** Execute `on_toggle_script` when toggled

### Common Lua Patterns

**Opening another GUI:**
```lua
mc.player.openGui("your_namespace:next_gui")
```

**Closing current GUI:**
```lua
mc.player.closeGui()
```

**Giving items:**
```lua
mc.player.giveItem("minecraft:diamond", 64)
```

**Teleporting:**
```lua
mc.player.teleport(100, 64, 200)
```

**Broadcasting messages:**
```lua
mc.util.broadcast("§6Server Announcement!")
```

**Executing commands:**
```lua
mc.util.executeCommand("time set day")
```

**Multi-step workflow:**
```lua
-- First button
if not ctx.step then
  ctx.step = 1
  mc.player.sendMessage("Step 1: Complete task A")
end

-- Second button
if ctx.step == 1 then
  ctx.step = 2
  mc.player.sendMessage("Step 2: Complete task B")
  mc.player.giveItem("minecraft:diamond", 1)
end

-- Third button
if ctx.step == 2 then
  mc.player.sendMessage("All steps complete!")
  mc.player.closeGui()
end
```

---

## Context Management

### Server-Side Context

For **container GUIs**, context is managed entirely on the server and persists across button clicks automatically.

### Client-Server Synchronization

For **custom GUIs**, context is synchronized between client and server:

1. Server creates/retrieves context when GUI opens
2. Server sends `SyncContextPacket` to client with current context
3. Client updates element states and executes scripts
4. Client sends `GuiStateUpdatePacket` when elements change
5. Server executes scripts and updates shared context
6. Context is cleaned up when GUI closes

### Context Lifecycle

**Creation:**
```java
// Automatically created when GUI opens
GuiContextManager.getGuiContext(player, guiId)
```

**Cleanup:**
```java
// Automatically cleaned when:
// 1. Player closes GUI
// 2. Player logs out
GuiContextManager.closeGui(player, guiId)
GuiContextManager.cleanupPlayer(player)
```

---

## Complete Examples

### Example 1: Simple Shop (Container)

**File:** `data/myshop/triggerapi/gui/weapon_shop.json`

```json
{
  "type": "container",
  "title": "§6Weapon Shop",
  "size": 27,
  "background": {
    "item": "minecraft:black_stained_glass_pane",
    "fill_empty": true
  },
  "container_buttons": [
    {
      "slot": 11,
      "item": "minecraft:wooden_sword",
      "name": "§fWooden Sword",
      "lore": ["§7Price: §e10 Gold", "§aClick to purchase"],
      "script": "myshop:buy_item.lua",
      "close_on_click": false
    },
    {
      "slot": 13,
      "item": "minecraft:iron_sword",
      "name": "§fIron Sword",
      "lore": ["§7Price: §e50 Gold", "§aClick to purchase"],
      "glow": true,
      "script": "myshop:buy_item.lua",
      "close_on_click": false
    },
    {
      "slot": 15,
      "item": "minecraft:diamond_sword",
      "name": "§bDiamond Sword",
      "lore": ["§7Price: §e200 Gold", "§aClick to purchase"],
      "glow": true,
      "script": "myshop:buy_item.lua",
      "close_on_click": false
    }
  ]
}
```

**File:** `data/myshop/triggerapi/gui/scripts/buy_item.lua`

```lua
-- Get item name from clicked slot
local items = {
  [11] = {name = "wooden_sword", price = 10},
  [13] = {name = "iron_sword", price = 50},
  [15] = {name = "diamond_sword", price = 200}
}

local item_data = items[tonumber(ctx.slot)]

if item_data then
  -- Check if player has enough money (stored in player NBT or similar)
  -- For this example, we'll just give the item
  
  mc.player.giveItem("minecraft:" .. item_data.name, 1)
  mc.player.sendMessage("§aPurchased " .. item_data.name .. " for " .. item_data.price .. " gold!")
  mc.player.playSound("minecraft:entity.experience_orb.pickup", 1.0)
else
  mc.player.sendMessage("§cInvalid item!")
end
```

---

### Example 2: Settings Panel (Custom)

**File:** `data/mymod/triggerapi/gui/settings.json`

```json
{
  "type": "custom",
  "title": "§6Game Settings",
  "width": 200,
  "height": 180,
  "background_texture": "minecraft:textures/gui/demo_background.png",
  "elements": [
    {
      "type": "text",
      "id": "title",
      "x": 50,
      "y": 10,
      "width": 100,
      "height": 10,
      "text": "§l§6Settings",
      "color": 0xFFFFFF,
      "shadow": true,
      "scale": 1.5
    },
    {
      "type": "text",
      "id": "volume_label",
      "x": 10,
      "y": 35,
      "width": 50,
      "height": 10,
      "text": "Volume:",
      "color": 0x404040
    },
    {
      "type": "slider",
      "id": "volume_slider",
      "x": 70,
      "y": 30,
      "width": 100,
      "height": 10,
      "min_value": 0.0,
      "max_value": 100.0,
      "default_value": 50.0,
      "step": 5.0,
      "show_value": true,
      "on_change_script": "ctx.volume = ctx.value; mc.player.sendMessage('Volume: ' .. ctx.value)"
    },
    {
      "type": "text",
      "id": "difficulty_label",
      "x": 10,
      "y": 60,
      "width": 60,
      "height": 10,
      "text": "Difficulty:",
      "color": 0x404040
    },
    {
      "type": "dropdown",
      "id": "difficulty_select",
      "x": 70,
      "y": 55,
      "width": 100,
      "height": 20,
      "options": ["Peaceful", "Easy", "Normal", "Hard"],
      "default_option": 2,
      "on_select_script": "mymod:set_difficulty.lua"
    },
    {
      "type": "checkbox",
      "id": "particles",
      "x": 10,
      "y": 90,
      "width": 16,
      "height": 16,
      "default_state": true,
      "label": "Enable Particles",
      "label_color": 0x404040,
      "on_toggle_script": "ctx.particles_enabled = ctx.value"
    },
    {
      "type": "checkbox",
      "id": "sounds",
      "x": 10,
      "y": 115,
      "width": 16,
      "height": 16,
      "default_state": true,
      "label": "Enable Sounds",
      "label_color": 0x404040,
      "on_toggle_script": "ctx.sounds_enabled = ctx.value"
    },
    {
      "type": "button",
      "id": "save_button",
      "x": 50,
      "y": 150,
      "width": 100,
      "height": 20,
      "texture": "minecraft:textures/gui/widgets.png",
      "texture_x": 0,
      "texture_y": 66,
      "hover_texture_y": 86,
      "script": "mymod:save_settings.lua"
    }
  ]
}
```

**File:** `data/mymod/triggerapi/gui/scripts/save_settings.lua`

```lua
mc.player.sendMessage("§aSettings saved!")
mc.player.sendMessage("Volume: " .. (ctx.volume or "50"))
mc.player.sendMessage("Particles: " .. (ctx.particles_enabled or "true"))
mc.player.sendMessage("Sounds: " .. (ctx.sounds_enabled or "true"))
mc.player.closeGui()
```

---

### Example 3: Player Stats Dashboard (Custom with Progress Bars)

**File:** `data/stats/triggerapi/gui/dashboard.json`

```json
{
  "type": "custom",
  "title": "§bPlayer Statistics",
  "width": 220,
  "height": 200,
  "elements": [
    {
      "type": "text",
      "id": "health_label",
      "x": 10,
      "y": 20,
      "width": 50,
      "height": 10,
      "text": "§cHealth:",
      "color": 0xFF0000
    },
    {
      "type": "progress_bar",
      "id": "health_bar",
      "x": 70,
      "y": 15,
      "width": 130,
      "height": 15,
      "bar_color": 0xFFFF0000,
      "background_color": 0xFF404040,
      "border_color": 0xFF000000,
      "show_percentage": true,
      "progress_script": "return mc.player.health / mc.player.maxHealth"
    },
    {
      "type": "text",
      "id": "food_label",
      "x": 10,
      "y": 50,
      "width": 50,
      "height": 10,
      "text": "§6Food:",
      "color": 0xFFAA00
    },
    {
      "type": "progress_bar",
      "id": "food_bar",
      "x": 70,
      "y": 45,
      "width": 130,
      "height": 15,
      "bar_color": 0xFFFFAA00,
      "background_color": 0xFF404040,
      "border_color": 0xFF000000,
      "show_percentage": true
,
      "progress_script": "return mc.player.foodLevel / 20.0"
    },
    {
      "type": "text",
      "id": "xp_label",
      "x": 10,
      "y": 80,
      "width": 50,
      "height": 10,
      "text": "§aXP:",
      "color": 0x00FF00
    },
    {
      "type": "progress_bar",
      "id": "xp_bar",
      "x": 70,
      "y": 75,
      "width": 130,
      "height": 15,
      "bar_color": 0xFF00FF00,
      "background_color": 0xFF404040,
      "border_color": 0xFF000000,
      "show_percentage": true,
      "progress_script": "return mc.player.xpProgress"
    },
    {
      "type": "text",
      "id": "level_display",
      "x": 10,
      "y": 110,
      "width": 200,
      "height": 10,
      "text": "§eLevel: " .. tostring(mc.player.level),
      "color": 0xFFFF00,
      "scale": 1.2
    },
    {
      "type": "entity",
      "id": "player_model",
      "x": 80,
      "y": 120,
      "width": 60,
      "height": 80,
      "entity_type": "minecraft:player",
      "entity_scale": 25.0,
      "rotate_entity": true
    }
  ]
}
```

---

### Example 4: Quest Tracker (Multi-step with Context)

**File:** `data/quests/triggerapi/gui/main_quest.json`

```json
{
  "type": "custom",
  "title": "§5Main Quest",
  "width": 250,
  "height": 220,
  "elements": [
    {
      "type": "text",
      "id": "quest_title",
      "x": 60,
      "y": 15,
      "width": 130,
      "height": 10,
      "text": "§l§5The Hero's Journey",
      "scale": 1.5,
      "shadow": true
    },
    {
      "type": "text",
      "id": "step_1_text",
      "x": 20,
      "y": 50,
      "width": 150,
      "height": 10,
      "text": "1. Collect 10 Diamonds",
      "color": 0x808080
    },
    {
      "type": "checkbox",
      "id": "step_1_check",
      "x": 180,
      "y": 48,
      "width": 16,
      "height": 16,
      "default_state": false,
      "check_color": 0xFF00FF00
    },
    {
      "type": "text",
      "id": "step_2_text",
      "x": 20,
      "y": 75,
      "width": 150,
      "height": 10,
      "text": "2. Defeat the Dragon",
      "color": 0x808080
    },
    {
      "type": "checkbox",
      "id": "step_2_check",
      "x": 180,
      "y": 73,
      "width": 16,
      "height": 16,
      "default_state": false,
      "check_color": 0xFF00FF00
    },
    {
      "type": "text",
      "id": "step_3_text",
      "x": 20,
      "y": 100,
      "width": 150,
      "height": 10,
      "text": "3. Find the Ancient Temple",
      "color": 0x808080
    },
    {
      "type": "checkbox",
      "id": "step_3_check",
      "x": 180,
      "y": 98,
      "width": 16,
      "height": 16,
      "default_state": false,
      "check_color": 0xFF00FF00
    },
    {
      "type": "progress_bar",
      "id": "overall_progress",
      "x": 25,
      "y": 140,
      "width": 200,
      "height": 20,
      "bar_color": 0xFF9900FF,
      "background_color": 0xFF404040,
      "border_color": 0xFF000000,
      "show_percentage": true,
      "progress_script": "quests:calculate_progress.lua"
    },
    {
      "type": "button",
      "id": "claim_reward",
      "x": 75,
      "y": 180,
      "width": 100,
      "height": 20,
      "texture": "minecraft:textures/gui/widgets.png",
      "texture_x": 0,
      "texture_y": 66,
      "hover_texture_y": 86,
      "script": "quests:claim_reward.lua"
    }
  ]
}
```

**File:** `data/quests/triggerapi/gui/scripts/calculate_progress.lua`

```lua
-- Count completed steps from context
local completed = 0
if ctx.step_1_check_value == "true" then completed = completed + 1 end
if ctx.step_2_check_value == "true" then completed = completed + 1 end
if ctx.step_3_check_value == "true" then completed = completed + 1 end

return completed / 3.0
```

**File:** `data/quests/triggerapi/gui/scripts/claim_reward.lua`

```lua
-- Check if all steps are complete
if ctx.step_1_check_value == "true" and 
   ctx.step_2_check_value == "true" and 
   ctx.step_3_check_value == "true" then
  
  mc.player.sendMessage("§a§lQUEST COMPLETE!")
  mc.player.giveItem("minecraft:nether_star", 1)
  mc.player.giveItem("minecraft:diamond", 64)
  mc.player.playSound("minecraft:ui.toast.challenge_complete", 1.0)
  mc.player.closeGui()
else
  mc.player.sendMessage("§cComplete all quest steps first!")
end
```

---

## Best Practices

### 1. GUI Design
- Keep GUIs focused on a single purpose
- Use consistent color schemes (ARGB hex format: 0xAARRGGBB)
- Provide clear visual feedback for interactive elements
- Include tooltips for complex elements
- Test at different GUI scales

### 2. Scripting
- Keep scripts simple and focused
- Use context (`ctx`) for persistent data
- Validate user input before executing actions
- Provide clear error messages
- Use file references for complex scripts

### 3. Performance
- Minimize progress bar update frequency (already limited to 50ms)
- Avoid heavy computations in frequently-called scripts
- Cache values in context when possible
- Close GUIs when no longer needed

### 4. User Experience
- Show progress indicators for multi-step processes
- Confirm destructive actions
- Provide escape routes (close buttons)
- Use sounds for feedback
- Display helpful error messages

### 5. Debugging
- Use `mc.util.print()` to log to console
- Check server logs for script errors
- Test with multiple players for container GUIs
- Verify context persistence across interactions

---

## Color Reference (ARGB Hex)

Common colors in `0xAARRGGBB` format:

| Color | Hex Code | Description |
|-------|----------|-------------|
| White | `0xFFFFFFFF` | Fully opaque white |
| Black | `0xFF000000` | Fully opaque black |
| Red | `0xFFFF0000` | Fully opaque red |
| Green | `0xFF00FF00` | Fully opaque green |
| Blue | `0xFF0000FF` | Fully opaque blue |
| Yellow | `0xFFFFFF00` | Fully opaque yellow |
| Purple | `0xFF9900FF` | Fully opaque purple |
| Orange | `0xFFFFAA00` | Fully opaque orange |
| Gray | `0xFF808080` | Fully opaque gray |
| Dark Gray | `0xFF404040` | Fully opaque dark gray |
| Light Gray | `0xFFCCCCCC` | Fully opaque light gray |
| Transparent | `0x00000000` | Fully transparent |
| Semi-transparent Black | `0x80000000` | 50% transparent black |

**Format breakdown:**
- `AA` = Alpha (transparency): `00` = invisible, `FF` = opaque
- `RR` = Red component: `00` to `FF`
- `GG` = Green component: `00` to `FF`
- `BB` = Blue component: `00` to `FF`

---

## Troubleshooting

### GUI doesn't appear
- Verify JSON syntax (use a validator)
- Check file location: `data/namespace/triggerapi/gui/filename.json`
- Ensure namespace matches your datapack
- Look for errors in server logs

### Scripts don't execute
- Check script file exists: `data/namespace/triggerapi/gui/scripts/filename.lua`
- Verify script reference format (inline vs file)
- Check server logs for Lua errors
- Ensure script has correct syntax

### Progress bars don't update
- Verify `progress_script` returns a number between 0.0 and 1.0
- Check script syntax
- Progress updates every 50ms automatically

### Context not persisting
- Ensure you're setting values in `ctx` table
- Don't use reserved names (`guiId`, `slot`, `button`, `clickType`, `value`)
- Context is cleared when GUI closes

### Elements misaligned
- Verify x, y, width, height values
- Check GUI dimensions (width, height)
- Remember: coordinates are pixels from top-left (0, 0)

---

## Additional Resources

- **Lua API Documentation:** See Document 11 for complete `mc` table reference
- **Minecraft Textures:** `minecraft:textures/gui/` for vanilla textures
- **Color Codes:** Use `§` followed by color code (0-9, a-f) for text formatting

---

**Happy GUI Creating!**__