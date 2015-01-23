package greymerk.roguelike.treasure.loot.provider;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Loot;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ItemSupply extends ItemBase{

	public ItemSupply(int weight, int level) {
		super(weight, level);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		return Loot.getSupplies(rand, level);
    }
}
