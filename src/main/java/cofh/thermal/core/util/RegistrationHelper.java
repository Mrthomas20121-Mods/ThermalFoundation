package cofh.thermal.core.util;

import cofh.core.item.*;
import cofh.lib.block.impl.crops.CropsBlockCoFH;
import cofh.lib.block.impl.crops.CropsBlockPerennial;
import cofh.lib.block.impl.crops.CropsBlockTall;
import cofh.thermal.lib.item.BlockItemAugmentable;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import static cofh.lib.util.constants.Constants.*;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.lib.common.ThermalFlags.*;
import static cofh.thermal.lib.common.ThermalItemGroups.*;
import static net.minecraft.block.AbstractBlock.Properties.of;

public class RegistrationHelper {

    public RegistrationHelper() {

    }

    // region BLOCKS
    public static void registerBlock(String name, Supplier<Block> sup) {

        registerBlock(name, sup, ID_THERMAL);
    }

    public static void registerBlock(String name, Supplier<Block> sup, BooleanSupplier showInGroups) {

        registerBlock(name, sup, showInGroups, ID_THERMAL);
    }

    public static void registerBlock(String name, Supplier<Block> sup, ItemGroup group, BooleanSupplier showInGroups) {

        registerBlock(name, sup, group, showInGroups, ID_THERMAL);
    }

    public static void registerBlock(String name, Supplier<Block> sup, Rarity rarity) {

        registerBlock(name, sup, rarity, ID_THERMAL);
    }

    public static void registerBlock(String name, Supplier<Block> sup, Rarity rarity, BooleanSupplier showInGroups) {

        registerBlock(name, sup, rarity, showInGroups, ID_THERMAL);
    }

    public static void registerBlock(String name, Supplier<Block> sup, ItemGroup group, Rarity rarity, BooleanSupplier showInGroups) {

        registerBlock(name, sup, group, rarity, showInGroups, ID_THERMAL);
    }

    // MOD ID
    public static void registerBlock(String name, Supplier<Block> sup, String modId) {

        registerBlock(name, sup, THERMAL_BLOCKS, Rarity.COMMON, TRUE, modId);
    }

    public static void registerBlock(String name, Supplier<Block> sup, ItemGroup group, String modId) {

        registerBlock(name, sup, group, Rarity.COMMON, TRUE, modId);
    }

    public static void registerBlock(String name, Supplier<Block> sup, BooleanSupplier showInGroups, String modId) {

        registerBlock(name, sup, THERMAL_BLOCKS, Rarity.COMMON, showInGroups, modId);
    }

    public static void registerBlock(String name, Supplier<Block> sup, ItemGroup group, BooleanSupplier showInGroups, String modId) {

        registerBlock(name, sup, group, Rarity.COMMON, showInGroups, modId);
    }

    public static void registerBlock(String name, Supplier<Block> sup, Rarity rarity, String modId) {

        registerBlock(name, sup, THERMAL_BLOCKS, rarity, TRUE, modId);
    }

    public static void registerBlock(String name, Supplier<Block> sup, ItemGroup group, Rarity rarity, String modId) {

        registerBlock(name, sup, group, rarity, TRUE, modId);
    }

    public static void registerBlock(String name, Supplier<Block> sup, Rarity rarity, BooleanSupplier showInGroups, String modId) {

        registerBlock(name, sup, THERMAL_BLOCKS, rarity, showInGroups, modId);
    }

    public static void registerBlock(String name, Supplier<Block> sup, ItemGroup group, Rarity rarity, BooleanSupplier showInGroups, String modId) {

        registerBlockAndItem(name, sup, () -> new BlockItemCoFH(BLOCKS.get(name), new Item.Properties().tab(group).rarity(rarity)).setShowInGroups(showInGroups).setModId(modId));
    }

    public static void registerBlockOnly(String name, Supplier<Block> sup) {

        BLOCKS.register(name, sup);
    }

    public static void registerBlockAndItem(String name, Supplier<Block> blockSup, Supplier<Item> itemSup) {

        BLOCKS.register(name, blockSup);
        ITEMS.register(name, itemSup);
    }
    // endregion

