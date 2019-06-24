package theShowman;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theShowman.cards.*;
import theShowman.characters.TheShowman;
import theShowman.relics.*;
import theShowman.util.IDCheckDontTouchPls;
import theShowman.util.TextureLoader;
import theShowman.variables.DefaultCustomVariable;
import theShowman.variables.DefaultSecondMagicNumber;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;


/*
 * With that out of the way:
 * Welcome to this super over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
 *
 * And pls. Read the comments.
 */

@SpireInitializer
public class ShowmanMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber {
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(ShowmanMod.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theDefaultDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "The Showman";
    private static final String AUTHOR = "Left Click, ComingVirus, DontTakeMyKidneys"; // And pretty soon - You!
    private static final String DESCRIPTION = "A tribute to the Original Slay the Spire Characters";

    // =============== INPUT TEXTURE LOCATION =================

    // Colors (RGB)
    // Character Color
    public static final Color SHOWMAN_PURPLE = CardHelper.getColor(143.0f, 109.0f, 237.0f);

    // Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown

    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!

    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_SHOWMAN_PURPLE = "theShowmanResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_SHOWMAN_PURPLE = "theShowmanResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_SHOWMAN_PURPLE = "theShowmanResources/images/512/bg_power_default_gray.png";

    private static final String ENERGY_ORB_SHOWMAN_PURPLE = "theShowmanResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "theShowmanResources/images/512/card_small_orb.png";

    private static final String ATTACK_SHOWMAN_PURPLE_PORTRAIT = "theShowmanResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_SHOWMAN_PURPLE_PORTRAIT = "theShowmanResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_SHOWMAN_PURPLE_PORTRAIT = "theShowmanResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_SHOWMAN_PURPLE_PORTRAIT = "theShowmanResources/images/1024/card_default_gray_orb.png";

    // Character assets
    private static final String THE_DEFAULT_BUTTON = "theShowmanResources/images/charSelect/DefaultCharacterButton.png";
    private static final String THE_DEFAULT_PORTRAIT = "theShowmanResources/images/charSelect/DefaultCharacterPortraitBG.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "theShowmanResources/images/char/defaultCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "theShowmanResources/images/char/defaultCharacter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "theShowmanResources/images/char/defaultCharacter/corpse.png";

    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "theShowmanResources/images/Badge.png";

    // Atlas and JSON files for the Animations
    public static final String THE_DEFAULT_SKELETON_ATLAS = "theShowmanResources/images/char/defaultCharacter/skeleton.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "theShowmanResources/images/char/defaultCharacter/skeleton.json";

    // =============== MAKE IMAGE PATHS =================

    public static TheShowman TheShowmanObject;

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/orbs/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    public static String makeEffectPath(String resourcePath)
    {
        return getModID() + "Resources/images/effects/" + resourcePath;
    }

    // =============== /MAKE IMAGE PATHS/ =================

    // =============== /INPUT TEXTURE LOCATION/ =================


    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================

    public ShowmanMod() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);


      /*
           (   ( /(  (     ( /( (            (  `   ( /( )\ )    )\ ))\ )
           )\  )\()) )\    )\()))\ )   (     )\))(  )\()|()/(   (()/(()/(
         (((_)((_)((((_)( ((_)\(()/(   )\   ((_)()\((_)\ /(_))   /(_))(_))
         )\___ _((_)\ _ )\ _((_)/(_))_((_)  (_()((_) ((_|_))_  _(_))(_))_
        ((/ __| || (_)_\(_) \| |/ __| __| |  \/  |/ _ \|   \  |_ _||   (_)
         | (__| __ |/ _ \ | .` | (_ | _|  | |\/| | (_) | |) |  | | | |) |
          \___|_||_/_/ \_\|_|\_|\___|___| |_|  |_|\___/|___/  |___||___(_)
      */

        setModID("theShowman");
        // cool

        // 1. Go to your resources folder in the project panel, and refactor> rename theShowmanResources to
        // yourModIDResources.

