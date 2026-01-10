-- Example Lua Scripts for GUI Components
-- Location: data/mymod/triggerapi/gui/scripts/example_gui_scripts.lua

-- ============================================
-- PROGRESS BAR SCRIPTS
-- ============================================

-- Health progress (returns 0.0 to 1.0)
function getHealthProgress()
    return player.health / player.maxHealth
end

-- Experience progress
function getXpProgress()
    return player.xpProgress
end

-- Custom progress based on a calculation
function getCustomProgress()
    local level = player.level
    local maxLevel = 100
    return math.min(level / maxLevel, 1.0)
end

-- Time-based progress (returns current time as progress)
function getTimeProgress()
    -- This would need to be implemented with a custom time tracking system
    local currentTime = os.time() % 60 -- Seconds in current minute
    return currentTime / 60.0
end

-- ============================================
-- SLIDER SCRIPTS
-- ============================================

-- Volume change handler
function onVolumeChange()
    local volume = tonumber(ctx.value)
    player.sendMessage("Volume set to: " .. volume)
    
    -- You could store this in player data or apply to game settings
    -- Example: setGameVolume(player, volume)
end

-- Difficulty slider
function onDifficultySliderChange()
    local difficulty = math.floor(tonumber(ctx.value))
    
    if difficulty <= 25 then
        player.sendMessage("§aDifficulty: Easy")
    elseif difficulty <= 50 then
        player.sendMessage("§eDifficulty: Normal")
    elseif difficulty <= 75 then
        player.sendMessage("§6Difficulty: Hard")
    else
        player.sendMessage("§cDifficulty: EXTREME!")
    end
end

-- Range slider for distance
function onRangeChange()
    local range = tonumber(ctx.value)
    player.sendMessage("Range set to: " .. range .. " blocks")
end

-- ============================================
-- DROPDOWN SCRIPTS
-- ============================================

-- Difficulty selection
function onDifficultySelect()
    local index = tonumber(ctx.selectedIndex)
    local difficulties = {"Easy", "Normal", "Hard", "Extreme"}
    
    player.sendMessage("§bSelected difficulty: §f" .. difficulties[index + 1])
    
    -- Apply difficulty changes
    if index == 0 then
        -- Easy mode
        player.health = player.maxHealth
        player.sendMessage("§aHealth restored!")
    elseif index == 3 then
        -- Extreme mode
        player.sendMessage("§c§lGood luck!")
    end
end

-- Class selection
function onClassSelect()
    local index = tonumber(ctx.selectedIndex)
    local classes = {"Warrior", "Mage", "Rogue", "Cleric"}
    local class = classes[index + 1]
    
    player.sendMessage("§6You have selected: §f" .. class)
    
    -- Give class-specific items
    if class == "Warrior" then
        player.giveItem("minecraft:iron_sword", 1)
        player.giveItem("minecraft:iron_chestplate", 1)
    elseif class == "Mage" then
        player.giveItem("minecraft:stick", 1) -- Wand
        player.giveItem("minecraft:book", 5)
    elseif class == "Rogue" then
        player.giveItem("minecraft:iron_sword", 1)
        player.giveItem("minecraft:leather_chestplate", 1)
    elseif class == "Cleric" then
        player.giveItem("minecraft:golden_apple", 3)
        player.giveItem("minecraft:potion", 1)
    end
end

-- ============================================
-- SWITCH/TOGGLE SCRIPTS
-- ============================================

-- PVP toggle
function onPvpToggle()
    local enabled = ctx.state == "true"
    
    if enabled then
        player.sendMessage("§c§lPVP ENABLED")
        player.playSound("minecraft:block.note_block.pling", 1.0)
    else
        player.sendMessage("§a§lPVP DISABLED")
        player.playSound("minecraft:block.note_block.bass", 1.0)
    end
end

