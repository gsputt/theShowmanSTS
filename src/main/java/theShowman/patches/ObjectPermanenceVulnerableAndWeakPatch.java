package theShowman.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theShowman.powers.ObjectPermanencePower;


public class ObjectPermanenceVulnerableAndWeakPatch {
    @SpirePatch(
            clz = VulnerablePower.class,
            method = "atEndOfRound"
    )
    public static class vulnerablePatch
    {
        @SpirePrefixPatch
        public static SpireReturn dontTickDownVulnerable(VulnerablePower __instance)
        {
            if(__instance.owner.hasPower(ObjectPermanencePower.POWER_ID)) {
                return SpireReturn.Return(null);
            }
            else
            {
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(
            clz = WeakPower.class,
            method = "atEndOfRound"
    )
    public static class weakPatch
    {
        @SpirePrefixPatch
        public static SpireReturn dontTickDownWeak(WeakPower __instance)
        {
            if(__instance.owner.hasPower(ObjectPermanencePower.POWER_ID)) {
                return SpireReturn.Return(null);
            }
            else
            {
                return SpireReturn.Continue();
            }
        }
    }

}
