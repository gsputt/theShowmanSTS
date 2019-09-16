package theShowman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theShowman.ShowmanMod;
import theShowman.actions.CardPickup52Action;
import theShowman.actions.RemoveSleightedFlourishFromExhaustPileAction;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class SleightedFlourish extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("SleightedFlourish");
    public static final String IMG = makeCardPath("SleightedFlourish.png");
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 1;
    private static final int AMOUNT = 2;
    private static final int UPGRADE_PLUS_AMOUNT = 2;
    private static final int SHUFFLE_AMOUNT = 2;
    private static final int UPGRADE_SHUFFLE_AMOUNT = 1;

    // /STAT DECLARATION/


    public SleightedFlourish() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = AMOUNT;
        this.defaultBaseSecondMagicNumber = this.defaultSecondMagicNumber = SHUFFLE_AMOUNT;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
    }

    @Override
    public void triggerOnExhaust()
    {
        AbstractCard card = this;
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                if(AbstractDungeon.player.exhaustPile.contains(card)) {
                    AbstractDungeon.actionManager.addToTop(new RemoveSleightedFlourishFromExhaustPileAction(card));
                    AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(card, 1, true, true));
                }
                this.isDone = true;
            }
        });

        AbstractDungeon.actionManager.addToBottom(new CardPickup52Action(this.defaultSecondMagicNumber));
    }

    @Override
    public AbstractDynamicCard makeCopy() {
        return new SleightedFlourish();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_AMOUNT);
            this.upgradeDefaultSecondMagicNumber(UPGRADE_SHUFFLE_AMOUNT);
            initializeDescription();
        }
    }
}
