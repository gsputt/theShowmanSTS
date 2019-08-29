package theShowman.relics;

import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import theShowman.ShowmanMod;
import theShowman.util.TextureLoader;

import static theShowman.ShowmanMod.makeRelicOutlinePath;
import static theShowman.ShowmanMod.makeRelicPath;

public class MagnetizedGloves extends CustomRelic {

    // ID, images, text.
    public static final String ID = ShowmanMod.makeID("MagnetizedGloves");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MagnetizedGloves.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MagnetizedGlovesOutline.png"));

    public static final int BONUS = 3;

    public MagnetizedGloves() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.HEAVY);
    }

    @Override
    public void onEquip()
    {
        BaseMod.MAX_HAND_SIZE += BONUS;
    }

    @Override
    public void onUnequip()
    {
        BaseMod.MAX_HAND_SIZE -= BONUS;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
