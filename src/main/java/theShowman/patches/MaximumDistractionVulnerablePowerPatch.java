package theShowman.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.PaperFrog;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;
import theShowman.cards.MaximumDistraction;
import theShowman.powers.MaximumDistractionPower;

public class MaximumDistractionVulnerablePowerPatch {
    private static final float ADDITIONAL_DAMAGE = 0.25F;


    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;




    @SpirePatch(
            clz = VulnerablePower.class,
            method = "atDamageReceive"
    )
    public static class atDamageRecieved
    {
        public static float Postfix(float __result, VulnerablePower __instance, float damage, DamageType type)
        {
            if(__instance.owner == AbstractDungeon.player)
            {
                return __result;
            }
            else
            {
                if(type == DamageType.NORMAL) {
                    if (AbstractDungeon.player.hasPower(MaximumDistractionPower.POWER_ID)) {
                        int timesToAmp = 0;
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            if (p instanceof MaximumDistractionPower) {
                                timesToAmp += (int) ((float) __instance.amount / (float) p.amount);
                            }
                        }
                        float amp = timesToAmp * ADDITIONAL_DAMAGE;
                        return __result + (amp * damage);
                    }
                }
                return __result;
            }
        }
    }
    @SpirePatch(
            clz = VulnerablePower.class,
            method = "updateDescription"
    )
    public static class updateDescriptions
    {
        public static void Postfix(VulnerablePower __instance)
        {
            if(__instance.owner != AbstractDungeon.player)
            {
                if (AbstractDungeon.player.hasPower(MaximumDistractionPower.POWER_ID)) {
                    int timesToAmp = 0;
                    for (AbstractPower p : AbstractDungeon.player.powers) {
                        if (p instanceof MaximumDistractionPower) {
                            timesToAmp += (int) ((float) __instance.amount / (float) p.amount);
                        }
                    }
                    float amp = timesToAmp * ADDITIONAL_DAMAGE;
                    int displayNumber = (int)(amp * 100);
                    __instance.description += MaximumDistractionPower.DESCRIPTIONS[2] + displayNumber + MaximumDistractionPower.DESCRIPTIONS[3] + (int)(__instance.atDamageReceive(100, DamageType.NORMAL)-100F) + MaximumDistractionPower.DESCRIPTIONS[4];
                }
            }
        }
    }
    @SpirePatch(
            clz = VulnerablePower.class,
            method = "atDamageReceive"
    )
public static class YUNOCHECKFORNULL
    {
        public static ExprEditor Instrument()
        {
            return new ExprEditor() {

                @Override
                public void edit(FieldAccess f) throws CannotCompileException
                {
                    if (f.getFieldName().equals("isPlayer")) {
                        f.replace("{" +
                                "$_ = " +
                                Nested.class.getName() +
                                ".IThinkYouForgotToCheckIfOwnerIsNull($0);" +
                                "}");
                    }
                }
            };
        }
    }
    public static class Nested {
        public static boolean IThinkYouForgotToCheckIfOwnerIsNull(AbstractCreature c)
        {
            return c != null && c == AbstractDungeon.player;
    }
    }

    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Vulnerable");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
