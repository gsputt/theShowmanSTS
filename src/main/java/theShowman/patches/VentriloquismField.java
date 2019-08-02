package theShowman.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;


@SpirePatch(
        clz = AbstractCard.class,
        method = SpirePatch.CLASS
)
public class VentriloquismField {
    public static SpireField<AbstractCard> linked = new SpireField<>(() -> null);

    @SpirePatch(
            clz = AbstractCard.class,
            method = "makeStatEquivalentCopy"
    )
    public static class makeStatEquivalentCopy
    {
        //                           __result is new card (copy), __instance, is old card (original)
        public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance)
        {
            if(linked.get(__instance) != null) {
                linked.set(__result, linked.get(__instance));
                linked.set(linked.get(__instance), __result);
            }
            return __result;
        }
    }
}
