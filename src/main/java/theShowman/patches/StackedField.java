package theShowman.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.relics.Millstone;

@SpirePatch(
        clz = AbstractCard.class,
        method=SpirePatch.CLASS
)
public class StackedField {
    public static SpireField<Integer> Stacked = new SpireField<>(() -> -1);
    public static SpireField<Integer> originalDamage = new SpireField<>(() -> -1);
    public static SpireField<Integer> originalBlock = new SpireField<>(() -> -1);

    @SpirePatch(
            clz = AbstractCard.class,
            method = "makeStatEquivalentCopy"
    )
    public static class MakeStatEquivalentCopy {
        public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance) {
            Stacked.set(__result, Stacked.get(__instance));
            originalDamage.set(__result, originalDamage.get(__instance));
            originalBlock.set(__result, originalBlock.get(__instance));
            return __result;
        }
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "upgradeDamage"
    )
    public static class upgradeDamage {
        public static void Prefix(AbstractCard __instance) {
            if(originalDamage.get(__instance) != -1 && Stacked.get(__instance) != -1) {
                __instance.baseDamage = originalDamage.get(__instance);
                originalDamage.set(__instance, -1);
            }
        }
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "upgradeBlock"
    )
    public static class upgradeBlock {
        public static void Prefix(AbstractCard __instance) {
            if(originalBlock.get(__instance) != -1 && Stacked.get(__instance) != -1)
            {
                __instance.baseBlock = originalBlock.get(__instance);
                originalBlock.set(__instance, -1);
            }
        }
    }


    @SpirePatch(
            clz = AbstractCard.class,
            method = "applyPowers"
    )
    public static class StackedApplyPowers {
        @SpirePrefixPatch
        public static void PrefixApplyPowers(AbstractCard __instance) {
            StackedCalculation.Nested(__instance);
        }
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "calculateCardDamage"
    )
    public static class StackedCalculateCardDamage {
        @SpirePrefixPatch
        public static void PrefixCalculateCardDamage(AbstractCard __instance, AbstractMonster mo) {
            StackedCalculation.Nested(__instance);
        }
    }

    public static class StackedCalculation {
        public static void Nested(AbstractCard __instance) {
            if(Stacked.get(__instance) != -1) {
                if (AbstractDungeon.getCurrRoom() != null) {
                    if (AbstractDungeon.player != null) {

                        if(originalDamage.get(__instance) == -1)
                        {
                            originalDamage.set(__instance, __instance.baseDamage);
                        }
                        else
                        {
                            __instance.baseDamage = originalDamage.get(__instance);
                        }
                        if(originalBlock.get(__instance) == -1)
                        {
                            originalBlock.set(__instance, __instance.baseBlock);
                        }
                        else
                        {
                            __instance.baseBlock = originalBlock.get(__instance);
                        }
                        __instance.magicNumber = __instance.baseMagicNumber;

                        int millstone = 0;
                        if(AbstractDungeon.player.hasRelic(Millstone.ID))
                        {
                            millstone = Millstone.MILLSTONE_BONUS;
                        }
                        if (AbstractDungeon.player.drawPile.size() - millstone > 0) {
                            __instance.isMagicNumberModified = true;
                            for (int i = 0; i < Stacked.get(__instance); i++) {
                                __instance.baseDamage = __instance.baseDamage - (AbstractDungeon.player.drawPile.size() - millstone);
                                __instance.baseBlock = __instance.baseBlock - (AbstractDungeon.player.drawPile.size() - millstone);
                                __instance.magicNumber = __instance.magicNumber - (AbstractDungeon.player.drawPile.size() - millstone);
                            }
                            if (__instance.baseDamage < 0) {
                                __instance.baseDamage = 0;
                            }
                            if (__instance.baseBlock < 0) {
                                __instance.baseBlock = 0;
                            }
                            if (__instance.magicNumber < 0) {
                                __instance.magicNumber = 0;
                            }
                        }
                    }
                }
            }
        }
    }
}
