package theShowman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theShowman.ShowmanMod;
import theShowman.patches.VentriloquismField;
import theShowman.util.TextureLoader;

import java.util.ArrayList;

import static theShowman.ShowmanMod.makePowerPath;


public class VentriloquismPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = ShowmanMod.makeID("VentriloquismPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Ventriloquism84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Ventriloquism32.png"));

    public boolean upgraded;

    public VentriloquismPower(final AbstractCreature owner, final int amount, final boolean upgraded) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.upgraded = this.upgraded || upgraded;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m)
    {
        if(VentriloquismField.linked.get(card) != null) {

                AbstractCard cardToCopy = VentriloquismField.linked.get(card);

                AbstractCard cardToPlay = cardToCopy.makeStatEquivalentCopy();
                VentriloquismField.linked.set(cardToPlay, null);

                if (this.upgraded) {
                    VentriloquismField.linked.set(cardToCopy, card);
                } else {
                    VentriloquismField.linked.set(cardToCopy, null);
                    VentriloquismField.linked.set(card, null);
                }

                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                    @Override
                    public void update() {
                        this.isDone = true;
                        AbstractMonster target = AbstractDungeon.getRandomMonster();
                        cardToPlay.freeToPlayOnce = true;
                        cardToPlay.purgeOnUse = true;
                        cardToPlay.applyPowers();
                        if (cardToPlay.canUse(AbstractDungeon.player, target)) {
                            cardToPlay.calculateCardDamage(target);
                            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(cardToPlay, target));
                        }
                    }
                });
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer)
    {
        for(AbstractCard c : AbstractDungeon.player.drawPile.group)
        {
            VentriloquismField.linked.set(c, null);
        }
        for(AbstractCard c : AbstractDungeon.player.discardPile.group)
        {
            VentriloquismField.linked.set(c, null);
        }
        for(AbstractCard c : AbstractDungeon.player.exhaustPile.group)
        {
            VentriloquismField.linked.set(c, null);
        }
    }

    @Override
    public void atStartOfTurn()
    {
        for(AbstractCard c : AbstractDungeon.player.drawPile.group)
        {
            VentriloquismField.linked.set(c, null);
        }
        for(AbstractCard c : AbstractDungeon.player.discardPile.group)
        {
            VentriloquismField.linked.set(c, null);
        }
        for(AbstractCard c : AbstractDungeon.player.exhaustPile.group)
        {
            VentriloquismField.linked.set(c, null);
        }

        ArrayList<AbstractCard> drawPile = new ArrayList<>(AbstractDungeon.player.drawPile.group);
        ArrayList<AbstractCard> exhaustPile = new ArrayList<>(AbstractDungeon.player.exhaustPile.group);
        if(!(drawPile.isEmpty() || exhaustPile.isEmpty())) {
            for (int i = 0; i < this.amount; i++) {
                if (drawPile.size() > 0 && exhaustPile.size() > 0) {
                    AbstractCard drawPileCard = null;
                    AbstractCard exhaustPileCard = null;
                    while(drawPile.size() > 0 && (drawPileCard == null || drawPileCard.cost == -2))
                    {
                        drawPileCard = pickCard(drawPile);
                    }
                    while(exhaustPile.size() > 0 && (exhaustPileCard == null || exhaustPileCard.cost == -2))
                    {
                        exhaustPileCard = pickCard(exhaustPile);
                    }
                    if(drawPileCard != null && exhaustPileCard != null) {
                        VentriloquismField.linked.set(drawPileCard, exhaustPileCard);
                        VentriloquismField.linked.set(exhaustPileCard, drawPileCard);
                        //ShowmanMod.logger.info("Linked: " + drawPileCard + " to " + exhaustPileCard);
                    }
                }
            }
            drawPile.clear();
            exhaustPile.clear();
        }
    }

    private AbstractCard pickCard(ArrayList<AbstractCard> chooseFrom)
    {
        AbstractCard chosenCard = chooseFrom.get(AbstractDungeon.cardRandomRng.random(chooseFrom.size() - 1));
        chooseFrom.remove(chosenCard);
        if(chosenCard.cost == -2)
        {
            return null;
        }
        else {
            return chosenCard;
        }
    }

    @Override
    public void updateDescription() {
        if(!this.upgraded)
        {
            if(this.amount == 1) {
                description = DESCRIPTIONS[0];
            }
            else
            {
                description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3];
            }
        }
        else
        {
            if(this.amount == 1) {
                description = DESCRIPTIONS[1];
            }
            else
            {
                description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[4];
            }
        }

    }

    @Override
    public AbstractPower makeCopy() {
        return new VentriloquismPower(owner, this.amount, this.upgraded);
    }
}
