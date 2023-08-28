package promej.putils.pro.putils.GUI;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import promej.putils.pro.putils.GUI.screen.PUtilsScreen;

public class FastMenu
        extends LightweightGuiDescription {
    public FastMenu() {

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        WGridPanel root = new WGridPanel();
        this.setRootPanel((WPanel)root);
        root.setSize(100, 100);
        root.setInsets(Insets.ROOT_PANEL);
        WButton rules = new WButton((Text)Text.literal((String)"Правила"));
        root.add((WWidget)rules, 0, 0, 7, 1);
        PUtilsScreen pTweakScreen = new PUtilsScreen((GuiDescription)new RulesGui());
        rules.setOnClick(() -> MinecraftClient.getInstance().setScreen((Screen)pTweakScreen));
        WButton vanish = new WButton((Text)Text.literal((String)"Ваниш"));
        root.add((WWidget)vanish, 0, 1, 7, 1);
        vanish.setOnClick(() -> player.networkHandler.sendChatCommand("v"));
        WButton ender = new WButton((Text)Text.literal((String)"Эндер-Сундук"));
        root.add((WWidget)ender, 0, 2, 7, 1);
        ender.setOnClick(() -> player.networkHandler.sendChatCommand("ender"));
        WButton simp = new WButton((Text)Text.literal((String)"SPAWN"));
        root.add((WWidget)simp, 0, 3, 7, 1);
        simp.setOnClick(() -> player.networkHandler.sendChatCommand("spawn"));
        WButton fspi = new WButton((Text)Text.literal((String)"ВОЙТИ НА SURVIVAL"));
        root.add((WWidget)fspi, 0, 4, 7, 1);
        fspi.setOnClick(() -> player.networkHandler.sendChatCommand("queue surv"));
    }
}

