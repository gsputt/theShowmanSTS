package theShowman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theShowman.ShowmanMod;
import theShowman.util.TextureLoader;

import static theShowman.ShowmanMod.makePowerPath;


public class NothingInMyHandsPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = ShowmanMod.makeID("NothingInMyHandsPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    private boolean upgraded;

    public NothingInMyHandsPower(final AbstractCreature owner, final boolean upgraded, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.upgraded = upgraded;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer)
        {
            int powerAmount = this.amount;
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    this.isDone = true;
                    int handSize = AbstractDungeon.player.hand.size();
                    if(upgraded)
                    {
                        AbstractCard dazed = new Dazed();
                        AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
                            @Override
                            public void update() {
                                this.isDone = true;
                                for(int i = 0; i < powerAmount; i++) {
                                    AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
                                        @Override
                                        public void update() {
                                            this.isDone = true;
                                            while (AbstractDungeon.player.hand.size() > 0) {
                                                AbstractDungeon.player.hand.moveToExhaustPile(AbstractDungeon.player.hand.getTopCard());
                                            }
                                        }
                                    });
                                    AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(dazed, handSize));
                                }
                            }
                        });

                    }
                    AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
                        @Override
                        public void update() {
                            this.isDone = true;
                            while (AbstractDungeon.player.hand.size() > 0) {
                                AbstractDungeon.player.hand.moveToExhaustPile(AbstractDungeon.player.hand.getTopCard());
                            }
                        }
                    });
                }
            });

        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
        if(upgraded)
        {
            description += DESCRIPTIONS[1];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new NothingInMyHandsPower(owner, upgraded, amount);
    }
}
