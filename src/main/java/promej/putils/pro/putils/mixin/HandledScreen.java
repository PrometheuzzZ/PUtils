package promej.putils.pro.putils.mixin;

import blue.endless.jankson.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import promej.putils.pro.putils.handlers.InventoryHandler;

@Mixin(value={net.minecraft.client.gui.screen.ingame.HandledScreen.class}, priority=1)
public abstract class HandledScreen {
    private static final Item[] SHULKER_BOX_ITEMS = new Item[]{Items.SHULKER_BOX, Items.WHITE_SHULKER_BOX, Items.ORANGE_SHULKER_BOX, Items.MAGENTA_SHULKER_BOX, Items.LIGHT_BLUE_SHULKER_BOX, Items.YELLOW_SHULKER_BOX, Items.LIME_SHULKER_BOX, Items.PINK_SHULKER_BOX, Items.GRAY_SHULKER_BOX, Items.LIGHT_GRAY_SHULKER_BOX, Items.CYAN_SHULKER_BOX, Items.PURPLE_SHULKER_BOX, Items.BLUE_SHULKER_BOX, Items.BROWN_SHULKER_BOX, Items.GREEN_SHULKER_BOX, Items.RED_SHULKER_BOX, Items.BLACK_SHULKER_BOX};

    @Shadow
    @Nullable
    protected abstract Slot getSlotAt(double var1, double var3);

    @Inject(method={"mouseClicked"}, at={@At(value="HEAD")}, cancellable=true)
    private void injected(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        InventoryHandler.openInventory(mouseX, mouseY, button, cir, this.getSlotAt(mouseX, mouseY));
    }


}
