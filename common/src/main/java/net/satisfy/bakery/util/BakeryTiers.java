package net.satisfy.bakery.util;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

@SuppressWarnings("all")
public enum BakeryTiers implements Tier
{
    IRON(2, 200, 5.0f, 0.0f, 12, () -> Ingredient.of(Items.IRON_INGOT));

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    private BakeryTiers(int j, int k, float f, float g, int l, Supplier<Ingredient> supplier) {
        this.level = j;
        this.uses = k;
        this.speed = f;
        this.damage = g;
        this.enchantmentValue = l;
        this.repairIngredient = new LazyLoadedValue<Ingredient>(supplier);
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}

