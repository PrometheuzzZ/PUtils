package promej.putils.pro.putils.handlers;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.text.Text;

public class JoinServerHandler implements ClientPlayConnectionEvents.Join {
    @Override
    public void onPlayReady(ClientPlayNetworkHandler handler, PacketSender sender, MinecraftClient client) {
        boolean send = MinecraftClient.getInstance().getNetworkHandler().sendCommand("queue SurvivalBuild1");
        MinecraftClient.getInstance().player.sendMessage(Text.literal(send+""));
    }
}
