package theShowman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theShowman.ShowmanMod;
import theShowman.patches.ImproviseField;
import theShowman.util.TextureLoader;

import static theShowman.ShowmanMod.makePowerPath;


public class PerfectedPerformancePower extends TwoAmountPower implements CloneablePowerInterface {

    public static final String POWER_ID = ShowmanMod.makeID("PerfectedPerformancePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    private boolean alreadySet;

    public PerfectedPerformancePower(final AbstractCreature owner, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        //this.amount2 += this.amount; // ??
        this.canGoNegative2 = false;

        //this.alreadySet = false;

        this.updateDescription();
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if(this.amount2 >= 0)
        {
            this.amount2--;
            updateDescription();
        }
        if(this.amount2 < 0 && !this.alreadySet)
        {
            updateDescription();
            this.alreadySet = true;
            ImproviseField.ImproviseRecording.set(AbstractDungeon.player, ImproviseField.ImproviseRecording.get(AbstractDungeon.player) - 1);
        }
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        ImproviseField.ImproviseRecording.set(AbstractDungeon.player, ImproviseField.ImproviseRecording.get(AbstractDungeon.player) + 1);
        this.amount2 = this.amount;
        this.alreadySet = false;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer)
    {
        if(!this.alreadySet)
        {
            this.alreadySet = true;
            ImproviseField.ImproviseRecording.set(AbstractDungeon.player, ImproviseField.ImproviseRecording.get(AbstractDungeon.player) - 1);
        }
    }

    @Override
    public void atStartOfTurn() {
        ImproviseField.ImproviseRecording.set(AbstractDungeon.player, ImproviseField.ImproviseRecording.get(AbstractDungeon.player) + 1);
        this.amount2 = this.amount;
        this.alreadySet = false;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + 6 + DESCRIPTIONS[2] + 6 + DESCRIPTIONS[3] + 1 + DESCRIPTIONS[4] + DESCRIPTIONS[5] + Math.max(this.amount2, 0) + DESCRIPTIONS[6];
    }

    @Override
    public AbstractPower makeCopy() {
        return new PerfectedPerformancePower(owner, amount);
    }
}
