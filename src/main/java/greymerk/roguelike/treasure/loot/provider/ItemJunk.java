package greymerk.roguelike.treasure.loot.provider;

import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.PotionMixture;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ChestGenHooks;

public class ItemJunk extends ItemBase{

	public ItemJunk(int weight, int level) {
		super(weight, level);
	}

	@Override
	public ItemStack getLootItem(Random rand, int level){
		if(rand.nextInt(500) == 0){
			if(rand.nextBoolean()) return ItemNovelty.getItem(ItemNovelty.VECHS);

			switch(level){
			case 0: return ItemNovelty.getItem(ItemNovelty.GRIM);
			case 1: return ItemNovelty.getItem(ItemNovelty.FOURLES);
			case 2: return ItemNovelty.getItem(ItemNovelty.ZISTEAUSIGN);
			case 3: return ItemNovelty.getItem(ItemNovelty.PAULSOARESJR);
			case 4: return ItemNovelty.getItem(ItemNovelty.DINNERBONE);
			}
		}

		if(level > 0 && rand.nextInt(20 / (1 + level)) == 0){
			
			if(level == 4 && rand.nextInt(10) == 0){
				ChestGenHooks hook = rand.nextBoolean() ?
						ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST):
						ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST);
				ItemStack toReturn = hook.getOneItem(rand);
				if(toReturn != null) return toReturn;
			}
			
			ChestGenHooks hook = ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST);
			ItemStack toReturn = hook.getOneItem(rand);
			if(toReturn != null) return toReturn;
		}

        return Loot.getJunk(rand, level);
    }
}
