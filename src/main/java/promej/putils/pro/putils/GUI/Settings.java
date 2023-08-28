package promej.putils.pro.putils.GUI;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

public class Settings
        extends LightweightGuiDescription {
    public Settings() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        WGridPanel root = new WGridPanel();
        this.setRootPanel((WPanel)root);
        root.setSize(100, 100);
        root.setInsets(Insets.ROOT_PANEL);
        WButton fspi = new WButton((Text)Text.literal((String)"Доп. кнопки"));
        root.add((WWidget)fspi, 0, 4, 7, 1);
        //fspi.setOnClick(() -> player.sendMessage((Text)Text.literal((String)("" + ModConfigs.BUTTONS))));
    }
}
