package theShowman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theShowman.ShowmanMod;
import theShowman.util.TextureLoader;

import static theShowman.ShowmanMod.makePowerPath;


public class ShowOfStrengthPower extends TwoAmountPower implements CloneablePowerInterface {

    public static final String POWER_ID = ShowmanMod.makeID("ShowOfStrengthPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public ShowOfStrengthPower(final AbstractCreature owner, final int amount, final int amount2) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.amount2 = amount2;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.canGoNegative = false;
        this.canGoNegative2 = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void onExhaust(AbstractCard card) {
        AbstractPlayer p = AbstractDungeon.player;
        if(this.amount > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ApplyTempStrengthNextTurn(p, this.amount)));
        }
        if(this.amount2 > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.amount2), this.amount2));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.amount2), this.amount2));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
        if(this.amount2 > 0)
        {
            this.description += DESCRIPTIONS[1] +  this.amount2 + DESCRIPTIONS[2];
        }
        this.description += DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[4];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ShowOfStrengthPower(owner, amount, amount2);
    }
}
