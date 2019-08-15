package theShowman.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theShowman.ShowmanMod;
import theShowman.patches.ImproviseField;
import theShowman.util.TextureLoader;

import static theShowman.ShowmanMod.makeRelicOutlinePath;
import static theShowman.ShowmanMod.makeRelicPath;

public class ImprovRelic extends CustomRelic {

    // ID, images, text.
    public static final String ID = ShowmanMod.makeID("ImprovRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("default_clickable_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("default_clickable_relic.png"));

    private static final int AMOUNT = 2;
    private boolean triggerOnce = false;

    public ImprovRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void atBattleStartPreDraw()
    {
        this.counter = AMOUNT;
        ImproviseField.ImproviseRecording.set(AbstractDungeon.player, ImproviseField.ImproviseRecording.get(AbstractDungeon.player) + 1);
        this.triggerOnce = false;
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if(this.counter > 0 )
        {
            counter--;
            if(this.counter == 0)
            {
                if(!triggerOnce)
                {
                    triggerOnce = true;
                    ImproviseField.ImproviseRecording.set(AbstractDungeon.player, ImproviseField.ImproviseRecording.get(AbstractDungeon.player) - 1);
                }
            }
        }

    }

    @Override
    public void onEquip()
    {
        this.counter = -1;
    }

    @Override
    public void onVictory()
    {
        this.counter = -1;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
