//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package theShowman.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theShowman.ShowmanMod;

public class SayCardEffect extends AbstractGameEffect {

    public static final String IMG0 = ShowmanMod.makeEffectPath("card_back_mk2.png");
    public static final String IMG1 = ShowmanMod.makeEffectPath("card_face_clubs.png");
    public static final String IMG2 = ShowmanMod.makeEffectPath("card_face_diamonds.png");
    public static final String IMG3 = ShowmanMod.makeEffectPath("club_3.png");
    public static final String IMG4 = ShowmanMod.makeEffectPath("club_7.png");
    public static final String IMG5 = ShowmanMod.makeEffectPath("diamond_4.png");
    public static final String IMG6 = ShowmanMod.makeEffectPath("diamond_8.png");
    public static final String IMG7 = ShowmanMod.makeEffectPath("heart_2.png");
    public static final String IMG8 = ShowmanMod.makeEffectPath("heart_6.png");
    public static final String IMG9 = ShowmanMod.makeEffectPath("heart_ace.png");
    public static final String IMG10 = ShowmanMod.makeEffectPath("spade_5.png");
    public static final String IMG11 = ShowmanMod.makeEffectPath("spade_9.png");

    private Texture texture0 = new Texture(IMG0);
    private Texture texture1 = new Texture(IMG1);
    private Texture texture2 = new Texture(IMG2);
    private Texture texture3 = new Texture(IMG3);
    private Texture texture4 = new Texture(IMG4);
    private Texture texture5 = new Texture(IMG5);
    private Texture texture6 = new Texture(IMG6);
    private Texture texture7 = new Texture(IMG7);
    private Texture texture8 = new Texture(IMG8);
    private Texture texture9 = new Texture(IMG9);
    private Texture texture10 = new Texture(IMG10);
    private Texture texture11 = new Texture(IMG11);

    private TextureRegion img = null;

    private AbstractMonster m;

    private float a;

    private float x;
    private float y;

    private float scaleTimer;
    private float scale_x;
    private float scale_y;

    private static float ADJUST_X;
    private static float ADJUST_Y;

    private void chooseImage(int img)
    {
        switch (img) {
            case 1:
                this.img = new TextureRegion(texture1);
                break;
            case 2:
                this.img = new TextureRegion(texture2);
                break;
            case 3:
                this.img = new TextureRegion(texture3);
                break;
            case 4:
                this.img = new TextureRegion(texture4);
                break;
            case 5:
                this.img = new TextureRegion(texture5);
                break;
            case 6:
                this.img = new TextureRegion(texture6);
                break;
            case 7:
                this.img = new TextureRegion(texture7);
                break;
            case 8:
                this.img = new TextureRegion(texture8);
                break;
            case 9:
                this.img = new TextureRegion(texture9);
                break;
            case 10:
                this.img = new TextureRegion(texture10);
                break;
            case 11:
                this.img = new TextureRegion(texture11);
                break;
            default:
                this.img = new TextureRegion(texture0);
                break;
        }
    }

    public SayCardEffect(AbstractMonster m, int specifiedCard) {
        if (this.img == null) {
            chooseImage(specifiedCard);
        }
        this.m = m;
        this.duration = this.startingDuration =  2.0F;
        this.a = 1.0F;
        this.x = m.hb.cX + m.dialogX - ADJUST_X;
        this.y = m.hb.cY + m.dialogY + ADJUST_Y;

        this.scaleTimer = 0.3F;

    }

    public void update() {
        updateScale();
        if(this.duration <= 0.5F)
        {
            a -= Gdx.graphics.getDeltaTime() * 2.0F;
            if(a < 0.0F)
            {
                a = 0.0F;
            }
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if(this.duration <= 0.0F)
        {
            this.isDone = true;
        }
    }

    private void updateScale() {
        this.scaleTimer -= Gdx.graphics.getDeltaTime();
        if (this.scaleTimer < 0.0F) {
            this.scaleTimer = 0.0F;
        }

        this.scale_x = Interpolation.circleIn.apply(Settings.scale, Settings.scale * 0.5F, this.scaleTimer / 0.3F);// 78
        this.scale_y = Interpolation.swingIn.apply(Settings.scale, Settings.scale * 0.8F, this.scaleTimer / 0.3F);// 79
    }

    public void render(SpriteBatch sb) {
        Color whiteMaybe = Color.WHITE.cpy();
        whiteMaybe.a = this.a;
        sb.setColor(whiteMaybe);
        sb.draw(this.img, this.x - this.img.getRegionWidth() / (2.0F * Settings.scale) * Settings.scale, this.y - this.img.getRegionHeight() / (2.0F * Settings.scale) * Settings.scale, this.img.getRegionWidth() / 2.0F, this.img.getRegionHeight() / 2.0F, this.img.getRegionWidth(), this.img.getRegionHeight(), 1.5F * scale_x, 1.5F * scale_y, 0.0F);
    }

    public void dispose() {
    }

    static {
        ADJUST_X = 170.0F * Settings.scale;
        ADJUST_Y = 116.0F * Settings.scale;
    }

}
