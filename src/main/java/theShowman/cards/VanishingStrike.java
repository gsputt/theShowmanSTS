package theShowman.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.ShowmanMod;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class VanishingStrike extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("VanishingStrike");
    public static final String IMG = makeCardPath("VanishingShowmanStrike.png");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 1;
    //private static final int UPGRADED_COST = 0;

    //private static final int DAMAGE = 7;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DAMAGE = 3;
    // /STAT DECLARATION/


    public VanishingStrike() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        FleetingField.fleeting.set(this, false);
        //tags.add(BaseModCardTags.BASIC_STRIKE);
        tags.add(CardTags.STRIKE);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        /*if(this.upgraded)
        {
            if(AbstractDungeon.player.masterDeck.contains(this)) {
                AbstractDungeon.player.masterDeck.removeCard(this);
            }
        }*/
    }


    @Override
    public AbstractDynamicCard makeCopy() {
        return new VanishingStrike();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            //upgradeBaseCost(UPGRADED_COST);
            FleetingField.fleeting.set(this, true);
            this.upgradeDamage(UPGRADE_PLUS_DAMAGE);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
