package theShowman.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theShowman.ShowmanMod;

public class HatTrickVFX extends AbstractGameEffect {
    public static final String HAT_STRING = ShowmanMod.makeEffectPath("hat.png");
    private static final Texture HAT_TEXTURE = new Texture(HAT_STRING);

    private TextureRegion HAT = null;

    private AbstractPlayer p;
    private float graphicsAnimation;
    private float drawY;
    private float drawX;
    private Color color;
    private float scale = 3.0F * Settings.scale;

    public HatTrickVFX(AbstractPlayer p)
    {
        this.p = p;
        if(HAT == null)
        {
            HAT = new TextureRegion(HAT_TEXTURE);
        }
        this.duration = this.startingDuration = 2.0F;
        this.graphicsAnimation = 0.0F;
        this.drawX = p.hb.x + (p.hb.width / 2.0F) - ((HAT.getRegionWidth() * this.scale) / 2.0F);
        this.drawY = p.hb.y + (1000.0F * Settings.scale);
        this.color = Color.WHITE.cpy();
    }

    @Override
    public void update()
    {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.graphicsAnimation += Gdx.graphics.getDeltaTime();
        if(this.duration > 1.0F) {
            this.drawY = Interpolation.linear.apply(p.hb.y + (1000.0F * Settings.scale), p.hb.y, graphicsAnimation / 1.0F);
        }
        else {
            this.color.a = Interpolation.linear.apply(1.0F, 0.0F, (graphicsAnimation - 1.0F) / 1.0F);
        }
        if(this.duration <= 0.0F)
        {
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.setColor(this.color);
        sb.draw(HAT, this.drawX, this.drawY, 0, 0, HAT.getRegionWidth(), HAT.getRegionHeight(), this.scale, this.scale, 0);
    }

    @Override
    public void dispose()
    {

    }
}
