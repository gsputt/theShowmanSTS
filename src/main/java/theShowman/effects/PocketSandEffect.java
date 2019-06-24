//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package theShowman.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class PocketSandEffect extends AbstractGameEffect {
    private boolean flipX;

    public PocketSandEffect(boolean shouldFlip) {
        this.flipX = shouldFlip;
    }

    public void update() {
        this.isDone = true;
        int i;
        float x;
        if (this.flipX) {
            for(i = 120; i > 0; --i) {
                x = AbstractDungeon.player.hb.cX - MathUtils.random(0.0F, 450.0F) * Settings.scale;
                AbstractDungeon.effectsQueue.add(new SandParticleEffect(x, AbstractDungeon.player.hb.cY + 12.00F * Settings.scale + (float)i * -1.8F * Settings.scale, (float)(i * 0.4) - 30.0F, true));
            }
        } else {
            for(i = 0; i < 120; ++i) {
                x = AbstractDungeon.player.hb.cX + MathUtils.random(0.0F, 450.0F) * Settings.scale;
                AbstractDungeon.effectsQueue.add(new SandParticleEffect(x, AbstractDungeon.player.hb.cY - 10.00F * Settings.scale + (float)i * 1.8F * Settings.scale, (float)(i * 0.4) - 20.0F, false));
            }
        }

    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
