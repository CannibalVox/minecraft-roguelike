package greymerk.roguelike.catacomb.dungeon.room;

import greymerk.roguelike.catacomb.dungeon.DungeonBase;
import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.theme.ITheme;
import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.treasure.TreasureChest;
import greymerk.roguelike.treasure.TreasureChestBase;
import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.LootSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.WorldGenPrimitive;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;



public class DungeonMess extends DungeonBase {

	IBlockFactory plank;
	MetaBlock stairSpruce;
	IBlockFactory log;
	
	@Override
	public boolean generate(World world, Random rand, CatacombLevelSettings settings, Cardinal[] entrances, int x, int y, int z) {
	
		ITheme theme = settings.getTheme();
		
		plank = theme.getSecondaryWall();
		stairSpruce = theme.getSecondaryStair();
		log = theme.getSecondaryPillar();
		
		MetaBlock air = new MetaBlock(Blocks.air);
		
		// air		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 6, y, z - 6, x + 6, y + 2, z + 6, air);

		// ceiling
		WorldGenPrimitive.fillRectSolid(world, rand, x - 6, y + 3, z - 6, x + 6, y + 4, z + 6, plank, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y + 3, z - 2, x + 4, y + 3, z - 2, WorldGenPrimitive.blockOrientation(stairSpruce, Cardinal.SOUTH, true), true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y + 3, z + 2, x + 4, y + 3, z + 2, WorldGenPrimitive.blockOrientation(stairSpruce, Cardinal.NORTH, true), true, true);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y + 3, z - 4, x - 2, y + 3, z + 4, WorldGenPrimitive.blockOrientation(stairSpruce, Cardinal.EAST, true), true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 2, y + 3, z - 4, x + 2, y + 3, z + 4, WorldGenPrimitive.blockOrientation(stairSpruce, Cardinal.WEST, true), true, true);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y + 3, z - 4, x + 1, y + 3, z + 4, air);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 4, y + 3, z - 1, x + 4, y + 3, z + 1, air);
		
		MetaBlock brownClay = new MetaBlock(Blocks.stained_hardened_clay, 12);
		
		// floor
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y - 1, z - 7, x + 7, y - 1, z + 7, brownClay, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y - 1, z - 6, x + 1, y - 1, z + 6, log, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 5, y - 1, z - 1, x - 2, y - 1, z + 1, log, true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 2, y - 1, z - 1, x + 5, y - 1, z + 1, log, true, true);

		
		
		// walls
		WorldGenPrimitive.fillRectSolid(world, rand, x + 7, y, z - 2, x + 7, y + 2, z + 6, plank, false, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 7, y, z - 6, x - 7, y + 2, z + 6, plank, false, true);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 6, y, z - 7, x + 2, y + 2, z - 7, plank, false, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 6, y, z + 7, x + 6, y + 2, z + 7, plank, false, true);
		
		// pillars
		for(int i = - 6; i <= 6; i = i += 4){
			for(int j = - 6; j <= 6; j += 4){
				if(i % 6 == 0 || j % 6 == 0){
					pillar(world, rand, theme, 2, x + i, y, z + j);	
				} else {
					pillar(world, rand, theme, 3, x + i, y, z + j);
				}
				
			}
		}
				
		// stove
		stove(world, rand, x + 4, y, z - 4);
		
		// storage
		storage(world, rand, settings.getLoot(), x + 4, y, z + 4);
		
		// table north
		northTable(world, rand, x - 4, y, z - 4);
		
		
		// table south
		southTable(world, rand, x - 4, y, z + 4);

		return true;
	}

	private void stove(World world, Random rand, int x, int y, int z){
		
		MetaBlock brick = new MetaBlock(Blocks.brick_block);
		MetaBlock stair = new MetaBlock(Blocks.brick_stairs);
		
		// floor
		
		// fire pit
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y - 1, z - 4, x + 2, y - 1, z + 1, brick);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y, z - 4, x + 1, y + 2, z - 3, brick);
		WorldGenPrimitive.setBlock(world, rand, x - 1, y, z - 2, WorldGenPrimitive.blockOrientation(stair, Cardinal.EAST, false), true, true);
		WorldGenPrimitive.setBlock(world, rand, x - 1, y + 1, z - 2, WorldGenPrimitive.blockOrientation(stair, Cardinal.EAST, true), true, true);
		
		WorldGenPrimitive.setBlock(world, rand, x + 1, y, z - 2, WorldGenPrimitive.blockOrientation(stair, Cardinal.WEST, false), true, true);
		WorldGenPrimitive.setBlock(world, rand, x + 1, y + 1, z - 2, WorldGenPrimitive.blockOrientation(stair, Cardinal.WEST, true), true, true);
		
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y + 2, z - 2, x + 1, y + 2, z - 2, brick);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y + 2, z - 1, x + 2, y + 2, z - 1, WorldGenPrimitive.blockOrientation(stair, Cardinal.SOUTH, true), true, true);
		
		WorldGenPrimitive.setBlock(world, x, y - 1, z - 3, Blocks.netherrack);
		WorldGenPrimitive.setBlock(world, x, y, z - 3, Blocks.fire);
		WorldGenPrimitive.setBlock(world, rand, x, y + 1, z - 3, WorldGenPrimitive.blockOrientation(stair, Cardinal.SOUTH, true), true, true);

		// furnace
		WorldGenPrimitive.fillRectSolid(world, rand, x + 2, y, z - 1, x + 2, y + 2, z + 1, brick);
		WorldGenPrimitive.fillRectSolid(world, rand, x + 1, y + 2, z - 1, x + 1, y + 2, z + 1, WorldGenPrimitive.blockOrientation(stair, Cardinal.WEST, true), true, true);
		
		WorldGenPrimitive.setBlock(world, x + 2, y, z, Blocks.furnace);
		WorldGenPrimitive.setBlock(world, rand, x + 2, y + 1, z, WorldGenPrimitive.blockOrientation(stair, Cardinal.WEST, true), true, true);
		
		// ceiling
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y + 3, z - 1, x + 1, y + 3, z + 1, brick);
	}
	
	private void storage(World world, Random rand, LootSettings loot, int x, int y, int z){
		
		// floor
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y - 1, z - 1, x + 1, y - 1, z + 1, plank, true, true);
		
		// east shelf
		WorldGenPrimitive.setBlock(world, rand, x + 2, y, z - 1, WorldGenPrimitive.blockOrientation(stairSpruce, Cardinal.SOUTH, true), true, true);
		WorldGenPrimitive.setBlock(world, rand, x + 2, y, z, WorldGenPrimitive.blockOrientation(stairSpruce, Cardinal.WEST, true), true, true);
		WorldGenPrimitive.setBlock(world, rand, x + 2, y, z + 1, WorldGenPrimitive.blockOrientation(stairSpruce, Cardinal.NORTH, true), true, true);
        TreasureChest.generate(world, rand, loot, x + 2, y + 1, z, 1, false);
		
		// south shelf
		WorldGenPrimitive.setBlock(world, rand, x - 1, y, z + 2, WorldGenPrimitive.blockOrientation(stairSpruce, Cardinal.EAST, true), true, true);
		WorldGenPrimitive.setBlock(world, rand, x, y, z + 2, WorldGenPrimitive.blockOrientation(stairSpruce, Cardinal.NORTH, true), true, true);
		WorldGenPrimitive.setBlock(world, rand, x + 1, y, z + 2, WorldGenPrimitive.blockOrientation(stairSpruce, Cardinal.WEST, true), true, true);
	}
	
	private void northTable(World world, Random rand, int x, int y, int z){
		// floor
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y - 1, z - 1, x + 1, y - 1, z + 1, plank, true, true);
		
		// benches
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y, z - 2, x + 1, y, z - 2, WorldGenPrimitive.blockOrientation(stairSpruce, Cardinal.SOUTH, false), true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y, z - 1, x - 2, y, z + 1, WorldGenPrimitive.blockOrientation(stairSpruce, Cardinal.EAST, false), true, true);
		
		// table
		WorldGenPrimitive.setBlock(world, rand, x, y, z, WorldGenPrimitive.blockOrientation(stairSpruce, Cardinal.NORTH, true), true, true);
		WorldGenPrimitive.setBlock(world, rand, x + 1, y, z, WorldGenPrimitive.blockOrientation(stairSpruce, Cardinal.EAST, true), true, true);
		WorldGenPrimitive.setBlock(world, rand, x + 1, y, z + 1, WorldGenPrimitive.blockOrientation(stairSpruce, Cardinal.SOUTH, true), true, true);
		WorldGenPrimitive.setBlock(world, rand, x, y, z + 1, WorldGenPrimitive.blockOrientation(stairSpruce, Cardinal.WEST, true), true, true);
		WorldGenPrimitive.setBlock(world, x, y + 1, z, Blocks.torch);
	}

	private void southTable(World world, Random rand, int x, int y, int z){
		// floor
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y - 1, z - 1, x + 1, y - 1, z + 1, plank, true, true);
		
		// benches
		WorldGenPrimitive.fillRectSolid(world, rand, x - 1, y, z + 2, x + 1, y, z + 2, WorldGenPrimitive.blockOrientation(stairSpruce, Cardinal.NORTH, false), true, true);
		WorldGenPrimitive.fillRectSolid(world, rand, x - 2, y, z - 1, x - 2, y, z + 1, WorldGenPrimitive.blockOrientation(stairSpruce, Cardinal.EAST, false), true, true);
		
		// table
		WorldGenPrimitive.setBlock(world, rand, x, y, z - 1, WorldGenPrimitive.blockOrientation(stairSpruce, Cardinal.NORTH, true), true, true);
		WorldGenPrimitive.setBlock(world, rand, x + 1, y, z - 1, WorldGenPrimitive.blockOrientation(stairSpruce, Cardinal.EAST, true), true, true);
		WorldGenPrimitive.setBlock(world, rand, x + 1, y, z, WorldGenPrimitive.blockOrientation(stairSpruce, Cardinal.SOUTH, true), true, true);
		WorldGenPrimitive.setBlock(world, rand, x, y, z, WorldGenPrimitive.blockOrientation(stairSpruce, Cardinal.WEST, true), true, true);
		WorldGenPrimitive.setBlock(world, x, y + 1, z, Blocks.torch);
				
	}
	
	private static void pillar(World world, Random rand, ITheme theme, int height, int x, int y, int z){
		
		MetaBlock stair = theme.getSecondaryStair();
		IBlockFactory pillar = theme.getSecondaryPillar();
		IBlockFactory wall = theme.getSecondaryWall();
		
		WorldGenPrimitive.fillRectSolid(world, rand, x, y, z, x, y + height - 1, z, pillar, true, true);
		WorldGenPrimitive.setBlock(world, rand, x, y + height, z, wall, true, true);
		WorldGenPrimitive.setBlock(world, rand, x + 1, y + height, z, WorldGenPrimitive.blockOrientation(stair, Cardinal.EAST, true), true, true);
		WorldGenPrimitive.setBlock(world, rand, x - 1, y + height, z, WorldGenPrimitive.blockOrientation(stair, Cardinal.WEST, true), true, true);
		WorldGenPrimitive.setBlock(world, rand, x, y + height, z + 1, WorldGenPrimitive.blockOrientation(stair, Cardinal.SOUTH, true), true, true);
		WorldGenPrimitive.setBlock(world, rand, x, y + height, z - 1, WorldGenPrimitive.blockOrientation(stair, Cardinal.NORTH, true), true, true);
	}
	
	public int getSize(){
		return 10;
	}
}
