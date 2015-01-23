package greymerk.roguelike.treasure.loot.provider;

import java.util.Random;

import greymerk.roguelike.treasure.loot.Loot;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ItemBlock extends ItemBase{
	
	public ItemBlock(int weight, int level) {
		super(weight, level);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		return getRandom(rand, level);
	}
	
	public static ItemStack getRandom(Random rand, int level){
		
		if(level > 0 && rand.nextInt(500) == 0){
			if(rand.nextBoolean()){
				return ItemNovelty.getItem(ItemNovelty.MMILLSS);
			} else {
				return ItemNovelty.getItem(ItemNovelty.QUANTUMLEAP);
			}
		}
		
		return Loot.getEquipment(rand, level);
	}
}
