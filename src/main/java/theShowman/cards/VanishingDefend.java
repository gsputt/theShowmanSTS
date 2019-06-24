package theShowman.cards;

import basemod.helpers.BaseModCardTags;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.ShowmanMod;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class VanishingDefend extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("VanishingDefend");
    public static final String IMG = makeCardPath("Skill.png");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 1;
    //private static final int UPGRADED_COST = 0;

    //private static final int DAMAGE = 7;
    private static final int BLOCK = 5;
    // /STAT DECLARATION/


    public VanishingDefend() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
        FleetingField.fleeting.set(this, false);
        tags.add(BaseModCardTags.BASIC_DEFEND);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        /*if(this.upgraded)
        {
            if(AbstractDungeon.player.masterDeck.contains(this)) {
                AbstractDungeon.player.masterDeck.removeCard(this);
            }
        }*/
    }


    @Override
    public AbstractDynamicCard makeCopy() {
        return new VanishingDefend();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            FleetingField.fleeting.set(this, true);
            //upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
