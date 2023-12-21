package hydrocks.disaster.NaturalDisasters;

import hydrocks.disaster.NaturalDisasters.util.AIHandler;
import hydrocks.disaster.NaturalDisasters.util.Disaster;
import hydrocks.disaster.enums.Disasters;
import hydrocks.disaster.methods.Chat;
import hydrocks.disaster.particles.ParticleShapes;
import hydrocks.disaster.utils.VectorUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_20_R1.CraftParticle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class Cyclone extends Disaster {
    private static final int delayTime = 100;
    private static final int tickTime = 2;
    private static final double initialRadius = 1.0;

    private double rad;
    private float yaw = 0;
    private final Location location;
    public Cyclone(Location spawn) {
        super(Disasters.CYCLONE, delayTime, tickTime);
        this.location = spawn.add(VectorUtil.getRandVector(150, 0, 150));
        this.rad = initialRadius;
    }

    /**
     * The runnable for the cyclone, just handles everything, Don't touch unless you know what you're doing.
     */
    @Override
    public void run() {
        try {
            if (this.isWithinFirstHalfDuration()) {
                this.handleDisaster();

                // Sends the sound of the cyclone to the online players.
                for (Player player : Bukkit.getOnlinePlayers())
                    player.playSound(this.location, Sound.ENTITY_ENDER_DRAGON_FLAP, 5, 0);

                // Moves the cyclone
                this.moveCyclone();

                // Pulls the blocks in the cyclone.
                this.doPull();

                // Self-explanatory
                if (this.ticks % 3 == 0) this.doEntityPull();
                this.particles();
            } else this.cancel();
        } catch (IllegalStateException e) {
            Bukkit.getLogger().warning("Error trying to send Cyclone " + e);
        }

        // Send the size of the cyclone to all the player's action bar.
        Chat.sendActionBarMessageToAll("&4&lCyclone &7Size: " + String.format("%.2f", rad));
    }

    /**
     * Check if the cyclone is within the first half of its duration.
     *
     * @return True if within the first half, false otherwise.
     */
    private boolean isWithinFirstHalfDuration() {
        return ticks < disaster.getDurationInSeconds() / 2;
    }

    /**
     * Moves the cyclone towards the closest player online
     */
    private void moveCyclone() {
        Player p = AIHandler.closestPlayer(this.location);

        // Check If the closest player is null or not, IF Nonnull continue
        if (p != null) {
            // Get the vector
            Vector v = p.getLocation().toVector().subtract(this.location.toVector()).normalize().multiply(0.35);
            v.setY(0);

            this.location.add(v);
        }
    }

    /**
     * Sends the cyclone particles, It looks real cool...
     */
    private void particles() {
        // Just the tornado particle formula

        double tempRad = 1;
        for (int i = 0; i < 75; i++) {
            ParticleShapes.draw2DShape(((CraftParticle.toNMS(Particle.SNOWFLAKE))), 3, this.rad + (i > 12 ? (tempRad *= 1.075) : 0), this.location.clone().add(0, i, 0), 0, yaw + 20 * i, false);
        }
        yaw+=5;
        this.rad+=0.08;
    }

    /**
     * Pulls any entities within the cyclones radius, to simulate a real tornado, they will fly around and then
     * and be damaged for 10% of their max health
     */
    @SuppressWarnings("deprecation")
    private void doEntityPull() {
        double value = this.rad * 3;
        Location loc = this.location.clone();

        if (this.location.getWorld() != null) {
            // Loop through entities within rad * 5
            this.location.getWorld().getNearbyEntities(this.location, this.rad * 5, this.rad * 5, this.rad * 5).forEach(entity -> {

                // Use vectors to accurately check hitbox
                Vector min = VectorUtil.getMin(loc, value, value, value);
                Vector max = VectorUtil.getMax(loc, value, value, value);


                // If player's bounding box overlaps with the vectors
                if (entity instanceof LivingEntity ent && ent.getBoundingBox().overlaps(min, max)) {
                    if (ent instanceof Player p)
                        if (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR) return;

                    // Do stuff
                    Location pLoc = location.clone().add(0, 20, 0).add(VectorUtil.getRandVector(rad * 2, rad * 2, rad * 2));
                    ent.setVelocity(getVector(pLoc, ent.getLocation(), 1));
                    ent.damage(ent.getMaxHealth() * 0.1);
                }
            });
        }
    }


    /**
     * Gets the pulled blocks inside the cyclone
     */
    private void doPull() {
        final Random rand = new Random();
        int value = (int) this.rad * 2;
        Location loc = this.location.clone().subtract(0, 7.5, 0);

        // Loop through 5 blocks at a time.
        for (int i = 0; i < 5; i++) {
            // Get the random values for X and Z coords
            int randX = rand.nextInt(value) - value / 2;
            int randZ = rand.nextInt(value) - value / 2;


            // Loop through the Y value to get blocks above the location (so it takes blocks from the top
            for (int y = 0; y < 10; y++) {
                Block b = loc.clone().add(randX, y, randZ).getBlock();
                if (!b.getType().isAir()) {
                    this.doBlockPull(b);
                }
            }
        }
    }

    /**
     * Pulls any blocks within the cyclones radius, to simulate a real tornado, they will fly around and then
     * de-spawn in 5 seconds
     *
     * @param b The block that was in the cyclones radius
     */
    private void doBlockPull(Block b) {
        Location loc = b.getLocation();
        final BlockData data = b.getBlockData();
        b.setType(Material.AIR);

        // Summon the falling block with the data of the block that gets destroyed
        final FallingBlock fb = this.location.getWorld().spawnFallingBlock(loc, data);
        fb.setInvulnerable(true);
        fb.setGravity(true);
        new BukkitRunnable() {
            int cT = 0;
            float speed = 1.5f;
            public void run() {
                // Check for the duration and if the block is dead
                if (cT < 100 && !fb.isDead()) {

                    // Everytime cT is a multiple of 30, continue
                    if (cT % 30 == 0) {
                        // Make the location and get the vector
                        Location loc = location.clone().add(0, 20, 0).add(VectorUtil.getRandVector(rad * 2, rad * 2, rad * 2));
                        Vector v = getVector(loc, fb.getLocation(), speed);

                        // Set the vector to the block, simulating it flying around in the cyclone
                        fb.setVelocity(v);

                        // Gradually decrease the speed
                        speed *= 0.75f;
                    }
                } else {
                    fb.remove();
                    this.cancel();
                }
                cT++;
            }
        }.runTaskTimer(hydrocks.disaster.Disaster.getPlugin(), 0, 1);
    }

    /**
     * Gets the vector for the methods
     *
     * @param to The location you want the [from] to go towards
     * @param from The location you start with
     * @param multi The speed of which you want the [from] to go towards the [to]
     * @return Returns the calculated vector
     */
    private Vector getVector(Location to, Location from, float multi) {
        return to.toVector().subtract(from.toVector()).normalize().multiply(multi);
    }
}
