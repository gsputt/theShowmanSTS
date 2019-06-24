package theShowman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.ShowmanMod;
import theShowman.powers.MirrorMirrorPower;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class MirrorMirror extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("MirrorMirror");
    public static final String IMG = makeCardPath("Skill.png");
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 1;
    private static final int BLOCK = 8;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int MIRROR_AMOUNT = 1;
    // /STAT DECLARATION/


    public MirrorMirror() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MirrorMirrorPower(p, MIRROR_AMOUNT), MIRROR_AMOUNT));
    }


    @Override
    public AbstractDynamicCard makeCopy() {
        return new MirrorMirror();
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