        // 2. Click on the localization > eng folder and press ctrl+shift+r, then select "Directory" (rather than in Project)
        // replace all instances of theShowman with yourModID.
        // Because your mod ID isn't the default. Your cards (and everything else) should have Your mod id. Not mine.

        // 3. FINALLY and most importantly: Scroll up a bit. You may have noticed the image locations above don't use getModID()
        // Change their locations to reflect your actual ID rather than theShowman. They get loaded before getID is a thing.

        logger.info("Done subscribing");

        logger.info("Creating the color " + TheShowman.Enums.COLOR_PURPLE.toString());

        BaseMod.addColor(COLOR_PURPLE, SHOWMAN_PURPLE, SHOWMAN_PURPLE, SHOWMAN_PURPLE,
                SHOWMAN_PURPLE, SHOWMAN_PURPLE, SHOWMAN_PURPLE, SHOWMAN_PURPLE,
                ATTACK_SHOWMAN_PURPLE, SKILL_SHOWMAN_PURPLE, POWER_SHOWMAN_PURPLE, ENERGY_ORB_SHOWMAN_PURPLE,
                ATTACK_SHOWMAN_PURPLE_PORTRAIT, SKILL_SHOWMAN_PURPLE_PORTRAIT, POWER_SHOWMAN_PURPLE_PORTRAIT,
                ENERGY_ORB_SHOWMAN_PURPLE_PORTRAIT, CARD_ENERGY_ORB);

        logger.info("Done creating the color");


