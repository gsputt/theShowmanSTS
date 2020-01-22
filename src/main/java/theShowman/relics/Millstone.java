package theShowman.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import theShowman.ShowmanMod;
import theShowman.util.TextureLoader;

import static theShowman.ShowmanMod.makeRelicOutlinePath;
import static theShowman.ShowmanMod.makeRelicPath;

public class Millstone extends CustomRelic {

    // ID, images, text.
    public static final String ID = ShowmanMod.makeID("Millstone");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("millstone.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("millstoneOutline.png"));

    public static final int MILLSTONE_BONUS = 2;

    public Millstone() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
