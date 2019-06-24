package theShowman.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.ShowmanMod;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;
import static theShowman.patches.StackedField.Stacked;

public class ParlorTrick extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("ParlorTrick");
    public static final String IMG = makeCardPath("Skill.png");
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;
    private static final int CARD_DRAW_AMOUNT = 10;
    private static final int STACKED_AMOUNT = 1;
    // /STAT DECLARATION/


    public ParlorTrick() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = CARD_DRAW_AMOUNT;
        Stacked.set(this, STACKED_AMOUNT);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.magicNumber > 0) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
        }
    }


    @Override
    public AbstractDynamicCard makeCopy() {
        return new ParlorTrick();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
