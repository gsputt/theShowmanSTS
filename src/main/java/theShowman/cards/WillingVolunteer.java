package theShowman.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.ShowmanMod;
import theShowman.effects.WillingVolunteerEffect;
import theShowman.powers.WillingVolunteerPower;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class WillingVolunteer extends AbstractDynamicCard {

    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("WillingVolunteer");
    public static final String IMG = makeCardPath("WillingVolunteer.png");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 2;
    private static final int POWER = 1;
    private static final int UPGRADE_PLUS_AMOUNT = 1;
    // /STAT DECLARATION/


    public WillingVolunteer() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = POWER;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p.hasPower(WillingVolunteerPower.POWER_ID))
        {
            WillingVolunteerPower power = (WillingVolunteerPower) p.getPower(WillingVolunteerPower.POWER_ID);
            power.amount += this.magicNumber;
            power.monsterIcon(m);
            power.m = m;
            power.flash();
            power.updateDescription();
        }
        else {
            if (m != null) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new WillingVolunteerEffect(m)));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WillingVolunteerPower(p, this.magicNumber, m), this.magicNumber));
            }
        }
    }


    @Override
    public AbstractDynamicCard makeCopy() {
        return new WillingVolunteer();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_AMOUNT);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
