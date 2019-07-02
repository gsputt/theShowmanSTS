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

public class SpireHeartDamageEffect
{

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
    public static class customAttackEffectAndSwapHeartForByrd
    {
        @SpireInsertPatch(
                locator=LocatorTwo.class
        )
        public static void Insert(SpireHeart __instance, int buttonPressed)
        {
            if (AbstractDungeon.player.chosenClass == TheShowman.Enums.THE_SHOWMAN) {
                AbstractDungeon.effectsQueue.add(new TossCardEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, (float)ReflectionHacks.getPrivate(__instance, SpireHeart.class, "x"), (float)ReflectionHacks.getPrivate(__instance, SpireHeart.class, "y"), 5));
            }

            if(AbstractDungeon.player.masterDeck.getCardNames().contains(Columbify.ID)) {
                AnimatedNpc npc = ((AnimatedNpc) ReflectionHacks.getPrivate(__instance, SpireHeart.class, "npc"));
                npc.dispose();
                ReflectionHacks.setPrivate(__instance, SpireHeart.class, "npc", new AnimatedNpc((float)ReflectionHacks.getPrivate(__instance, SpireHeart.class, "x"), (float)ReflectionHacks.getPrivate(__instance, SpireHeart.class, "y") - (180F * Settings.scale), "images/monsters/theCity/byrd/flying.atlas", "images/monsters/theCity/byrd/flying.json", "idle_flap"));
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