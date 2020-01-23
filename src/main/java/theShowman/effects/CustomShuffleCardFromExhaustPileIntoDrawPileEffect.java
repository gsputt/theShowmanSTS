package theShowman.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;

public class CustomShuffleCardFromExhaustPileIntoDrawPileEffect extends AbstractGameEffect {

    private static final float EFFECT_DUR = 1.5F;
    private AbstractCard card;
    private boolean randomSpot;

    public CustomShuffleCardFromExhaustPileIntoDrawPileEffect(AbstractCard srcCard, boolean randomSpot, boolean toBottom) {
        this.card = srcCard;

        this.card.setAngle(0.0F);
        this.card.drawScale = 0.12F;
        this.card.targetDrawScale = 0.75F;
        this.card.current_x = AbstractDungeon.overlayMenu.exhaustPanel.current_x;
        this.card.current_y = AbstractDungeon.overlayMenu.exhaustPanel.current_y;

        this.duration = EFFECT_DUR;
        this.randomSpot = randomSpot;
        this.card.target_x = MathUtils.random((float) Settings.WIDTH * 0.1F, (float)Settings.WIDTH * 0.9F);
        this.card.target_y = MathUtils.random((float) Settings.HEIGHT * 0.2F, (float)Settings.HEIGHT * 0.8F);
        AbstractDungeon.effectsQueue.add(new CardPoofEffect(this.card.target_x, this.card.target_y));

        AbstractDungeon.player.exhaustPile.removeCard(this.card);

        if (toBottom) {
            AbstractDungeon.player.drawPile.addToBottom(this.card);
        } else if (randomSpot) {
            AbstractDungeon.player.drawPile.addToRandomSpot(this.card);
        } else {
            AbstractDungeon.player.drawPile.addToTop(this.card);
        }

        this.card.unfadeOut();
        this.card.untip();
        this.card.unhover();
        this.card.lighten(true);
    }

    public void update()
    {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.card.update();
        if (this.duration < 0.0F) {
            this.isDone = true;
            this.card.shrink();
            AbstractDungeon.getCurrRoom().souls.onToDeck(this.card, this.randomSpot, true);
        }
        this.card.unfadeOut();
        this.card.untip();
        this.card.unhover();
    }

    public void render(SpriteBatch sb)
    {
        if(!this.isDone)
        {
            this.card.render(sb);
        }
    }

    public void dispose()
    {

    }

}
