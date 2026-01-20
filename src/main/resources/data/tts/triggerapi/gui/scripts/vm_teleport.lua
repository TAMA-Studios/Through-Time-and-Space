function clamp(value, min, max)
    local result = tonumber(value)
    if result == nil then
        error("clamp(): value is not a number: " .. tostring(value))
    end

    if result > max then return max end
    if result < min then return min end
    return result
end

function useEnergy(stack)
    if stack == nil then
        print("No item in main hand")
        return
    end

    print("Listing")
    --for key, value in pairs(stack.getItem()) do
    --    print(key, "=", value)
    --end
    print("Getting damage")
    local maxDamage = tonumber(stack.getMaxDamage())
    print("damage: " .. maxDamage)
    if not maxDamage then
        error("getMaxDamage() returned non-number")
    end

    print("using")

    stack.item.consumeEnergy(stack, 100)
    print("used")
end

local y = clamp(ctx.y, -64, 999999)

mc.player.teleportTo(ctx.x, y, ctx.z)

local stack = mc.player.getMainHandItem()
useEnergy(stack)

mc.player.sendMessage(
    "Teleported to " .. ctx.x .. ", " .. y .. ", " .. ctx.z
)

mc.player.closeGui()
