package theShowman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theShowman.ShowmanMod;
import theShowman.util.TextureLoader;

import static theShowman.ShowmanMod.makePowerPath;


public class MirrorMirrorPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = ShowmanMod.makeID("MirrorMirrorPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public MirrorMirrorPower(final AbstractCreature owner, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.canGoNegative = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card.makeStatEquivalentCopy(), 1));
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, this, 1));
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer)
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, this));
        }
    }

    @Override
    public void updateDescription() {
        if(this.amount == 1) {
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }
        else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new MirrorMirrorPower(owner, amount);
    }
}
