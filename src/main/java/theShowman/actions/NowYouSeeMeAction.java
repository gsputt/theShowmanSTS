package theShowman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import theShowman.ShowmanMod;

import java.util.Iterator;

public class NowYouSeeMeAction extends AbstractGameAction {
    private boolean isRandom;
    private AbstractPlayer p;
    public static final String ID = ShowmanMod.makeID("NowYouSeeMeAction");
    public static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);
    private static final String[] TEXT = UI_STRINGS.TEXT;


    public NowYouSeeMeAction(final boolean upgraded) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = AbstractDungeon.player;
        this.amount = 1;
        this.p = AbstractDungeon.player;
        this.isRandom = upgraded;
    }

    @Override
    public void update() {
        if(this.p.discardPile.size() == 0)
        {
            this.isDone = true;
            return;
        }
        /*else if(this.p.discardPile.size() == 1)
        {
            AbstractCard card = (AbstractCard)this.p.discardPile.group.get(0);
            this.p.discardPile.moveToExhaustPile(card);
            card.lighten(false);
            this.isDone = true;
            return;
        }*/
        else if(this.isRandom)
        {
            AbstractCard card1 = (AbstractCard)this.p.discardPile.group.get(AbstractDungeon.cardRandomRng.random(AbstractDungeon.player.discardPile.group.size() - 1));
            this.p.discardPile.moveToExhaustPile(card1);
            card1.lighten(false);
            this.isDone = true;
            return;
        }
        else if(this.duration == Settings.ACTION_DUR_FAST)
        {
            AbstractDungeon.gridSelectScreen.open(this.p.discardPile, this.amount, TEXT[0], false);
            this.tickDuration();
        }
        else
        {
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0)
            {
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                AbstractCard c;
                while(var1.hasNext())
                {
                    c = (AbstractCard)var1.next();
                    this.p.discardPile.moveToExhaustPile(c);
                    c.lighten(false);
                    c.unhover();
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                for(var1 = this.p.discardPile.group.iterator(); var1.hasNext(); c.target_y = 0.0F)
                {
                    c = (AbstractCard)var1.next();
                    c.unhover();
                    c.target_x = (float) CardGroup.DISCARD_PILE_X;
                }

                this.isDone = true;
            }
            this.tickDuration();
        }
    }
}


