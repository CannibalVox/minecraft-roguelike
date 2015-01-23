package greymerk.roguelike.treasure.loot.provider;

import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.Quality;

import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ItemTool extends ItemBase {


	public ItemTool(int weight, int level) {
		super(weight, level);
	}
	
	@Override
	public ItemStack getLootItem(Random rand, int level) {
        if(level > 1 && rand.nextInt(500) == 0){
			return ItemNovelty.getItem(ItemNovelty.AMLP);
		}

        return Loot.getEquipment(rand, level);
	}
}
