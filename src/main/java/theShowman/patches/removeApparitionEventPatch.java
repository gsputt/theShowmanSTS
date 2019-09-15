package theShowman.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Ghosts;
import theShowman.characters.TheShowman;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "initializeCardPools"
)
public class removeApparitionEventPatch {
    public static void prefix(AbstractDungeon __instance)
    {
        if(AbstractDungeon.player instanceof TheShowman)
        {
            __instance.eventList.remove(Ghosts.ID);
        }
    }
}
