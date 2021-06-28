package rmc.mixins.save_tile_ownership.inject;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.ActionResultType;
import rmc.libs.tile_ownership.TileOwnership;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
@Mixin(value = BlockItem.class)
public abstract class BlockItemMixin {

    @Inject(method = "Lnet/minecraft/item/BlockItem;place(Lnet/minecraft/item/BlockItemUseContext;)Lnet/minecraft/util/ActionResultType;",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/block/Block;setPlacedBy(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V"))
    private void saveIdToNBT(BlockItemUseContext context, CallbackInfoReturnable<ActionResultType> mixin) {
        PlayerEntity player;
        if ((player = context.getPlayer()) != null && TileOwnership.convert(context.getLevel(), context.getClickedPos()) != null) {
            TileOwnership.saveOwner(TileOwnership.convert(context.getLevel(), context.getClickedPos()), player.getUUID());
        }
    }

}