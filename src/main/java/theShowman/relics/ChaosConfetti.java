package theShowman.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.ShowmanMod;
import theShowman.util.TextureLoader;

import static theShowman.ShowmanMod.makeRelicOutlinePath;
import static theShowman.ShowmanMod.makeRelicPath;

public class ChaosConfetti extends CustomRelic {

    // ID, images, text.
    public static final String ID = ShowmanMod.makeID("ChaosConfetti");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("confetti.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("confettiOutline.png"));

    private static final int AMOUNT = 1;

    public ChaosConfetti() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if(this.counter > 0)
        {
            this.counter--;
            if(this.counter == 0)
            {
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.pulse = false;
                AbstractCard removeCard = StSLib.getMasterDeckEquivalent(card);
                if(removeCard != null)
                {
                    AbstractDungeon.player.masterDeck.removeCard(removeCard);
                }
                card.purgeOnUse = true;
            }
        }
    }

    @Override
    public void atPreBattle() {
        this.flash();
        this.counter = AMOUNT;
        if(!this.pulse) {
            this.beginPulse();
            this.pulse = true;
        }
    }

    @Override
    public void onVictory() {
        this.counter = -1;
        this.pulse = false;
    }

    @Override
    public void onEquip()
    {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public void onUnequip()
    {
        --AbstractDungeon.player.energy.energyMaster;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
