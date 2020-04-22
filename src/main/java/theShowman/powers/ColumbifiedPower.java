package theShowman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theShowman.ShowmanMod;
import theShowman.actions.RetrieveMonsterFromLimboAction;
import theShowman.util.TextureLoader;

import static theShowman.ShowmanMod.makePowerPath;


public class ColumbifiedPower extends AbstractPower implements CloneablePowerInterface, onDeathBeforeStuffInterface
//, OnReceivePowerPower
{

    public static final String POWER_ID = ShowmanMod.makeID("ColumbifiedPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Columbified84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Columbified32.png"));

    private AbstractMonster linked;

    //public static final int YEET_AMOUNT = 100;

    public ColumbifiedPower(final AbstractMonster owner, final AbstractMonster linkedToAffect) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        this.canGoNegative = false;

        this.linked = linkedToAffect;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    /*@Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source)
    {
        if(target != source)
        {
            if(power.type == PowerType.DEBUFF) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(linked, source, ((CloneablePowerInterface)power).makeCopy()));
            }
        }
        return true;
    }*/

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        linked.currentHealth -= damageAmount;
        linked.healthBarUpdatedEvent();
        return damageAmount;
    }

    @Override
    public boolean beforeDoingOtherStuffOnDead(boolean triggerStuff)
    {
        AbstractDungeon.actionManager.addToBottom(new HideHealthBarAction(this.owner));
        AbstractDungeon.actionManager.addToBottom(
                new RetrieveMonsterFromLimboAction(AbstractDungeon.getCurrRoom(), this.linked, true));

        /*AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                this.isDone = true;
                linked.drawY = linked.drawY - YEET_AMOUNT;
                linked.halfDead = false;
                linked.damage(new DamageInfo((AbstractCreature) null, 1, DamageInfo.DamageType.THORNS));
            }
        });*/
        return false;
    }


    @Override
    public void atEndOfRound() {

        AbstractDungeon.actionManager.addToBottom(
                new RetrieveMonsterFromLimboAction(AbstractDungeon.getCurrRoom(), this.linked));

        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        AbstractDungeon.actionManager.addToBottom(new HideHealthBarAction(this.owner));

        AbstractDungeon.actionManager.addToBottom(new SuicideAction((AbstractMonster)this.owner, false));

        /*AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                this.isDone = true;
                linked.drawY = linked.drawY - YEET_AMOUNT;
                //linked.halfDead = false;
            }
        });*/
    }

    @Override
    public void updateDescription() {
        this.description = FontHelper.colorString(linked.name, "y") + " " + DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ColumbifiedPower((AbstractMonster)owner, linked);
    }
}
