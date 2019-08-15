package theShowman.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import javassist.CtBehavior;
import theShowman.powers.IAmAByrd;

import java.util.ArrayList;

@SpirePatch(
        clz = GameActionManager.class,
        method = "getNextAction"
)
public class targetMonsterColumbifiedPatch {
    @SpireInsertPatch(
                    locator = Locator.class
            )
    public static void insert(GameActionManager __instance)
    {
        if(__instance.cardQueue.get(0) != null) {
            if (__instance.cardQueue.get(0).monster != null) {
                if (__instance.cardQueue.get(0).monster.hasPower(IAmAByrd.POWER_ID)) {
                    __instance.cardQueue.get(0).monster = ((IAmAByrd)__instance.cardQueue.get(0).monster.getPower(IAmAByrd.POWER_ID)).linked;
                }
            }
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "get");
            int[] line = LineFinder.findInOrder(ctBehavior, new ArrayList<>(), finalMatcher);
            line[0] += 1;
            return line;
        }
    }
}
