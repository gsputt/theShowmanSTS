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

public class NowYouDontAction extends AbstractGameAction {
    private boolean isRandom;
    private AbstractPlayer p;
    public static final String ID = ShowmanMod.makeID("NowYouDontAction");
    public static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);
    private static final String[] TEXT = UI_STRINGS.TEXT;
    private boolean anyNumber;

    public  NowYouDontAction(final boolean isRandom, final boolean anyNumber) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = AbstractDungeon.player;
        this.amount = 1;
        if(anyNumber)
        {
            this.amount = 999;
        }
        this.p = AbstractDungeon.player;
        this.isRandom = isRandom;
        this.anyNumber = anyNumber;
    }

    @Override
    public void update() {
        if(this.p.drawPile.size() == 0)
        {
            this.isDone = true;
            return;
        }
        /*else if(this.p.drawPile.size() == 1)
        {
            AbstractCard card = (AbstractCard)this.p.drawPile.group.get(0);
            this.p.drawPile.moveToExhaustPile(card);
            card.lighten(false);
            this.isDone = true;
            return;
        }*/
        else if(this.isRandom)
        {
            AbstractCard card1 = (AbstractCard)this.p.drawPile.group.get(AbstractDungeon.cardRandomRng.random(AbstractDungeon.player.drawPile.group.size() - 1));
            this.p.drawPile.moveToExhaustPile(card1);
            card1.lighten(false);
            this.isDone = true;
            return;
        }

        else if(this.duration == Settings.ACTION_DUR_FAST)
        {
            if(this.anyNumber)
            {
                AbstractDungeon.gridSelectScreen.open(this.p.drawPile, this.amount, this.anyNumber, TEXT[1]);
            }
            else {
                AbstractDungeon.gridSelectScreen.open(this.p.drawPile, this.amount, TEXT[0], false, false, false, false);
            }
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
                    this.p.drawPile.moveToExhaustPile(c);
                    c.lighten(false);
                    c.unhover();
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                for(var1 = this.p.drawPile.group.iterator(); var1.hasNext(); c.target_y = 0.0F)
                {
                    c = (AbstractCard)var1.next();
                    c.unhover();
                    c.target_x = (float) CardGroup.DRAW_PILE_X;
                }

                this.isDone = true;
            }
            this.tickDuration();
        }
    }
}


