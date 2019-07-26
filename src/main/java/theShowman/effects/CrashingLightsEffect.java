package theShowman.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theShowman.ShowmanMod;

import java.util.ArrayList;


public class CrashingLightsEffect extends AbstractGameEffect {

    private static final Texture NUM_0_TEXTURE = new Texture(ShowmanMod.makeEffectPath("0.png"));
    private static final Texture NUM_1_TEXTURE = new Texture(ShowmanMod.makeEffectPath("1.png"));
    private static final Texture NUM_2_TEXTURE = new Texture(ShowmanMod.makeEffectPath("2.png"));
    private static final Texture NUM_3_TEXTURE = new Texture(ShowmanMod.makeEffectPath("3.png"));
    private static final Texture NUM_4_TEXTURE = new Texture(ShowmanMod.makeEffectPath("4.png"));
    private static final Texture NUM_5_TEXTURE = new Texture(ShowmanMod.makeEffectPath("5.png"));
    private static final Texture NUM_6_TEXTURE = new Texture(ShowmanMod.makeEffectPath("6.png"));
    private static final Texture NUM_7_TEXTURE = new Texture(ShowmanMod.makeEffectPath("7.png"));
    private static final Texture NUM_8_TEXTURE = new Texture(ShowmanMod.makeEffectPath("8.png"));
    private static final Texture NUM_9_TEXTURE = new Texture(ShowmanMod.makeEffectPath("9.png"));

    private TextureRegion num0 = null;
    private TextureRegion num1 = null;
    private TextureRegion num2 = null;
    private TextureRegion num3 = null;
    private TextureRegion num4 = null;
    private TextureRegion num5 = null;
    private TextureRegion num6 = null;
    private TextureRegion num7 = null;
    private TextureRegion num8 = null;
    private TextureRegion num9 = null;


    private AbstractMonster m;
    private float damage;
    private ArrayList<ArrayList<Float>> numberList = new ArrayList<>();

    private static float Y_OFFSET;
    private static float Y_GRAVITY;
    private static float Y_LAUNCH;
    private static float X_LAUNCH;
    private static float X_JITTER;
    private static float Y_JITTER;
    private static float PADDING_X;

    private boolean once;
    private float vX;
    private float vY;

    private float drawX;
    private float drawY;
    private float scale;
    private float rotation;
    private float graphicsAnimation;

    public CrashingLightsEffect(AbstractMonster m, float damage)
    {
        if(num0 == null)
        {
            num0 = new TextureRegion(NUM_0_TEXTURE);
        }
        if(num1 == null)
        {
            num1 = new TextureRegion(NUM_1_TEXTURE);
        }
        if(num2 == null)
        {
            num2 = new TextureRegion(NUM_2_TEXTURE);
        }
        if(num3 == null)
        {
            num3 = new TextureRegion(NUM_3_TEXTURE);
        }
        if(num4 == null)
        {
            num4 = new TextureRegion(NUM_4_TEXTURE);
        }
        if(num5 == null)
        {
            num5 = new TextureRegion(NUM_5_TEXTURE);
        }
        if(num6 == null)
        {
            num6 = new TextureRegion(NUM_6_TEXTURE);
        }
        if(num7 == null)
        {
            num7 = new TextureRegion(NUM_7_TEXTURE);
        }
        if(num8 == null)
        {
            num8 = new TextureRegion(NUM_8_TEXTURE);
        }
        if(num9 == null)
        {
            num9 = new TextureRegion(NUM_9_TEXTURE);
        }
        scale = 1.5F * Settings.scale;
        this.m = m;
        this.damage = damage;
        String damageAsString = "" + (int)damage;
        float totalWidth = damageAsString.length() * (num0.getRegionWidth() + PADDING_X) * scale;
        float totalHeight = num0.getRegionHeight() * scale;

        float cX = m.hb.cX;
        float cY = m.hb.cY;

        drawX = cX - totalWidth / 2.0F; // Center drawX
        drawY = cY + Y_OFFSET - totalHeight / 2.0F;

        this.color = Color.WHITE.cpy();
        this.rotation = 0.0F;
        this.duration = this.startingDuration = 2.5F;
        this.graphicsAnimation = 0.0F;

        once = true;
        vX = 0.0F;
        vY = 0.0F;


        while(damageAsString.length() > 0)
        {
            ArrayList<Float> placeholder = new ArrayList<>(6);

            placeholder.add(0, (float)Integer.parseInt("" + damageAsString.charAt(damageAsString.length() - 1))); // 0 Number to display
            placeholder.add(1, drawX + (damageAsString.length() - 1) * (num0.getRegionWidth() + PADDING_X) * scale); // 1 drawX
            placeholder.add(2, drawY); // 2 drawY
            placeholder.add(3, rotation); // 3 rotation
            placeholder.add(4, vX); // 4 VelocityX
            placeholder.add(5, vY); // 5 VelocityY

            numberList.add(placeholder);

            damageAsString = damageAsString.substring(0, damageAsString.length() - 1);
        }
    }

