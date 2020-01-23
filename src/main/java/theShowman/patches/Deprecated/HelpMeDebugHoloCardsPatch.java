/*package theShowman.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class HelpMeDebugHoloCardsPatch {
    @SpirePatch(
            clz = AbstractCard.class,
            method = SpirePatch.CLASS
    )
    public static class spireField
    {
        public static SpireField<Color> renderColor = new SpireField<>(() -> Color.WHITE.cpy());
        public static SpireField<Float> drawScale = new SpireField<>(() -> 0.0F);
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderCard",
            paramtypez = {SpriteBatch.class, boolean.class, boolean.class}
    )
    public static class HoloCardOutput
    {
        @SpirePostfixPatch
        public static void whyMe(AbstractCard __instance, SpriteBatch sb, boolean hovered, boolean selected)
        {
            Color color = ((Color)ReflectionHacks.getPrivate(__instance, AbstractCard.class, "renderColor")).cpy();
            if(!color.equals(spireField.renderColor.get(__instance)) || spireField.drawScale.get(__instance) != __instance.drawScale) {
                System.out.println("_________________");
                System.out.println("Card Name: " + __instance.name);

                if(!color.equals(spireField.renderColor.get(__instance))) {
                    System.out.println("renderColorR: " + color.r);
                    System.out.println("renderColorG: " + color.g);
                    System.out.println("renderColorB: " + color.b);
                    System.out.println("renderColorA: " + color.a);
                    spireField.renderColor.set(__instance, color);
                }

                if(spireField.drawScale.get(__instance) != __instance.drawScale) {
                    System.out.println("drawScale: " + __instance.drawScale);
                    spireField.drawScale.set(__instance, __instance.drawScale);
                }
                System.out.println("_________________");
            }
        }
    }
}
*/