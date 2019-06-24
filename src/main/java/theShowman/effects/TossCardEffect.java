
package theShowman.effects;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.ShowmanMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class TossCardEffect extends AbstractGameEffect {

    public static final String IMG = ShowmanMod.makeEffectPath("card.png");
    private TextureRegion img = null;
    private Texture texture = new Texture(IMG);

    private AbstractMonster m;
    private float x;
    private float y;
    private float targetX;
    private float targetY;
    private float progress;
    private int damage;

    public TossCardEffect(float x, float y, AbstractMonster m, int damage) {
        if (this.img == null) {
            this.img = new TextureRegion(texture);
        }

        this.damage = damage;

        this.x = x;
        this.y = y;
        this.targetX = m.hb.cX;
        this.targetY = m.hb.cY;
        this.m = m;
        this.color = Color.WHITE.cpy();
        this.duration = 0.1F;
        this.startingDuration = 0.1F;
        this.scale = 0.3F * Settings.scale;
    }

    public void update() {
        if(this.m.isDead || this.m.halfDead || this.m.isDying)
        {
            this.isDone = true;
            return;
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        this.progress += Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            int j = this.damage;
            if(j > 20)
            {
                j = 20;
            }
            for (int i = 0; i < j; i++) {
                AbstractDungeon.effectsQueue.add(new ExplodeCardEffect(this.targetX, this.targetY));
            }
            this.isDone = true;
        }
        this.x = MathUtils.lerp(this.x, this.targetX, progress / this.startingDuration);
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float)this.img.getRegionWidth() /2.0F * this.scale, (float)this.img.getRegionHeight() /2.0F * this.scale, (float)this.img.getRegionWidth(), (float)this.img.getRegionHeight(), this.scale, this.scale, 90F);
    }

    public void dispose() {
    }
}
