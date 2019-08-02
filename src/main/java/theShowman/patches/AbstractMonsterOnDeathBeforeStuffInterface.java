package theShowman.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theShowman.powers.onDeathBeforeStuffInterface;

@SpirePatch(
        clz = AbstractMonster.class,
        method = "die",
        paramtypez = {
                boolean.class
        }
)
public class AbstractMonsterOnDeathBeforeStuffInterface {
    @SpirePrefixPatch
    public static void triggerBeforeOtherStuff(AbstractMonster __instance, @ByRef boolean[] triggerStuff)
    {
        if(!__instance.isDying)
        {
            for(AbstractPower p : __instance.powers)
            {
                if(p instanceof onDeathBeforeStuffInterface)
                {
                    triggerStuff[0] = ((onDeathBeforeStuffInterface) p).beforeDoingOtherStuffOnDead(triggerStuff[0]);
                }
            }
        }
    }
}
