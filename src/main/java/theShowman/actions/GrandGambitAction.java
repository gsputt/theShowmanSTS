package theShowman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import theShowman.ShowmanMod;

import java.util.Iterator;

public class GrandGambitAction extends AbstractGameAction {

    private int plusAmount;
    private AbstractPlayer p;
    private boolean anyNumber;
    private boolean canPickZero;

    public static final String ID = ShowmanMod.makeID("GrandGambitAction");
    public static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);
    private static final String[] TEXT = UI_STRINGS.TEXT;


    public GrandGambitAction(int plus)
    {
        this.plusAmount = plus;
        this.anyNumber = true;
        this.canPickZero = true;
        this.p = AbstractDungeon.player;
        this.target = this.p;
        this.source = this.p;
        this.amount = AbstractDungeon.player.hand.size();
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.EXHAUST;
    }

    @Override
    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.size() == 0) {
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, this.anyNumber, this.canPickZero);
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            Iterator var4 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();
            int i = 0;
            while(var4.hasNext()) {
                AbstractCard c = (AbstractCard)var4.next();
                this.p.hand.moveToExhaustPile(c);
                i++;
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;

            i += this.plusAmount;

            if(i > 0)
            {
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, i));
            }


        }

        this.tickDuration();
    }
}


