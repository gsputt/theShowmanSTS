//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package theShowman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import theShowman.ShowmanMod;
import theShowman.powers.ForMyNextTrickPower;

public class ForMyNextTrickAction extends AbstractGameAction {
    public static final String ID = ShowmanMod.makeID("ForMyNextTrickAction");
    public static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);
    private static final String[] TEXT = UI_STRINGS.TEXT;
    private AbstractPlayer p;
    private static final float DURATION = Settings.ACTION_DUR_XFAST;

    public ForMyNextTrickAction(AbstractCreature target, AbstractCreature source, int amount) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.EXHAUST;
        this.duration = DURATION;
        this.p = (AbstractPlayer)target;
    }

    public void update() {
        if (this.duration == DURATION) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            } else if (this.p.hand.size() == 1) {
                AbstractCard c = this.p.hand.getBottomCard();
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.p, this.p, new ForMyNextTrickPower(this.p, this.amount, c.makeCopy())));
                this.p.hand.moveToExhaustPile(c);
                this.isDone = true;
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false);
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractCard tmpCard = AbstractDungeon.handCardSelectScreen.selectedCards.getBottomCard();
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.p, this.p, new ForMyNextTrickPower(this.p, this.amount, tmpCard)));
                AbstractDungeon.player.hand.addToHand(tmpCard);
                this.p.hand.moveToExhaustPile(tmpCard);
                AbstractDungeon.handCardSelectScreen.selectedCards.clear();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            this.tickDuration();
        }
    }
}
