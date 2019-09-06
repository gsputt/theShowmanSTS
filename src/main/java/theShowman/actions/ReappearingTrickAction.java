package theShowman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

import java.util.ArrayList;

public class ReappearingTrickAction extends AbstractGameAction {


    private AbstractPlayer p;
    public ReappearingTrickAction()
    {
        this.p = AbstractDungeon.player;
        this.target = AbstractDungeon.player;
        this.source = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update()
    {
        ArrayList<AbstractCard> exhaustList = new ArrayList<>();
        for(AbstractCard c : AbstractDungeon.player.exhaustPile.group)
        {
            c.stopGlowing();
            c.unhover();
            c.unfadeOut();

            if(c.type != AbstractCard.CardType.STATUS && c.type != AbstractCard.CardType.CURSE)
            {
                exhaustList.add(c);
            }
        }
        for(int i = 0; i < exhaustList.size(); i++)
        {
            AbstractCard card = exhaustList.get(i);
            //exhaustList.remove(card);
            //AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(card, 1, true, true));
            AbstractDungeon.effectsQueue.add(new ShowCardAndAddToDrawPileEffect(card.makeSameInstanceOf(), true, false));

            //AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            //    @Override
            //    public void update() {
                    AbstractDungeon.player.exhaustPile.removeCard(card);
            //        this.isDone = true;
            //    }
            //});

            //AbstractDungeon.player.drawPile.addToRandomSpot(card);
        }
        exhaustList.clear();
        this.isDone = true;
    }
}
