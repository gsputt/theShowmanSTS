package theShowman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theShowman.ShowmanMod;
import theShowman.util.TextureLoader;

import static theShowman.ShowmanMod.makePowerPath;


public class RetainThisTurnPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = ShowmanMod.makeID("RetainThisTurnPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public RetainThisTurnPower(final AbstractCreature owner, final int amount) {
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
    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer) {
            if (!AbstractDungeon.player.hand.isEmpty() && !AbstractDungeon.player.hasRelic("Runic Pyramid") && !AbstractDungeon.player.hasPower("Equilibrium")) {
                AbstractDungeon.actionManager.addToBottom(new RetainCardsAction(this.owner, this.amount));
            }
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else if (this.amount > 1) {
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new RetainThisTurnPower(owner, amount);
    }
}
