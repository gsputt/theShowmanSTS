package theShowman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import theShowman.ShowmanMod;
import theShowman.actions.CardPickup52Action;

import java.util.ArrayList;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class CardPickup52 extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("CardPickup52");
    public static final String IMG = makeCardPath("Skill.png");
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 0;
    //private static final int UPGRADED_COST = 0;

    //private static final int DAMAGE = 7;
    private static final int SHUFFLE_AMOUNT = 2;
    private static final int UPGRADE_PLUS_SHUFFLE = 1;
    // /STAT DECLARATION/


    public CardPickup52() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = SHUFFLE_AMOUNT;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new CardPickup52Action(this.magicNumber));
    }

    @Override
    public AbstractDynamicCard makeCopy() {
        return new CardPickup52();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_SHUFFLE);
            //upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
