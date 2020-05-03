package theShowman.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.RefreshEnergyEffect;
import theShowman.characters.TheShowman;
import theShowman.effects.ShowmanEnergyOrbRechargeConfettiEffect;
import theShowman.effects.ShowmanEnergyOrbRechargeStarEffect;

public class ConfettiRefreshEnergyPatch {
    private static float VFX_DURATION;
    @SpirePatch(
            clz = RefreshEnergyEffect.class,
            method = "update"
    )
    public static class playCustomVFX
    {
        @SpirePrefixPatch
        public static void refresh(RefreshEnergyEffect __instance)
        {
            if(__instance.duration == VFX_DURATION && AbstractDungeon.player instanceof TheShowman)
            {
                for(int i = 0; i < 25; i++) {
                    AbstractDungeon.topLevelEffectsQueue.add(new ShowmanEnergyOrbRechargeConfettiEffect());
                }
                for(int j = 0; j < 5; j++)
                {
                    AbstractDungeon.topLevelEffectsQueue.add(new ShowmanEnergyOrbRechargeStarEffect());
                }
            }
        }
    }
    static
    {
        VFX_DURATION = (float)ReflectionHacks.getPrivateStatic(RefreshEnergyEffect.class, "EFFECT_DUR");
    }
}
