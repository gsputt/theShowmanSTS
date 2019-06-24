package theShowman.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CtBehavior;
import theShowman.cards.PropShow;

import java.util.ArrayList;

import static theShowman.patches.PropShowField.PropShowRecording;

@SpirePatch(
        clz= AbstractPlayer.class,
        method="draw",
        paramtypez={int.class}
)
public class PropShowOnCardDraw
{
    @SpireInsertPatch(
            locator=Locator.class,
            localvars={"c"}
    )
    public static void Insert(AbstractPlayer __instance, int numCards, AbstractCard drawnCard)
    {
        if(PropShowRecording.get(__instance))
        {
            if(drawnCard.type == AbstractCard.CardType.ATTACK)
            {
                PropShow.PropShowDealDamage();
            }
            else if(drawnCard.type == AbstractCard.CardType.SKILL)
            {
                PropShow.PropShowGainBlock();
            }
            else
            {
                PropShow.PropShowGainEnergy();
            }
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics");
            return LineFinder.findAllInOrder(ctBehavior, new ArrayList<>(), finalMatcher);
        }
    }
}