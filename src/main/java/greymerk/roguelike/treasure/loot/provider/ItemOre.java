package greymerk.roguelike.treasure.loot.provider;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Loot;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ItemOre extends ItemBase{

	public ItemOre(int weight, int level) {
		super(weight, level);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		if(level < 2 && rand.nextInt(500) == 0){
			return ItemNovelty.getItem(ItemNovelty.MCGAMER);
		}
		return Loot.getSupplies(rand, level);
    }
}
