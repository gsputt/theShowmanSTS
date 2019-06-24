package theShowman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.cards.ItsShowtime;
import theShowman.cards.Showstopper;

import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class ShowAction extends AbstractGameAction {

    private AbstractPlayer p;
    private int amount;

    public ShowAction(int amount)
    {
        this.amount = amount;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.WAIT;
    }

    @Override
    public void update()
    {
        CardGroup exhaustList = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for(AbstractCard c : p.exhaustPile.group)
        {
            if(c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS)
            {
                if(c.color == COLOR_PURPLE && !(c.cardID.equals(Showstopper.ID) || c.cardID.equals(ItsShowtime.ID)))
                {
                    exhaustList.addToRandomSpot(c.makeStatEquivalentCopy());
                }
            }
        }
        if(this.amount > exhaustList.size())
        {
            this.amount = exhaustList.size();
        }
        for(int i = 0; i < this.amount; i++)
        {
            AbstractMonster targetMonster = AbstractDungeon.getRandomMonster();
            AbstractCard card = exhaustList.getTopCard().makeSameInstanceOf();

            card.current_x = card.target_x = Settings.WIDTH / 2.0f - 300.0f * Settings.scale;
            card.current_y = card.target_y = Settings.HEIGHT / 2.0f;
            card.freeToPlayOnce = true;
            if(card.canUse(p, targetMonster)) {
                card.applyPowers();
                if (targetMonster != null) {
                    card.calculateCardDamage(targetMonster);
                }
                card.purgeOnUse = true;
                AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(card, targetMonster));
            }


            exhaustList.removeCard(exhaustList.getTopCard());
        }
        exhaustList.clear();
        this.isDone = true;
    }
}
