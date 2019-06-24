package theShowman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class VanDeGraaffDazedAction extends AbstractGameAction {

    public VanDeGraaffDazedAction(int amount) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.amount = amount;
    }

    @Override
    public void update() {
        isDone = true;
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Dazed(), this.amount, true, true));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Dazed(), this.amount));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Dazed(), this.amount));
    }
}