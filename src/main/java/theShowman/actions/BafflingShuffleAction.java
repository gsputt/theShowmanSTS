package theShowman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.Iterator;

public class BafflingShuffleAction extends AbstractGameAction {

    private AbstractPlayer p;
    private AbstractMonster m;
    private int amount;

    public BafflingShuffleAction(AbstractMonster m, int amount)
    {
        this.p = AbstractDungeon.player;
        this.m = m;
        this.amount = amount;

    }

    @Override
    public void update()
    {
        if(p.drawPile.size() + p.discardPile.size() == 0)
        {
            this.isDone = true;
            return;
        }
        AbstractCard c;
        CardGroup g = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        int totalCost = 0;
        if(this.amount > p.drawPile.size() + p.discardPile.size())
        {
            this.amount = p.drawPile.size() + p.discardPile.size();
        }
        if(this.amount > p.drawPile.size())
        {
            AbstractDungeon.actionManager.addToTop(new BafflingShuffleAction(m, this.amount));
            AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
            this.isDone = true;
            return;
        }
        for(int i = 0; i < this.amount; i++)
        {
            if(p.drawPile.size() > 0) {
                c = p.drawPile.getTopCard();
                g.addToTop(c);
                if (c.cost == -1) {
                    totalCost += EnergyPanel.totalCount;
                } else if (c.cost < -1) {
                    totalCost += 0;
                } else {
                    totalCost += c.cost;
                }
                p.drawPile.removeCard(c);
            }
        }
        Iterator addBack = g.group.iterator();
        while(addBack.hasNext())
        {
            p.drawPile.addToTop((AbstractCard) addBack.next());
        }
        g.clear();
        if(totalCost > 0) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, p, new VulnerablePower(m, totalCost, false), totalCost));
        }
        AbstractDungeon.actionManager.addToTop(new DrawCardAction(p, this.amount));

        this.isDone = true;
    }
}
