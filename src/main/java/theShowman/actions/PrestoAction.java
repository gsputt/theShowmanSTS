package theShowman.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collections;

public class PrestoAction extends AbstractGameAction {
    private AbstractPlayer p;

    public PrestoAction() {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        if (AbstractDungeon.player.hand.size() >= BaseMod.MAX_HAND_SIZE) {
            AbstractDungeon.player.createHandIsFullDialog();
            this.isDone = true;
        } else if (this.p.exhaustPile.isEmpty()) {
            this.isDone = true;
        } else {
            AbstractCard c = getRandomCard();
            if(c != null) {

                c.current_x = AbstractDungeon.overlayMenu.exhaustPanel.current_x;
                c.current_y = AbstractDungeon.overlayMenu.exhaustPanel.current_y;
                c.target_x = (Settings.WIDTH / 2.0F);
                c.target_y = (Settings.HEIGHT / 2.0F);

                c.unfadeOut();
                this.p.hand.addToHand(c);

                c.setCostForTurn(0);

                this.p.exhaustPile.removeCard(c);

                c.unhover();
                c.fadingOut = false;
            }
            this.isDone = true;
        }
    }
    private AbstractCard getRandomCard() {
        ArrayList<AbstractCard> tmp = new ArrayList<>();

        //Decompiler why

        for (AbstractCard c : this.p.exhaustPile.group) {
            if (c.type != CardType.CURSE && c.type != CardType.STATUS) {
                tmp.add(c);
            }
        }

        if (tmp.isEmpty()) {
            return null;
        } else {
            Collections.sort(tmp);
            return (AbstractCard)tmp.get(AbstractDungeon.cardRng.random(tmp.size() - 1));
        }
    }
}
