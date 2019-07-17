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

import static com.badlogic.gdx.graphics.GL20.GL_DST_COLOR;
import static com.badlogic.gdx.graphics.GL20.GL_ONE;

public class CurtainCallVFX extends AbstractGameEffect {

    public static final String CURTAIN_LEFT_STRING = ShowmanMod.makeEffectPath("curtain_left.png");
    public static final String CURTAIN_RIGHT_STRING = ShowmanMod.makeEffectPath("curtain_right.png");
    public static final String CURTAIN_MIDDLE_STRING = ShowmanMod.makeEffectPath("curtain_main_long.png");
    public static final String BLACK_BACKGROUND_STRING = ShowmanMod.makeEffectPath("BlackBackground.png");
    public static final String SPOTLIGHT_STRING = ShowmanMod.makeEffectPath("Spotlight.png");
    private static final Texture CURTAIN_LEFT_TEXTURE = new Texture(CURTAIN_LEFT_STRING);
    private static final Texture CURTAIN_RIGHT_TEXTURE = new Texture(CURTAIN_RIGHT_STRING);
    private static final Texture CURTAIN_MIDDLE_TEXTURE = new Texture(CURTAIN_MIDDLE_STRING);
    private static final Texture BLACK_BACKGROUND_TEXTURE = new Texture(BLACK_BACKGROUND_STRING);
    private static final Texture SPOTLIGHT_TEXTURE = new Texture(SPOTLIGHT_STRING);

    private TextureRegion CURTAIN_LEFT = null;
    private TextureRegion CURTAIN_RIGHT = null;
    private TextureRegion CURTAIN_MIDDLE = null;
    private TextureRegion BLACK_BACKGROUND = null;
    private TextureRegion SPOTLIGHT = null;

    private float offSetLeftX;
    private float offSetRightX;
    private float offSetMiddleY;

    private float graphicsAnimation;

    private Color BackgroundColor = Color.BLACK.cpy();
    private Color SpotlightColor = Color.WHITE.cpy();

    private AbstractPlayer p;

    private boolean renderSpotlight;

    public CurtainCallVFX(AbstractPlayer p)
    {
        this.p = p;
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
        if(BLACK_BACKGROUND == null)
        {
            BLACK_BACKGROUND = new TextureRegion(BLACK_BACKGROUND_TEXTURE);
        }
        if(SPOTLIGHT == null)
        {
            SPOTLIGHT = new TextureRegion(SPOTLIGHT_TEXTURE);
        }
        this.color = Color.WHITE.cpy();
        this.duration = this.startingDuration = 2.5F;
        this.graphicsAnimation = 0.0F;
        this.offSetRightX = 0.0F;
        this.offSetLeftX = 0.0F;
        this.offSetMiddleY = 0.0F;

        offSetMiddleY = CURTAIN_MIDDLE.getRegionHeight();
        offSetLeftX = 0 - CURTAIN_LEFT.getRegionWidth();
        offSetRightX = CURTAIN_RIGHT.getRegionWidth();

        BackgroundColor.a = 0.3F;
        SpotlightColor.a = 0.0F;
        renderSpotlight = true;
    }

    @Override
    public void update()
    {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.graphicsAnimation += Gdx.graphics.getDeltaTime();
        if(this.graphicsAnimation > 2.0F)
        {
            this.color.a = duration * 2;
            if(this.color.a < 0.0F)
            {
                this.color.a = 0.0F;
            }
            renderSpotlight = false;
        }
        else {
            renderSpotlight = true;
        }
        if(this.graphicsAnimation <= 0.5F)
        {
            SpotlightColor.a = Interpolation.bounceIn.apply(0, 1.0F, this.graphicsAnimation / 0.5F);
        }
        if(this.graphicsAnimation >= 0.5F && this.graphicsAnimation <= 2.0F) {
            offSetLeftX = Interpolation.fade.apply(0 - CURTAIN_LEFT.getRegionWidth(), 0, (this.graphicsAnimation - 0.5F) / 1.5F);
            offSetRightX = Interpolation.fade.apply(CURTAIN_RIGHT.getRegionWidth(), 0, (this.graphicsAnimation - 0.5F) / 1.5F);
            offSetMiddleY = Interpolation.fade.apply(CURTAIN_MIDDLE.getRegionHeight(), 0, (this.graphicsAnimation - 0.5F) / 1.5F);
        }
        if(this.duration <= 0.0F)
        {
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb)
    {
        int blendSrcFunc = sb.getBlendSrcFunc();
        int blendDstFunc = sb.getBlendDstFunc();

        sb.setBlendFunction(GL_DST_COLOR, GL_ONE);
        sb.setColor(BackgroundColor);
        sb.draw(BLACK_BACKGROUND, 0, 0, 0, 0, BLACK_BACKGROUND.getRegionWidth(), BLACK_BACKGROUND.getRegionHeight(), Settings.scale, Settings.scale, 0.0F);
        sb.setColor(SpotlightColor);
        if(this.renderSpotlight) {
            sb.draw(SPOTLIGHT, p.hb.cX - (SPOTLIGHT.getRegionWidth() / 2.0F) * (1.0F / ((SPOTLIGHT.getRegionHeight() * Settings.scale) / p.hb_h)), p.hb.cY - (SPOTLIGHT.getRegionHeight() / 2.0F) * (1.0F / ((SPOTLIGHT.getRegionHeight() * Settings.scale) / p.hb_h)), 0, 0, SPOTLIGHT.getRegionWidth(), SPOTLIGHT.getRegionHeight(), 1.0F / ((SPOTLIGHT.getRegionHeight() * Settings.scale) / p.hb_h), 1.0F / ((SPOTLIGHT.getRegionHeight() * Settings.scale) / p.hb_h), 0.0F);
        }

        sb.setBlendFunction(blendSrcFunc, blendDstFunc);

        sb.setColor(this.color);
        sb.draw(CURTAIN_MIDDLE, 0, 0 + offSetMiddleY * Settings.scale, 0, 0, CURTAIN_MIDDLE.getRegionWidth(), CURTAIN_MIDDLE.getRegionHeight(), Settings.scale, Settings.scale, 0.0F);
        sb.draw(CURTAIN_LEFT, 0 + offSetLeftX * Settings.scale, 0, 0, 0, CURTAIN_LEFT.getRegionWidth(), CURTAIN_LEFT.getRegionHeight(), Settings.scale, Settings.scale, 0.0F);
        sb.draw(CURTAIN_RIGHT, Settings.WIDTH - CURTAIN_RIGHT.getRegionWidth() * Settings.scale + offSetRightX * Settings.scale, 0, 0, 0, CURTAIN_RIGHT.getRegionWidth(), CURTAIN_RIGHT.getRegionHeight(), Settings.scale, Settings.scale, 0.0F);
    }

    @Override
    public void dispose()
    {

    }
}
