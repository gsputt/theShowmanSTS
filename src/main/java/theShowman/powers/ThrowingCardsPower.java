package theShowman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theShowman.ShowmanMod;
import theShowman.effects.TossCardEffect;
import theShowman.util.TextureLoader;

import static theShowman.ShowmanMod.makePowerPath;


public class ThrowingCardsPower extends AbstractPower implements CloneablePowerInterface{

    public static final String POWER_ID = ShowmanMod.makeID("ThrowingCardsPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("ThrowingCards84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("ThrowingCards32.png"));

    public ThrowingCardsPower(final AbstractCreature owner, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    public void onExhaust(AbstractCard card) {
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        if(m != null) {
            AbstractDungeon.actionManager.addToTop(new VFXAction(new TossCardEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, m, this.amount, -1)));
            AbstractDungeon.actionManager.addToTop(
                    new DamageAction(m, new DamageInfo(AbstractDungeon.player, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ThrowingCardsPower(owner, amount);
    }
}
