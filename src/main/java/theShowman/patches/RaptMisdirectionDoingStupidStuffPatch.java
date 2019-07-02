package theShowman.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theShowman.cards.RaptMisdirection;

import java.util.Iterator;


public class RaptMisdirectionDoingStupidStuffPatch {
    private static float addedDamage = 0;
    @SpirePatch(
            clz= AbstractCard.class,
            method="calculateCardDamage"
    )
    public static class whyTho {
        @SpireInsertPatch(
                loc = 3066,
                localvars = {"tmp"}
        )
        public static void Insert(AbstractCard __instance, AbstractMonster mo, float tmp) {
            if (__instance instanceof RaptMisdirection) {
                Iterator iterator = mo.powers.iterator();
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
                }
            }
        }
        @SpirePatch(
                clz= AbstractCard.class,
                method="calculateCardDamage"
        )
        public static class whyTho2 {
            @SpireInsertPatch(
                    loc = 3071,
                    localvars = {"tmp"}
            )
            public static void InsertrEEEEEEEEEEE(AbstractCard __instance, AbstractMonster mo, @ByRef float[] tmp) {
                tmp[0] += addedDamage;
                //System.out.println("tmp = " + tmp[0]);
                addedDamage = 0;
            }
        }
    }
}


