package theShowman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.cards.ItsShowtime;
import theShowman.cards.Showstopper;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

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
        ArrayList<AbstractCard> exhaustList = new ArrayList<>();
        for(AbstractCard c : p.exhaustPile.group)
        {
            if(c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS && !(c instanceof Showstopper) && !(c instanceof ItsShowtime))
            {
               //boolean addThisCard = false;
                /*AbstractCard.CardColor colorToCheck;
                for(AbstractPlayer player : CardCrawlGame.characterManager.getAllCharacters()) {
                    colorToCheck = player.getCardColor();
                    if (c.color == colorToCheck) {
                        addThisCard = true;
                        break;
                    }
                }*/
                //if(addThisCard)
                //{
                    exhaustList.add(c.makeSameInstanceOf());
                //}
            }
        }
        if(this.amount > exhaustList.size())
        {
            this.amount = exhaustList.size();
        }
        for(int i = 0; i < this.amount; i++)
        {
            AbstractCard original = exhaustList.get(cardRandomRng.random(exhaustList.size() - 1));
            AbstractCard card = original.makeSameInstanceOf();
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    this.isDone = true;
                    AbstractMonster targetMonster = AbstractDungeon.getRandomMonster();


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



                }
            });
            exhaustList.remove(original);
        }
        exhaustList.clear();
        this.isDone = true;
    }
}
