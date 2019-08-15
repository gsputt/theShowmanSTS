package theShowman.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theShowman.ShowmanMod;

public class CurtainVFX2 extends AbstractGameEffect {

    public static final String CURTAIN_LEFT_STRING = ShowmanMod.makeEffectPath("curtain_left.png");
    public static final String CURTAIN_RIGHT_STRING = ShowmanMod.makeEffectPath("curtain_right.png");
    private static final Texture CURTAIN_LEFT_TEXTURE = new Texture(CURTAIN_LEFT_STRING);
    private static final Texture CURTAIN_RIGHT_TEXTURE = new Texture(CURTAIN_RIGHT_STRING);

    private TextureRegion CURTAIN_LEFT = null;
    private TextureRegion CURTAIN_RIGHT = null;

    private float offSetLeftX;
    private float offSetRightX;

    private float graphicsAnimation;

    private float drawYStartMonster;


    public CurtainVFX2()
    {

        if(CURTAIN_LEFT == null)
        {
            CURTAIN_LEFT = new TextureRegion(CURTAIN_LEFT_TEXTURE);
        }
        if(CURTAIN_RIGHT == null)
        {
            CURTAIN_RIGHT = new TextureRegion(CURTAIN_RIGHT_TEXTURE);
        }

        this.color = Color.WHITE.cpy();
        this.color.a = 1.0F;
        this.duration = this.startingDuration = 2.0F;
        this.graphicsAnimation = 0.0F;
        this.offSetRightX = 0.0F;
        this.offSetLeftX = 0.0F;

        offSetLeftX = 0.0F;
        offSetRightX = 0.0F;

    }

    @Override
    public void update()
    {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.graphicsAnimation += Gdx.graphics.getDeltaTime();

        if(this.graphicsAnimation <= 0.5F) {
            offSetLeftX = Interpolation.fade.apply(0, CURTAIN_LEFT.getRegionWidth(), this.graphicsAnimation / 0.5F);
            offSetRightX = Interpolation.fade.apply(0, 0 - CURTAIN_RIGHT.getRegionWidth(), this.graphicsAnimation / 0.5F);
        }
        if(this.graphicsAnimation >= 1.5F)
        {
            offSetLeftX = Interpolation.fade.apply(CURTAIN_LEFT.getRegionWidth(), 0, (this.graphicsAnimation - 1.5F) / 0.5F);
            offSetRightX = Interpolation.fade.apply(0 - CURTAIN_RIGHT.getRegionWidth(), 0, (this.graphicsAnimation - 1.5F) / 0.5F);
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
        sb.draw(CURTAIN_LEFT, 0 - CURTAIN_LEFT.getRegionWidth() * Settings.scale + offSetLeftX * Settings.scale, 0, 0, 0, CURTAIN_LEFT.getRegionWidth(), CURTAIN_LEFT.getRegionHeight(), Settings.scale, Settings.scale, 0.0F);
        sb.draw(CURTAIN_RIGHT, Settings.WIDTH + offSetRightX * Settings.scale, 0, 0, 0, CURTAIN_RIGHT.getRegionWidth(), CURTAIN_RIGHT.getRegionHeight(), Settings.scale, Settings.scale, 0.0F);
    }

    @Override
    public void dispose()
    {

    }
}
