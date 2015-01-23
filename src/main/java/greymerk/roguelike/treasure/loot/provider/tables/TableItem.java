package greymerk.roguelike.treasure.loot.provider.tables;

import greymerk.roguelike.treasure.loot.Loot;
import greymerk.roguelike.treasure.loot.provider.ItemArmour;
import greymerk.roguelike.util.IWeighted;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class TableItem implements IWeighted<ItemStack> {
    private int weight;
    private String item;
    private int meta;
    private int min = 1;
    private int max = 0;
    private boolean enchant;
    private boolean dye;

    private transient int level;
    private transient Item parsedItem;

    public void postLoadProcess(int level) {
        this.level = level;
        if (max < min)
            max = min;

        parsedItem = (Item)Item.itemRegistry.getObject(item);

        if (parsedItem == null)
            throw new RuntimeException("Invalid item '"+item+"'.");
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public ItemStack get(Random rand) {
        int count = 0;
        if (max > min)
            count = rand.nextInt(max-min);
        count += min;
        ItemStack newItem = new ItemStack(parsedItem, count, meta);

        if (enchant)
            Loot.enchantItem(newItem, rand, Loot.getEnchantLevel(rand, level));
        if (dye)
            ItemArmour.dyeArmor(newItem, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

        return newItem;
    }
}
