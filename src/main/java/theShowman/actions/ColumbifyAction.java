package theShowman.actions;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Byrd;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.FlightPower;
import theShowman.powers.ColumbifiedPower;

import static theShowman.patches.ColumbifyTrackingFields.redirectTargetField.goHitThisByrdInstead;

public class ColumbifyAction extends AbstractGameAction {

    private AbstractMonster m;
    private AbstractPlayer p;
    public ColumbifyAction(AbstractMonster m)
    {
        this.m = m;
        this.p = AbstractDungeon.player;
    }


    @Override
    public void update()
    {
        this.isDone = true;
        if(m != null)
        {
            if(!m.hasPower(ArtifactPower.POWER_ID) && !m.hasPower(ColumbifiedPower.POWER_ID)) {
                //AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(m, p, 1));
                float offSetX = (m.drawX - Settings.WIDTH * 0.75F) / Settings.scale;
                float offSetY = (m.drawY - AbstractDungeon.floorY) / Settings.scale;
                AbstractMonster Byrd = new Byrd(offSetX, offSetY);
                /*AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                    @Override
                    public void update() {
                        this.isDone = true;
                        //m.drawY = m.drawY + ColumbifiedPower.YEET_AMOUNT;
                        //m.halfDead = true;
                    }
                });*/
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(Byrd, false));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Byrd, Byrd, new FlightPower(Byrd, (int)ReflectionHacks.getPrivate(Byrd, Byrd.class, "flightAmt"))));
                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                    @Override
                    public void update() {
                        this.isDone = true;
                        Byrd.maxHealth =  m.maxHealth;
                        Byrd.currentHealth = m.currentHealth;
                        Byrd.healthBarUpdatedEvent();
                        Byrd.setMove((byte)1, AbstractMonster.Intent.ATTACK, ((DamageInfo)Byrd.damage.get(0)).base, (int)ReflectionHacks.getPrivate(Byrd, Byrd.class, "peckCount"), true);
                        Byrd.createIntent();

                        goHitThisByrdInstead.set(m, Byrd);
                    }
                });
                AbstractDungeon.actionManager.addToBottom(new SendMonsterToLimboAction(AbstractDungeon.getCurrRoom(), m));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Byrd, p, new ColumbifiedPower(Byrd, m)));
                //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new IAmAByrd(m, Byrd)));
                /*AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                    @Override
                    public void update() {
                        this.isDone = true;
                        m.halfDead = true;
                    }
                });*/
            }
            else
            {
                if(m.hasPower(ArtifactPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StunMonsterPower(m, 1), 1));
                }
            }
        }
    }
}
