package theShowman.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import javassist.CtBehavior;
import theShowman.powers.WillingVolunteerPower;

import java.util.ArrayList;


@SpirePatch(
        clz = TipHelper.class,
        method = "renderPowerTips"
)
public class renderPowerTipsWillingVolunteer {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars={"tip"}
    )
    public static void insert(float x, float y, SpriteBatch sb, ArrayList<PowerTip> powerTips, PowerTip tip)
    {
        if(AbstractDungeon.player != null) {
            if (AbstractDungeon.player.powers != null) {
                if (AbstractDungeon.player.hasPower(WillingVolunteerPower.POWER_ID)) {
                    WillingVolunteerPower power = (WillingVolunteerPower) AbstractDungeon.player.getPower(WillingVolunteerPower.POWER_ID);
                    if (tip.imgRegion == power.region48) {
                        tip.imgRegion = null;
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
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(PowerTip.class, "imgRegion");
            return LineFinder.findAllInOrder(ctBehavior, new ArrayList<>(), finalMatcher);
        }
    }

}
