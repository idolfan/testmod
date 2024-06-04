package net.idolfan.testmod.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.idolfan.testmod.TestMod;
import net.idolfan.testmod.util.StateSaverAndLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;

import java.util.HashMap;
import java.util.Map;

public class ExtendWorldBorderHandler implements ServerEntityEvents.Unload {

    public static Map<String, Integer> sacrificedAmounts = new HashMap<String, Integer>();

    @Override
    public void onUnload(Entity entity, ServerWorld world) {
        if (entity instanceof ItemEntity && entity.wasOnFire) {
            StateSaverAndLoader.getServerState(world.getServer());
            double[] position = new double[]{entity.getX(), entity.getZ()};

            ItemStack itemStack = ((ItemEntity) entity).getStack();
            int amount = itemStack.getCount();
            String name = itemStack.getItem().getName().getString().toLowerCase().replaceAll("\\s", "");
            System.out.println("Amount: " + amount + " Name: " + name);
            if (!sacrificedAmounts.containsKey(name)) {
                sacrificedAmounts.put(name, 1);
                amount --;
                updateBorder(1, position);
            }
            for(int i = 0; i < amount; i++) {
                int oldAmount = sacrificedAmounts.get(name);
                int newAmount = oldAmount + 1;
                sacrificedAmounts.put(name, newAmount);
                updateBorder(newAmount, position);

            }

        }
    }

    private void updateBorder(int count, double[] pos) {
        for (World w : TestMod.server_world) {
            WorldBorder worldBorder = w.getWorldBorder();
            double radius = worldBorder.getSize();
            double bonusRadius = calculateIncrease(count);
            worldBorder.setSize(radius + bonusRadius);

            double[] worldBorderCenter = new double[] { worldBorder.getCenterX(), worldBorder.getCenterZ() };
            double[] relativeToCenter = new double[] { pos[0] - worldBorderCenter[0], pos[1] - worldBorderCenter[1] };
            double[] direction = new double[] {(double)Integer.signum((int)relativeToCenter[0]), (double)Integer.signum((int)relativeToCenter[1]) };
            worldBorder.setCenter(worldBorderCenter[0] + direction[0] * bonusRadius * 0.5, worldBorderCenter[1] + direction[1] * bonusRadius * 0.5);
        }
    }

    public static double calculateIncrease(int count){
        return 1.0/Math.sqrt(count+5);
    }
}
