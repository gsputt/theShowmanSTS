package theShowman.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.ShowmanMod;
import theShowman.actions.ColumbifyAction;
import theShowman.powers.ColumbifiedPower;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class Columbify extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("Columbify");
    public static final String IMG = makeCardPath("Columbify.png");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 3;
    private static final int UPGRADE_COST = 2;

    private static final String NO_BYRDING = EXTENDED_DESCRIPTION[0];
    // /STAT DECLARATION/


    public Columbify() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
        if(m != null && m.hasPower(ColumbifiedPower.POWER_ID)){
            this.cantUseMessage = NO_BYRDING;
            return false;
        }
        return super.canUse(p, m);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ColumbifyAction(m));
    }


    @Override
    public AbstractDynamicCard makeCopy() {
        return new Columbify();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}