    @Override
    public void update()
    {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.graphicsAnimation += Gdx.graphics.getDeltaTime();
        if(this.duration > 2.0F)
        {
            for(int i = numberList.size() - 1; i >= 0; i--) {
                //Rotation
                numberList.get(i).set(3, Interpolation.pow4In.apply(0, 135F, this.graphicsAnimation / 0.5F));
            }
        }
        if(this.duration < 2.0F && this.duration > 1.5F)
        {
            for(int i = numberList.size() - 1; i >= 0; i--) {
                //Draw Y Value
                numberList.get(i).set(2, Interpolation.pow4In.apply(m.hb.cY + Y_OFFSET - num0.getRegionHeight() / 2.0F, m.drawY, (this.graphicsAnimation - 0.5F) / 0.5F));
            }
        }
        if(this.duration < 1.5F)
        {
            if(once) {
                for (int i = numberList.size() - 1; i >= 0; i--) {
                    //DrawX Value
                    vX = MathUtils.random(X_LAUNCH, X_JITTER);
                    vY = MathUtils.random(Y_LAUNCH, Y_JITTER);

                    if(numberList.get(i).get(1) + num0.getRegionWidth() / 2.0F * scale < m.hb.cX)
                    {
                        vX = -1 * vX;
                    }

                    numberList.get(i).set(4, vX);
                    numberList.get(i).set(5, vY);
                }
                once = false;
            }
            for(int i = numberList.size() - 1; i >= 0; i--)
            {
                //Set X with Velocity
                numberList.get(i).set(1, numberList.get(i).get(1) + Gdx.graphics.getDeltaTime() * numberList.get(i).get(4));
                //Set Y with Velocity
                numberList.get(i).set(2, numberList.get(i).get(2) + Gdx.graphics.getDeltaTime() * numberList.get(i).get(5));
                //Change Y Velocity with Gravity
                numberList.get(i).set(5, numberList.get(i).get(5) - Y_GRAVITY * Gdx.graphics.getDeltaTime());
            }
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
        for(int i = numberList.size() - 1; i >= 0; i--)
        {
            float f = numberList.get(i).get(0);
            TextureRegion toDraw = null;
            switch((int)f)
            {
                case 1:
                    toDraw = num1;
                    break;
                case 2:
                    toDraw = num2;
                    break;
                case 3:
                    toDraw = num3;
                    break;
                case 4:
                    toDraw = num4;
                    break;
                case 5:
                    toDraw = num5;
                    break;
                case 6:
                    toDraw = num6;
                    break;
                case 7:
                    toDraw = num7;
                    break;
                case 8:
                    toDraw = num8;
                    break;
                case 9:
                    toDraw = num9;
                    break;
                default:
                    toDraw = num0;
                    break;
            }
            sb.draw(toDraw, numberList.get(i).get(1), numberList.get(i).get(2), toDraw.getRegionWidth() / 2.0F * scale, 0, toDraw.getRegionWidth(), toDraw.getRegionHeight(), scale, scale, numberList.get(i).get(3));
        }
    }

    @Override
    public void dispose()
    {

    }

    static {
        Y_OFFSET = 200 * Settings.scale;
        Y_GRAVITY = 2000F * Settings.scale;
        Y_LAUNCH = 800F * Settings.scale;
        Y_JITTER = 400F * Settings.scale;
        X_LAUNCH = 1000F * Settings.scale;
        X_JITTER = 400F * Settings.scale;
        PADDING_X = -30.0F * Settings.scale;
    }

}
