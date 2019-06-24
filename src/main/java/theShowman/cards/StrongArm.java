package theShowman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theShowman.ShowmanMod;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

import static theShowman.patches.StackedField.Stacked;

public class StrongArm extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("StrongArm");
    public static final String IMG = makeCardPath("Skill.png");
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 1;
    //private static final int UPGRADED_COST = 0;

    //private static final int DAMAGE = 7;
    private static final int STRENGTH_AMOUNT = 9;
    private static final int UPGRADE_PLUS_STRENGTH = 2;
    private static final int STACKED_AMOUNT = 1;
    // /STAT DECLARATION/


    public StrongArm() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = STRENGTH_AMOUNT;
        Stacked.set(this, STACKED_AMOUNT);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.magicNumber > 0) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber), this.magicNumber));
        }
    }


    @Override
    public void applyPowers()
    {
        super.applyPowers();
        stacked();
    }

    @Override
    public void calculateCardDamage(AbstractMonster m)
    {
        super.calculateCardDamage(m);
        stacked();
    }

    private void stacked()
    {
        this.magicNumber = this.baseMagicNumber;
        if(AbstractDungeon.player.drawPile.size() > 0)
        {
            this.isMagicNumberModified = true;
            this.magicNumber = this.magicNumber - AbstractDungeon.player.drawPile.size();
            if(this.magicNumber < 0)
            {
                this.magicNumber = 0;
            }
        }
    }

    @Override
    public AbstractDynamicCard makeCopy() {
        return new StrongArm();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_STRENGTH);
            //upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
