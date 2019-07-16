package theShowman.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theShowman.ShowmanMod;
import theShowman.effects.PrepareSayCardEffect;
import theShowman.effects.SayCardEffect;
import theShowman.effects.TossCardEffect;

import static theShowman.ShowmanMod.makeCardPath;
import static theShowman.characters.TheShowman.Enums.COLOR_PURPLE;

public class IsThisYourCard extends AbstractDynamicCard {


    // TEXT DECLARATION
    public static final String ID = ShowmanMod.makeID("IsThisYourCard");
    public static final String IMG = makeCardPath("Attack.png");
    // /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = COLOR_PURPLE;

    private static final int COST = 2;
    private static final int DAMAGE = 4;
    private static final int UPGRADE_PLUS_DAMAGE = 1;

    private static final int TOTAL_CARD_CHOICE_PICTURE_AMOUNT = 11;
    // /STAT DECLARATION/


    public IsThisYourCard() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int specifiedCard = MathUtils.random(1,TOTAL_CARD_CHOICE_PICTURE_AMOUNT);
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new PrepareSayCardEffect(m, specifiedCard)));
        for(int i = 0; i < AbstractDungeon.player.exhaustPile.size(); i++) {
            if(i == AbstractDungeon.player.exhaustPile.size() - 1)
            {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new TossCardEffect(p.hb.cX, p.hb.cY, m, this.damage, specifiedCard), 0.1F));
            }
            else {
                int notSpecified = specifiedCard;
                while(notSpecified == specifiedCard) {
                    notSpecified = MathUtils.random(1,TOTAL_CARD_CHOICE_PICTURE_AMOUNT);
                }
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new TossCardEffect(p.hb.cX, p.hb.cY, m, this.damage, notSpecified), 0.1F));
            }
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }


    @Override
    public AbstractDynamicCard makeCopy() {
        return new IsThisYourCard();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            initializeDescription();
        }
    }
}
