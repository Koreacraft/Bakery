package net.satisfy.bakery.client.gui.handler;


import de.cristelknight.doapi.client.recipebook.IRecipeBookGroup;
import de.cristelknight.doapi.client.recipebook.handler.AbstractRecipeBookGUIScreenHandler;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.satisfy.bakery.client.gui.handler.slot.ExtendedSlot;
import net.satisfy.bakery.client.gui.handler.slot.StoveOutputSlot;
import net.satisfy.bakery.client.recipebook.group.StoveRecipeBookGroup;
import net.satisfy.bakery.recipe.StoveRecipe;
import net.satisfy.bakery.registry.RecipeTypeRegistry;
import net.satisfy.bakery.registry.ScreenHandlerTypeRegistry;

import java.util.List;

public class StoveGuiHandler extends AbstractRecipeBookGUIScreenHandler {
    public static final int INPUTS = 4;
    public static final int FUEL_SLOT = 4;
    public static final int OUTPUT_SLOT = 0;

    public StoveGuiHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(5), new SimpleContainerData(4));
    }

    public StoveGuiHandler(int syncId, Inventory playerInventory, Container inventory, ContainerData delegate) {
        super(ScreenHandlerTypeRegistry.STOVE_SCREEN_HANDLER.get(), syncId, INPUTS, playerInventory, inventory, delegate);
        buildBlockEntityContainer(playerInventory, inventory);
        buildPlayerContainer(playerInventory);
    }

    private void buildBlockEntityContainer(Inventory playerInventory, Container inventory) {
        this.addSlot(new StoveOutputSlot(playerInventory.player, inventory, 0, 126, 42));

        this.addSlot(new ExtendedSlot(inventory, 1, 29, 18, this::isIngredient));
        this.addSlot(new ExtendedSlot(inventory, 2, 47, 18, this::isIngredient));
        this.addSlot(new ExtendedSlot(inventory, 3, 65, 18, this::isIngredient));

        this.addSlot(new ExtendedSlot(inventory, 4, 42, 48, StoveGuiHandler::isFuel));

    }

    private void buildPlayerContainer(Inventory playerInventory) {
        int i;
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    private boolean isIngredient(ItemStack stack) {
        return this.world.getRecipeManager().getAllRecipesFor(RecipeTypeRegistry.STOVE_RECIPE_TYPE.get()).stream().anyMatch(recipe -> recipe.getIngredients().stream().anyMatch(x -> x.test(stack)));
    }

    private static boolean isFuel(ItemStack stack) {
        return AbstractFurnaceBlockEntity.isFuel(stack);
    }

    public int getScaledProgress(int arrowWidth) {
        final int progress = this.propertyDelegate.get(2);
        final int totalProgress = this.propertyDelegate.get(3);
        if (progress == 0) {
            return 0;
        }
        return progress * arrowWidth / totalProgress + 1;
    }

    public boolean isBeingBurned() {
        return propertyDelegate.get(1) != 0;
    }

    @Override
    public List<IRecipeBookGroup> getGroups() {
        return StoveRecipeBookGroup.STOVE_GROUPS;
    }

    @Override
    public boolean hasIngredient(Recipe<?> recipe) {
        if (recipe instanceof StoveRecipe StoveRecipe) {
            for (Ingredient ingredient : StoveRecipe.getIngredients()) {
                boolean found = false;
                for (Slot slot : this.slots) {
                    if (ingredient.test(slot.getItem())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int getCraftingSlotCount() {
        return INPUTS;
    }

}
