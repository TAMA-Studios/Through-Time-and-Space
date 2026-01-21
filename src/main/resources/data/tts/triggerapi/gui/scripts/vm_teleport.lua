function clamp(value, min, max)
    local result = tonumber(value)
    if result == nil then
        error("clamp(): value is not a number: " .. tostring(value))
    end

    if result > max then return max end
    if result < min then return min end
    return result
end

--for key, value in pairs(stack.getItem()) do
--    print(key, "=", value)
--end
local y = clamp(ctx.y, -64, 999999)
print("Y: " .. y)

mc.player.teleport(tonumber(ctx.x), y, tonumber(ctx.z))
--local stack = mc.player.getMainHandItem()
--useEnergy(stack)

mc.player.closeGui()

mc.player.sendMessage(
    "Teleported to " .. ctx.x .. ", " .. y .. ", " .. ctx.z
)

