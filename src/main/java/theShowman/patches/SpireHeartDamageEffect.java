package theShowman.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AnimatedNpc;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.combat.DamageHeartEffect;
import javassist.CtBehavior;
import theShowman.cards.Columbify;
import theShowman.characters.TheShowman;
import theShowman.effects.TossCardEffect;

@SpirePatch(
        clz=DamageHeartEffect.class,
        method="update"
)
public class SpireHeartDamageEffect
{
    @SpireInsertPatch(
            locator=Locator.class
    )
    public static void Insert(DamageHeartEffect __instance)
    {
        if (AbstractDungeon.player.chosenClass == TheShowman.Enums.THE_SHOWMAN) {
            AbstractDungeon.effectsQueue.add(new TossCardEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, (float)ReflectionHacks.getPrivate(__instance, DamageHeartEffect.class, "x") + (float)__instance.img.packedWidth / 2.0F, (float)ReflectionHacks.getPrivate(__instance, DamageHeartEffect.class, "y") + (float)__instance.img.packedHeight / 2.0F, 10));
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "effectsQueue");
            int[] found = LineFinder.findInOrder(ctBehavior, finalMatcher);
            for(int i = 0; i < found.length; i++)
            {
                found[i] += 1;
            }
            return found;
        }
    }

    @SpirePatch(
            clz=DamageHeartEffect.class,
            method="loadImage"
    )
    public static class showBluntLightAttackEffect {
        public static SpireReturn<TextureAtlas.AtlasRegion> Prefix(DamageHeartEffect __instance) {
            if (AbstractDungeon.player.chosenClass == TheShowman.Enums.THE_SHOWMAN) {
                return SpireReturn.Return(ImageMaster.ATK_BLUNT_LIGHT);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = SpireHeart.class,
            method = "buttonEffect"
    )
    public static class swapHeartForByrd
    {
        @SpireInsertPatch(
                locator=LocatorTwo.class
        )
        public static void Insert(SpireHeart __instance, int buttonPressed)
        {
            if(AbstractDungeon.player.masterDeck.getCardNames().contains(Columbify.ID)) {
                AnimatedNpc npc = ((AnimatedNpc) ReflectionHacks.getPrivate(__instance, SpireHeart.class, "npc"));
                npc.dispose();
                ReflectionHacks.setPrivate(__instance, SpireHeart.class, "npc", new AnimatedNpc(1300.0F * Settings.scale, (float) Settings.HEIGHT - 860.0F * Settings.scale, "images/monsters/theCity/byrd/flying.atlas", "images/monsters/theCity/byrd/flying.json", "idle_flap"));
            }
        }
    }

    private static class LocatorTwo extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "getSpireHeartSlashEffect");
            return LineFinder.findInOrder(ctBehavior, finalMatcher);
        }
    }
}