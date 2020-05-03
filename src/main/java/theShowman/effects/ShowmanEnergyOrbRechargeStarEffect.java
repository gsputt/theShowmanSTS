package theShowman.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theShowman.ShowmanMod;

public class ShowmanEnergyOrbRechargeStarEffect extends AbstractGameEffect {

    private static final float STARTING_DURATION = 1.5F;
    private float scale;
    private Color color;
    private static final String STAR_IMAGE_STRING = ShowmanMod.makeEffectPath("star.png");
    private static final Texture STAR_TEXTURE = new Texture(STAR_IMAGE_STRING);
    private static final TextureRegion STAR_TEXTURE_REGION = new TextureRegion(STAR_TEXTURE);
    private float startingX;
    private float startingY;
    private float targetY;
    private float x;
    private float y;
    private float rotationTarget;
    private float rotationStart;
    private static final float ROTATION_FINAL = 180.0F;
    private static final float ROTATION_JITTER = 90.0F;
    private Vector2 rotationStuff = new Vector2(0.0F, 0.0F);
    private float rotation;
    private TextureRegion img;

    public ShowmanEnergyOrbRechargeStarEffect()
    {
        this.duration = this.startingDuration = STARTING_DURATION;
        this.scale = Settings.scale * MathUtils.random(0.5F, 1.25F);
        this.color = new Color(MathUtils.random(0.0F, 1.0F), MathUtils.random(0.0F, 1.0F), MathUtils.random(0.0F, 1.0F), 0.5F);
        this.img = STAR_TEXTURE_REGION;
        this.rotationStart = this.rotation = MathUtils.random(0.0F, 360.0F);
        this.rotationTarget = this.rotationStart + MathUtils.random(ROTATION_FINAL - ROTATION_JITTER, ROTATION_FINAL + ROTATION_JITTER);
        this.x = this.startingX = 198.0F * Settings.scale - (STAR_TEXTURE_REGION.getRegionWidth() / 2.0F);
        this.y = this.startingY = 198.0F * Settings.scale - (STAR_TEXTURE_REGION.getRegionHeight() / 2.0F);
        this.targetY = MathUtils.random(50.0F * Settings.scale, 100.0F * Settings.scale);
    }

    public void update()
    {
        this.duration -= Gdx.graphics.getDeltaTime();
        if(this.duration <= 0.0F)
        {
            this.isDone = true;
        }

        this.rotation = MathUtils.lerp(this.rotationStart, this.rotationTarget, 1.0F - (this.duration / this.startingDuration));
        this.rotationStuff.x = 0.0F;
        this.rotationStuff.y = MathUtils.lerp(0.0F, this.targetY, 1.0F - (this.duration / this.startingDuration));
        this.rotationStuff.rotate(this.rotation);

        this.x = this.startingX + this.rotationStuff.x;
        this.y = this.startingY + this.rotationStuff.y;

        if(this.duration < 0.5F)
        {
            this.color.a = MathUtils.lerp(0.5F, 0.0F, 1.0F - (this.duration / 0.5F));
        }

    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float)this.img.getRegionWidth() / 2.0F, (float)this.img.getRegionHeight() / 2.0F, (float)this.img.getRegionWidth(), (float)this.img.getRegionHeight(), this.scale, this.scale, this.rotation);
    }

    public void dispose()
    {

    }

}
