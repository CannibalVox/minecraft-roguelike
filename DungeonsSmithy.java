package greymerk.roguelike;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityFurnace;
import net.minecraft.src.World;

public class DungeonsSmithy implements IDungeon{

	
	World world;
	Random rand;
	int originX;
	int originY;
	int originZ;
	byte dungeonHeight;
	int dungeonLength;
	int dungeonWidth;
	int woolColor;
	int numChests;
	
	int floorBlock;
	int wallBlock;
	
	public DungeonsSmithy() {
		
		dungeonHeight = 4;
		dungeonLength = 2;
		dungeonWidth = 2;
		numChests = 0;
		
		
	}

	public boolean generate(World inWorld, Random inRandom, int inOriginX, int inOriginY, int inOriginZ) {
		world = inWorld;
		rand = inRandom;
		originX = inOriginX;
		originY = inOriginY;
		originZ = inOriginZ;

		// clear air space
		WorldGenPrimitive.fillRectSolid(world,  originX - 2, originY, originZ - 2,
												originX + 2, originY + 4, originZ + 2, 0);
		
		buildWalls();
		buildFloor();
		buildRoof();
		
		WorldGenPrimitive.setBlock(world, inOriginX, inOriginY, inOriginZ, Block.anvil.blockID);
		
		List<Coord> space = new ArrayList<Coord>();
		space.add(new Coord(originX - 2, originY, originZ - 2));
		space.add(new Coord(originX - 2, originY, originZ + 2));
		space.add(new Coord(originX + 2, originY, originZ - 2));
		space.add(new Coord(originX + 2, originY, originZ + 2));
		
		TreasureChest[] types = {TreasureChest.ORE};
		TreasureChest.createChests(inWorld, inRandom, 1, space, types);
		
		return true;
	}
	
	protected void buildRoof(){
		
		List<Coord> walls = WorldGenPrimitive.getRectHollow(
				originX - dungeonLength - 1, originY + dungeonHeight + 1, originZ - dungeonWidth - 1,
				originX + dungeonLength + 1, originY + dungeonHeight + 1, originZ + dungeonWidth + 1);

		for (Coord block : walls){
		
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();	
			
			WorldGenPrimitive.setBlock(world, x, y, z, Block.stoneBrick.blockID, rand.nextInt(3), 2, false, true);
		}
	}
    
	protected void buildFloor(){
		
		WorldGenPrimitive.fillRectSolid(world,
				originX - dungeonLength - 1, originY - 1, originZ - dungeonWidth - 1,
				originX + dungeonLength + 1, originY - 1, originZ + dungeonWidth + 1, Block.brick.blockID);
	}

	protected void buildWalls() {
		
		List<Coord> walls = WorldGenPrimitive.getRectHollow(
							originX - dungeonLength - 1, originY - 1, originZ - dungeonWidth - 1,
							originX + dungeonLength + 1, originY + dungeonHeight, originZ + dungeonWidth + 1);
		
		for (Coord block : walls){
		
			int x = block.getX();
			int y = block.getY();
			int z = block.getZ();
			
			int blockID;
			
			blockID = Block.cobblestone.blockID;
			
			if(rand.nextInt(300) == 0){
				blockID = Block.blockIron.blockID;
			}
			
			if(rand.nextInt(5) == 0){
				blockID = Block.fenceIron.blockID;
			}

			if(y == (originY + dungeonHeight)){
				blockID = Block.stoneBrick.blockID;
			}
			
			if(y == originY && (z == originZ || x == originX)){
				blockID = Block.glowStone.blockID;
			}
			
			if(y == originY + 1 && (z == originZ || x == originX) && !world.isAirBlock(x, y, z)){
				blockID = Block.furnaceIdle.blockID;
				if(WorldGenPrimitive.setBlock(world, x, y, z, blockID)){
					TileEntityFurnace furnace = (TileEntityFurnace)world.getBlockTileEntity(x, y, z);
					
					ItemStack coal = new ItemStack(Item.coal, 5 + rand.nextInt(10));
					furnace.setInventorySlotContents(1, coal);
				}
				continue;
			}
			
			WorldGenPrimitive.setBlock(world, x, y, z, blockID, 0, 2, false, true);
			
		}
	}

	public boolean isValidDungeonLocation(World world, int x, int y, int z){
		return false;
	}
	
}