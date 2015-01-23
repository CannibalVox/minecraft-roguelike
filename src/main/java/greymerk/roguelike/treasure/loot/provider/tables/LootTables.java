package greymerk.roguelike.treasure.loot.provider.tables;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.item.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LootTables {
    private Map<Integer, FileTable> equipmentTables = new HashMap<Integer, FileTable>();
    private Map<Integer, FileTable> junkTables = new HashMap<Integer, FileTable>();
    private Map<Integer, FileTable> supplyTables = new HashMap<Integer, FileTable>();

    public LootTables(File directory) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        equipmentTables.put(0, FileTable.load(new File(directory, "equipment-level0.json"), gson, 0));
        equipmentTables.put(1, FileTable.load(new File(directory, "equipment-level1.json"), gson, 1));
        equipmentTables.put(2, FileTable.load(new File(directory, "equipment-level2.json"), gson, 2));
        equipmentTables.put(3, FileTable.load(new File(directory, "equipment-level3.json"), gson, 3));
        equipmentTables.put(4, FileTable.load(new File(directory, "equipment-level4.json"), gson, 4));

        junkTables.put(0, FileTable.load(new File(directory, "junk.json"), gson, 0));
        junkTables.put(1, FileTable.load(new File(directory, "junk.json"), gson, 1));
        junkTables.put(2, FileTable.load(new File(directory, "junk.json"), gson, 2));
        junkTables.put(3, FileTable.load(new File(directory, "junk.json"), gson, 3));
        junkTables.put(4, FileTable.load(new File(directory, "junk.json"), gson, 4));

        supplyTables.put(0, FileTable.load(new File(directory, "supplies.json"), gson, 0));
        supplyTables.put(1, FileTable.load(new File(directory, "supplies.json"), gson, 1));
        supplyTables.put(2, FileTable.load(new File(directory, "supplies.json"), gson, 2));
        supplyTables.put(3, FileTable.load(new File(directory, "supplies.json"), gson, 3));
        supplyTables.put(4, FileTable.load(new File(directory, "supplies.json"), gson, 4));
    }

    public ItemStack getEquipment(int level, Random rand) {
        return equipmentTables.get(level).get(rand);
    }

    public ItemStack getJunk(int level, Random rand) {
        return junkTables.get(level).get(rand);
    }

    public ItemStack getSupplies(int level, Random rand) {
        return supplyTables.get(level).get(rand);
    }
}