    // region AUGMENTABLE BLOCKS
    public static void registerAugmentableBlock(String name, Supplier<Block> sup, IntSupplier numSlots, BiPredicate<ItemStack, List<ItemStack>> validAugment, BooleanSupplier showInGroups) {

        registerAugmentableBlock(name, sup, numSlots, validAugment, showInGroups, ID_THERMAL);
    }

    public static void registerAugmentableBlock(String name, Supplier<Block> sup, IntSupplier numSlots, BiPredicate<ItemStack, List<ItemStack>> validAugment, String modId) {

        registerAugmentableBlock(name, sup, numSlots, validAugment, THERMAL_DEVICES, Rarity.COMMON, TRUE, modId);
    }

    public static void registerAugmentableBlock(String name, Supplier<Block> sup, IntSupplier numSlots, BiPredicate<ItemStack, List<ItemStack>> validAugment, BooleanSupplier showInGroups, String modId) {

        registerAugmentableBlock(name, sup, numSlots, validAugment, THERMAL_DEVICES, Rarity.COMMON, showInGroups, modId);
    }

    public static void registerAugmentableBlock(String name, Supplier<Block> sup, IntSupplier numSlots, BiPredicate<ItemStack, List<ItemStack>> validAugment, ItemGroup group, Rarity rarity, BooleanSupplier showInGroups, String modId) {

        BLOCKS.register(name, sup);
        ITEMS.register(name, (Supplier<Item>) () -> new BlockItemAugmentable(BLOCKS.get(name), new Item.Properties().tab(group).rarity(rarity)).setNumSlots(numSlots).setAugValidator(validAugment).setShowInGroups(showInGroups).setModId(modId));
    }
    // endregion

