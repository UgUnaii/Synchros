package me.ugunaii.synchros;

import com.mojang.logging.LogUtils;
import me.ugunaii.synchros.networking.Networking;
import me.ugunaii.synchros.networking.event.JoinServer;
import me.ugunaii.synchros.util.ModHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


@Mod(Synchros.MOD_ID)
public class Synchros {

    public static final String MOD_ID = "synchros";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static String installedMods;

    public Synchros() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        Networking.register();
    }

    @SubscribeEvent // Server side
    public void onServerStarting(ServerStartingEvent event) {
        installedMods = ModHandler.getInstalledMods();
        ModHandler.printInstalledMods();
    }

    // Client side
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            installedMods = ModHandler.getInstalledMods();
            MinecraftForge.EVENT_BUS.register(new JoinServer());
        }
    }
}
