package theShowman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theShowman.ShowmanMod;
import theShowman.patches.ObjectPermanenceRenderField;

import java.util.ArrayList;


public class ObjectPermanencePower extends AbstractPower implements CloneablePowerInterface//, OnReceivePowerPower
{

    public static final String POWER_ID = ShowmanMod.makeID("ObjectPermanencePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public int cardsLeft = 0;
    public ArrayList<AbstractCard> duplicateThis;

    //private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    //private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public ObjectPermanencePower(final AbstractCreature owner, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;

        this.loadRegion("time");

        this.cardsLeft = this.amount;
        this.duplicateThis = new ArrayList<>();

        //this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        //this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void atStartOfTurn()
    {
        this.cardsLeft = this.amount;
        this.duplicateThis.clear();
    }

    @Override
    public void onCardDraw(AbstractCard card)
    {
        if(this.cardsLeft > 0 && this.duplicateThis != null && card.type != AbstractCard.CardType.STATUS && card.type != AbstractCard.CardType.CURSE)
        {
            this.duplicateThis.add(card);
            ObjectPermanenceRenderField.ObjectPermanenceFlag.set(card, true);
            this.cardsLeft -= 1;
        }
        this.updateDescription();
    }

    @Override
    public void onExhaust(AbstractCard card)
    {
        for(AbstractCard c : duplicateThis)
        {
            if(card.uuid.equals(c.uuid)) {
                this.flash();
                AbstractCard whoops = card.makeStatEquivalentCopy();
                ObjectPermanenceRenderField.ObjectPermanenceFlag.set(whoops, false);
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(whoops));
            }
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer)
    {
        for(AbstractCard c : this.duplicateThis)
        {
            ObjectPermanenceRenderField.ObjectPermanenceFlag.set(c, false);
        }
        this.duplicateThis.clear();
    }

    /*@Override
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
    }*/

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0]; //The first
        if(this.amount == 1)
        {
            description += DESCRIPTIONS[1] + DESCRIPTIONS[2];// The first non-Curse, non-Status card
        }
        else
        {
            description += this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[3]; // The first 2 non-Curse, non-Status cards
        }
        description += DESCRIPTIONS[4];

        if(duplicateThis.size() > 0) {
            description += DESCRIPTIONS[5]; // Currently affecting
            if(duplicateThis.size() > 1) {
                for (int i = duplicateThis.size() - 1; i > 0; i--) {
                    description += FontHelper.colorString(duplicateThis.get(i).name, "y") + DESCRIPTIONS[6]; // Card Name,
                }
                description += DESCRIPTIONS[7]; // and
            }
            description += FontHelper.colorString(duplicateThis.get(0).name, "y") + DESCRIPTIONS[8]; // Card Name.
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new ObjectPermanencePower(this.owner, this.amount);
    }
}
