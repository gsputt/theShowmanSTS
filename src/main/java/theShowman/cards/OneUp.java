package theShowman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theShowman.ShowmanMod;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class OneUp extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("OneUp");
    public static final String IMG = makeCardPath("Attack.png");
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 1;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DAMAGE = 1;
    private static final int CARD_DRAW = 2;
    private static final int UPGRADE_CARD_DRAW = 1;
    // /STAT DECLARATION/


    public OneUp() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = CARD_DRAW;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            if(m.hasPower(VulnerablePower.POWER_ID))
            {
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
            }
        }


        @Override
        public AbstractDynamicCard makeCopy() {
            return new OneUp();
        }

        // Upgraded stats.
        @Override
        public void upgrade() {
            if (!upgraded) {
                upgradeName();
                upgradeDamage(UPGRADE_PLUS_DAMAGE);
                this.upgradeMagicNumber(UPGRADE_CARD_DRAW);
                initializeDescription();
            }
        }
    }
