package greymerk.roguelike.treasure.loot.provider;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Loot;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ItemFood extends ItemBase{

	public ItemFood(int weight, int level) {
		super(weight, level);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {

		ItemNovelty[] items = {
				ItemNovelty.GINGER,
				ItemNovelty.CLEO
		};
		
		if(level > 0 && rand.nextInt(500) == 0){
			return ItemNovelty.getItem(items[rand.nextInt(items.length)]);
		}
		
		return Loot.getSupplies(rand, level);
	}
}
