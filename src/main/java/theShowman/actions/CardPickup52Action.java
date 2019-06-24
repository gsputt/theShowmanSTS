package theShowman.actions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;

import java.util.ArrayList;

import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

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

            if(c.color == COLOR_PURPLE)
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

            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(card, 1, true, true));

            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractDungeon.player.exhaustPile.removeCard(card);
                    this.isDone = true;
                }
            });


            exhaustList.remove(card);


            this.isDone = true;
        }
    }
}
