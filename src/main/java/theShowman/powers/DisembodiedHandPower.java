package theShowman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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


public class DisembodiedHandPower extends AbstractPower implements CloneablePowerInterface, NonStackablePower {

    public static final String POWER_ID = ShowmanMod.makeID("DisembodiedHandPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("DisembodiedHand84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("DisembodiedHand32.png"));

    private AbstractCard card;

    public DisembodiedHandPower(final AbstractCreature owner, final AbstractCard card) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.card = card;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void atStartOfTurn()
    {
        if(card != null) {
            AbstractCard c = card.makeSameInstanceOf();
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    this.isDone = true;
                    AbstractMonster target = AbstractDungeon.getRandomMonster();
                    if (c.canUse(AbstractDungeon.player, target)) {
                        c.purgeOnUse = true;
                        c.freeToPlayOnce = true;
                        c.applyPowers();
                        c.calculateCardDamage(target);
                        AbstractDungeon.actionManager.addToTop(new NewQueueCardAction(c, target, false, true));
                    }
                }
            });
        }
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + FontHelper.colorString(this.card.name, "y") + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new DisembodiedHandPower(owner, card);
    }
}
