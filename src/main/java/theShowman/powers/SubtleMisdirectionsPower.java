package theShowman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnCardDrawPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theShowman.ShowmanMod;
import theShowman.util.TextureLoader;

import static theShowman.ShowmanMod.makePowerPath;


public class SubtleMisdirectionsPower extends AbstractPower implements CloneablePowerInterface, OnCardDrawPower {

    public static final String POWER_ID = ShowmanMod.makeID("SubtleMisdirectionsPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));
    private int ExhaustedStatuses = 999;

    public SubtleMisdirectionsPower(final AbstractCreature owner, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.ExhaustedStatuses = 0;


        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void onCardDraw(AbstractCard c)
    {
        if(c.type == AbstractCard.CardType.STATUS && this.ExhaustedStatuses < this.amount)
        {
            AbstractDungeon.player.hand.moveToExhaustPile(c);
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
            this.ExhaustedStatuses++;
            this.updateDescription();
        }

    }

    @Override
    public void atStartOfTurn() {
            this.ExhaustedStatuses = 0;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];

        description += DESCRIPTIONS[2];

        description += DESCRIPTIONS[3] + DESCRIPTIONS[4] + this.ExhaustedStatuses + DESCRIPTIONS[5];

    }

    @Override
    public AbstractPower makeCopy() {
        return new SubtleMisdirectionsPower(owner, amount);
    }
}
