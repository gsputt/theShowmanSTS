package theShowman.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theShowman.effects.CustomShuffleCardFromExhaustPileIntoDrawPileEffect;

public class SwapDrawAndExhaustPileAction extends AbstractGameAction {

    @Override
    public void update()
    {
        this.isDone = true;
        //CardGroup draw = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        CardGroup exhaust = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        /*for(AbstractCard c: AbstractDungeon.player.drawPile.group)
        {
            draw.addToBottom(c);
        }*/
        for(AbstractCard c: AbstractDungeon.player.exhaustPile.group)
        {
            exhaust.addToBottom(c);
        }
        /*while(AbstractDungeon.player.exhaustPile.size() > 0)
        {
            AbstractDungeon.player.exhaustPile.removeTopCard();
        }*/
        AbstractCard card;
        while(AbstractDungeon.player.drawPile.size() > 0) {
            card = AbstractDungeon.player.drawPile.getTopCard();
            card.current_x = card.target_x = MathUtils.random((float) Settings.WIDTH * 0.1F, (float)Settings.WIDTH * 0.9F);
            card.current_y = card.target_y = MathUtils.random((float)Settings.HEIGHT * 0.8F, (float)Settings.HEIGHT * 0.2F);
            AbstractDungeon.player.drawPile.moveToExhaustPile(card);
        }

        /*for(AbstractCard c: draw.group)
        {
            c.current_x = c.target_x = MathUtils.random((float) Settings.WIDTH * 0.1F, (float)Settings.WIDTH * 0.9F);
            c.current_y = c.target_y = MathUtils.random((float)Settings.HEIGHT * 0.8F, (float)Settings.HEIGHT * 0.2F);
            AbstractDungeon.effectsQueue.add(new ExhaustCardEffect(c));
            AbstractDungeon.player.exhaustPile.addToBottom(c);
        }*/
        //draw.clear();
        for(AbstractCard c: exhaust.group)
        {
            //AbstractDungeon.player.drawPile.addToRandomSpot(c.makeSameInstanceOf());
            AbstractDungeon.effectsQueue.add(new CustomShuffleCardFromExhaustPileIntoDrawPileEffect(c, true, false));
        }
        exhaust.clear();
    }

}
