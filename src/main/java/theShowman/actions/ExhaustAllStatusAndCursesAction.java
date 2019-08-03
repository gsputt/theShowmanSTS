//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package theShowman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class ExhaustAllStatusAndCursesAction extends AbstractGameAction {
    private float startingDuration;

    public ExhaustAllStatusAndCursesAction() {
        this.actionType = ActionType.WAIT;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            Iterator var1 = AbstractDungeon.player.hand.group.iterator();
            ArrayList<AbstractCard> cardsToRemove = new ArrayList<>();
            while(var1.hasNext()) {
                AbstractCard c = (AbstractCard)var1.next();
                if (c.type == CardType.CURSE || c.type == CardType.STATUS) {
                    cardsToRemove.add(c);
                }
            }
            while(cardsToRemove.size() > 0)
            {
                AbstractDungeon.player.hand.moveToExhaustPile(cardsToRemove.get(0));
                cardsToRemove.remove(0);
            }

            this.isDone = true;
        }

    }
}
