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

public class AndBehindCurtainAction extends AbstractGameAction {
    private AbstractPlayer p;
    public static final String ID = ShowmanMod.makeID("AndBehindCurtainAction");
    public static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);
    private static final String[] TEXT = UI_STRINGS.TEXT;
    private ArrayList<AbstractCard> notClassCard = new ArrayList<>();

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

                    boolean addThisCard = false;
                    AbstractCard.CardColor colorToCheck;
                    for(AbstractPlayer player : CardCrawlGame.characterManager.getAllCharacters()) {
                        colorToCheck = player.getCardColor();
                        if (noRagrets.color == colorToCheck) {
                            addThisCard = true;
                            break;
                        }
                    }
                    if(!addThisCard)
                    {
                        c.remove();
                        this.notClassCard.add(noRagrets);
                    }

                }

                if (this.p.exhaustPile.isEmpty()) {
                    this.p.exhaustPile.group.addAll(this.notClassCard);
                    this.notClassCard.clear();
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
                AbstractCard cardToAddToHand;
                for(c = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); c.hasNext(); noRagrets.unhover()) {
                    noRagrets = (AbstractCard)c.next();
                     cardToAddToHand = noRagrets.makeSameInstanceOf();
                    this.p.hand.addToHand(cardToAddToHand);
                    if (AbstractDungeon.player.hasPower("Corruption") && noRagrets.type == CardType.SKILL) {
                        cardToAddToHand.setCostForTurn(-9);
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
                this.p.exhaustPile.group.addAll(this.notClassCard);
                this.notClassCard.clear();

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
