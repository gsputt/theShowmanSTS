package theShowman.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

@SpirePatch(
        clz = AbstractCard.class,
        method = "renderCard"
)
public class RenderTinyCardOnCardPatch {

    public static void Postfix(AbstractCard __instance, SpriteBatch sb, boolean hovered, boolean selected)
    {
        if (!Settings.hideCards && !__instance.isFlipped) {
            if(VentriloquismField.linked.get(__instance) != null)
            {
                AbstractCard waitDontDoThat = VentriloquismField.linked.get(__instance);
                AbstractCard show = VentriloquismField.linked.get(__instance).makeStatEquivalentCopy();
                VentriloquismField.linked.set(__instance, waitDontDoThat);
                VentriloquismField.linked.set(show, null);

                show.drawScale = __instance.drawScale * 0.4F;

                Vector2 tmp = new Vector2(135F, 185F);
                tmp.scl(__instance.drawScale * Settings.scale);
                show.current_x = __instance.current_x + tmp.x;
                show.current_y = __instance.current_y + tmp.y;
                show.render(sb);
            }
        }
    }
}
