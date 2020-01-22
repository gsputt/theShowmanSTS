package theShowman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theShowman.effects.CustomShuffleCardFromExhaustPileIntoDrawPileEffect;

import java.util.ArrayList;

public class ReappearingTrickAction extends AbstractGameAction {

    public ReappearingTrickAction()
    {
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
        for (AbstractCard card : exhaustList) {
            //exhaustList.remove(card);
            //AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(card, 1, true, true));
            AbstractDungeon.effectsQueue.add(new CustomShuffleCardFromExhaustPileIntoDrawPileEffect(card, true, false));

            //AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            //    @Override
            //    public void update() {
            //AbstractDungeon.player.exhaustPile.removeCard(card);
            //        this.isDone = true;
            //    }
            //});

            //AbstractDungeon.player.drawPile.addToRandomSpot(card);
        }
        exhaustList.clear();
        this.isDone = true;
    }
}
