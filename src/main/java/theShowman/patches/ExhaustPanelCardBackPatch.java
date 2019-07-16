package theShowman.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.ExhaustPanel;
import theShowman.characters.TheShowman;



@SpirePatch(
        clz = ExhaustPanel.class,
        method = "render"
)
public class ExhaustPanelCardBackPatch {

    @SpireInsertPatch(
            rloc = 0
    )
    public static void Insert(ExhaustPanel __instance, SpriteBatch sb)
    {
        if(AbstractDungeon.player != null && AbstractDungeon.player.chosenClass.equals(TheShowman.Enums.THE_SHOWMAN) && AbstractDungeon.player.exhaustPile != null && !AbstractDungeon.player.exhaustPile.isEmpty())
        {
            if(CUSTOM_CARD_BACK == null)
            {
                CUSTOM_CARD_BACK = new TextureRegion(new Texture(THE_SHOWMAN_CUSTOM_CARD_BACK));
            }
            sb.setColor(Color.WHITE.cpy());
            sb.draw(CUSTOM_CARD_BACK, AbstractDungeon.overlayMenu.exhaustPanel.current_x - (CUSTOM_CARD_BACK.getRegionWidth() / 2.0F) * Settings.scale, AbstractDungeon.overlayMenu.exhaustPanel.current_y - (CUSTOM_CARD_BACK.getRegionHeight() / 2.0F) * Settings.scale, 0, 0, CUSTOM_CARD_BACK.getRegionWidth(), CUSTOM_CARD_BACK.getRegionHeight(), Settings.scale, Settings.scale, 0.0F);
        }
    }

    private static final String THE_SHOWMAN_CUSTOM_CARD_BACK = "theShowmanResources/images/char/showmanCharacter/card_back_mk2.png";
    private static TextureRegion CUSTOM_CARD_BACK = null;

}
