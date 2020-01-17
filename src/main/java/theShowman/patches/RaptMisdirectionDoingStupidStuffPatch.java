package theShowman.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import javassist.CtBehavior;
import theShowman.cards.RaptMisdirection;



public class RaptMisdirectionDoingStupidStuffPatch {
    private static float addedDamage = 0;
    @SpirePatch(
            clz= AbstractCard.class,
            method="calculateCardDamage"
    )
    public static class whyTho {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"tmp"}
        )
        public static void Insert(AbstractCard __instance, AbstractMonster mo, float tmp) {
            if (__instance instanceof RaptMisdirection) {
                addedDamage = 0;
                for (AbstractPower p : mo.powers)
                {
                    if(p instanceof VulnerablePower)
                    {
                        for(int i = 0; i < p.amount - 1; i++) {
                            addedDamage += p.atDamageReceive(tmp, __instance.damageTypeForTurn) - tmp;
                            //System.out.println("DID AN ADD DAMAGE:" + addedDamage);
                        }
                    }
                }
                /*Iterator iterator = mo.powers.iterator();
                AbstractPower powerCheck;
                addedDamage = 0;
                while (iterator.hasNext()) {
                    powerCheck = (AbstractPower) iterator.next();
                    if (powerCheck instanceof VulnerablePower) {
                        for(int i = 0; i < powerCheck.amount - 1; i++) {
                            addedDamage += powerCheck.atDamageReceive(tmp, __instance.damageTypeForTurn) - tmp;
                            //System.out.println("DID AN ADD DAMAGE:" + addedDamage);
                        }
                    }
                }*/
            }
        }
        @SpirePatch(
                clz= AbstractCard.class,
                method="calculateCardDamage"
        )
        public static class whyTho2 {
            @SpireInsertPatch(
                    locator = LocatorTwo.class,
                    localvars = {"tmp"}
            )
            public static void InsertrEEEEEEEEEEE(AbstractCard __instance, AbstractMonster mo, @ByRef float[] tmp) {
                tmp[0] += addedDamage;
                //System.out.println("tmp = " + tmp[0]);
                addedDamage = 0;
            }
        }
    }
    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPower.class, "atDamageReceive");
            int[] found = LineFinder.findInOrder(ctBehavior, finalMatcher);
            found[0] -= 1;
            return found;
        }
    }
    private static class LocatorTwo extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPower.class, "atDamageReceive");
            int[] found = LineFinder.findInOrder(ctBehavior, finalMatcher);
            found[0] += 2;
            return found;
        }
    }
}


