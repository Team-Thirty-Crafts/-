package jp.thirtycraft.randommob;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public  class Randommob extends JavaPlugin implements Listener {
    private Random random;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);

        random = new Random();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // エンダーアイを投げたイベントのみ処理する
        if (event.getItem() != null && event.getItem().getType() == Material.ENDER_EYE) {
            // スポーンさせるmobのエンティティタイプをランダムに選択する
            EntityType[] entityTypes = EntityType.values();
            EntityType entityType = entityTypes[random.nextInt(entityTypes.length)];

            // プレイヤーの位置から一定の距離離れた場所にmobをスポーンさせる
            Player player = event.getPlayer();
            Location location = player.getLocation().add(player.getLocation().getDirection().multiply(5));
            player.getWorld().spawnEntity(location, entityType);

            // イベントをキャンセルしてエンダーアイを消費しないようにする
            event.setCancelled(true);
            if (event.getItem().getAmount() > 1) {
                event.getItem().setAmount(event.getItem().getAmount() - 1);
            } else {
                player.getInventory().remove(event.getItem());
            }

            // エンダーアイの代わりにTNTを追加する
            player.getInventory().addItem(new ItemStack(Material.TNT));
        }
    }
}

