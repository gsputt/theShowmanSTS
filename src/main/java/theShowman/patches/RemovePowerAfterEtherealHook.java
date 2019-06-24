package theShowman.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DiscardAtEndOfTurnAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;
import theShowman.powers.TriggerAfterEtherealInterface;

import java.util.ArrayList;

public class RemovePowerAfterEtherealHook {
    @SpirePatch(
            clz = DiscardAtEndOfTurnAction.class,
            method = "update"
    )
    public static class doTheThing
    {
        public static void Postfix(DiscardAtEndOfTurnAction __instance)
        {
            for(AbstractPower p: AbstractDungeon.player.powers)
            {
                if(p instanceof TriggerAfterEtherealInterface)
                {
                    ((TriggerAfterEtherealInterface)p).triggerAfterEtherealCards();
                }
            }
        }
    }
}
