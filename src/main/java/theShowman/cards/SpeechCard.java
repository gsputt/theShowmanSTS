package theShowman.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.ShowmanMod;
import theShowman.actions.QueueSpeechEffect;
import theShowman.patches.ImproviseField;

import java.util.ArrayList;

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
        if(this.upgraded) {
            //AbstractDungeon.actionManager.addToBottom(new VFXAction(new CurtainVFX2()));
            //AbstractDungeon.actionManager.addToBottom(new TalkAction(true, EXTENDED_DESCRIPTION[28], 0.1F, 0.1F));

            ArrayList<String> sayThis = new ArrayList<>();
            if(CardCrawlGame.playerName.equals("sneakyteak"))
            {
                sayThis.add(EXTENDED_DESCRIPTION[0]);
                sayThis.add(EXTENDED_DESCRIPTION[29]);
                sayThis.add(EXTENDED_DESCRIPTION[30]);
                sayThis.add(EXTENDED_DESCRIPTION[31]);
            }
            else {
                sayThis.add(EXTENDED_DESCRIPTION[0]);
                int i = (int) (Math.random() * 9) + 1;
                sayThis.add(EXTENDED_DESCRIPTION[i]);
                i = (int) (Math.random() * 9) + 10;
                sayThis.add(EXTENDED_DESCRIPTION[i]);
                i = (int) (Math.random() * 9) + 19;
                if (i == 21) {
                    i = (int) (Math.random() * 9) + 19;
                }
                sayThis.add(EXTENDED_DESCRIPTION[i]);
            }

            AbstractDungeon.effectList.add(new QueueSpeechEffect(sayThis, 0.75F, true));
        }
        else {
            //AbstractDungeon.actionManager.addToBottom(new VFXAction(new CurtainVFX()));
            ArrayList<String> talkList = new ArrayList<>();
            if(CardCrawlGame.playerName.equals("sneakyteak"))
            {
                talkList.add(EXTENDED_DESCRIPTION[0]);
                talkList.add(EXTENDED_DESCRIPTION[29]);
                talkList.add(EXTENDED_DESCRIPTION[30]);
                talkList.add(EXTENDED_DESCRIPTION[31]);
            }
            else {
                talkList.add(EXTENDED_DESCRIPTION[0]);
                int i = (int) (Math.random() * 9) + 1;
                talkList.add(EXTENDED_DESCRIPTION[i]);
                i = (int) (Math.random() * 9) + 10;
                talkList.add(EXTENDED_DESCRIPTION[i]);
                i = (int) (Math.random() * 9) + 19;
                if (i == 21) {
                    i = (int) (Math.random() * 9) + 19;
                }
                talkList.add(EXTENDED_DESCRIPTION[i]);
            }

            AbstractDungeon.effectList.add(new QueueSpeechEffect(talkList, 2.0F, false));
        }
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                this.isDone = true;
                ImproviseField.ImproviseRecording.set(p, ImproviseField.ImproviseRecording.get(p) + 1);
            }
        });
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                this.isDone = true;
                ImproviseField.ImproviseRecording.set(p, ImproviseField.ImproviseRecording.get(p) - 1);
            }
        });
    }

    /*
    private void waitForShortTime()
    {
        for(int i = 0; i < 5; i++)
        {
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        }
    }*/

    @Override
    public AbstractDynamicCard makeCopy() {
        return new SpeechCard();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = true;
            FleetingField.fleeting.set(this, false);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
