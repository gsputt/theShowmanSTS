package theShowman.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theShowman.relics.HeartOfTheCards;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "createHandIsFullDialog"
)
public class HeartOfTheCardsOnHandIsFull {
    @SpirePrefixPatch
    public static SpireReturn HeartOfTheCardsPatch(AbstractPlayer __instance)
    {
        if(__instance.hasRelic(HeartOfTheCards.ID))
        {
            HeartOfTheCards relic = (HeartOfTheCards) __instance.getRelic(HeartOfTheCards.ID);
            if(relic.counter > 0)
            {
                relic.counter--;
                if(relic.counter == 0)
                {
                    relic.flash();
                    relic.stopPulse();
                    AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(__instance, relic));
                    AbstractDungeon.actionManager.addToBottom(new PlayTopCardAction(AbstractDungeon.getRandomMonster(), false));
                    return SpireReturn.Return(null);
                }
            }
        }
        return SpireReturn.Continue();
    }
}
