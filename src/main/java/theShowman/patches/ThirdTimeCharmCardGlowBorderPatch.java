package theShowman.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;
import theShowman.relics.ThirdTimeCharm;

@SpirePatch(
        clz = CardGlowBorder.class,
        method = SpirePatch.CONSTRUCTOR
)
public class ThirdTimeCharmCardGlowBorderPatch {
    @SpirePostfixPatch
    public static void GlowRedPls(CardGlowBorder __instance, AbstractCard c)
    {
        if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
        {
            if(AbstractDungeon.player.hasRelic(ThirdTimeCharm.ID))
            {
                if(AbstractDungeon.player.getRelic(ThirdTimeCharm.ID).counter == 2)
                {
                    ReflectionHacks.setPrivate(__instance, AbstractGameEffect.class, "color", Color.RED.cpy());
                }
            }
        }
    }
}
