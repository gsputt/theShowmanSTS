package theShowman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theShowman.ShowmanMod;
import theShowman.util.TextureLoader;

import static theShowman.ShowmanMod.makePowerPath;


public class WillingVolunteerPower extends AbstractPower implements CloneablePowerInterface, onAttackedExceptItRespectsBlockInterface {

    public static final String POWER_ID = ShowmanMod.makeID("WillingVolunteerPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    private AbstractMonster m;

    public WillingVolunteerPower(final AbstractCreature owner, final int amount, final AbstractMonster m) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.canGoNegative = false;
        this.m = m;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public int onAttackedExceptItRespectsBlock(DamageInfo info, int damageAmount)
    {
        if(info.type == DamageInfo.DamageType.NORMAL)
        {
            AbstractPlayer p = AbstractDungeon.player;
            if(this.m != null)
            {
                AbstractDungeon.actionManager.addToTop(
                        new DamageAction(m, new DamageInfo(m, info.output, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SMASH));
            }
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p, p, this, 1));
            return 0;
        }
        else
        {
            return damageAmount;
        }
    }

    /*
    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(info.type == DamageInfo.DamageType.NORMAL)
        {
            AbstractPlayer p = AbstractDungeon.player;
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, this, 1));
            if(this.m != null)
            {
                AbstractDungeon.actionManager.addToBottom(
                        new DamageAction(m, new DamageInfo(m, info.output, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SMASH));
            }
            return 0;
        }
        else
        {
            return damageAmount;
        }
    }*/

    @Override
    public void atStartOfTurn() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, this));
    }

    @Override
    public void updateDescription() {
        if(this.amount == 1) {
            this.description = FontHelper.colorString(this.m.name, "y") + " " + DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }
        else
        {
            this.description = FontHelper.colorString(this.m.name, "y") + " " + DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new WillingVolunteerPower(owner, amount, this.m);
    }
}
