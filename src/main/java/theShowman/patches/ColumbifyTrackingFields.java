package theShowman.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;

import java.util.ArrayList;

import static theShowman.ShowmanMod.logger;


public class ColumbifyTrackingFields {
        @SpirePatch(
                clz = AbstractRoom.class,
                method = SpirePatch.CLASS
        )
        public static class limboMonsterGroupSpireField {
                public static SpireField<MonsterGroup> limboMonsterGroup =
                        new SpireField<>(() -> new MonsterGroup((AbstractMonster) null));
        }

        @SpirePatch(
                clz = AbstractMonster.class,
                method = SpirePatch.CLASS
        )
        public static class redirectTargetField
        {
                public static SpireField<Boolean> imInLimboField = new SpireField<>(() -> false);
                public static SpireField<AbstractMonster> goHitThisByrdInstead = new SpireField<>(() -> null);
        }

        @SpirePatch(
                clz = GameActionManager.class,
                method = "getNextAction"
        )
        public static class redirectingCards {
                @SpireInsertPatch(
                        locator = Locator.class
                )
                public static void insert(GameActionManager __instance) {
                        CardQueueItem queuedCard = __instance.cardQueue.get(0);
                        if (queuedCard != null) {
                                AbstractMonster targetMonster = queuedCard.monster;
                                if (targetMonster != null) {
                                        if (redirectTargetField.imInLimboField.get(targetMonster)) {
                                                AbstractMonster redirectByrd = redirectTargetField.goHitThisByrdInstead.get(targetMonster);
                                                if(AbstractDungeon.getCurrRoom().monsters.monsters.contains(redirectByrd)
                                                && !redirectByrd.isDeadOrEscaped()) {
                                                        queuedCard.monster = redirectTargetField.goHitThisByrdInstead.get(targetMonster);
                                                }
                                                else
                                                {
                                                        logger.warn("Columbify tried to redirect " + queuedCard.card.name + " but failed.");
                                                }
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
