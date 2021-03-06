package theShowman.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theShowman.ShowmanMod;
import theShowman.cards.*;
import theShowman.effects.FloatingCardCharEffect;
import theShowman.relics.ThirdTimeCharm;

import java.util.ArrayList;

import static theShowman.ShowmanMod.*;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class TheShowman extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(ShowmanMod.class.getName());

    private static final String LEFT_CARD_STRING;
    private static final String MIDDLE_CARD_STRING;
    private static final String RIGHT_CARD_STRING;
    private static final TextureRegion LEFT_CARD;
    private static final TextureRegion MIDDLE_CARD;
    private static final TextureRegion RIGHT_CARD;

    private ArrayList<FloatingCardCharEffect> floatingCards = new ArrayList<>();
    private ArrayList<TextureRegion> floatingCardsTexture = new ArrayList<>();

    private int playerTimeUntilIdle = 0;

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_SHOWMAN;
        @SpireEnum(name = "SHOWMAN_PURPLE_COLOR")
        public static AbstractCard.CardColor COLOR_PURPLE;
        @SpireEnum(name = "SHOWMAN_PURPLE_COLOR") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    // =============== CHARACTER ENUMERATORS  =================


    // =============== BASE STATS =================

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    // =============== /BASE STATS/ =================


    // =============== STRINGS =================

    private static final String ID = makeID("DefaultCharacter");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    // =============== /STRINGS/ =================



    // =============== CHARACTER CLASS START =================

    public TheShowman(String name, PlayerClass setClass) {
        super(name, setClass, new ShowmanEnergyOrb(),
                new SpriterAnimation(
                        "theShowmanResources/images/char/showmanCharacter/ShowmanSpriter/Spriter.scml"));

        initializeClass(null,

                THE_SHOWMAN_SHOULDER_1, // campfire pose
                THE_SHOWMAN_SHOULDER_2, // another campfire pose
                THE_SHOWMAN_CORPSE, // dead corpse
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        loadAnimation(
                THE_DEFAULT_SKELETON_ATLAS,
                THE_DEFAULT_SKELETON_JSON,
                1.0f);
        AnimationState.TrackEntry e = state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        dialogX = (drawX + 0.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale); // you can just copy these values

        resetFloatingCards();

        floatingCardsTexture.clear();
        floatingCardsTexture.add(LEFT_CARD);
        floatingCardsTexture.add(MIDDLE_CARD);
        floatingCardsTexture.add(RIGHT_CARD);
    }

    // =============== /CHARACTER CLASS END/ =================

    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        logger.info("Begin loading starter Deck Strings");

        /*retVal.add(DefaultCommonAttack.ID);
        retVal.add(DefaultUncommonAttack.ID);
        retVal.add(DefaultRareAttack.ID);

        retVal.add(DefaultCommonSkill.ID);
        retVal.add(DefaultUncommonSkill.ID);
        retVal.add(DefaultRareSkill.ID);

        retVal.add(DefaultCommonPower.ID);
        retVal.add(DefaultUncommonPower.ID);
        retVal.add(DefaultRarePower.ID);

        retVal.add(DefaultAttackWithVariable.ID);
        retVal.add(DefaultSecondMagicNumberSkill.ID);
        retVal.add(OrbSkill.ID);*/

        retVal.add(VanishingStrike.ID);
        retVal.add(VanishingStrike.ID);
        retVal.add(BasicStrike.ID);
        retVal.add(BasicStrike.ID);
        retVal.add(BasicDefend.ID);
        retVal.add(BasicDefend.ID);
        //retVal.add(BasicDefend.ID);
        retVal.add(VanishingDefend.ID);
        retVal.add(VanishingDefend.ID);
        retVal.add(CardPickup52.ID);
        retVal.add(StageHook.ID);
        retVal.add(SpeechCard.ID);



        return retVal;
    }

    // Starting Relics	
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(ThirdTimeCharm.ID);
        //retVal.add(PlaceholderRelic2.ID);
        //retVal.add(DefaultClickableRelic.ID);

        UnlockTracker.markRelicAsSeen(ThirdTimeCharm.ID);
        //UnlockTracker.markRelicAsSeen(PlaceholderRelic2.ID);
        //UnlockTracker.markRelicAsSeen(DefaultClickableRelic.ID);

        return retVal;
    }

    // character Select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false); // Screen Effect
    }

    // character Select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 0;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_PURPLE;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return SHOWMAN_PURPLE;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    //Which card should be obtainable from the Match and Keep event?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new CardPickup52();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new TheShowman(name, chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return SHOWMAN_PURPLE;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return SHOWMAN_PURPLE;
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in DamageAction and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT};
    }


    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        if(AbstractDungeon.player.masterDeck.getCardNames().contains(Columbify.ID)) {
            return TEXT[3];
        }
            return TEXT[1];
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return TEXT[2];
    }


    @Override
    public void damage(DamageInfo info) {

        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output - this.currentBlock > 0) {
            if(this.animation instanceof SpriterAnimation)
            {
                ((SpriterAnimation) this.animation).myPlayer.getFirstPlayer().setAnimation(2); // Hurt
                ((SpriterAnimation) this.animation).myPlayer.getFirstPlayer().setTime(0);
                this.playerTimeUntilIdle = 500; // animation lasts 500 ms, 500 ms after is placeholder, total of 1000 ms
            }
        }
        super.damage(info);// 308
    }

    @Override
    public void renderPlayerImage(SpriteBatch sb)
    {
        if(this.animation instanceof SpriterAnimation) {
            //System.out.println(((SpriterAnimation) this.animation).myPlayer.getFirstPlayer().getTime());
            if (this.playerTimeUntilIdle > 0 && ((SpriterAnimation) this.animation).myPlayer.getFirstPlayer().getTime() >= this.playerTimeUntilIdle)
            {
                //System.out.println("timeUntilIdle has been reached");
                this.playerTimeUntilIdle = 0;
                ((SpriterAnimation) this.animation).myPlayer.getFirstPlayer().setAnimation(0); // Idle
                ((SpriterAnimation) this.animation).myPlayer.getFirstPlayer().setTime(0);
            }
        }
        this.renderFloatingCards(sb);
        super.renderPlayerImage(sb);
    }

    public void renderFloatingCards(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE.cpy());
        for(int i = 0; i < floatingCards.size(); i++)
        {
            sb.draw(
                    floatingCardsTexture.get(i), //TextureRegion
                    floatingCards.get(i).currentX - floatingCardsTexture.get(i).getRegionWidth() / 2.0F, //X
                    floatingCards.get(i).currentY - floatingCardsTexture.get(i).getRegionHeight() / 2.0F, // Y
                    floatingCardsTexture.get(i).getRegionWidth() / 2.0F, //originX
                    floatingCardsTexture.get(i).getRegionHeight() / 2.0F, //originY
                    floatingCardsTexture.get(i).getRegionWidth(), //Width
                    floatingCardsTexture.get(i).getRegionHeight(), //Height
                    0.5F * Settings.scale, //scaleX
                    0.5F * Settings.scale, //scaleY
                    floatingCards.get(i).rotation //rotation
            );
        }
    }

    @Override
    public void updateAnimations()
    {
        super.updateAnimations();
        for(FloatingCardCharEffect effect : floatingCards)
        {
            effect.update();
        }
    }


    @Override
    public void useCard(AbstractCard c, AbstractMonster m, int energyOnUse)
    {
        if(c.type == AbstractCard.CardType.ATTACK)
        {
            if(this.animation instanceof SpriterAnimation)
            {
                ((SpriterAnimation) this.animation).myPlayer.getFirstPlayer().setAnimation(1); //Attack
                ((SpriterAnimation) this.animation).myPlayer.getFirstPlayer().setTime(0);
                this.playerTimeUntilIdle = 500; //animation lasts 500 ms, 500 ms after is placeholder, total of 1000ms
            }
            if(m != null) {
                for (FloatingCardCharEffect effect : floatingCards) {
                    effect.sendToLocation(m.hb.cX, m.hb.cY);
                }
            }
        }
        super.useCard(c, m, energyOnUse);
    }

    public void resetFloatingCards()
    {
        floatingCards.clear();
        int multiplier = 1;
        /*if(this.flipHorizontal) {
            multiplier = -1;
        }*/
        floatingCards.add(new FloatingCardCharEffect(this.hb.cX - (100.0F * Settings.scale) - (20F * Settings.scale * multiplier), this.hb.cY + 180F * Settings.scale, 0.0F, 20.0F * Settings.scale, 1.0F, 45.0F)); //0 - left
        floatingCards.add(new FloatingCardCharEffect(this.hb.cX - (20F * Settings.scale * multiplier), this.hb.cY + 180F * Settings.scale, 0.0F, -20.0F * Settings.scale, 1.0F, 0.0F)); //1 - middle
        floatingCards.add(new FloatingCardCharEffect(this.hb.cX + (100.0F * Settings.scale) - (20F * Settings.scale * multiplier), this.hb.cY + 180F * Settings.scale, 0.0F, 20.0F * Settings.scale, 1.0F, -45.0F)); //2 - right
    }

    static
    {
        LEFT_CARD_STRING = ShowmanMod.makeEffectPath("card_face_clubs.png");
        MIDDLE_CARD_STRING = ShowmanMod.makeEffectPath("card_back_mk2.png");
        RIGHT_CARD_STRING = ShowmanMod.makeEffectPath("heart_ace.png");
        LEFT_CARD = new TextureRegion(new Texture(LEFT_CARD_STRING));
        MIDDLE_CARD = new TextureRegion(new Texture(MIDDLE_CARD_STRING));
        RIGHT_CARD = new TextureRegion(new Texture(RIGHT_CARD_STRING));
    }
}
