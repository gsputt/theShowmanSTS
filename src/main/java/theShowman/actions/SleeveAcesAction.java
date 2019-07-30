package theShowman.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import theShowman.ShowmanMod;

import java.util.ArrayList;
import java.util.Iterator;

public class SleeveAcesAction extends AbstractGameAction {

    private AbstractPlayer p;
    public static final String ID = ShowmanMod.makeID("SleeveAcesAction");
    public static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);
    private static final String[] TEXT = UI_STRINGS.TEXT;
    private ArrayList<AbstractCard> dontShowForSelect = new ArrayList<>();

    private int copyAmount;

    public SleeveAcesAction(int amount, int copyAmount)
    {
        this.copyAmount = copyAmount;
        this.amount = amount;
        this.p = AbstractDungeon.player;
        this.target = AbstractDungeon.player;
        this.source = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update()
    {
        Iterator c;
        AbstractCard noRagrets;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hand.size() == BaseMod.MAX_HAND_SIZE) {
                AbstractDungeon.player.createHandIsFullDialog();
                this.isDone = true;
            } else if (this.p.exhaustPile.isEmpty()) {
                this.isDone = true;
            } else {
                c = this.p.exhaustPile.group.iterator();

                while(c.hasNext()) {
                    noRagrets = (AbstractCard)c.next();
                    noRagrets.stopGlowing();
                    noRagrets.unhover();
                    noRagrets.unfadeOut();
                }

                c = this.p.exhaustPile.group.iterator();

                while(c.hasNext()) {
                    noRagrets = (AbstractCard)c.next();

                    if(noRagrets.type == AbstractCard.CardType.STATUS || noRagrets.type == AbstractCard.CardType.CURSE) {
                        c.remove();
                        this.dontShowForSelect.add(noRagrets);
                    }

                }

                if (this.p.exhaustPile.isEmpty()) {
                    this.p.exhaustPile.group.addAll(this.dontShowForSelect);
                    this.dontShowForSelect.clear();
                    this.isDone = true;
                } else {

                    if (AbstractDungeon.player.hand.size() + this.amount > BaseMod.MAX_HAND_SIZE) {
                        this.amount = BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.size();
                    }
                    if(this.amount > AbstractDungeon.player.exhaustPile.size())
                    {
                        this.amount = AbstractDungeon.player.exhaustPile.size();
                    }
                    if (this.copyAmount == 1) {
                        AbstractDungeon.gridSelectScreen.open(this.p.exhaustPile, this.amount, TEXT[0], false);
                    } else {
                        AbstractDungeon.gridSelectScreen.open(this.p.exhaustPile, this.amount, TEXT[1] + this.copyAmount + TEXT[2], false);
                    }
                }
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                AbstractCard cardToAdd;
                for(c = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); c.hasNext(); noRagrets.unhover()) {
                    noRagrets = (AbstractCard)c.next();
                    cardToAdd = noRagrets.makeStatEquivalentCopy();
                    if (AbstractDungeon.player.hasPower("Corruption") && noRagrets.type == AbstractCard.CardType.SKILL) {
                        cardToAdd.setCostForTurn(-9);
                    }
                    //AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(cardToAdd, this.copyAmount));
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(cardToAdd, this.copyAmount));
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(cardToAdd, this.copyAmount, true, true));

                    //this.p.exhaustPile.removeCard(noRagrets);
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();
                this.p.exhaustPile.group.addAll(this.dontShowForSelect);
                this.dontShowForSelect.clear();

                for(c = this.p.exhaustPile.group.iterator(); c.hasNext(); noRagrets.target_y = 0.0F) {
                    noRagrets = (AbstractCard)c.next();
                    noRagrets.unhover();
                    noRagrets.target_x = (float) CardGroup.DISCARD_PILE_X;
                }
            }

            this.tickDuration();
        }
    }
}
