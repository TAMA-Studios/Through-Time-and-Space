function clamp(value, min, max)
    local result = tonumber(value)

    if result > max then
        result = max
    end
    if result < min then
        result = min
    end

    return result
end

local y = ctx.y
y = clamp(y, -64, 999999)
mc.player.teleport(ctx.x, y, ctx.z)
mc.player.sendMessage('Teleported to ' .. ctx.x .. ', ' .. ctx.y .. ', ' .. ctx.z)
mc.player.closeGui()