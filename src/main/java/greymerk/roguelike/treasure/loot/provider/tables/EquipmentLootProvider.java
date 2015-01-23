package greymerk.roguelike.treasure.loot.provider.tables;

import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.provider.ItemBase;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class EquipmentLootProvider extends ItemBase {
    public EquipmentLootProvider(int weight, int level) {
        super(weight, level);
    }

    @Override
    public ItemStack getLootItem(Random rand, int level) {
        return Loot.getEquipment(rand, level);
    }
}
