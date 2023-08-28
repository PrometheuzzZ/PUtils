package promej.putils.pro.putils.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(value= EnvType.CLIENT)
@Mixin(value={HandledScreen.class})
public interface AccessorHandledScreen {
  //  @Accessor(value="backgroundWidth")
  //  public int getBackgroundWidth();

  //  @Accessor(value="backgroundHeight")
  //  public int getBackgroundHeight();
}
