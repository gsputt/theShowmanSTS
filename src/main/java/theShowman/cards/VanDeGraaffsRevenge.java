package theShowman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.ShowmanMod;
import theShowman.actions.VanDeGraaffDazedAction;
import theShowman.effects.VanDeGraaff1;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class VanDeGraaffsRevenge extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("VanDeGraaffsRevenge");
    public static final String IMG = makeCardPath("Attack.png");
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 3;
    private static final int DAMAGE = 50;
    private static final int DAZED_AMOUNT = 5;
    private static final int UPGRADE_DAZED_AMOUNT = -2;
    // /STAT DECLARATION/


    public VanDeGraaffsRevenge() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = DAZED_AMOUNT;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 1; i <= 5; i++) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new VanDeGraaff1(p.hb.cX, p.hb.cY, p.hb.cX + (i * 200F) - 400F, p.hb.cY + 300F, (float)(1.0 + (i * -0.1)), m)));
        }
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new VanDeGraaffDazedAction(this.magicNumber));

        //AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Dazed(), this.magicNumber, true, true));
        //AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Dazed(), this.magicNumber));
        //AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Dazed(), this.magicNumber));
    }


    @Override
    public AbstractDynamicCard makeCopy() {
        return new VanDeGraaffsRevenge();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeMagicNumber(UPGRADE_DAZED_AMOUNT);
            initializeDescription();
        }
    }
}
