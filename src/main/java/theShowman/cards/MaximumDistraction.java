package theShowman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theShowman.ShowmanMod;
import theShowman.powers.MaximumDistractionPower;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class MaximumDistraction extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("MaximumDistraction");
    public static final String IMG = makeCardPath("Power.png");
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 1;
    private static final int POWER_AMOUNT = 3;
    private static final int UPGRADE_POWER_AMOUNT = -1;
    // /STAT DECLARATION/


    public MaximumDistraction() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = POWER_AMOUNT;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MaximumDistractionPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                this.isDone = true;
                for(AbstractMonster mo: AbstractDungeon.getCurrRoom().monsters.monsters)
                {
                    if(mo.hasPower(VulnerablePower.POWER_ID))
                    {
                        mo.getPower(VulnerablePower.POWER_ID).updateDescription();
                    }
                }
            }
        });
    }


    @Override
    public AbstractDynamicCard makeCopy() {
        return new MaximumDistraction();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeMagicNumber(UPGRADE_POWER_AMOUNT);
            initializeDescription();
        }
    }
}
