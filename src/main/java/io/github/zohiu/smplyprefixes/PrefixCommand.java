package io.github.zohiu.smplyprefixes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.lang.reflect.Array;
import java.util.*;

public class PrefixCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            Map<TextComponent, Long> list = new LinkedHashMap<TextComponent, Long>();
            list.put(Component.text("§aHey! \n")
                    .hoverEvent(Component.text("§7Cool book, right??").asHoverEvent()), 0L);

            list.put(Component.text("§7This book is pure awesome. \n"), 10L);

            list.put(Component.text("Or is it not? \n\n"), 10L);

            list.put(Component.text("§7Just click "), 10L);
            list.put(Component.text("§7§lhere")
                    .hoverEvent(Component.text("UwU do it").asHoverEvent())
                    .clickEvent(ClickEvent.runCommand("lol")), 0L);
            list.put(Component.text(" §7to see for yourself. "), 0L);

            showBookText(player, list, 1L);

        }
        return true;
    }


    public void showBookText(Player player, Map<TextComponent, Long> map_in, Long delay) {
        final Map<TextComponent, Long> map = map_in;
        Long delay_multi = 0L;
        TextComponent prev_key = Component.text("");

        Integer global_index = 0;

        // Loop all strings and schedule them
        for (Map.Entry<TextComponent, Long> entry : map.entrySet()) {
            final TextComponent current_key = entry.getKey();
            final Long current_val = entry.getValue();
            final Integer test_history_index = global_index;

            Long delay_addon = delay * prev_key.content().length();

            Bukkit.getScheduler().scheduleSyncDelayedTask(SMPlyPrefixes.getInstance(), new Runnable() {
                @Override
                public void run() {
                    // Loop all characters in each string and schedule them
                    for (int i = 0; i <= current_key.content().length(); i++) {
                        final Integer current_index = i;

                        Bukkit.getScheduler().scheduleSyncDelayedTask(SMPlyPrefixes.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                TextComponent total_keep = Component.text("");

                                for (int i = 0; i < test_history_index; i++) {
                                    total_keep = total_keep.append((TextComponent) map.keySet().toArray()[i]);
                                }

                                ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
                                BookMeta bookMeta = (BookMeta) book.getItemMeta();

                                //set the title and author of this book
                                bookMeta.setTitle("Animated Book");
                                bookMeta.setAuthor("Zohiu");

                                bookMeta.addPages(total_keep.append(Component.text(current_key.content().substring(0, current_index))));

                                book.setItemMeta(bookMeta);

                                player.openBook(book);
                                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);

                            }
                        }, delay * current_index);
                    }
                }
            }, current_val + delay_multi + delay_addon);

            delay_multi += current_val + delay_addon;

            prev_key = current_key;
            global_index += 1;
        }
    }

    @Override
    public List<String> onTabComplete (CommandSender sender, Command cmd, String label, String[] args){
        if(cmd.getName().equalsIgnoreCase("xppickup") && args.length >= 0){
            if(sender instanceof Player){
                List<String> list = new ArrayList<>();

                list.add("gui");

                return list;
            }
        }
        return null;
    }
}
