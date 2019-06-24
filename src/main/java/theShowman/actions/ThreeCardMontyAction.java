
package theShowman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ThreeCardMontyAction extends AbstractGameAction {
    public ThreeCardMontyAction(int amount) {
        this.amount = amount;
        this.target = AbstractDungeon.player;
        this.source = AbstractDungeon.player;
        this.actionType = ActionType.DRAW;
    }

    @Override
    public void update()
    {
        this.isDone = true;
        AbstractCard c = AbstractDungeon.player.hand.getTopCard();
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c.makeStatEquivalentCopy(), 2));
    }
}


