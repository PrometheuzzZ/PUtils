package promej.putils.pro.putils.gui.buttons;


import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class PUtilsButtonWidget extends TexturedButtonWidget {
    private static final Identifier TEXTURE_MAIN = new Identifier("textures/gui/button.png");
    private String descript;
    private int line;
    private int row;
    private final HandledScreen<?> screen;
    private Item itemIco;

    private PressAction onPress;

    public PUtilsButtonWidget(HandledScreen<?> screen, int buttonIndex, int row, String descript, Item items, PressAction pressAction) {
        super(0, 0, 20, 20, 0, 0, 20, TEXTURE_MAIN, 20, 40, pressAction);
        this.line = buttonIndex;
        this.descript = descript;
        this.screen = screen;
        this.itemIco = items;
        this.row = row;
        this.setX(getX(screen));
        this.setY(getY(screen));
    }



    public int getX(HandledScreen<?> screen) {
        int invOffset = 1;

        int x = (MinecraftClient.getInstance().getWindow().getScaledWidth() + getBackgroundWidth()) / 2;
        x += invOffset;
        if (this.doRecipeAdjust(screen)) {
            x += 77;
        }

        if (this.row == 1) {
            x += 22;
        }
        return x;
    }

    private int getBackgroundWidth() {
        return 176;
    }


    private int getBackgroundHeight() {
        return 166;
    }

    public int getY(HandledScreen<?> screen) {
        int invOffset = this.line * 22;
        int y = (MinecraftClient.getInstance().getWindow().getScaledHeight() - getBackgroundHeight()) / 2;
        return y += invOffset;
    }

    protected boolean doRecipeAdjust(Screen screen) {
        if (screen instanceof RecipeBookProvider) {
            RecipeBookWidget widget = ((InventoryScreen)screen).getRecipeBookWidget();
            return widget.isOpen();
        }
        return false;
    }


    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.reposition();
        boolean bl = this.hovered = mouseX >= this.getX(this.screen) && mouseY >= this.getY(this.screen) && mouseX < this.getX(this.screen) + this.width && mouseY < this.getY(this.screen) + this.height;
        if (this.visible) {
            this.renderButton(context, mouseX, mouseY, delta);
        }
        if (this.isMouseOver(mouseX, mouseY)) {
            int offset = 0;
            context.drawTooltip(MinecraftClient.getInstance().textRenderer, Text.literal(this.descript),mouseX - offset, mouseY);
            //  this.screen.(matrices, Text.literal(this.descript), mouseX - offset, mouseY);
        }
    }


    public void reposition() {
        if (MinecraftClient.getInstance().player != null) {
            this.isMouseOver(this.getX(this.screen), this.getY(this.screen));
        }
    }

    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        //RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        //RenderSystem.setShaderTexture(0, ButtonWidget.TEXTURE_MAIN);
        int offset = 0;
        if (this.isHovered()) {
            offset = 20;
        }
        RenderSystem.enableDepthTest();

        this.drawTexture(context, TEXTURE_MAIN, (int)this.getX(this.screen), (int)this.getY(this.screen), 0, 0, offset, this.width, this.height, 20, 40);


        if (this.itemIco == Items.ENDER_CHEST) {
            context.drawItem(new ItemStack(this.itemIco), this.getX(this.screen) + 2, this.getY(this.screen) + 2);
            context.drawItem(new ItemStack(this.itemIco), this.getX(this.screen) + 2, this.getY(this.screen) + 2);
        } else if (this.itemIco == Items.CHEST) {
            context.drawItem(new ItemStack(this.itemIco), this.getX(this.screen) + 2, this.getY(this.screen) + 2);
        } else {
            context.drawItem(new ItemStack(this.itemIco), this.getX(this.screen) + 2, this.getY(this.screen) + 2);
        }
    }



}

