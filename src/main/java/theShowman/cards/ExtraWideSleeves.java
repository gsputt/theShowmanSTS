package theShowman.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.ShowmanMod;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;
import static theShowman.patches.StackedField.Stacked;

public class ExtraWideSleeves extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("ExtraWideSleeves");
    public static final String IMG = makeCardPath("Skill.png");
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 1;
    //private static final int UPGRADED_COST = 0;

    //private static final int DAMAGE = 7;
    private static final int BLOCK = 15;
    private static final int UPGRADE_PLUS_BLOCK = 4;
    private static final int STACKED_AMOUNT = 1;
    // /STAT DECLARATION/


    public ExtraWideSleeves() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
        Stacked.set(this, STACKED_AMOUNT);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.block > 0) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }
    }

    @Override
    public AbstractDynamicCard makeCopy() {
        return new ExtraWideSleeves();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            //upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
