//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package theShowman.actions;

import basemod.BaseMod;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

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
                c.unfadeOut();
                this.p.hand.addToHand(c);

                c.setCostForTurn(0);

                this.p.exhaustPile.removeCard(c);

                c.unhover();
                c.fadingOut = false;
            }
            this.isDone = true;
        }
        this.isDone = true;
    }
    private AbstractCard getRandomCard() {
        ArrayList<AbstractCard> tmp = new ArrayList();
        Iterator var4 = this.p.exhaustPile.group.iterator();

        while(var4.hasNext()) {
            AbstractCard c = (AbstractCard)var4.next();
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