        /*logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        theDefaultDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("theShowman", "theShowmanConfig", theDefaultDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");*/

    }

    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP

    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = ShowmanMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO

    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH

    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NNOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = ShowmanMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = ShowmanMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO

    // ====== YOU CAN EDIT AGAIN ======


    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("Initializing theShowman Mod. Feed Cookies to continue");
        ShowmanMod defaultmod = new ShowmanMod();
        logger.info(" theShowman Mod Initialized - Cookies have been fed");
    }

    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================


    // =============== LOAD THE CHARACTER =================

    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheShowman.Enums.THE_SHOWMAN.toString());
        TheShowmanObject = new TheShowman("the Showman", TheShowman.Enums.THE_SHOWMAN);
        BaseMod.addCharacter(TheShowmanObject,
                THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, TheShowman.Enums.THE_SHOWMAN);

        receiveEditPotions();
        logger.info("Added " + TheShowman.Enums.THE_SHOWMAN.toString());
    }

    // =============== /LOAD THE CHARACTER/ =================


    // =============== POST-INITIALIZE =================

    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");

        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();

        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:

            enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.

        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);


        // =============== EVENTS =================

        // This event will be exclusive to the City (act 2). If you want an event that's present at any
        // part of the game, simply don't include the dungeon ID
        // If you want to have a character-specific event, look at slimebound (CityRemoveEventPatch).
        // Essentially, you need to patch the game and say "if a player is not playing my character class, remove the event from the pool"
        //BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);

        // =============== /EVENTS/ =================

        BaseMod.addSaveField("TheShowman", TheShowmanObject);

        logger.info("Done loading badge Image and mod options");
    }

    // =============== / POST-INITIALIZE/ =================


    // ================ ADD POTIONS ===================

    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");

        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        //BaseMod.addPotion(PlaceholderPotion.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, PlaceholderPotion.POTION_ID, TheShowman.Enums.THE_SHOWMAN);

        logger.info("Done editing potions");
    }

    // ================ /ADD POTIONS/ ===================


    // ================ ADD RELICS ===================

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(new ThirdTimeCharm(), COLOR_PURPLE);
        //BaseMod.addRelicToCustomPool(new BottledPlaceholderRelic(), COLOR_PURPLE);
        //BaseMod.addRelicToCustomPool(new DefaultClickableRelic(), COLOR_PURPLE);

        // This adds a relic to the Shared pool. Every character can find this relic.
        //BaseMod.addRelic(new PlaceholderRelic2(), RelicType.SHARED);

        // Mark relics as seen (the others are all starters so they're marked as seen in the character file
        UnlockTracker.markRelicAsSeen(ThirdTimeCharm.ID);
        logger.info("Done adding relics!");
    }

    // ================ /ADD RELICS/ ===================


    // ================ ADD CARDS ===================

    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variabls");
        // Add the Custom Dynamic variabls
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());

        logger.info("Adding cards");
        // Add the cards
        // Don't comment out/delete these cards (yet). You need 1 of each type and rarity (technically) for your game not to crash
        // when generating card rewards/shop screen items.

        /*BaseMod.addCard(new OrbSkill());
        BaseMod.addCard(new DefaultSecondMagicNumberSkill());
        BaseMod.addCard(new DefaultCommonAttack());
        BaseMod.addCard(new DefaultAttackWithVariable());
        BaseMod.addCard(new DefaultCommonSkill());
        BaseMod.addCard(new DefaultCommonPower());
        BaseMod.addCard(new DefaultUncommonSkill());
        BaseMod.addCard(new DefaultUncommonAttack());
        BaseMod.addCard(new DefaultUncommonPower());
        BaseMod.addCard(new DefaultRareAttack());
        BaseMod.addCard(new DefaultRareSkill());
        BaseMod.addCard(new DefaultRarePower());*/

        BaseMod.addCard(new ExtraWideSleeves());
        BaseMod.addCard(new FullHouse());
        BaseMod.addCard(new StrongArm());
        BaseMod.addCard(new ExaggeratedArmSweeps());
        BaseMod.addCard(new BasicDefend());
        BaseMod.addCard(new BasicStrike());
        BaseMod.addCard(new CardPickup52());
        BaseMod.addCard(new VanishingDefend());
        BaseMod.addCard(new VanishingStrike());
        BaseMod.addCard(new SleeveStash());
        BaseMod.addCard(new NowYouSeeMe());
        BaseMod.addCard(new SleightedHand());
        BaseMod.addCard(new PocketSand());
        BaseMod.addCard(new FlashyFlourish());
        BaseMod.addCard(new Prestidigitation());
        BaseMod.addCard(new NowYouDont());
        BaseMod.addCard(new GotYourNose());
        BaseMod.addCard(new RazzleDazzle());
        BaseMod.addCard(new WildFlourish());
        BaseMod.addCard(new AndBehindCurtain());
        BaseMod.addCard(new ThreeCardMonty());
        BaseMod.addCard(new PropManacles());
        BaseMod.addCard(new ForMyNextTrick());
        BaseMod.addCard(new ParlorTrick());
        BaseMod.addCard(new IsThisYourCard());
        BaseMod.addCard(new StackTheDeck());
        BaseMod.addCard(new ByrdBGone());
        BaseMod.addCard(new PotentialUnleashed());
        BaseMod.addCard(new DivertingDisplay());
        BaseMod.addCard(new SetTheStage());
        BaseMod.addCard(new VanDeGraaffsRevenge());
        BaseMod.addCard(new CrashingLights());
        BaseMod.addCard(new OpenTheCurtain());
        BaseMod.addCard(new MirrorMirror());
        BaseMod.addCard(new SecondAct());
        BaseMod.addCard(new HypeTheCrowd());
        BaseMod.addCard(new SybilFlourish());
        BaseMod.addCard(new CurtainCall());
        BaseMod.addCard(new WardrobeWarding());
        BaseMod.addCard(new AGrossDisplay());
        BaseMod.addCard(new HatTrick());
        BaseMod.addCard(new SawnInHalf());
        BaseMod.addCard(new KnockThePile());
        BaseMod.addCard(new DapperFlourish());
        BaseMod.addCard(new UpMySleeve());
        BaseMod.addCard(new PropShow());
        BaseMod.addCard(new WhirlingDervish());
        BaseMod.addCard(new Stupefy());
        BaseMod.addCard(new FistFullOfCards());
        BaseMod.addCard(new VanishingAct());
        BaseMod.addCard(new WillingVolunteer());
        BaseMod.addCard(new MaximumDistraction());
        BaseMod.addCard(new NothingInMyHands());
        BaseMod.addCard(new ThrowingCards());
        BaseMod.addCard(new Toro());
        BaseMod.addCard(new BlackJack());
        BaseMod.addCard(new OneUp());
        BaseMod.addCard(new StartlingShowmanship());
        BaseMod.addCard(new BafflingShuffle());
        BaseMod.addCard(new Columbify());
        BaseMod.addCard(new Showstopper());
        BaseMod.addCard(new ItsShowtime());
        BaseMod.addCard(new BaitedBreath());
        BaseMod.addCard(new GrandGambit());
        BaseMod.addCard(new ObjectPermanence());
        BaseMod.addCard(new BottomDoubleDeal());
        BaseMod.addCard(new Presto());
        BaseMod.addCard(new SubtleMisdirections());
        BaseMod.addCard(new RaptMisdirection());
        BaseMod.addCard(new ReappearingTrick());
        BaseMod.addCard(new MischiefManaged());
        BaseMod.addCard(new KiblerFlick());
        BaseMod.addCard(new SpeechCard());

        logger.info("Making sure the cards are unlocked.");
        // Unlock the cards
        // This is so that they are all "seen" in the library, for people who like to look at the card list
        // before playing your mod.
        /*UnlockTracker.unlockCard(OrbSkill.ID);
        UnlockTracker.unlockCard(DefaultSecondMagicNumberSkill.ID);
        UnlockTracker.unlockCard(DefaultCommonAttack.ID);
        UnlockTracker.unlockCard(DefaultAttackWithVariable.ID);
        UnlockTracker.unlockCard(DefaultCommonSkill.ID);
        UnlockTracker.unlockCard(DefaultCommonPower.ID);
        UnlockTracker.unlockCard(DefaultUncommonSkill.ID);
        UnlockTracker.unlockCard(DefaultUncommonAttack.ID);
        UnlockTracker.unlockCard(DefaultUncommonPower.ID);
        UnlockTracker.unlockCard(DefaultRareAttack.ID);
        UnlockTracker.unlockCard(DefaultRareSkill.ID);
        UnlockTracker.unlockCard(DefaultRarePower.ID);*/
        UnlockTracker.unlockCard(ExtraWideSleeves.ID);
        UnlockTracker.unlockCard(FullHouse.ID);
        UnlockTracker.unlockCard(StrongArm.ID);
        UnlockTracker.unlockCard(ExaggeratedArmSweeps.ID);
        UnlockTracker.unlockCard(BasicDefend.ID);
        UnlockTracker.unlockCard(BasicStrike.ID);
        UnlockTracker.unlockCard(CardPickup52.ID);
        UnlockTracker.unlockCard(VanishingDefend.ID);
        UnlockTracker.unlockCard(VanishingStrike.ID);
        UnlockTracker.unlockCard(NowYouSeeMe.ID);
        UnlockTracker.unlockCard(SleightedHand.ID);
        UnlockTracker.unlockCard(PocketSand.ID);
        UnlockTracker.unlockCard(FlashyFlourish.ID);
        UnlockTracker.unlockCard(Prestidigitation.ID);
        UnlockTracker.unlockCard(NowYouDont.ID);
        UnlockTracker.unlockCard(GotYourNose.ID);
        UnlockTracker.unlockCard(RazzleDazzle.ID);
        UnlockTracker.unlockCard(WildFlourish.ID);
        UnlockTracker.unlockCard(AndBehindCurtain.ID);
        UnlockTracker.unlockCard(ThreeCardMonty.ID);
        UnlockTracker.unlockCard(PropManacles.ID);
        UnlockTracker.unlockCard(ForMyNextTrick.ID);
        UnlockTracker.unlockCard(ParlorTrick.ID);
        UnlockTracker.unlockCard(IsThisYourCard.ID);
        UnlockTracker.unlockCard(StackTheDeck.ID);
        UnlockTracker.unlockCard(ByrdBGone.ID);
        UnlockTracker.unlockCard(PotentialUnleashed.ID);
        UnlockTracker.unlockCard(DivertingDisplay.ID);
        UnlockTracker.unlockCard(SetTheStage.ID);
        UnlockTracker.unlockCard(VanDeGraaffsRevenge.ID);
        UnlockTracker.unlockCard(CrashingLights.ID);
        UnlockTracker.unlockCard(OpenTheCurtain.ID);
        UnlockTracker.unlockCard(MirrorMirror.ID);
        UnlockTracker.unlockCard(SecondAct.ID);
        UnlockTracker.unlockCard(HypeTheCrowd.ID);
        UnlockTracker.unlockCard(SybilFlourish.ID);
        UnlockTracker.unlockCard(CurtainCall.ID);
        UnlockTracker.unlockCard(WardrobeWarding.ID);
        UnlockTracker.unlockCard(AGrossDisplay.ID);
        UnlockTracker.unlockCard(HatTrick.ID);
        UnlockTracker.unlockCard(SawnInHalf.ID);
        UnlockTracker.unlockCard(KnockThePile.ID);
        UnlockTracker.unlockCard(DapperFlourish.ID);
        UnlockTracker.unlockCard(UpMySleeve.ID);
        UnlockTracker.unlockCard(PropShow.ID);
        UnlockTracker.unlockCard(WhirlingDervish.ID);
        UnlockTracker.unlockCard(Stupefy.ID);
        UnlockTracker.unlockCard(FistFullOfCards.ID);
        UnlockTracker.unlockCard(VanishingAct.ID);
        UnlockTracker.unlockCard(WillingVolunteer.ID);
        UnlockTracker.unlockCard(MaximumDistraction.ID);
        UnlockTracker.unlockCard(NothingInMyHands.ID);
        UnlockTracker.unlockCard(ThrowingCards.ID);
        UnlockTracker.unlockCard(Toro.ID);
        UnlockTracker.unlockCard(BlackJack.ID);
        UnlockTracker.unlockCard(OneUp.ID);
        UnlockTracker.unlockCard(StartlingShowmanship.ID);
        UnlockTracker.unlockCard(BafflingShuffle.ID);
        UnlockTracker.unlockCard(Columbify.ID);
        UnlockTracker.unlockCard(Showstopper.ID);
        UnlockTracker.unlockCard(ItsShowtime.ID);
        UnlockTracker.unlockCard(BaitedBreath.ID);
        UnlockTracker.unlockCard(GrandGambit.ID);
        UnlockTracker.unlockCard(ObjectPermanence.ID);
        UnlockTracker.unlockCard(BottomDoubleDeal.ID);
        UnlockTracker.unlockCard(Presto.ID);
        UnlockTracker.unlockCard(SubtleMisdirections.ID);
        UnlockTracker.unlockCard(RaptMisdirection.ID);
        UnlockTracker.unlockCard(ReappearingTrick.ID);
        UnlockTracker.unlockCard(MischiefManaged.ID);
        UnlockTracker.unlockCard(KiblerFlick.ID);
        UnlockTracker.unlockCard(SpeechCard.ID);

        logger.info("Done adding cards!");
    }

    // There are better ways to do this than listing every single individual card, but I do not want to complicate things
    // in a "tutorial" mod. This will do and it's completely ok to use. If you ever want to clean up and
    // shorten all the imports, go look take a look at other mods, such as Hubris.

    // ================ /ADD CARDS/ ===================


    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        logger.info("FEED DEM COOKIES");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/ShowmanMod-Card-Strings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/ShowmanMod-Power-Strings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/ShowmanMod-Relic-Strings.json");

        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/ShowmanMod-Event-Strings.json");

        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/ShowmanMod-Potion-Strings.json");

        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/ShowmanMod-Character-Strings.json");

        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/ShowmanMod-Orb-Strings.json");

        BaseMod.loadCustomStringsFile(UIStrings.class,
                        getModID() + "Resources/localization/eng/ShowmanMod-UI-Strings.json");

        logger.info("Done editing strings");
    }

    // ================ /LOAD THE TEXT/ ===================

    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword

        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/ShowmanMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }

    // ================ /LOAD THE KEYWORDS/ ===================

    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}
