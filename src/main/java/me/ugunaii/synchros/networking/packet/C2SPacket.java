package me.ugunaii.synchros.networking.packet;

import me.ugunaii.synchros.util.ModHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class C2SPacket {
    private String mods;

    public C2SPacket (String mods) {
        this.mods = mods;
    }

    public C2SPacket (FriendlyByteBuf buf) {
        mods = buf.readUtf();
    }

    public void toBytes (FriendlyByteBuf buf) {
        buf.writeUtf(mods);
    }

    public boolean handle (Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server-side
            ServerPlayer player = context.getSender();

            checkMods(player);

        });
        return true;
    }

    private void checkMods(ServerPlayer player) {
        boolean hasRequired = true;
        String[] clientMods = mods.split("%%%");
        String[] serverMods = ModHandler.getInstalledMods().split("%%%");

        Set<String> modHash = new HashSet<>(Arrays.asList(clientMods));
        for (String mod : serverMods) {
            if(!modHash.contains(mod)) {
                hasRequired = false;
                break;
            }
        }

        if(hasRequired) {
            System.out.println(player.getName() + "Has the required mods!!");
        } else {
            System.out.println(player.getName() + "Is missing mods!!");
            player.connection.disconnect(Component.literal("No cumples los requisitos de mods!!\n" + ""));
        }
    }
}
