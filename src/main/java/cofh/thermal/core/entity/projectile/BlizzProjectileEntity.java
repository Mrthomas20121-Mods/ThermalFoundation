package cofh.thermal.core.entity.projectile;

import cofh.lib.util.AreaUtils;
import cofh.lib.util.Utils;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import static cofh.lib.util.references.CoreReferences.CHILLED;
import static cofh.thermal.core.init.TCoreReferences.BLIZZ_PROJECTILE_ENTITY;
import static cofh.thermal.lib.common.ThermalIDs.ID_BLIZZ;

public class BlizzProjectileEntity extends ElementalProjectileEntity {

    private static final int CLOUD_DURATION = 20;
    public static float defaultDamage = 5.0F;
    public static int effectAmplifier = 0;
    public static int effectDuration = 100;
    public static int effectRadius = 2;

    public BlizzProjectileEntity(EntityType<? extends DamagingProjectileEntity> type, World world) {

        super(type, world);
    }

    public BlizzProjectileEntity(LivingEntity shooter, double accelX, double accelY, double accelZ, World world) {

        super(BLIZZ_PROJECTILE_ENTITY, shooter, accelX, accelY, accelZ, world);
    }

    public BlizzProjectileEntity(double x, double y, double z, double accelX, double accelY, double accelZ, World world) {

        super(BLIZZ_PROJECTILE_ENTITY, x, y, z, accelX, accelY, accelZ, world);
    }

    @Override
    protected IParticleData getTrailParticle() {

        return ParticleTypes.ITEM_SNOWBALL;
    }

    @Override
    protected void onHit(RayTraceResult result) {

        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult) result).getEntity();
            if (entity.isOnFire()) {
                entity.clearFire();
            }
            if (entity.hurt(BlizzDamageSource.causeDamage(this, getOwner()), getDamage(entity)) && !entity.isInvulnerable() && entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) entity;
                living.addEffect(new EffectInstance(CHILLED, getEffectDuration(entity), getEffectAmplifier(entity), false, false));
            }
        }
        if (Utils.isServerWorld(level)) {
            if (effectRadius > 0) {
                AreaUtils.freezeNearbyGround(this, level, this.blockPosition(), effectRadius);
                AreaUtils.freezeSurfaceWater(this, level, this.blockPosition(), effectRadius, false);
                AreaUtils.freezeSurfaceLava(this, level, this.blockPosition(), effectRadius, false);
                makeAreaOfEffectCloud();
            }
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.remove();
        }
    }

    private void makeAreaOfEffectCloud() {

        AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(level, getX(), getY(), getZ());
        cloud.setRadius(1);
        cloud.setParticle(ParticleTypes.ITEM_SNOWBALL);
        cloud.setDuration(CLOUD_DURATION);
        cloud.setWaitTime(0);
        cloud.setRadiusPerTick((effectRadius - cloud.getRadius()) / (float) cloud.getDuration());

        level.addFreshEntity(cloud);
    }

    // region HELPERS
    @Override
    public float getDamage(Entity target) {

        return target.fireImmune() ? defaultDamage + 3.0F : defaultDamage;
    }

    @Override
    public int getEffectAmplifier(Entity target) {

        return effectAmplifier;
    }

    @Override
    public int getEffectDuration(Entity target) {

        return effectDuration;
    }
    // endregion

    // region DAMAGE SOURCE
    protected static class BlizzDamageSource extends EntityDamageSource {

        public BlizzDamageSource(Entity source) {

            super(ID_BLIZZ, source);
        }

        public static DamageSource causeDamage(BlizzProjectileEntity entityProj, Entity entitySource) {

            return (new IndirectEntityDamageSource(ID_BLIZZ, entityProj, entitySource == null ? entityProj : entitySource)).setProjectile();
        }

    }
    // endregion
}
