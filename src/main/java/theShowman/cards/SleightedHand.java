package theShowman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theShowman.ShowmanMod;

import java.util.Iterator;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class SleightedHand extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("SleightedHand");
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
    private static final int STRENGTH_LOSS = 6;
    // /STAT DECLARATION/


    public SleightedHand() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = STRENGTH_LOSS;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.upgraded)
        {
            Iterator monstersToDebuff = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            AbstractMonster monsterApplyDebuff;
            while (monstersToDebuff.hasNext()) {
                monsterApplyDebuff = (AbstractMonster) monstersToDebuff.next();
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monsterApplyDebuff, AbstractDungeon.player, new StrengthPower(monsterApplyDebuff, -this.magicNumber), -this.magicNumber));
                if (monsterApplyDebuff != null && !monsterApplyDebuff.hasPower(ArtifactPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monsterApplyDebuff, p, new GainStrengthPower(monsterApplyDebuff, this.magicNumber), this.magicNumber));
                }
            }
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StrengthPower(m, -this.magicNumber), -this.magicNumber));
            if (m != null && !m.hasPower("Artifact")) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new GainStrengthPower(m, this.magicNumber), this.magicNumber));
            }
        }
    }


    @Override
    public AbstractDynamicCard makeCopy() {
        return new SleightedHand();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.target = CardTarget.ALL_ENEMY;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
