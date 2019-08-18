package theShowman.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import theShowman.ShowmanMod;
import theShowman.util.TextureLoader;

import static theShowman.ShowmanMod.makeRelicOutlinePath;
import static theShowman.ShowmanMod.makeRelicPath;

public class HeartOfTheCards extends CustomRelic {

    // ID, images, text.
    public static final String ID = ShowmanMod.makeID("HeartOfTheCards");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("default_clickable_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("default_clickable_relic.png"));

    public static final int AMOUNT = 1;

    public HeartOfTheCards() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);
    }

    @Override
    public void atPreBattle() {
        setCounter();
    }

    @Override
    public void onVictory()
    {
        this.counter = -1;
    }

    @Override
    public void atTurnStart()
    {
        setCounter();
    }

    public void stopPulse()
    {
        this.pulse = false;
    }

    private void setCounter()
    {
        this.counter = AMOUNT;
        if(!this.pulse)
        {
            this.beginPulse();
            this.pulse = true;
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
