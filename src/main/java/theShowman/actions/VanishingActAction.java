//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package theShowman.actions;

import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import theShowman.ShowmanMod;

import java.util.Iterator;


public class VanishingActAction extends AbstractGameAction {
    public static final String ID = ShowmanMod.makeID("VanishingActAction");
    public static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);
    private static final String[] TEXT = UI_STRINGS.TEXT;
    private AbstractPlayer p;
    private boolean isRandom;
    private boolean anyNumber;
    private boolean canPickZero;
    public static int numExhausted;

    public VanishingActAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom) {
        this(target, source, amount, isRandom, false, false);
    }

    public VanishingActAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, boolean anyNumber, boolean canPickZero) {
        this.canPickZero = false;
        this.anyNumber = anyNumber;
        this.canPickZero = canPickZero;
        this.p = (AbstractPlayer)target;
        this.isRandom = isRandom;
        this.setValues(target, source, amount);
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public VanishingActAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, boolean anyNumber) {
        this(target, source, amount, isRandom, anyNumber, false);
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.size() == 0) {
                this.isDone = true;
                return;
            }

            int i;
            if (!this.anyNumber && this.p.hand.size() <= this.amount) {
                this.amount = this.p.hand.size();
                numExhausted = this.amount;
                i = this.p.hand.size();

                for(int j = 0; j < i; j++) {
                    AbstractCard c = this.p.hand.getTopCard();
                    doStuff(c);
                }
                return;
            }

            if (!this.isRandom) {
                numExhausted = this.amount;
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, this.anyNumber, this.canPickZero);
                this.tickDuration();
                return;
            }

            for(i = 0; i < this.amount; ++i) {
                doStuff(this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng));
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            Iterator var4 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while(var4.hasNext()) {
                AbstractCard c = (AbstractCard)var4.next();
                doStuff(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
    }


    public void doStuff(AbstractCard card)
    {
        AbstractCard cardToRemove = StSLib.getMasterDeckEquivalent(card);
        if(cardToRemove != null)
        {
            AbstractDungeon.player.masterDeck.removeCard(cardToRemove);
        }
        this.p.hand.removeCard(card);
        AbstractDungeon.getCurrRoom().souls.remove(card);
        this.p.hand.refreshHandLayout();
        AbstractDungeon.actionManager.addToTop(new ShowCardAndPoofAction(card));

    }
}
