package me.synapz.paintball.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ActionBar {
    private static Class<?> chatPacket;
    private static Class<?> chatBasePacket;
    private static Class<?> chatSerializerPacket;
    private static Class<?> packet;

    public static void sendActionBar(Player player, String message) {
        try {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
        } catch (NoSuchMethodError error) {
            try {
                loadNmsClasses();
                Object connection = ReflectionUtil.getConnection(player);
                Method sendPacket = ReflectionUtil.getMethod(connection.getClass(), "sendPacket", packet);
                Object serialized = chatSerializerPacket.getConstructor(String.class).newInstance(ChatColor.translateAlternateColorCodes('&', message));
                Object packet = chatPacket.getConstructor(chatBasePacket, byte.class).newInstance(serialized, (byte) 2);
                sendPacket.invoke(connection, packet);
            } catch (NoSuchFieldException | IllegalAccessException | InvocationTargetException | NoSuchMethodException  | InstantiationException e) {

            }
        }
    }

    private static void loadNmsClasses() {
        try {
            if (chatPacket == null) chatPacket = ReflectionUtil.getNMSClass("PacketPlayOutChat");
            if (chatBasePacket == null) chatBasePacket = ReflectionUtil.getNMSClass("IChatBaseComponent");
            if (chatSerializerPacket == null) chatSerializerPacket = ReflectionUtil.getNMSClass("ChatComponentText");
            if (packet == null) packet = ReflectionUtil.getNMSClass("Packet");
        } catch (ClassNotFoundException e) {

        }
    }
}