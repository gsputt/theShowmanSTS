package theShowman.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.ShowmanMod;
import theShowman.actions.SleeveAcesAction;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class SleeveAces extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("SleeveAces");
    public static final String IMG = makeCardPath("SleeveAces.png");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 1;

    private static final int COPY_AMOUNT = 1;
    private static final int UPGRADE_COPY_AMOUNT = 1;
    // /STAT DECLARATION/


    public SleeveAces() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = COPY_AMOUNT;
        this.exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SleeveAcesAction(1, this.magicNumber));
    }


    @Override
    public AbstractDynamicCard makeCopy() {
        return new SleeveAces();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeMagicNumber(UPGRADE_COPY_AMOUNT);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
