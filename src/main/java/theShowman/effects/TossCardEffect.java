
package theShowman.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theShowman.ShowmanMod;

public class TossCardEffect extends AbstractGameEffect {

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

    private static final int TOTAL_CHOICE_AMOUNT = 11;

    private TextureRegion img = null;

    private AbstractMonster m;
    private float x;
    private float y;
    private float targetX;
    private float targetY;
    private float progress;
    private int damage;

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

    public TossCardEffect(float x, float y, AbstractMonster m, int damage, int specifyImg) {
        if (this.img == null) {
            if(specifyImg == -1) {
                if(MathUtils.randomBoolean()) {
                    chooseImage(MathUtils.random(1, TOTAL_CHOICE_AMOUNT));
                }
                else
                {
                    this.img = new TextureRegion(texture0);
                }
            }
            else
            {
                chooseImage(specifyImg);
            }
        }

        this.damage = damage;

        this.x = x;
        this.y = y;
        this.targetX = m.hb.cX;
        this.targetY = m.hb.cY;
        this.m = m;
        this.color = Color.WHITE.cpy();
        this.duration = 0.1F;
        this.startingDuration = 0.1F;
        this.scale = 1.2F * Settings.scale;
    }

    public TossCardEffect(float x, float y, float mX, float mY, int damage) {
        if (this.img == null) {
            if(MathUtils.randomBoolean()) {
                chooseImage(MathUtils.random(1, TOTAL_CHOICE_AMOUNT));
            }
            else
            {
                this.img = new TextureRegion(texture0);
            }
        }

        this.damage = damage;

        this.x = x;
        this.y = y;
        this.targetX = mX;
        this.targetY = mY;
        this.color = Color.WHITE.cpy();
        this.duration = 0.1F;
        this.startingDuration = 0.1F;
        this.scale = 0.3F * Settings.scale;
    }

    public void update() {
        if(m != null) {
            if (this.m.isDead || this.m.halfDead || this.m.isDying) {
                this.isDone = true;
                return;
            }
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        this.progress += Gdx.graphics.getDeltaTime();

        this.x = Interpolation.linear.apply(this.x, this.targetX, Math.min(1F, this.progress/this.startingDuration));
        this.y = Interpolation.linear.apply(this.y, this.targetY, Math.min(1F, this.progress/this.startingDuration));

        if (this.duration < 0.0F) {
            int j = this.damage;
            if(j > 20)
            {
                j = 20;
            }
            for (int i = 0; i < j; i++) {
                AbstractDungeon.effectsQueue.add(new ExplodeCardEffect(this.targetX, this.targetY, this.img));
            }
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img, this.x - ((float)this.img.getRegionWidth() /2.0F), this.y - ((float)this.img.getRegionHeight() /2.0F), (float)this.img.getRegionWidth() /2.0F, (float)this.img.getRegionHeight() /2.0F, (float)this.img.getRegionWidth(), (float)this.img.getRegionHeight(), this.scale, this.scale, 90F);
    }

    public void dispose() {
    }
}