-- Flight mode toggle
function onFlightToggle()
    local enabled = ctx.state == "true"
    
    if enabled then
        player.sendMessage("§bFlight mode enabled!")
        -- Enable creative flight here
    else
        player.sendMessage("§7Flight mode disabled")
    end
end

-- God mode toggle
function onGodModeToggle()
    local enabled = ctx.state == "true"
    
    if enabled then
        player.sendMessage("§6§lGOD MODE ACTIVATED")
        player.health = player.maxHealth
    else
        player.sendMessage("§7God mode deactivated")
    end
end

-- ============================================
-- CHECKBOX SCRIPTS
-- ============================================

-- Terms acceptance
function onTermsToggle()
    local accepted = ctx.state == "true"
    
    if accepted then
        player.sendMessage("§aTerms & Conditions accepted")
    else
        player.sendMessage("§7Terms declined")
    end
end

-- Newsletter subscription
function onNewsletterToggle()
    local subscribed = ctx.state == "true"
    
    if subscribed then
        player.sendMessage("§bYou are now subscribed to the newsletter!")
    else
        player.sendMessage("§7Unsubscribed from newsletter")
    end
end

-- Multiple checkboxes example
function onFeatureToggle()
    local enabled = ctx.state == "true"
    local featureName = ctx.elementId
    
    if enabled then
        player.sendMessage("§aEnabled: " .. featureName)
    else
        player.sendMessage("§cDisabled: " .. featureName)
    end
end

-- ============================================
-- TEXT BOX SCRIPTS
-- ============================================

-- Text input handler
function onTextChange()
    local text = ctx.text
    player.sendMessage("§7Current text: §f" .. text)
    
    -- Validation example
    if string.len(text) < 3 then
        player.sendMessage("§eText must be at least 3 characters")
    end
end

-- Command input
function onCommandInput()
    local command = ctx.text
    
    if string.sub(command, 1, 1) == "/" then
        player.sendMessage("§aExecuting command: " .. command)
        -- Execute the command
    else
        player.sendMessage("§cCommands must start with /")
    end
end

-- Chat message
function onChatInput()
    local message = ctx.text
    
    if string.len(message) > 0 then
        player.sendMessage("§b" .. player.name .. "§f: " .. message)
    end
end

-- ============================================
-- COMPLEX INTERACTIONS
-- ============================================

-- Form submission with validation
function onFormSubmit()
    player.sendMessage("§a§lForm submitted successfully!")
    player.closeGui()
    
    -- Give reward
    player.giveItem("minecraft:diamond", 5)
    player.playSound("minecraft:entity.player.levelup", 1.0)
end

-- Settings save
function onSaveSettings()
    player.sendMessage("§aSettings saved!")
    player.playSound("minecraft:block.note_block.bell", 1.0)
    
    -- Save all settings to player data here
}

-- Reset to defaults
function onResetDefaults()
    player.sendMessage("§eSettings reset to defaults")
    
    -- Reset all GUI values
    player.openGui("mymod:settings_gui")
end

-- ============================================
-- ENTITY DISPLAY HELPER
-- ============================================

-- Change displayed entity based on selection
function updateEntityDisplay()
    local selection = tonumber(ctx.selectedIndex)
    
    -- This would require additional packet to update entity type
    -- For now, just show message
    local entities = {"Zombie", "Skeleton", "Creeper", "Spider"}
    player.sendMessage("§7Previewing: §f" .. entities[selection + 1])
end

-- ============================================
-- UTILITY FUNCTIONS
-- ============================================

-- Play feedback sound
function playFeedbackSound(soundType)
    if soundType == "success" then
        player.playSound("minecraft:entity.player.levelup", 1.0)
    elseif soundType == "error" then
        player.playSound("minecraft:block.note_block.bass", 1.0)
    else
        player.playSound("minecraft:ui.button.click", 1.0)
    end
end

-- Send formatted message
function sendFormattedMessage(message, color)
    local colorCode = color or "§f"
    player.sendMessage(colorCode .. message)
end