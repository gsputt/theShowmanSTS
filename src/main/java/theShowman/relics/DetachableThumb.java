package theShowman.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theShowman.ShowmanMod;
import theShowman.util.TextureLoader;

import static theShowman.ShowmanMod.makeRelicOutlinePath;
import static theShowman.ShowmanMod.makeRelicPath;

public class DetachableThumb extends CustomRelic {

    // ID, images, text.
    public static final String ID = ShowmanMod.makeID("DetachableThumb");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("default_clickable_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("default_clickable_relic.png"));

    public static final int COST = 0;

    public DetachableThumb() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public void onExhaust(AbstractCard card) {
        card.freeToPlayOnce = true;
        this.flash();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
