package theShowman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import theShowman.ShowmanMod;

import java.util.Iterator;

public class BottomDoubleDealAction extends AbstractGameAction {
    private AbstractPlayer p;
    public static final String ID = ShowmanMod.makeID("BottomDoubleDealAction");
    public static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);
    private static final String[] TEXT = UI_STRINGS.TEXT;
    private int check;

    public  BottomDoubleDealAction(final int check) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = AbstractDungeon.player;
        this.check = check;
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
            CardGroup skillGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            CardGroup skillAndAttackGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            CardGroup attackGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for(int i = 0; i < this.p.drawPile.size(); ++i) {
                AbstractCard c = AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - i - 1);
                if(c.type == AbstractCard.CardType.SKILL) {
                    skillGroup.addToTop(c);
                }
                if(c.type == AbstractCard.CardType.SKILL || c.type == AbstractCard.CardType.ATTACK)
                {
                    skillAndAttackGroup.addToTop(c);
                }
                if(c.type == AbstractCard.CardType.ATTACK)
                {
                    attackGroup.addToTop(c);
                }
            }

            if(this.check == 0) {
                if(skillAndAttackGroup.size() > 0) {
                    AbstractDungeon.gridSelectScreen.open(skillAndAttackGroup, 1, !(skillAndAttackGroup.size() > 0), TEXT[0]);
                }
            }
            else if(this.check == 1)
            {
                if(skillGroup.size() > 0) {
                    AbstractDungeon.gridSelectScreen.open(skillGroup, 1, !(skillGroup.size() > 0), TEXT[1]);
                }
                AbstractDungeon.actionManager.addToBottom(new BottomDoubleDealAction(2));
            }
            else if(this.check == 2)
            {
                if(attackGroup.size() > 0) {
                    AbstractDungeon.gridSelectScreen.open(attackGroup, 1, !(attackGroup.size() > 0), TEXT[2]);
                }
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
                    AbstractDungeon.actionManager.addToTop(new DrawCardAction(this.p, 1));
                    AbstractDungeon.actionManager.addToTop(new PutCardOnTopOfDrawPileAction(c));
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.isDone = true;
            }
            this.tickDuration();
        }
    }
}


