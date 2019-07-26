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

public class AndBehindCurtainVFX extends AbstractGameEffect implements RenderOverEverythingInterface{

    public static final String CURTAIN_LEFT_STRING = ShowmanMod.makeEffectPath("curtain_left.png");
    public static final String CURTAIN_RIGHT_STRING = ShowmanMod.makeEffectPath("curtain_right.png");
    public static final String CURTAIN_MIDDLE_STRING = ShowmanMod.makeEffectPath("curtain_main_long.png");
    public static final String NUMBER_THREE_STRING = ShowmanMod.makeEffectPath("3.png");
    private static final Texture CURTAIN_LEFT_TEXTURE = new Texture(CURTAIN_LEFT_STRING);
    private static final Texture CURTAIN_RIGHT_TEXTURE = new Texture(CURTAIN_RIGHT_STRING);
    private static final Texture CURTAIN_MIDDLE_TEXTURE = new Texture(CURTAIN_MIDDLE_STRING);
    private static final Texture NUMBER_THREE_TEXTURE = new Texture(NUMBER_THREE_STRING);

    private TextureRegion CURTAIN_LEFT = null;
    private TextureRegion CURTAIN_RIGHT = null;
    private TextureRegion CURTAIN_MIDDLE = null;
    private TextureRegion NUMBER_THREE = null;

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
    private float scaleNumber;

    private boolean showNumber;
    private Color numberColor;

    public AndBehindCurtainVFX()
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
        if(NUMBER_THREE == null)
        {
            NUMBER_THREE = new TextureRegion(NUMBER_THREE_TEXTURE);
        }
        this.color = Color.WHITE.cpy();
        this.color.a = 0.0F;
        this.duration = this.startingDuration = 3.0F;
        this.graphicsAnimation = 0.0F;
        this.offSetRightX = 0.0F;
        this.offSetLeftX = 0.0F;
        this.offSetMiddleY = 0.0F;

        scaleLeftWidth = 1.0F * Settings.scale;
        scaleLeftHeight = Settings.scale;
        scaleRightWidth = 1.0F * Settings.scale;
        scaleRightHeight = Settings.scale;
        scaleMiddleWidth = Settings.scale;
        scaleMiddleHeight = Settings.scale;
        scaleNumber = 3 * Settings.scale;


        offSetMiddleY = 0.0F;
        offSetLeftX = 0.0F;
        offSetRightX = 0.0F;

        showNumber = false;
        numberColor = Color.WHITE.cpy();
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
        if(this.graphicsAnimation > 0.5F)
        {
            showNumber = true;
            if(graphicsAnimation > 0.8F && graphicsAnimation < 1.0F)
            {
                showNumber = false;
            }
            else if(graphicsAnimation > 1.3F && graphicsAnimation < 1.5F)
            {
                showNumber = false;
            }
        }
        else
        {
            showNumber = false;
        }
        if(this.graphicsAnimation > 1.5F && this.graphicsAnimation <= 3.0F) {
            offSetLeftX = Interpolation.fade.apply(0, 0 - CURTAIN_LEFT.getRegionWidth(), (this.graphicsAnimation - 1.5F) / 1.5F);
            offSetRightX = Interpolation.fade.apply(0, CURTAIN_RIGHT.getRegionWidth(), (this.graphicsAnimation - 1.5F) / 1.5F);
            offSetMiddleY = Interpolation.fade.apply(0, CURTAIN_MIDDLE.getRegionHeight(), (this.graphicsAnimation - 1.5F) / 1.5F);
            numberColor.a = Interpolation.linear.apply(1, 0, (this.graphicsAnimation - 1.5F) / 1.5F);
        }
        if(this.duration <= 0.0F)
        {
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb)
    {

    }

    @Override
    public void renderOverOtherStuff(SpriteBatch sb)
    {
        sb.setColor(this.color);
        sb.draw(CURTAIN_MIDDLE, 0, 0 + offSetMiddleY * scaleMiddleHeight, 0, 0, CURTAIN_MIDDLE.getRegionWidth(), CURTAIN_MIDDLE.getRegionHeight(), scaleMiddleWidth, scaleMiddleHeight, 0.0F);
        sb.draw(CURTAIN_LEFT, 0 + offSetLeftX * scaleLeftWidth, 0, 0, 0, CURTAIN_LEFT.getRegionWidth(), CURTAIN_LEFT.getRegionHeight(), scaleLeftWidth, scaleLeftHeight, 0.0F);
        sb.draw(CURTAIN_RIGHT, Settings.WIDTH - CURTAIN_RIGHT.getRegionWidth() * scaleRightWidth + offSetRightX * scaleRightWidth, 0, 0, 0, CURTAIN_RIGHT.getRegionWidth(), CURTAIN_RIGHT.getRegionHeight(), scaleRightWidth, scaleRightHeight, 0.0F);
        if(showNumber)
        {
            sb.setColor(numberColor);
            sb.draw(NUMBER_THREE, Settings.WIDTH / 2.0F - NUMBER_THREE.getRegionWidth() / 2.0F * scaleNumber, Settings.HEIGHT / 2.0F - NUMBER_THREE.getRegionHeight() / 2.0F * scaleNumber, 0, 0, NUMBER_THREE.getRegionWidth(), NUMBER_THREE.getRegionHeight(), scaleNumber, scaleNumber, 0.0F);
        }
    }

    @Override
    public void dispose()
    {

    }
}
