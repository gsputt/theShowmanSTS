package theShowman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import theShowman.powers.ApplyTempStrengthNextTurn;

public class MischiefManagedStrikeAction extends AbstractGameAction {
    private DamageInfo damage;
    private AbstractPlayer p;

    public MischiefManagedStrikeAction(AbstractCreature target, DamageInfo damage, AttackEffect effect) {
        this.damage = damage;
        this.setValues(target, damage);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.p = AbstractDungeon.player;
    }

    public void update() {
        if (this.duration == 0.5F) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
        }

        this.tickDuration();
        if (this.isDone) {
            this.gainStrength(this.damage);
            this.target.damage(this.damage);
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

    }

    private void gainStrength(DamageInfo damage) {
        int StrengthAmount = damage.output;
        if (StrengthAmount >= 0) {
            StrengthAmount -= this.target.currentBlock;
            if (StrengthAmount > this.target.currentHealth) {
                StrengthAmount = this.target.currentHealth;
            }

            if (StrengthAmount > 0) {
                if (StrengthAmount > 1 && this.target.hasPower("Buffer")) {
                    return;
                }

                if (StrengthAmount > 1 && (this.target.hasPower("IntangiblePlayer") || this.target.hasPower("Intangible"))) {
                    StrengthAmount = 1;
                }

                if(this.target.hasPower(VulnerablePower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.p, this.p, new ApplyTempStrengthNextTurn(p, StrengthAmount), StrengthAmount));
                }

            }

        }
    }
}
