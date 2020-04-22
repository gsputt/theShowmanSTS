/*package theShowman.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;
import theShowman.powers.ColumbifiedPower;
import theShowman.powers.Deprecated.IAmAByrd;

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
        CardQueueItem queuedCard = __instance.cardQueue.get(0);
        if(queuedCard != null) {
            AbstractMonster targetMonster = queuedCard.monster;
            if (targetMonster != null) {
                if (targetMonster.hasPower(IAmAByrd.POWER_ID)) {
                    if(((IAmAByrd)targetMonster.getPower(IAmAByrd.POWER_ID)).linked.hasPower(ColumbifiedPower.POWER_ID))
                    {
                        queuedCard.monster = ((IAmAByrd)queuedCard.monster.getPower(IAmAByrd.POWER_ID)).linked;
                    }
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
*/