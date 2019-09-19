package theShowman.powers.Deprecated;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;
import theShowman.ShowmanMod;
import theShowman.powers.HatTrickPower;
import theShowman.powers.RetainThisTurnPower;


public class ObjectPermanencePower extends AbstractPower implements CloneablePowerInterface, OnReceivePowerPower {

    public static final String POWER_ID = ShowmanMod.makeID("ObjectPermanencePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    //private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    //private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public ObjectPermanencePower(final AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;

        this.loadRegion("time");

        //this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        //this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source)
    {
        if(power instanceof GainStrengthPower)
        {
            return false;
        }
        if(power instanceof LoseStrengthPower)
        {
            return false;
        }
        if(power instanceof LoseDexterityPower)
        {
            return false;
        }
        if(power instanceof HatTrickPower)
        {
            return false;
        }
        if(power instanceof RetainThisTurnPower)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, source, new RetainCardPower(target, power.amount), power.amount));
            return false;
        }
        if(power instanceof BlurPower)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, source, new BarricadePower(target)));
            return false;
        }
        if(power.ID.equals("theScribe:ThornsDown"))
        {
            return false;
        }
        return true;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + FontHelper.colorString(this.owner.name, "y") + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ObjectPermanencePower(owner);
    }
}
