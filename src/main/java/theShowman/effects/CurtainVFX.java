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

public class CurtainVFX extends AbstractGameEffect {

    public static final String CURTAIN_LEFT_STRING = ShowmanMod.makeEffectPath("curtain_left.png");
    public static final String CURTAIN_RIGHT_STRING = ShowmanMod.makeEffectPath("curtain_right.png");
    public static final String CURTAIN_MIDDLE_STRING = ShowmanMod.makeEffectPath("curtain_main_long.png");
    private static final Texture CURTAIN_LEFT_TEXTURE = new Texture(CURTAIN_LEFT_STRING);
    private static final Texture CURTAIN_RIGHT_TEXTURE = new Texture(CURTAIN_RIGHT_STRING);
    private static final Texture CURTAIN_MIDDLE_TEXTURE = new Texture(CURTAIN_MIDDLE_STRING);

    private TextureRegion CURTAIN_LEFT = null;
    private TextureRegion CURTAIN_RIGHT = null;
    private TextureRegion CURTAIN_MIDDLE = null;

    private float offSetLeftX;
    private float offSetRightX;
    private float offSetMiddleY;

    private float graphicsAnimation;

    private float scaleLeftWidth;
    private float scaleLeftHeight;
    private float scaleRightWidth;
    private float scaleRightHeight;
    private float scaleMiddleWidth;
    private float scaleMiddleHeight;

    public CurtainVFX()
    {
        if(CURTAIN_LEFT == null)
        {
            CURTAIN_LEFT = new TextureRegion(CURTAIN_LEFT_TEXTURE);
        }
        if(CURTAIN_RIGHT == null)
        {
            CURTAIN_RIGHT = new TextureRegion(CURTAIN_RIGHT_TEXTURE);
        }
        if(CURTAIN_MIDDLE == null)
        {
            CURTAIN_MIDDLE = new TextureRegion(CURTAIN_MIDDLE_TEXTURE);
        }
        this.color = Color.WHITE.cpy();
        this.color.a = 0.0F;
        this.duration = this.startingDuration = 2.0F;
        this.graphicsAnimation = 0.0F;
        this.offSetRightX = 0.0F;
        this.offSetLeftX = 0.0F;
        this.offSetMiddleY = 0.0F;

        scaleLeftWidth = 1.0F * Settings.scale;
        scaleLeftHeight = Settings.scale;//((1.0F * Settings.scale) / (CURTAIN_LEFT.getRegionHeight() / (float)Settings.HEIGHT));
        scaleRightWidth = 1.0F * Settings.scale;
        scaleRightHeight = Settings.scale;//((1.0F * Settings.scale) / (CURTAIN_RIGHT.getRegionHeight() / (float)Settings.HEIGHT));
        scaleMiddleWidth = Settings.scale;//((1.0F * Settings.scale) / (CURTAIN_MIDDLE.getRegionWidth() / (float)Settings.WIDTH));
        scaleMiddleHeight = Settings.scale;//((1.0F * Settings.scale) / (CURTAIN_MIDDLE.getRegionHeight() / (float)Settings.HEIGHT));

        offSetMiddleY = 0.0F;
        offSetLeftX = 0.0F;
        offSetRightX = 0.0F;
    }

    @Override
    public void update()
    {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.graphicsAnimation += Gdx.graphics.getDeltaTime();
        if(this.graphicsAnimation <= 0.5F)
        {
            this.color.a = graphicsAnimation * 2;
            if(this.color.a > 1.0F)
            {
                this.color.a = 1.0F;
            }
        }
        if(this.graphicsAnimation > 0.5F && this.graphicsAnimation <= 2.0F) {
            offSetLeftX = Interpolation.fade.apply(0, 0 - CURTAIN_LEFT.getRegionWidth(), (this.graphicsAnimation - 0.5F) / 1.5F);
            offSetRightX = Interpolation.fade.apply(0, CURTAIN_RIGHT.getRegionWidth(), (this.graphicsAnimation - 0.5F) / 1.5F);
            offSetMiddleY = Interpolation.fade.apply(0, CURTAIN_MIDDLE.getRegionHeight(), (this.graphicsAnimation - 0.5F) / 1.5F);
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
        sb.draw(CURTAIN_MIDDLE, 0, 0 + offSetMiddleY * scaleMiddleHeight, 0, 0, CURTAIN_MIDDLE.getRegionWidth(), CURTAIN_MIDDLE.getRegionHeight(), scaleMiddleWidth, scaleMiddleHeight, 0.0F);
        sb.draw(CURTAIN_LEFT, 0 + offSetLeftX * scaleLeftWidth, 0, 0, 0, CURTAIN_LEFT.getRegionWidth(), CURTAIN_LEFT.getRegionHeight(), scaleLeftWidth, scaleLeftHeight, 0.0F);
        sb.draw(CURTAIN_RIGHT, Settings.WIDTH - CURTAIN_RIGHT.getRegionWidth() * scaleRightWidth + offSetRightX * scaleRightWidth, 0, 0, 0, CURTAIN_RIGHT.getRegionWidth(), CURTAIN_RIGHT.getRegionHeight(), scaleRightWidth, scaleRightHeight, 0.0F);
    }

    @Override
    public void dispose()
    {

    }
}
