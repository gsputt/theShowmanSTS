package theShowman.actions.Unused;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theShowman.patches.ImproviseField;

public class PropShowAction extends AbstractGameAction {

    private AbstractPlayer p;
    private int energyTotalUsed;

    public PropShowAction(AbstractPlayer p, int energyTotalUsed) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = p;
        this.energyTotalUsed = energyTotalUsed;
    }

    @Override
    public void update() {
        isDone = true;
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            public void update() {
                this.isDone = true;
                ImproviseField.ImproviseRecording.set(p, ImproviseField.ImproviseRecording.get(p) + 1);
            }
        });
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, energyTotalUsed));
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            public void update() {
                this.isDone = true;
                ImproviseField.ImproviseRecording.set(p, ImproviseField.ImproviseRecording.get(p) - 1);
            }
        });
    }
}

