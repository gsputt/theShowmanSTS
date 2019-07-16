package theShowman.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.SpeechBubble;

public class PrepareSayCardEffect extends AbstractGameEffect {

    private AbstractMonster m;
    private int specifiedCard;
    public PrepareSayCardEffect(AbstractMonster m, int specifiedCard) {
        this.m = m;
        this.specifiedCard = specifiedCard;
    }

    @Override
    public void update()
    {
        this.isDone = true;
        AbstractDungeon.effectsQueue.add(new SpeechBubble(this.m.hb.cX + this.m.dialogX, m.hb.cY + this.m.dialogY, 2.0F, "", false));
        AbstractDungeon.effectsQueue.add(new SayCardEffect(this.m, this.specifiedCard));
    }
    @Override
    public void render(SpriteBatch sb)
    {

    }
    @Override
    public void dispose()
    {

    }
}
