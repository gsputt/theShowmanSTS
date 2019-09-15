package theShowman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.ShowmanMod;
import theShowman.actions.SwapDrawAndExhaustPileAction;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class ParadigmPerformance extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("ParadigmPerformance");
    public static final String IMG = makeCardPath("ParadigmSift.png");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DAMAGE = 4;
    private static final int ENERGY_GAIN = 1;
    private static final int UPGRADE_ENERGY_GAIN = 1;
    // /STAT DECLARATION/


    public ParadigmPerformance() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        //this.baseMagicNumber = this.magicNumber = ENERGY_GAIN;
        this.exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        //AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new SwapDrawAndExhaustPileAction());
    }


    @Override
    public AbstractDynamicCard makeCopy() {
        return new ParadigmPerformance();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            //upgradeMagicNumber(UPGRADE_ENERGY_GAIN);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
