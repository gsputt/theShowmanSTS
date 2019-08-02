package theShowman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import theShowman.ShowmanMod;
import theShowman.powers.DisembodiedHandPower;

import java.util.Iterator;


public class DisembodiedHandAction extends AbstractGameAction {
    public static final String ID = ShowmanMod.makeID("DisembodiedHandAction");
    public static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);
    private static final String[] TEXT = UI_STRINGS.TEXT;
    private AbstractPlayer p;
    private boolean isRandom;
    private boolean anyNumber;
    private boolean canPickZero;

    public DisembodiedHandAction(final boolean upgraded) {
        this.canPickZero = false;
        this.anyNumber = false;
        this.canPickZero = false;
        this.p = AbstractDungeon.player;
        this.isRandom = !upgraded;
        this.setValues(p, p, 1);
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.size() == 0) {
                this.isDone = true;
                return;
            }

            int i;
            if (this.p.hand.size() <= this.amount) {
                i = this.p.hand.size();

                for(int j = 0; j < i; j++) {
                    AbstractCard c = this.p.hand.getTopCard();
                    doStuff(c);
                }
                this.isDone = true;
                return;
            }

            if (!this.isRandom) {
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


    private void doStuff(AbstractCard card)
    {
        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, this.p.hand));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DisembodiedHandPower(p, card)));
    }
}
