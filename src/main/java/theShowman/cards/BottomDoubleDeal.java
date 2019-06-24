package theShowman.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.ShowmanMod;
import theShowman.actions.BottomDoubleDealAction;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class BottomDoubleDeal extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("BottomDoubleDeal");
    public static final String IMG = makeCardPath("Skill.png");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 0;
    // /STAT DECLARATION/


    public BottomDoubleDeal() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.upgraded)
        {
            AbstractDungeon.actionManager.addToBottom(new BottomDoubleDealAction(1));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new BottomDoubleDealAction(0));
        }
    }


    @Override
    public AbstractDynamicCard makeCopy() {
        return new BottomDoubleDeal();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.name = EXTENDED_DESCRIPTION[0];
            this.initializeTitle();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
