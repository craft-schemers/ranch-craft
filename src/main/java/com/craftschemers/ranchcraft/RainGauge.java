package com.craftschemers.ranchcraft;

/**
    The rain gauge is a block with a custom model like a torch that naturally connects
    with redstone wire and outputs a signal. Its signal strength is dependent on how long
    it has been raining (need to figure out average rain times to find reasonable
    sweet spots).

    When right-clicked, it can be closed and maintain its signal strength until broken.
    If opened, the water will slowly evaporate after the rain.
     */
public class RainGauge extends Block {

    public RainGauge(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
        Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            player.sendMessage(new LiteralText("Hello, world!"), false);
        }

        return ActionResult.SUCCESS;
    }
}
