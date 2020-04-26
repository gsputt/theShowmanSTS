package theShowman.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import theShowman.ShowmanMod;
import theShowman.relics.HeartOfTheCards;


public class HeartOfTheCardsOnHandIsFull {

    public static final String ID;
    public static final RelicStrings RELIC_STRINGS;
    public static final String[] RELIC_TEXT;

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = SpirePatch.CLASS
    )
    public static class drawTracker
    {
        public static SpireField<Boolean> didTryToDraw = new SpireField<>(() -> false);
    }

    @SpirePatch(
            clz = DrawCardAction.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {AbstractCreature.class, int.class, boolean.class}
    )
    public static class drawCardActionPatch
    {
        @SpirePostfixPatch
        public static void patchConstructor(DrawCardAction __instance, AbstractCreature source, int amount, boolean endTurnDraw)
        {
            setFlag.flagTrue(AbstractDungeon.player);
        }
    }

    @SpirePatch(
            clz = DrawCardAction.class,
            method = "update"
    )
    public static class updateMethodPatch
    {
        @SpirePostfixPatch
        public static void finishAction(DrawCardAction __instance)
        {
            if(__instance.isDone) {
                setFlag.flagFalse(AbstractDungeon.player);
            }
        }
    }


    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "draw",
            paramtypez = {}
    )

    public static class drawAttempt
    {
        @SpirePrefixPatch
        public static void attemptToDraw(AbstractPlayer __instance)
        {
            setFlag.flagTrue(__instance);
        }

        @SpirePostfixPatch
        public static void postDrawAttempt(AbstractPlayer __instance)
        {
            setFlag.flagFalse(__instance);
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "draw",
            paramtypez = {int.class}
    )
    public static class drawAttemptTwo
    {
        @SpirePrefixPatch
        public static void attemptToDraw(AbstractPlayer __instance, int drawnNumber)
        {
            setFlag.flagTrue(__instance);
        }

        @SpirePostfixPatch
        public static void postDrawAttempt(AbstractPlayer __instance, int drawnNumber)
        {
            setFlag.flagFalse(__instance);
        }
    }

    public static class setFlag
    {
        public static void flagTrue(AbstractPlayer player)
        {
            drawTracker.didTryToDraw.set(player, true);
        }

        public static void flagFalse(AbstractPlayer player)
        {
            drawTracker.didTryToDraw.set(player, false);
        }
    }


    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "createHandIsFullDialog"
    )
    public static class handIsFull {
        @SpirePrefixPatch
        public static SpireReturn HeartOfTheCardsPatch(AbstractPlayer __instance) {
            if (__instance.hasRelic(HeartOfTheCards.ID)) {
                HeartOfTheCards relic = (HeartOfTheCards) __instance.getRelic(HeartOfTheCards.ID);
                if (relic.counter > 0 && drawTracker.didTryToDraw.get(__instance)) {
                    relic.counter--;
                    if (relic.counter == 0) {
                        relic.flash();
                        relic.stopPulse();
                        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(__instance, relic));
                        AbstractDungeon.actionManager.addToBottom(new PlayTopCardAction(AbstractDungeon.getRandomMonster(), false));
                        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, RELIC_TEXT[1], 2.0F, 2.0F));
                        return SpireReturn.Return(null);
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }

    static
    {
        ID = ShowmanMod.makeID("HeartOfTheCards");
        RELIC_STRINGS = CardCrawlGame.languagePack.getRelicStrings(ID);
        RELIC_TEXT = RELIC_STRINGS.DESCRIPTIONS;
    }
}
