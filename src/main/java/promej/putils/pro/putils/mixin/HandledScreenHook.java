package promej.putils.pro.putils.mixin;

import blue.endless.jankson.annotation.Nullable;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import promej.putils.pro.putils.handlers.InventoryHandler;

@Mixin(value={net.minecraft.client.gui.screen.ingame.HandledScreen.class}, priority=100)
public abstract class HandledScreenHook {

    @Shadow
    @Nullable
    protected abstract Slot getSlotAt(double var1, double var3);

    @Inject(method={"mouseClicked"}, at={@At(value="HEAD")}, cancellable=true)
    private void injected(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        InventoryHandler.openInventory(mouseX, mouseY, button, cir, this.getSlotAt(mouseX, mouseY));
    }


}
