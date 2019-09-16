package theShowman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class CardPickup52Action extends AbstractGameAction {
    AbstractPlayer p;
    public CardPickup52Action(int amount)
    {
        this.amount = amount;
        this.p = AbstractDungeon.player;
        this.target = AbstractDungeon.player;
        this.source = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update()
    {
        this.isDone = true;
        ArrayList<AbstractCard> exhaustList = new ArrayList<>();
        for(AbstractCard c : AbstractDungeon.player.exhaustPile.group)
        {
            c.stopGlowing();
            c.unhover();
            c.unfadeOut();

            boolean addThisCard = false;
            AbstractCard.CardColor colorToCheck;
            for(AbstractPlayer player : CardCrawlGame.characterManager.getAllCharacters()) {
                colorToCheck = player.getCardColor();
                if (c.color == colorToCheck) {
                    addThisCard = true;
                    break;
                }
            }
            if(addThisCard)
            {
                exhaustList.add(c);
            }
        }
        if(exhaustList.size() < this.amount)
        {
            this.amount = exhaustList.size();
        }
        for(int i = 0; i < this.amount; i++)
        {
            AbstractCard card = exhaustList.get(AbstractDungeon.cardRng.random(exhaustList.size() - 1));

            AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractDungeon.player.exhaustPile.removeCard(card);
                    this.isDone = true;
                }
            });

            AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(card, 1, true, true));

            exhaustList.remove(card);

            this.isDone = true;
        }
    }
}
