package theShowman.cards;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theShowman.ShowmanMod;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;
import static theShowman.patches.PropShowField.PropShowRecording;

public class PropShow extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("PropShow");
    public static final String IMG = makeCardPath("Attack.png");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = -1;
    private static final int DAMAGE = 8;
    private static final int BLOCK = 9;
    private static final int ENERGY = 1;
    // /STAT DECLARATION/


    public PropShow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseBlock = BLOCK;
        this.magicNumber = this.baseMagicNumber = ENERGY;
        this.isMultiDamage = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }
        int energyTotalUsed = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            energyTotalUsed = this.energyOnUse;
        }
        if (p.hasRelic("Chemical X")) {
            energyTotalUsed += 2;
            p.getRelic("Chemical X").flash();
        }
        if(upgraded)
        {
            energyTotalUsed += 1;
        }


        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            public void update() {
                this.isDone = true;
                PropShowRecording.set(p, true);
            }
        });
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, energyTotalUsed));
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            public void update() {
                this.isDone = true;
                PropShowRecording.set(p, false);
            }
        });


        if (!this.freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }
    }

    public static void PropShowDealDamage()
    {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            public void update() {
                AbstractCard c = new PropShow();
                this.isDone = true;
                c.resetAttributes();
                ReflectionHacks.setPrivate(c, AbstractCard.class, "isMultiDamage", true);
                c.applyPowers();
                c.calculateCardDamage(null);
                AbstractDungeon.actionManager.addToBottom(
                        new DamageAllEnemiesAction(AbstractDungeon.player, (int[])ReflectionHacks.getPrivate(c, AbstractCard.class, "multiDamage"), c.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
                ReflectionHacks.setPrivate(c, AbstractCard.class, "isMultiDamage", false);
            }
        });

    }

    public static void PropShowGainBlock()
    {

        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            public void update() {
                AbstractPlayer p = AbstractDungeon.player;
                AbstractCard c = new PropShow();
                this.isDone = true;
                c.resetAttributes();
                c.applyPowers();
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, c.block));
            }
        });

    }

    public static void PropShowGainEnergy()
    {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            public void update() {
                AbstractCard c = new PropShow();
                this.isDone = true;
                c.resetAttributes();
                c.applyPowers();
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(c.magicNumber));
            }
        });

    }

    @Override
    public AbstractDynamicCard makeCopy() {
        return new PropShow();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
