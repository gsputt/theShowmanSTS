package theShowman.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;


@SpirePatch(
        clz = AbstractCard.class,
        method = SpirePatch.CLASS
)

public class ObjectPermanenceRenderField {
    public static SpireField<Boolean> ObjectPermanenceFlag = new SpireField<>(() -> false);

    @SpirePatch(
            clz = AbstractCard.class,
            method = "makeStatEquivalentCopy"
    )
    public static class pleaseCopyOverTheObjectPermanenceFieldToTheNewMakeStatEquivalentCopy
    {
        public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance)
        {
            if(ObjectPermanenceFlag.get(__instance))
            {
                ObjectPermanenceFlag.set(__result, true);
            }
            return __result;
        }
    }


}
