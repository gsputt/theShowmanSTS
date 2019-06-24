package theShowman.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.ShowmanMod;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class SpeechCard extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("SpeechCard");
    public static final String IMG = makeCardPath("Skill.png");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 0;
    // /STAT DECLARATION/


    public SpeechCard() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        FleetingField.fleeting.set(this, true);
        this.isInnate = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, EXTENDED_DESCRIPTION[0], 2.0F, 2.0F));
        screwYourFastMode();

        int i = (int)(Math.random() * 9) + 1;
        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, EXTENDED_DESCRIPTION[i], 2.0F, 2.0F));
        screwYourFastMode();

        i = (int)(Math.random() * 9) + 10;
        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, EXTENDED_DESCRIPTION[i], 2.0F, 2.0F));
        screwYourFastMode();

        i = (int)(Math.random() * 9) + 19;
        if(i == 21)
        {
            i = (int)(Math.random() * 9) + 19;
        }
        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, EXTENDED_DESCRIPTION[i], 2.0F, 2.0F));
        screwYourFastMode();


    }

    private void screwYourFastMode()
    {
        for(int i = 0; i < 5; i++)
        {
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        }
    }

    @Override
    public AbstractDynamicCard makeCopy() {
        return new SpeechCard();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}
