package theShowman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theShowman.ShowmanMod;


public class WillingVolunteerPower extends AbstractPower implements CloneablePowerInterface, onAttackedExceptItRespectsBlockInterface {

    public static final String POWER_ID = ShowmanMod.makeID("WillingVolunteerPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    //private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    //private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public AbstractMonster m;

    //private float scaleFactor;
    private static FrameBuffer fbo;

    public WillingVolunteerPower(final AbstractCreature owner, final int amount, final AbstractMonster mo) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.canGoNegative = false;

        if(this.m != mo) {
            monsterIcon(mo);
        }

        this.m = mo;


        this.updateDescription();
    }

    public void monsterIcon(AbstractMonster mo)
    {
        SpriteBatch spriteBatch = new SpriteBatch();

        fbo.begin();

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glColorMask(true, true, true, true);

        spriteBatch.begin();
        spriteBatch.enableBlending();
        spriteBatch.setColor(Color.WHITE);
        spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        mo.render(spriteBatch);

        spriteBatch.end();

        fbo.end();

        Texture fboTexture = fbo.getColorBufferTexture();

        this.region128 = new TextureAtlas.AtlasRegion(fboTexture, (int) mo.hb.x, (int) mo.hb.y, (int) mo.hb.width, (int) mo.hb.height);
        this.region128.flip(false, true);
        this.region48 = new TextureAtlas.AtlasRegion(fboTexture, (int) mo.hb.x, (int) mo.hb.y, (int) mo.hb.width, (int) mo.hb.height);
        this.region48.flip(false, true);

        spriteBatch.dispose();
    }

    @Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c)
    {
        float scaleBefore = Settings.scale;
        Settings.scale = (48.0F * Settings.scale / Gdx.graphics.getWidth()) * (1.0F / (m.hb.width / Gdx.graphics.getWidth()));
        super.renderIcons(sb, x, y, c);
        Settings.scale = scaleBefore;
    }

    @Override
    public int onAttackedExceptItRespectsBlock(DamageInfo info, int damageAmount)
    {
        if(info.type == DamageInfo.DamageType.NORMAL)
        {
            AbstractPlayer p = AbstractDungeon.player;
            if(this.m != null)
            {
                AbstractDungeon.actionManager.addToTop(
                        new DamageAction(m, new DamageInfo(m, info.output, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SMASH));
            }
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p, p, this, 1));
            return 0;
        }
        else
        {
            return damageAmount;
        }
    }

    /*
    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(info.type == DamageInfo.DamageType.NORMAL)
        {
            AbstractPlayer p = AbstractDungeon.player;
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, this, 1));
            if(this.m != null)
            {
                AbstractDungeon.actionManager.addToBottom(
                        new DamageAction(m, new DamageInfo(m, info.output, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SMASH));
            }
            return 0;
        }
        else
        {
            return damageAmount;
        }
    }*/

    @Override
    public void atStartOfTurn() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, this));
    }

    @Override
    public void updateDescription() {
        if(this.amount == 1) {
            this.description = FontHelper.colorString(this.m.name, "y") + " " + DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }
        else
        {
            this.description = FontHelper.colorString(this.m.name, "y") + " " + DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new WillingVolunteerPower(owner, amount, this.m);
    }

    static
    {
       fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);
    }
}
