package theShowman.cards;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.ShowmanMod;
import theShowman.powers.ShowOfStrengthPower;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class ShowOfStrength extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("ShowOfStrength");
    public static final String IMG = makeCardPath("ShowOfStrength.png");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 2;
    private static final int STR_NEXT_TURN = 1;
    private static final int STR_THIS_TURN = 0;
    private static final int STR_THIS_TURN_UPGRADE = 1;
    // /STAT DECLARATION/


    public ShowOfStrength() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = STR_NEXT_TURN;
        this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber = STR_THIS_TURN;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ShowOfStrengthPower(p, this.magicNumber, this.defaultSecondMagicNumber), this.magicNumber));
        if(AbstractDungeon.player.hasPower(ShowOfStrengthPower.POWER_ID))
        {
            TwoAmountPower power = (TwoAmountPower) AbstractDungeon.player.getPower(ShowOfStrengthPower.POWER_ID);
            power.amount2 += this.defaultSecondMagicNumber;
            power.updateDescription();
        }
    }


    @Override
    public AbstractDynamicCard makeCopy() {
        return new ShowOfStrength();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeDefaultSecondMagicNumber(STR_THIS_TURN_UPGRADE);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
