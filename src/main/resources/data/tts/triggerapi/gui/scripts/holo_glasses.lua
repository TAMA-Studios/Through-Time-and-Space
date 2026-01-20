print("Holo Glasses Script Running")

for key, value in pairs(mc.player.getInventory()) do
    print(key, "=", value)
end

mc.player.getInventory().getArmor(3).getItem()