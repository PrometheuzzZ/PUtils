package promej.putils.pro.putils.mixin;

import blue.endless.jankson.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import promej.putils.pro.putils.utils.EnchantReplace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mixin(value={HandledScreen.class}, priority=1)
public abstract class InventoryHandler {
    private static final Item[] SHULKER_BOX_ITEMS = new Item[]{Items.SHULKER_BOX, Items.WHITE_SHULKER_BOX, Items.ORANGE_SHULKER_BOX, Items.MAGENTA_SHULKER_BOX, Items.LIGHT_BLUE_SHULKER_BOX, Items.YELLOW_SHULKER_BOX, Items.LIME_SHULKER_BOX, Items.PINK_SHULKER_BOX, Items.GRAY_SHULKER_BOX, Items.LIGHT_GRAY_SHULKER_BOX, Items.CYAN_SHULKER_BOX, Items.PURPLE_SHULKER_BOX, Items.BLUE_SHULKER_BOX, Items.BROWN_SHULKER_BOX, Items.GREEN_SHULKER_BOX, Items.RED_SHULKER_BOX, Items.BLACK_SHULKER_BOX};

    @Shadow
    @Nullable
    protected abstract Slot getSlotAt(double var1, double var3);

    @Inject(method={"mouseClicked"}, at={@At(value="HEAD")}, cancellable=true)
    private void injected(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        InventoryParse: {
            Inventory inventory = MinecraftClient.getInstance().player.getInventory();

            try {
                NbtList loreList = new NbtList();

                Slot slot2 = this.getSlotAt(mouseX, mouseY);

                if (!slot2.hasStack() || button != 2) break InventoryParse;
                ItemStack itemStack = slot2.getStack();

                 if (true) {
                    //NbtList loreList = new NbtList();
                    int id = Item.getRawId(itemStack.getItem());
                    loreList.add(NbtString.of(("{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"white\",\"text\":\"ID: "+id+" \"}],\"text\":\"\"}")));
                    if(itemStack.getNbt()!=null) {
                        itemStack.getNbt().getCompound("display").put("Lore", loreList);
                    } else {
                        NbtCompound display = new NbtCompound();
                        display.put("display", new NbtCompound());
                        //display.getCompound("display").put("Lore", loreList);
                        itemStack.setNbt(display);
                    }

                }

                if (itemStack.getItem() == Items.SPAWNER) {
                    NbtCompound nbt = itemStack.getNbt();
                    String spawnDate = nbt.getCompound("BlockEntityTag").getCompound("SpawnData").getCompound("entity").getString("id");
                    //NbtList loreList = new NbtList();
                    if (spawnDate.contains("blaze")) {
                        spawnDate = "Ифрит";
                    } else if (spawnDate.contains("magma_cube")) {
                        spawnDate = "Магмовый куб";
                    } else if (spawnDate.contains("zombie")) {
                        spawnDate = "Зомби";
                    } else if (spawnDate.contains("skeleton")) {
                        spawnDate = "Скелет";
                    } else if (spawnDate.contains("cave_spider")) {
                        spawnDate = "Ядовитый паук";
                    } else if (spawnDate.contains("spider")) {
                        spawnDate = "Обычный паук";
                    }
                    loreList.add(NbtString.of(("{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"white\",\"text\":\"Тип моба: " + spawnDate + "\"}],\"text\":\"\"}")));
                    //itemStack.getNbt().getCompound("display").put("Lore", loreList);
                    //break InventoryParse;
                }
                if (InventoryHandler.isShalker(itemStack)) {
                    List<ItemStack> itemStacks = InventoryHandler.getItemsInContainer(itemStack);
                    if (itemStacks.size() > 0) {
                        for (int i = 0; i < itemStacks.size(); ++i) {
                            inventory.setStack(i + 9, itemStacks.get(i));
                        }
                    }
                   // break InventoryParse;
                }
                if (itemStack.getItem() == Items.WRITTEN_BOOK) {
                    MinecraftClient.getInstance().setScreen(new BookScreen(BookScreen.Contents.create(itemStack)));
                    break InventoryParse;
                }
                if (itemStack.getItem() == Items.WRITABLE_BOOK) {
                    MinecraftClient.getInstance().setScreen(new BookEditScreen(MinecraftClient.getInstance().player, itemStack, Hand.OFF_HAND));
                    //break InventoryParse;
                }



                NbtCompound nbt = itemStack.getNbt();
                String lorePlus = "нет";
                String displayPlus = "нет";
                try {
                    if (nbt.contains("ModifiedLore")) {
                        lorePlus = "да";
                    }
                    if (nbt.contains("ModifiedName")) {
                        displayPlus = "да";
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
                //NbtList loreList = new NbtList();
                loreList.add(NbtString.of(("{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"white\",\"text\":\"ItemName: " + displayPlus + "\"}],\"text\":\"\"}")));
                loreList.add(NbtString.of(("{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"white\",\"text\":\"ItemLore: " + lorePlus + "\"}],\"text\":\"\"}")));
                itemStack.getNbt().getCompound("display").put("Lore", loreList);

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static List<ItemStack> getItemsInContainer(ItemStack item) {
        NbtCompound blockEntityTag;
        ArrayList<ItemStack> items = new ArrayList(Collections.nCopies(27, new ItemStack(Items.AIR)));
        NbtCompound nbt = item.getNbt();
        if (nbt != null && nbt.contains("BlockEntityTag") && (blockEntityTag = nbt.getCompound("BlockEntityTag")).contains("Items")) {
            NbtList itemList = blockEntityTag.getList("Items", 10);
            for (int i = 0; i < itemList.size(); ++i) {
                boolean book = false;
                NbtCompound itemNbt = itemList.getCompound(i);
                NbtCompound itemTag = itemNbt.getCompound("tag");
                String itemId = itemNbt.getString("id");
                if (itemTag.getInt("HideFlags") != 127) {
                    try {
                        NbtList enchantments;
                        if (itemId.contains("minecraft:enchanted_book")) {
                            enchantments = itemTag.getList("StoredEnchantments", 10);
                            itemTag.remove("StoredEnchantments");
                            book = true;
                        } else {
                            enchantments = itemNbt.getCompound("tag").getList("Enchantments", 10);
                        }
                        itemTag.putInt("HideFlags", 127);
                        NbtList LoreList = new NbtList();
                        for (int j = 0; j < enchantments.size(); ++j) {
                            NbtCompound enchant = enchantments.getCompound(j);
                            String enchantID = enchant.getString("id").toString();
                            String loreColore = "white";
                            if (EnchantReplace.getString((enchantID).split(":")[1]) != null) {
                                enchantID = EnchantReplace.getString((enchantID).split(":")[1]);
                                loreColore = (enchantID).split(":")[1];
                                enchantID = (enchantID).split(":")[0];
                            } else if ((enchantID).contains("artifact")) {
                                enchantID = "Артефакт";
                                loreColore = "#E2E47D";
                            } else {
                                enchantID = (enchantID).split(":")[1] + " (Не переведено)";
                            }
                            String enchantLVL = book ? String.valueOf(enchant.getShort("lvl")) : String.valueOf(enchant.getInt("lvl"));
                            enchantLVL = InventoryHandler.getEnchantLevelStr(enchantLVL);
                            if ((enchantID).contains("Артефакт")) {
                                enchantLVL = " ";
                            }
                            String enchantStr = enchantID + " " + enchantLVL;
                            NbtString loreEnchant = NbtString.of(("{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"" + loreColore + "\",\"text\":\"" + enchantStr + "\"}],\"text\":\"\"}"));
                            if (loreColore.contains("gray")) {
                                LoreList.add(0, loreEnchant);
                                continue;
                            }
                            LoreList.add(loreEnchant);
                        }
                        NbtList loreStore = itemTag.getCompound("display").getList("Lore", 8);
                        if (loreStore.size() > 0) {
                            LoreList.add(NbtString.of("{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"gray\",\"text\":\" \"}],\"text\":\"\"}"));
                            LoreList.add(NbtString.of("{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"gray\",\"text\":\"Лор:\"}],\"text\":\"\"}"));
                            for (int j = 0; j < loreStore.size(); ++j) {
                                LoreList.add(LoreList.size(), NbtString.of(loreStore.getString(j)));
                            }
                        }
                        if (itemTag.contains("display")) {
                            itemTag.getCompound("display").put("Lore", LoreList);
                        } else {
                            itemTag.put("display", new NbtCompound());
                            itemTag.getCompound("display").put("Lore", LoreList);
                        }
                    }
                    catch (Exception enchantments) {
                        // empty catch block
                    }
                }
                ItemStack s = ItemStack.fromNbt(itemNbt);
                items.set(itemList.getCompound(i).getByte("Slot"), s);
            }
        }
        return items;
    }

    private static boolean isShalker(ItemStack itemStack) {
        for (Item item : SHULKER_BOX_ITEMS) {
            if (!(itemStack.getItem() == item)) continue;
            return true;
        }
        return false;
    }

    private static String getEnchantLevelStr(String lvl) {
        String enchantLVL = "I";
        switch (lvl) {
            case "1": {
                enchantLVL = "I";
                break;
            }
            case "2": {
                enchantLVL = "II";
                break;
            }
            case "3": {
                enchantLVL = "III";
                break;
            }
            case "4": {
                enchantLVL = "IV";
                break;
            }
            case "5": {
                enchantLVL = "V";
                break;
            }
            case "6": {
                enchantLVL = "VI";
                break;
            }
            case "7": {
                enchantLVL = "VII";
                break;
            }
            case "8": {
                enchantLVL = "VIII";
                break;
            }
            case "9": {
                enchantLVL = "IX";
                break;
            }
            case "10": {
                enchantLVL = "X";
                break;
            }
            default: {
                enchantLVL = "I";
            }
        }
        return enchantLVL;
    }

    private static String removeLastNchars(String str, int n) {
        if (str == null || str.length() < n) {
            return str;
        }
        return str.substring(0, str.length() - n);
    }
}
