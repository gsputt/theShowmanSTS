package theShowman.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;
import theShowman.powers.onAttackedExceptItRespectsBlockInterface;

public class onAttackedExceptItRespectsBlockPatch {

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "damage"
    )
    public static class playerOnAttacked {

        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"damageAmount"}
        )
        public static void Insert(AbstractPlayer __instance, DamageInfo info, @ByRef int[] damageAmount) {
            for (AbstractPower p : __instance.powers) {
                if (p instanceof onAttackedExceptItRespectsBlockInterface) {
                    damageAmount[0] = ((onAttackedExceptItRespectsBlockInterface) p).onAttackedExceptItRespectsBlock(info, damageAmount[0]);
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "decrementBlock");
                int[] found = LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
                return found;
            }
        }
    }

    @SpirePatch(
            clz = AbstractMonster.class,
            method = "damage"
    )
    public static class monsterOnAttacked {

        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"damageAmount"}
        )
        public static void Insert(AbstractMonster __instance, DamageInfo info, @ByRef int[] damageAmount) {
            for (AbstractPower p : __instance.powers) {
                if (p instanceof onAttackedExceptItRespectsBlockInterface) {
                    damageAmount[0] = ((onAttackedExceptItRespectsBlockInterface) p).onAttackedExceptItRespectsBlock(info, damageAmount[0]);
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractMonster.class, "decrementBlock");
                int[] found = LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
                return found;
            }
        }
    }
}
