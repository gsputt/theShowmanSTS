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

public class KnockThePileAction extends AbstractGameAction {
    private AbstractPlayer p;
    public static final String ID = ShowmanMod.makeID("KnockThePileAction");
    public static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);
    private static final String[] TEXT = UI_STRINGS.TEXT;

    public  KnockThePileAction(final int amount) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = AbstractDungeon.player;
        this.amount = amount;
        this.p = AbstractDungeon.player;
    }

    @Override
    public void update() {
        if(this.p.drawPile.size() == 0)
        {
            this.isDone = true;
            return;
        }
        else if(this.duration == Settings.ACTION_DUR_FAST)
        {
            CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            if(this.amount > this.p.drawPile.size())
            {
                this.amount = this.p.drawPile.size();
            }
            for(int i = 0; i < this.amount; ++i) {
                tmpGroup.addToTop((AbstractCard)AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - i - 1));
            }

            AbstractDungeon.gridSelectScreen.open(tmpGroup, this.amount, true, TEXT[0] + this.amount + TEXT[1]);
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


