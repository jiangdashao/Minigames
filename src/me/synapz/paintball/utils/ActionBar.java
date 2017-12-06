package me.synapz.paintball.utils;

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
            loadNmsClasses();
            Object connection = ReflectionUtil.getConnection(player);
            Method sendPacket = ReflectionUtil.getMethod(connection.getClass(), "sendPacket", packet);
            Object serialized = chatSerializerPacket.getConstructor(String.class).newInstance(ChatColor.translateAlternateColorCodes('&', message));
            Object packet = chatPacket.getConstructor(chatBasePacket, byte.class).newInstance(serialized, (byte) 2);
            sendPacket.invoke(connection, packet);
        } catch (NoSuchFieldException | IllegalAccessException | InvocationTargetException | NoSuchMethodException  | InstantiationException e) {
            // Messenger.error(Bukkit.getConsoleSender(), "Error sending action bar to player: " + player.getName());
            // e.printStackTrace();
        }
    }

    private static void loadNmsClasses() {
        try {
            if (chatPacket == null) chatPacket = ReflectionUtil.getNMSClass("PacketPlayOutChat");
            if (chatBasePacket == null) chatBasePacket = ReflectionUtil.getNMSClass("IChatBaseComponent");
            if (chatSerializerPacket == null) chatSerializerPacket = ReflectionUtil.getNMSClass("ChatComponentText");
            if (packet == null) packet = ReflectionUtil.getNMSClass("Packet");
        } catch (ClassNotFoundException e) {
            // Messenger.error(Bukkit.getConsoleSender(), "Error loading nms classes for action bar util.");
        }
    }
}