package me.ugunaii.synchros.networking.event;

import me.ugunaii.synchros.networking.Networking;
import me.ugunaii.synchros.networking.packet.C2SPacket;

import me.ugunaii.synchros.util.ModHandler;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class JoinServer {

    @SubscribeEvent
    public void onClientTick(ClientPlayerNetworkEvent.LoggingIn event) {
        Networking.sendToServer(new C2SPacket(ModHandler.getInstalledMods()));
    }
}
