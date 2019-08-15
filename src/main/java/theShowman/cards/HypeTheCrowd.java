package theShowman.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.ShowmanMod;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;
import static theShowman.patches.StackedField.Stacked;

public class HypeTheCrowd extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("HypeTheCrowd");
    public static final String IMG = makeCardPath("HypeTheCrowd.png");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 1;
    private static final int ENERGY_GAIN = 4;
    private static final int UPGRADE_PLUS_ENERGY_GAIN = 1;
    private static final int STACKED_AMOUNT = 1;
    // /STAT DECLARATION/


    public HypeTheCrowd() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = ENERGY_GAIN;
        Stacked.set(this, STACKED_AMOUNT);
    }

    @Override
    public void applyPowers()
    {
        super.applyPowers();
        doEnergyDisplayChanges();
    }

    private void doEnergyDisplayChanges()
    {
        if(this.magicNumber == 1) {
            this.rawDescription = EXTENDED_DESCRIPTION[0] + EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[4];
        }
        else if(this.magicNumber == 2)
        {
            this.rawDescription = EXTENDED_DESCRIPTION[0] + EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[4];
        }
        else
        {
            this.rawDescription = EXTENDED_DESCRIPTION[0] + EXTENDED_DESCRIPTION[3] + EXTENDED_DESCRIPTION[4];
        }
        initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo)
    {
        super.calculateCardDamage(mo);
        doEnergyDisplayChanges();
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.magicNumber));
    }


    @Override
    public AbstractDynamicCard makeCopy() {
        return new HypeTheCrowd();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_ENERGY_GAIN);
            initializeDescription();
        }
    }
}
