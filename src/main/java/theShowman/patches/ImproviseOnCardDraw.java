package theShowman.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import javassist.CtBehavior;

import java.util.ArrayList;

import static theShowman.patches.ImproviseField.ImproviseRecording;

@SpirePatch(
        clz= AbstractPlayer.class,
        method="draw",
        paramtypez={int.class}
)
public class ImproviseOnCardDraw
{
    @SpireInsertPatch(
            locator=Locator.class,
            localvars={"c"}
    )
    public static void Insert(AbstractPlayer __instance, int numCards, AbstractCard drawnCard)
    {
        if(ImproviseRecording.get(__instance) > 0)
        {
            for(int i = 0; i < ImproviseRecording.get(__instance); i++) {
                if (drawnCard.type == AbstractCard.CardType.ATTACK) {
                    ImproviseDoStuff.ImproviseDealDamage();
                    //Deal Damage
                } else if (drawnCard.type == AbstractCard.CardType.SKILL) {
                    ImproviseDoStuff.ImproviseGainBlock();
                    //Gain Block
                } else if (drawnCard.type == AbstractCard.CardType.STATUS || drawnCard.type == AbstractCard.CardType.CURSE) {
                    ImproviseDoStuff.ImproviseApplyWeakAndVulnerable();
                    //Apply Weak and Vulnerable
                } else {
                    ImproviseDoStuff.ImproviseGainEnergy();
                    //Gain Energy
                }
            }
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics");
            return LineFinder.findAllInOrder(ctBehavior, new ArrayList<>(), finalMatcher);
        }
    }

    public static class ImproviseDoStuff {
        public static void ImproviseDealDamage() {
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction((AbstractCreature) null, DamageInfo.createDamageMatrix(6, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
        }

        public static void ImproviseGainBlock() {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 6));
        }

        public static void ImproviseGainEnergy() {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        }

        public static void ImproviseApplyWeakAndVulnerable() {
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    this.isDone = true;
                    for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                        if (!m.isDeadOrEscaped() && !m.halfDead)
                            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, AbstractDungeon.player, new WeakPower(m, 1, false), 1));
                        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, AbstractDungeon.player, new VulnerablePower(m, 1, false), 1));
                    }
                }
            });
        }
    }
}