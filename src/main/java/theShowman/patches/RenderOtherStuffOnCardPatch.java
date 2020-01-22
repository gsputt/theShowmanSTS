package theShowman.patches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

@SpirePatch(
        clz = AbstractCard.class,
        method = "renderCard"
)
public class RenderOtherStuffOnCardPatch {

    private static final TextureAtlas ATLAS;
    private static final TextureAtlas.AtlasRegion POWER_IMAGE;

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
                tmp.rotate(__instance.angle);
                tmp.scl(__instance.drawScale * Settings.scale);
                show.current_x = __instance.current_x + tmp.x;
                show.current_y = __instance.current_y + tmp.y;
                show.angle = __instance.angle;
                show.render(sb);
            }

            if (ObjectPermanenceRenderField.ObjectPermanenceFlag.get(__instance)) {

                Vector2 tmp = new Vector2(135F, 185F);
                tmp.rotate(__instance.angle);
                tmp.scl(__instance.drawScale * Settings.scale);

                sb.setColor(Color.WHITE.cpy());
                sb.draw(
                        POWER_IMAGE,
                        tmp.x + __instance.current_x - (float)POWER_IMAGE.packedWidth / 2.0F,
                        tmp.y + __instance.current_y - (float)POWER_IMAGE.packedHeight / 2.0F,
                        (float)POWER_IMAGE.packedWidth / 2.0F,
                        (float)POWER_IMAGE.packedHeight / 2.0F,
                        (float)POWER_IMAGE.packedWidth,
                        (float)POWER_IMAGE.packedHeight,
                        __instance.drawScale * 2.5F,
                        __instance.drawScale * 2.5F,
                        __instance.angle - 45.0F
                );

            }


        }
    }

    static
    {
        ATLAS = new TextureAtlas(Gdx.files.internal("powers/powers.atlas"));
        POWER_IMAGE = ATLAS.findRegion("48/time");
    }
}
