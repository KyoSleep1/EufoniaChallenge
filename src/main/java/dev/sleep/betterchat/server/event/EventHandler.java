package dev.sleep.betterchat.server.event;

import dev.sleep.betterchat.common.network.NetworkManager;
import dev.sleep.betterchat.common.network.packet.PacketPlayerDisconnected;
import dev.sleep.betterchat.server.chat.ServerChatHandler;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public class EventHandler {

    public static void registerAll() {
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            UUID disconnectedPlayerUUID = handler.getPlayer().getUUID();

            for (ServerPlayer serverPlayer : PlayerLookup.all(server)) {
                ServerPlayNetworking.send(serverPlayer, NetworkManager.PLAYER_DISCONNECTED_PACKET_ID, PacketPlayerDisconnected.createWritedBuf(disconnectedPlayerUUID));
            }

            ServerChatHandler.INSTANCE.removeMessageFromUUID(disconnectedPlayerUUID);
        });
    }
}
