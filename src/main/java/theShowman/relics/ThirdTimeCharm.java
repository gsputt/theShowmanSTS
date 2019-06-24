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

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public ThirdTimeCharm() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }


    @Override
    public void atBattleStart()
    {
        this.counter = 0;
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
    }

    @Override
    public void onVictory()
    {
        this.counter = -1;
    }

    // Gain 1 energy on equip.
    @Override
    public void onEquip() {
        this.counter = -1;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
