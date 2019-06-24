package theShowman.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.ShowmanMod;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class SybilFlourish extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("SybilFlourish");
    public static final String IMG = makeCardPath("Skill.png");
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 2;
    private static final int BLOCK = 13;
    private static final int UPGRADE_PLUS_BLOCK = 5;
    private static final int ENERGY_GAIN = 2;
    // /STAT DECLARATION/


    public SybilFlourish() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void triggerOnExhaust()
    {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(ENERGY_GAIN));
    }


    @Override
    public AbstractDynamicCard makeCopy() {
        return new SybilFlourish();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
