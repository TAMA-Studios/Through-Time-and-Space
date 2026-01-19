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

    print("clamping")

    local maxDamage = tonumber(stack.getMaxDamage())
    print("damage: " .. maxDamage)
    if not maxDamage then
        error("getMaxDamage() returned non-number")
    end

    local clampedEnergy = clamp(maxDamage - 100, 0, 1000)

    print("using")
    stack.setDamageValue(maxDamage - clampedEnergy)
    print("used")
end

local y = clamp(ctx.y, -64, 999999)
mc.player.teleport(ctx.x, y, ctx.z)

local stack = mc.player.getInventory().mainhand
useEnergy(stack)

mc.player.sendMessage(
    "Teleported to " .. ctx.x .. ", " .. y .. ", " .. ctx.z
)

mc.player.closeGui()
