package greymerk.roguelike.treasure.loot.provider.tables;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import greymerk.roguelike.util.IWeighted;
import net.minecraft.item.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class FileTable implements IWeighted<ItemStack> {

    private String table;
    private List<TableItem> entries;

    private transient int totalWeight;

    public FileTable() {
    }

    public static FileTable load(File file, Gson gson, int level) {
        String content;

        try {
            content = Files.toString(file, Charsets.UTF_8);
        } catch (IOException e1) {
            return null;
        }

        FileTable table = gson.fromJson(content, FileTable.class);
        table.postLoadProcess(level);
        return table;
    }

    public void postLoadProcess(int level) {
        totalWeight = 0;

        try {
            for (TableItem item : entries) {
                item.postLoadProcess(level);
                totalWeight += item.getWeight();
            }
        } catch (RuntimeException ex) {
            throw new RuntimeException("Error while loading table '"+table+"'.",ex);
        }
    }

    @Override
    public int getWeight() {
        return 0;
    }

    @Override
    public ItemStack get(Random rand) {
        int random = rand.nextInt(totalWeight);

        if (entries.size() < 1)
            return null;

        for (TableItem item : entries) {
            if (random < item.getWeight()) {
                return item.get(rand);
            }
            random -= item.getWeight();
        }

        return entries.get(0).get(rand);
    }
}
