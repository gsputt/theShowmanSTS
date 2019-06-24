package theShowman.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import theShowman.ShowmanMod;

import java.util.ArrayList;
import java.util.Iterator;

import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class AndBehindCurtainAction extends AbstractGameAction {
    private AbstractPlayer p;
    public static final String ID = ShowmanMod.makeID("AndBehindCurtainAction");
    public static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);
    private static final String[] TEXT = UI_STRINGS.TEXT;
    private ArrayList<AbstractCard> exhumes = new ArrayList();
    private ArrayList<AbstractCard> notPurple = new ArrayList<>();

    public AndBehindCurtainAction(int amount) {
        this.amount = amount;
        this.p = AbstractDungeon.player;
        this.target = AbstractDungeon.player;
        this.source = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        Iterator c;
        AbstractCard noRagrets;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hand.size() == BaseMod.MAX_HAND_SIZE) {
                AbstractDungeon.player.createHandIsFullDialog();
                this.isDone = true;
            } else if (this.p.exhaustPile.isEmpty()) {
                this.isDone = true;
            } else if (this.p.exhaustPile.size() == 1) {
                AbstractCard card = this.p.exhaustPile.getTopCard();
                card.unfadeOut();
                this.p.hand.addToHand(card);
                if (AbstractDungeon.player.hasPower("Corruption") && card.type == CardType.SKILL) {
                    card.setCostForTurn(-9);
                }

                this.p.exhaustPile.removeCard(card);

                card.unhover();
                card.fadingOut = false;
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
                    if (noRagrets.color != COLOR_PURPLE) {
                        c.remove();
                        this.notPurple.add(noRagrets);
                    }
                }

                if (this.p.exhaustPile.isEmpty()) {
                    this.p.exhaustPile.group.addAll(this.notPurple);
                    this.notPurple.clear();
                    this.isDone = true;
                } else {

                /*c = this.p.exhaustPile.group.iterator();

                while(c.hasNext()) {
                    noRagrets = (AbstractCard)c.next();
                    if (noRagrets.cardID.equals("Exhume") || noRagrets.cardID.equals("AndBehindCurtain")) {
                        c.remove();
                        this.exhumes.add(noRagrets);
                    }
                }
                if (this.p.exhaustPile.isEmpty()) {
                    this.p.exhaustPile.group.addAll(this.exhumes);
                    this.exhumes.clear();
                    this.isDone = true;
                } else {*/
                    if (AbstractDungeon.player.hand.size() + this.amount > BaseMod.MAX_HAND_SIZE) {
                        this.amount = BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.size();
                    }
                    if(this.amount > AbstractDungeon.player.exhaustPile.size())
                    {
                        this.amount = AbstractDungeon.player.exhaustPile.size();
                    }
                    if (this.amount == 1) {
                        AbstractDungeon.gridSelectScreen.open(this.p.exhaustPile, this.amount, TEXT[0], false);
                    } else {
                        AbstractDungeon.gridSelectScreen.open(this.p.exhaustPile, this.amount, TEXT[1] + this.amount + TEXT[2], false);
                    }
                }
                //}
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for(c = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); c.hasNext(); noRagrets.unhover()) {
                    noRagrets = (AbstractCard)c.next();
                    this.p.hand.addToHand(noRagrets);
                    if (AbstractDungeon.player.hasPower("Corruption") && noRagrets.type == CardType.SKILL) {
                        noRagrets.setCostForTurn(-9);
                    }

                    this.p.exhaustPile.removeCard(noRagrets);
                    /*if (this.upgrade && noRagrets.canUpgrade()) {
                        noRagrets.upgrade();
                    }*/
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();
                //this.p.exhaustPile.group.addAll(this.exhumes);
                //this.exhumes.clear();
                this.p.exhaustPile.group.addAll(this.notPurple);
                this.notPurple.clear();

                for(c = this.p.exhaustPile.group.iterator(); c.hasNext(); noRagrets.target_y = 0.0F) {
                    noRagrets = (AbstractCard)c.next();
                    noRagrets.unhover();
                    noRagrets.target_x = (float)CardGroup.DISCARD_PILE_X;
                }
            }

            this.tickDuration();
        }
    }

}
