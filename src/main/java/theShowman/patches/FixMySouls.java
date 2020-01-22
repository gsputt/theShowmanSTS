package theShowman.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FixMySouls {
    @SpirePatch(
            clz = Soul.class,
            method = "update"
    )
    public static class RefreshHandLayoutWhenYourDoneMovingSoulsAround
    {
        @SpirePostfixPatch
        public static void sometimesWhenYouMoveTheSoulsAroundButTheyEndUpInYourHandDueToBeingDrawnBeforeTheSoulAnimationFinishesTheRotationDerps(Soul __instance)
        {
            if(__instance.isDone)
            {
                AbstractDungeon.player.hand.refreshHandLayout();
            }
        }
    }

    /*@SpirePatch(
            clz = Soul.class,
            method = SpirePatch.CLASS
    )
    public static class fixMySoulVisualsField {
        public static SpireField<Boolean> fixMyVisuals = new SpireField<>(() -> false);
    }

    @SpirePatch(
            clz = Soul.class,
            method = "onToDeck",
            paramtypez = {AbstractCard.class, boolean.class, boolean.class}
    )
    public static class cardSouls3DieHarderEdition {
        @SpirePostfixPatch
        public static void fixMySoulPls(Soul __instance, AbstractCard card, boolean randomSpot, boolean visualOnly)
        {
            if(visualOnly)
            {
                fixMySoulVisualsField.fixMyVisuals.set(__instance, true);
            }
        }
    }

    @SpirePatch(
            clz = Soul.class,
            method = "update"
    )
    public static class isCarryingCardMessesUpMyVisuals
    {
        public static ExprEditor Instrument()
        {
            return new ExprEditor()
            {
                @Override
                public void edit(MethodCall m) throws CannotCompileException
                {
                    if(m.getMethodName().equals("isCarryingCard"))
                    {
                        m.replace("{" +
                                "$_ = $proceed() || " +
                                carryCardOrDieTrying.class.getName() +
                                ".youDiedTrying($0);" +
                                "}"
                        );
                    }
                }
            };
        }

        @SpirePostfixPatch
        public static void cardSoulsStoleMyRealSouls(Soul __instance)
        {
            if(__instance.isReadyForReuse)
            {
                fixMySoulVisualsField.fixMyVisuals.set(__instance, false);
            }
        }
    }

    public static class carryCardOrDieTrying
    {
        public static boolean youDiedTrying(Soul soul)
        {
            return fixMySoulVisualsField.fixMyVisuals.get(soul);
        }
    }*/

    /*@SpirePatch(
            clz = ShowCardAndAddToDrawPileEffect.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {AbstractCard.class, boolean.class, boolean.class}
    )
    public static class ShowingCardsIsStupid
    {
        @SpirePostfixPatch
        public static void ughhhFixMyXYPos(ShowCardAndAddToDrawPileEffect __instance, AbstractCard srcCard, boolean randomSpot, boolean toBottom)
        {
            AbstractCard modifyThisCardPlsKThnxBye = (AbstractCard)ReflectionHacks.getPrivate(__instance, ShowCardAndAddToDrawPileEffect.class, "card");
            modifyThisCardPlsKThnxBye.current_x = srcCard.current_x;
            modifyThisCardPlsKThnxBye.current_y = srcCard.current_y;
        }
    }*/
}
