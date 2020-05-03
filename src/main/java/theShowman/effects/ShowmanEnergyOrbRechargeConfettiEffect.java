package theShowman.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theShowman.ShowmanMod;

public class ShowmanEnergyOrbRechargeConfettiEffect extends AbstractGameEffect {
    private static final float STARTING_DURATION = 1.5F;
    private float scale;
    private Color color;
    private static final String CONFETTI_IMAGE_STRING = ShowmanMod.makeEffectPath("confetti.png");
    private static final Texture CONFETTI_TEXTURE = new Texture(CONFETTI_IMAGE_STRING);
    private static final TextureRegion CONFETTI_TEXTURE_REGION = new TextureRegion(CONFETTI_TEXTURE);
    private static final float GRAVITY;
    private static final float START_VY;
    private static final float START_VX;
    private static final float START_VX_JITTER;
    private static final float START_VY_JITTER;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float rotation;
    private TextureRegion img;

    public ShowmanEnergyOrbRechargeConfettiEffect()
    {
        this.duration = this.startingDuration = STARTING_DURATION;
        this.scale = Settings.scale * 0.75F;
        this.color = new Color(MathUtils.random(0.0F, 1.0F), MathUtils.random(0.0F, 1.0F), MathUtils.random(0.0F, 1.0F), 0.5F);
        this.x = 198.0F * Settings.scale - (CONFETTI_TEXTURE_REGION.getRegionWidth() / 2.0F);
        this.y = 198.0F * Settings.scale - (CONFETTI_TEXTURE_REGION.getRegionHeight() / 2.0F);
        this.vX = MathUtils.random(START_VX - START_VX_JITTER, START_VX + START_VX_JITTER);
        this.vY = MathUtils.random(START_VY - START_VY_JITTER, START_VY + START_VY_JITTER);
        this.rotation = MathUtils.atan2(this.vY, this.vX);
        this.rotation = this.rotation * MathUtils.radiansToDegrees;
        this.rotation = this.rotation - 90.0F;
        this.img = CONFETTI_TEXTURE_REGION;

        this.x += 0.5F * this.vX;
        this.y += 0.5F * this.vY;
    }

    public void update()
    {
        this.duration -= Gdx.graphics.getDeltaTime();
        if(this.duration <= 0.2F)
        {
            this.color.a = MathUtils.lerp(0.5F, 0.0F, 1.0F - ((this.duration * 5) / this.startingDuration));
        }
        if(this.duration < 0.0F)
        {
            this.isDone = true;
        }
        this.x += Gdx.graphics.getDeltaTime() * this.vX;
        this.y += Gdx.graphics.getDeltaTime() * this.vY;

        this.rotation = MathUtils.atan2(this.vY, this.vX);
        this.rotation = this.rotation * MathUtils.radiansToDegrees;
        this.rotation = this.rotation - 90.0F;

        this.vY -= Gdx.graphics.getDeltaTime() * GRAVITY;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float)this.img.getRegionWidth() / 2.0F, (float)this.img.getRegionHeight() / 2.0F, (float)this.img.getRegionWidth(), (float)this.img.getRegionHeight(), this.scale, this.scale, this.rotation);
    }

    public void dispose()
    {

    }

    static
    {
        GRAVITY = 25.0F * Settings.scale;
        START_VY = 0.0F * Settings.scale;
        START_VY_JITTER = 50.0F * Settings.scale;
        START_VX = 0.0F * Settings.scale;
        START_VX_JITTER = 50.0F * Settings.scale;
    }
}
