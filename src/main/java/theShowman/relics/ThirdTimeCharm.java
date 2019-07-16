package theShowman.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theShowman.ShowmanMod;
import theShowman.util.TextureLoader;

import static theShowman.ShowmanMod.makeRelicOutlinePath;
import static theShowman.ShowmanMod.makeRelicPath;

public class ThirdTimeCharm extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = ShowmanMod.makeID("ThirdTimeCharm");

    private static final Texture IMG3 = TextureLoader.getTexture(makeRelicPath("3-TimeCharm.png"));
    private static final Texture OUTLINE3 = TextureLoader.getTexture(makeRelicOutlinePath("3-TimeCharmOutline.png"));
    private static final Texture IMG2 = TextureLoader.getTexture(makeRelicPath("2-TimeCharm.png"));
    private static final Texture OUTLINE2 = TextureLoader.getTexture(makeRelicOutlinePath("2-TimeCharmOutline.png"));
    private static final Texture IMG1 = TextureLoader.getTexture(makeRelicPath("1-TimeCharm.png"));
    private static final Texture OUTLINE1 = TextureLoader.getTexture(makeRelicOutlinePath("1-TimeCharmOutline.png"));


    public ThirdTimeCharm() {
        super(ID, IMG3, OUTLINE3, RelicTier.STARTER, LandingSound.MAGICAL);
    }


    @Override
    public void atBattleStart()
    {
        this.counter = 0;
        setImage();
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        this.counter++;
        if(this.counter >= 3)
        {
            this.counter = 0;
            if(AbstractDungeon.player.drawPile.isEmpty())
            {
                AbstractDungeon.actionManager.addToBottom(new EmptyDeckShuffleAction());
            }
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
            useCardAction.exhaustCard = true;
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
       setImage();
    }

    private void setImage()
    {
        if(this.counter == 1)
        {
            setTextures(IMG2, OUTLINE2);
        }
        else if(this.counter == 2)
        {
            setTextures(IMG1, OUTLINE1);
        }
        else
        {
            setTextures(IMG3, OUTLINE3);
        }
    }

    private void setTextures(Texture texture, Texture outline)
    {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        outline.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.setTextureOutline(texture, outline);
    }

    @Override
    public void onVictory()
    {
        this.counter = -1;
        setImage();
    }

    // Gain 1 energy on equip.
    @Override
    public void onEquip() {
        this.counter = -1;
        setImage();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
