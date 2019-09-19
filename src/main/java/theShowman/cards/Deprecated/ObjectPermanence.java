package theShowman.cards.Deprecated;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.ShowmanMod;
import theShowman.cards.AbstractDynamicCard;
import theShowman.powers.Deprecated.ObjectPermanencePower;

import java.util.Iterator;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class ObjectPermanence extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("ObjectPermanence");
    public static final String IMG = makeCardPath("Skill.png");
    //public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    //public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;
    // /STAT DECLARATION/


    public ObjectPermanence() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.purgeOnUse = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ObjectPermanencePower(p)));

        Iterator monsterList = AbstractDungeon.getMonsters().monsters.iterator();
        AbstractMonster monster;
        while(monsterList.hasNext())
        {
            monster = (AbstractMonster) monsterList.next();
            if(!monster.isDeadOrEscaped())
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new ObjectPermanencePower(monster)));
            }
        }
    }


    @Override
    public AbstractDynamicCard makeCopy() {
        return new ObjectPermanence();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
            //this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}