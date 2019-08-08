package theShowman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theShowman.ShowmanMod;

import java.util.Iterator;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class FlashyFlourish extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("FlashyFlourish");
    public static final String IMG = makeCardPath("FlashyFlourish.png");
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 1;
    //private static final int UPGRADED_COST = 0;

    //private static final int DAMAGE = 7;
    private static final int BLOCK = 6;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int VULNERABLE_AMOUNT = 2;
    // /STAT DECLARATION/


    public FlashyFlourish() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
        this.magicNumber = this.baseMagicNumber = VULNERABLE_AMOUNT;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void triggerOnExhaust()
    {
        Iterator monstersToDebuff = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        AbstractMonster monsterApplyDebuff;
        while(monstersToDebuff.hasNext()) {
            monsterApplyDebuff = (AbstractMonster) monstersToDebuff.next();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monsterApplyDebuff, AbstractDungeon.player, new VulnerablePower(monsterApplyDebuff, this.magicNumber, false), this.magicNumber));
        }
    }


    @Override
    public AbstractDynamicCard makeCopy() {
        return new FlashyFlourish();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            //upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
