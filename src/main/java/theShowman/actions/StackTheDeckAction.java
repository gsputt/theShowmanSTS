//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package theShowman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import theShowman.ShowmanMod;

import java.util.Iterator;

public class StackTheDeckAction extends AbstractGameAction {
    private AbstractPlayer p;
    public static final String ID = ShowmanMod.makeID("StackTheDeckAction");
    public static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);
    private static final String[] TEXT = UI_STRINGS.TEXT;
    private int amount;

    public StackTheDeckAction(int amount) {
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.amount = amount;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false, true, false, false, true);

                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractCard c;
                for (Iterator var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); var1.hasNext(); this.p.hand.moveToDeck(c, false)) {
                    c = (AbstractCard) var1.next();
                }

                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            this.tickDuration();
        }
    }
}
