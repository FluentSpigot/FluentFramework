package io.github.jwdeveloper.ff.core.spigot.items;

import com.google.common.io.BaseEncoding;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemStackUtils {

    /**
     * Converts the player inventory to a String array of Base64 strings. First string is the content and second string is the armor.
     *
     * @param playerInventory to turn into an array of strings.
     * @return Array of strings: [ main content, armor content ]
     * @throws IllegalStateException
     */
    public static String[] playerInventoryToBase64(PlayerInventory playerInventory) throws IllegalStateException {
        //get the main content part, this doesn't return the armor
        String content = toBase64(playerInventory);
        String armor = itemStackArrayToBase64(playerInventory.getArmorContents());

        return new String[]{content, armor};
    }

    /**
     * A method to serialize an {@link ItemStack} array to Base64 String.
     * <p>
     * <p/>
     * <p>
     * Based off of {@link #toBase64(Inventory)}.
     *
     * @param items to turn into a Base64 String.
     * @return Base64 string of the items.
     * @throws IllegalStateException
     */
    public static String itemStackArrayToBase64(ItemStack[] items) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(items.length);

            // Save every element in the list
            for (int i = 0; i < items.length; i++) {
                dataOutput.writeObject(items[i]);
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    /**
     * A method to serialize an inventory to Base64 string.
     * <p>
     * <p/>
     * <p>
     * Special thanks to Comphenix in the Bukkit forums or also known
     * as aadnk on GitHub.
     *
     * <a href="https://gist.github.com/aadnk/8138186">Original Source</a>
     *
     * @param inventory to serialize
     * @return Base64 string of the provided inventory
     * @throws IllegalStateException
     */
    public static String toBase64(Inventory inventory) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(inventory.getSize());

            // Save every element in the list
            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }


    public static String toBase64(ItemStack[] items) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(items.length);

            // Save every element in the list
            for (int i = 0; i < items.length; i++) {
                dataOutput.writeObject(items[i]);
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }


    /**
     * A method to get an {@link Inventory} from an encoded, Base64, string.
     * <p>
     * <p/>
     * <p>
     * Special thanks to Comphenix in the Bukkit forums or also known
     * as aadnk on GitHub.
     *
     * <a href="https://gist.github.com/aadnk/8138186">Original Source</a>
     *
     * @param data Base64 string of data containing an inventory.
     * @return Inventory created from the Base64 string.
     * @throws IOException
     */
    public static Inventory fromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt());

            // Read the serialized inventory
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }

            dataInput.close();
            return inventory;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    /**
     * Gets an array of ItemStacks from Base64 string.
     * <p>
     * <p/>
     * <p>
     * Base off of {@link #fromBase64(String)}.
     *
     * @param data Base64 string to convert to ItemStack array.
     * @return ItemStack array created from the Base64 string.
     * @throws IOException
     */
    public static ItemStack[] itemStackArrayFromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            // Read the serialized inventory
            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return items;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }


    public static String serializeItemStack(ItemStack is) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             BukkitObjectOutputStream bukkitOutputStream = new BukkitObjectOutputStream(outputStream)) {
            bukkitOutputStream.writeObject(is);
            String encoded = outputStream.toString();
            return encoded;
        } catch (IOException e) {
            throw new RuntimeException("Unable to serialize ItemStack!", e);
        }
    }

    public static String serializeIntoFormattingCodes(ItemStack is) {
        // Commence crappy hack to hide String from client where needed.
        String hex = BaseEncoding.base16().encode(serializeItemStack(is).getBytes());

        StringBuilder formatting = new StringBuilder();
        for (char c : hex.toCharArray()) {
            formatting.append(ChatColor.COLOR_CHAR).append(c);
        }
        return formatting.toString();
    }

    public static ItemStack deserializeItemStack(byte[] bytes) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
             BukkitObjectInputStream bukkitInputStream = new BukkitObjectInputStream(inputStream)) {
            ItemStack decoded = (ItemStack) bukkitInputStream.readObject();
            return decoded;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Unable to deserialize ItemStack!", e);
        }
    }

    public static ItemStack deserializeFromFormattingCodes(String s) {
        s = s.replace(String.valueOf(ChatColor.COLOR_CHAR), "").toUpperCase();
        return deserializeItemStack(BaseEncoding.base16().decode(s));
    }

    public static List<ItemStack> getNotEmptyItemStack(ItemStack[] array) {
        var result = new ArrayList<ItemStack>();
        for (var item : array) {
            if (item == null) {
                continue;
            }
            if (item.getType().isAir()) {
                continue;
            }
            result.add(item);
        }
        return result;
    }

    public static boolean isEmptyArray(ItemStack[] array) {
        var size = array.length;
        var emptySlots = 0;
        for (var item : array) {
            if (item == null) {
                emptySlots++;
                continue;
            }
            if (item.getType().isAir()) {
                emptySlots++;
                continue;
            }
        }
        return size == emptySlots;
    }

    public static void dropItems(Location location, ItemStack[] itemStacks) {
        var world = location.getWorld();
        for (var it : itemStacks) {
            if (it == null) {
                continue;
            }
            if (it.getType().isAir()) {
                continue;
            }
            world.dropItem(location, it);
        }
    }
}
