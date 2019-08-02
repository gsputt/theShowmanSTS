package theShowman.cards.Deprecated;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theShowman.ShowmanMod;
import theShowman.cards.AbstractDynamicCard;

import java.util.Iterator;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class BaitedBreath extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("BaitedBreath");
    public static final String IMG = makeCardPath("Skill.png");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 1;
    // /STAT DECLARATION/


    public BaitedBreath() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.upgraded)
        {
            Iterator monsterInRoom = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            AbstractMonster monsterCheck;
            int totalVulnerable = 0;
            while(monsterInRoom.hasNext())
            {
                monsterCheck = (AbstractMonster)monsterInRoom.next();
                if(!monsterCheck.isDeadOrEscaped()) {
                    if (monsterCheck.hasPower(VulnerablePower.POWER_ID))
                    {
                        totalVulnerable += monsterCheck.getPower(VulnerablePower.POWER_ID).amount;
                    }
                }
            }
            this.baseBlock = totalVulnerable;
        }
        else
        {
            if(m.hasPower(VulnerablePower.POWER_ID))
            {
                this.baseBlock = m.getPower(VulnerablePower.POWER_ID).amount;
            }
        }
        this.applyPowers();
        if(this.block > 0) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }
}

    @Override
    public AbstractDynamicCard makeCopy() {
        return new BaitedBreath();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.target = CardTarget.ALL_ENEMY;
            initializeDescription();
        }
    }
}
