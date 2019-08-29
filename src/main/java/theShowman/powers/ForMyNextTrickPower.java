package theShowman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theShowman.ShowmanMod;
import theShowman.util.TextureLoader;

import static theShowman.ShowmanMod.makePowerPath;


public class ForMyNextTrickPower extends AbstractPower implements CloneablePowerInterface, NonStackablePower {

    public static final String POWER_ID = ShowmanMod.makeID("ForMyNextTrickPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final PowerStrings NIGHTMARE = CardCrawlGame.languagePack.getPowerStrings("Night Terror");
    private static final String[] NIGHTMARE_DESCRIPTIONS = NIGHTMARE.DESCRIPTIONS;


    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("ThreeCards84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("ThreeCards32.png"));

    private AbstractCard card;

    public ForMyNextTrickPower(AbstractCreature owner, int cardAmt, AbstractCard copyMe) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = cardAmt;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.canGoNegative = false;
        this.card = copyMe.makeStatEquivalentCopy();
        this.card.resetAttributes();

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    public void atStartOfTurn() {
        this.card.modifyCostForTurn(-1);
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(this.card, this.amount));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    @Override
    public void updateDescription() {
        this.description = NIGHTMARE_DESCRIPTIONS[0] + this.amount + " " + FontHelper.colorString(this.card.name, "y") + NIGHTMARE_DESCRIPTIONS[1] + " " + DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ForMyNextTrickPower(this.owner, this.amount, this.card);
    }
}
