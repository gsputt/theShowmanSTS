package theShowman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theShowman.ShowmanMod;
import theShowman.actions.BafflingShuffleAction;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

import static theShowman.patches.StackedField.Stacked;

public class BafflingShuffle extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("BafflingShuffle");
    public static final String IMG = makeCardPath("Skill.png");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 1;
    //private static final int UPGRADED_COST = 0;

    //private static final int DAMAGE = 7;
    private static final int DRAW = 1;
    private static final int UPGRADE_DRAW = 1;
    // /STAT DECLARATION/


    public BafflingShuffle() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = DRAW;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new BafflingShuffleAction(m, this.magicNumber));
    }


    @Override
    public AbstractDynamicCard makeCopy() {
        return new BafflingShuffle();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeMagicNumber(UPGRADE_DRAW);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
