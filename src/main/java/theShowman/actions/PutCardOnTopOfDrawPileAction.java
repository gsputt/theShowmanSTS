package theShowman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PutCardOnTopOfDrawPileAction extends AbstractGameAction {

    private AbstractCard c;
    private AbstractPlayer p;
    public PutCardOnTopOfDrawPileAction(final AbstractCard c)
    {
        this.c = c;
        this.p = AbstractDungeon.player;
    }

    @Override
    public void update()
    {
        if(this.p.drawPile.contains(c)) {
            this.p.drawPile.removeCard(c);
            this.p.drawPile.addToTop(c);
        }
        this.isDone = true;
    }
}