    // region BLOCK SETS
    public static void registerWoodBlockSet(String woodName, Material material, MaterialColor color, float hardness, float resistance, SoundType soundType, String modId) {

        registerBlock(woodName + "_planks", () -> new Block(of(material, color).strength(hardness, resistance).sound(soundType)), modId);
        registerBlock(woodName + "_slab", () -> new SlabBlock(of(material, color).strength(hardness, resistance).sound(soundType)), modId);
        registerBlock(woodName + "_stairs", () -> new StairsBlock(() -> BLOCKS.get(woodName + "_planks").defaultBlockState(), of(material, color).strength(hardness, resistance).sound(soundType)), modId);
        registerBlock(woodName + "_door", () -> new DoorBlock(of(material, color).strength(resistance).sound(soundType).noOcclusion()), modId);
        registerBlock(woodName + "_trapdoor", () -> new TrapDoorBlock(of(material, color).strength(resistance).sound(soundType).noOcclusion().isValidSpawn((state, reader, pos, entityType) -> false)), modId);
        registerBlock(woodName + "_button", () -> new WoodButtonBlock(of(Material.DECORATION).noCollission().strength(0.5F).sound(soundType)), modId);
        registerBlock(woodName + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, of(material, color).noCollission().strength(0.5F).sound(soundType)), modId);
        registerBlock(woodName + "_fence", () -> new FenceBlock(of(material, color).strength(hardness, resistance).sound(soundType)), modId);
        registerBlock(woodName + "_fence_gate", () -> new FenceGateBlock(of(material, color).strength(hardness, resistance).sound(soundType)), modId);
    }
    // endregion

    // region ITEMS
    public static RegistryObject<Item> registerItem(String name, Supplier<Item> sup) {

        return ITEMS.register(name, sup);
    }

    public static RegistryObject<Item> registerItem(String name, ItemGroup group) {

        return registerItem(name, group, Rarity.COMMON);
    }

    public static RegistryObject<Item> registerItem(String name, ItemGroup group, Rarity rarity) {

        return registerItem(name, () -> new ItemCoFH(new Item.Properties().tab(group).rarity(rarity)));
    }
    // endregion

    // region METAL SETS
    public static void registerMetalSet(String prefix, ItemGroup group, Rarity rarity) {

        registerMetalSet(prefix, group, rarity, TRUE, false, false);
    }

    public static void registerMetalSet(String prefix, ItemGroup group, Rarity rarity, BooleanSupplier showInGroups) {

        registerMetalSet(prefix, group, rarity, showInGroups, false, false);
    }

    public static void registerMetalSet(String prefix, ItemGroup group, BooleanSupplier showInGroups) {

        registerMetalSet(prefix, group, Rarity.COMMON, showInGroups, false, false);
    }

    public static void registerAlloySet(String prefix, ItemGroup group, Rarity rarity) {

        registerMetalSet(prefix, group, rarity, TRUE, false, true);
    }

    public static void registerAlloySet(String prefix, ItemGroup group, Rarity rarity, BooleanSupplier showInGroups) {

        registerMetalSet(prefix, group, rarity, showInGroups, false, true);
    }

    public static void registerAlloySet(String prefix, ItemGroup group, BooleanSupplier showInGroups) {

        registerMetalSet(prefix, group, Rarity.COMMON, showInGroups, false, true);
    }

    public static void registerVanillaMetalSet(String prefix, ItemGroup group) {

        registerMetalSet(prefix, group, Rarity.COMMON, TRUE, true, false);
    }

    public static void registerMetalSet(String prefix, ItemGroup group, Rarity rarity, BooleanSupplier showInGroups, boolean vanilla, boolean alloy) {

        // Hacky but whatever.
        //        if (prefix.equals("netherite")) {
        //            ITEMS.register(prefix + "_nugget", () -> new ItemCoFH(new Item.Properties().group(group).rarity(rarity)).setShowInGroups(showInGroups));
        //            ITEMS.register(prefix + "_dust", () -> new ItemCoFH(new Item.Properties().group(group).rarity(rarity)).setShowInGroups(showInGroups));
        //            ITEMS.register(prefix + "_gear", () -> new ItemCoFH(new Item.Properties().group(group).rarity(rarity)).setShowInGroups(showInGroups));
        //            ITEMS.register(prefix + "_plate", () -> new CountedItem(new Item.Properties().group(group).rarity(rarity)).setShowInGroups(() -> getFlag(FLAG_PLATES).getAsBoolean() && showInGroups.getAsBoolean()));
        //            ITEMS.register(prefix + "_coin", () -> new CoinItem(new Item.Properties().group(group).rarity(rarity)).setShowInGroups(() -> getFlag(FLAG_COINS).getAsBoolean() && showInGroups.getAsBoolean()));
        //            return;
        //        }

        if (!vanilla) {
            if (false) {    // TODO: 1.17 - if NOT alloy
                ITEMS.register("raw_" + prefix, () -> new ItemCoFH(new Item.Properties().tab(group).rarity(rarity)).setShowInGroups(showInGroups));
            }
            ITEMS.register(prefix + "_ingot", () -> new ItemCoFH(new Item.Properties().tab(group).rarity(rarity)).setShowInGroups(showInGroups));
            ITEMS.register(prefix + "_nugget", () -> new ItemCoFH(new Item.Properties().tab(group).rarity(rarity)).setShowInGroups(showInGroups));
        }
        ITEMS.register(prefix + "_dust", () -> new ItemCoFH(new Item.Properties().tab(group).rarity(rarity)).setShowInGroups(showInGroups));
        ITEMS.register(prefix + "_gear", () -> new ItemCoFH(new Item.Properties().tab(group).rarity(rarity)).setShowInGroups(showInGroups));
        ITEMS.register(prefix + "_plate", () -> new CountedItem(new Item.Properties().tab(group).rarity(rarity)).setShowInGroups(() -> getFlag(FLAG_PLATES).getAsBoolean() && showInGroups.getAsBoolean()));
        ITEMS.register(prefix + "_coin", () -> new CoinItem(new Item.Properties().tab(group).rarity(rarity)).setShowInGroups(() -> getFlag(FLAG_COINS).getAsBoolean() && showInGroups.getAsBoolean()));
    }
    // endregion

    // region GEM SETS
    public static void registerGemSet(String prefix, ItemGroup group, Rarity rarity, BooleanSupplier showInGroups) {

        registerGemSet(prefix, group, rarity, showInGroups, false);
    }

    public static void registerGemSet(String prefix, ItemGroup group, BooleanSupplier showInGroups) {

        registerGemSet(prefix, group, Rarity.COMMON, showInGroups, false);
    }

    public static void registerVanillaGemSet(String prefix, ItemGroup group) {

        registerGemSet(prefix, group, Rarity.COMMON, TRUE, true);
    }

    public static void registerGemSet(String prefix, ItemGroup group, Rarity rarity, BooleanSupplier showInGroups, boolean vanilla) {

        if (!vanilla) {
            ITEMS.register(prefix, () -> new ItemCoFH(new Item.Properties().tab(group).rarity(rarity)).setShowInGroups(showInGroups));
        }
        // ITEMS.register(prefix + "_nugget", () -> new ItemCoFH(new Item.Properties().group(group).rarity(rarity)));
        ITEMS.register(prefix + "_dust", () -> new ItemCoFH(new Item.Properties().tab(group).rarity(rarity)).setShowInGroups(showInGroups));
        ITEMS.register(prefix + "_gear", () -> new ItemCoFH(new Item.Properties().tab(group).rarity(rarity)).setShowInGroups(showInGroups));
        // ITEMS.register(prefix + "_plate", () -> new CountedItem(new Item.Properties().group(group).rarity(rarity)));
        // ITEMS.register(prefix + "_coin", () -> new CoinItem(new Item.Properties().group(group).rarity(rarity)));
    }
    // endregion

    // region CROPS
    public static void registerAnnual(String id) {

        BLOCKS.register(id, () -> new CropsBlockCoFH(of(Material.PLANT).noCollission().randomTicks().strength(0.0F, 0.0F).sound(SoundType.CROP)).crop(ITEMS.getSup(id)).seed(ITEMS.getSup(seeds(id))));
    }

    public static void registerTallAnnual(String id) {

        BLOCKS.register(id, () -> new CropsBlockTall(of(Material.PLANT).noCollission().randomTicks().strength(0.0F, 0.0F).sound(SoundType.CROP)).crop(ITEMS.getSup(id)).seed(ITEMS.getSup(seeds(id))));
    }

    public static void registerPerennial(String id) {

        registerPerennial(id, CropsBlockPerennial.DEFAULT_POST_HARVEST_AGE);
    }

    public static void registerPerennial(String id, int postHarvestAge) {

        BLOCKS.register(id, () -> new CropsBlockPerennial(of(Material.PLANT).noCollission().randomTicks().strength(0.0F, 0.0F).sound(SoundType.CROP)).postHarvestAge(postHarvestAge).crop(ITEMS.getSup(id)).seed(ITEMS.getSup(seeds(id))));
    }

    public static void registerCropAndSeed(String id) {

        registerCropAndSeed(id, THERMAL_FOODS);
    }

    public static void registerCropAndSeed(String id, Food food) {

        registerCropAndSeed(id, THERMAL_FOODS, food);
    }

    public static void registerCropAndSeed(String id, ItemGroup group) {

        registerCropAndSeed(id, group, null);
    }

    public static void registerCropAndSeed(String id, ItemGroup group, Food food) {

        if (food != null) {
            ITEMS.register(id, () -> new ItemCoFH(new Item.Properties().tab(group).food(food)).setModId(ID_THERMAL_CULTIVATION));
        } else {
            ITEMS.register(id, () -> new ItemCoFH(new Item.Properties().tab(group)).setModId(ID_THERMAL_CULTIVATION));
        }
        ITEMS.register(seeds(id), () -> new BlockNamedItemCoFH(BLOCKS.get(id), new Item.Properties().tab(group)).setModId(ID_THERMAL_CULTIVATION));
    }

    public static void registerSpores(String id) {

        registerSpores(id, THERMAL_FOODS);
    }

    public static void registerSpores(String id, ItemGroup group) {

        ITEMS.register(spores(id), () -> new BlockNamedItemCoFH(BLOCKS.get(id), new Item.Properties().tab(group)).setModId(ID_THERMAL_CULTIVATION));
    }

    public static String deepslate(String id) {

        return "deepslate_" + id;
    }

    public static String block(String id) {

        return id + "_block";
    }

    public static String seeds(String id) {

        return id + "_seeds";
    }

    public static String spores(String id) {

        return id + "_spores";
    }
    // endregion
}
