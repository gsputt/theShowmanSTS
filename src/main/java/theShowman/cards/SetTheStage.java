package theShowman.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.ShowmanMod;
import theShowman.powers.RetainThisTurnPower;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class SetTheStage extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("SetTheStage");
    public static final String IMG = makeCardPath("Skill.png");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 1;
    // /STAT DECLARATION/


    public SetTheStage() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            public void update() {
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, BaseMod.MAX_HAND_SIZE - p.hand.size()));
                this.isDone = true;
            }
        });

        if(this.upgraded)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RetainThisTurnPower(p, BaseMod.MAX_HAND_SIZE), BaseMod.MAX_HAND_SIZE));
        }
    }


    @Override
    public AbstractDynamicCard makeCopy() {
        return new SetTheStage();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
