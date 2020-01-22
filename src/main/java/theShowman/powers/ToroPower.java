package theShowman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theShowman.ShowmanMod;
import theShowman.util.TextureLoader;

import static theShowman.ShowmanMod.makePowerPath;


public class ToroPower extends AbstractPower implements CloneablePowerInterface{

    public static final String POWER_ID = ShowmanMod.makeID("ToroPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Toro84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Toro32.png"));

    //private boolean applyVulnerableFlag = false;

    public ToroPower(final AbstractCreature owner, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;

        //this.applyVulnerableFlag = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster monster)
    {
        if(card.type == AbstractCard.CardType.SKILL) {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m.getIntentBaseDmg() >= 0) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new VulnerablePower(m, this.amount, false), this.amount));
                }
            }
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer)
    {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, this));
    }

   /* @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(info.owner != AbstractDungeon.player && info.type == DamageInfo.DamageType.NORMAL) {
            AbstractMonster m = (AbstractMonster)info.owner;
            AbstractPlayer p = AbstractDungeon.player;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.amount, false), this.amount));

            this.applyVulnerableFlag = true;
        }
        return damageAmount;
    }*/

    /*@Override
    public void atStartOfTurn()
    {
        if(this.applyVulnerableFlag)
        {
            this.flash();
            for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, this.owner, new VulnerablePower(mo, this.amount, false), this.amount));
            }
        }
        this.applyVulnerableFlag = false;
    }*/

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ToroPower(owner, amount);
    }
}
