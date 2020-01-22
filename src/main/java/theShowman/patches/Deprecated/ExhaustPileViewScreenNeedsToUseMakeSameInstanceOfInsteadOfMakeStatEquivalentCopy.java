/*package theShowman.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.ExhaustPileViewScreen;
import javassist.CtBehavior;

import java.util.ArrayList;

public class ExhaustPileViewScreenNeedsToUseMakeSameInstanceOfInsteadOfMakeStatEquivalentCopy {

    @SpirePatch(
            clz = ExhaustPileViewScreen.class,
            method = "open"
    )
    public static class thesePatchNamesKeepGettingLonger
    {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"toAdd", "c"}
        )
        public static void rEEEEEEEEEE (ExhaustPileViewScreen __instance, @ByRef AbstractCard[] toAdd, AbstractCard c)
        {
            toAdd[0] = c.makeSameInstanceOf();
        }
    }
    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "makeStatEquivalentCopy");
            int[] helpIwantTheLineAfterThisOne = LineFinder.findInOrder(ctBehavior, new ArrayList<>(), finalMatcher);
            helpIwantTheLineAfterThisOne[0] += 1;
            return helpIwantTheLineAfterThisOne;
        }
    }
}
*/