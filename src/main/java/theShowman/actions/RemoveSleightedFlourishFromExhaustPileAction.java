package theShowman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RemoveSleightedFlourishFromExhaustPileAction extends AbstractGameAction {

    AbstractCard card;
    public RemoveSleightedFlourishFromExhaustPileAction(AbstractCard card)
    {
        this.card = card;
    }
    @Override
    public void update()
    {
        this.isDone = true;
        AbstractDungeon.player.exhaustPile.removeCard(card);
    }
}
